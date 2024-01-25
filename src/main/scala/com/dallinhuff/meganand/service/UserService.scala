package com.dallinhuff.meganand.service

import com.dallinhuff.meganand.model.data.{User, UserToken}
import com.dallinhuff.meganand.model.error.UnauthorizedError
import com.dallinhuff.meganand.model.request.CreateUserRequest
import com.dallinhuff.meganand.repository.UserRepository
import zio.*

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

trait UserService:
  def register(req: CreateUserRequest): Task[User]
  def login(email: String, password: String): Task[UserToken]

class UserServiceLive private (
    userRepo: UserRepository,
    jwtService: JwtService
) extends UserService:
  override def register(req: CreateUserRequest): Task[User] =
    userRepo.create:
      User(-1L, req.email, UserServiceLive.Hasher.generateHash(req.password))

  override def login(email: String, password: String): Task[UserToken] =
    for
      user  <- verifyUser(email, password)
      token <- jwtService.createToken(user)
    yield token

  private def verifyUser(email: String, password: String): Task[User] =
    for
      user <- userRepo
        .readByEmail(email)
        .someOrFail(UnauthorizedError("Invalid email or password."))
      verified <- ZIO.attempt:
        UserServiceLive.Hasher.validateHash(password, user.hashedPassword)
      verifiedUser <- ZIO
        .attempt(user)
        .when(verified)
        .someOrFail(UnauthorizedError("Invalid email or password."))
    yield verifiedUser
end UserServiceLive

object UserServiceLive:
  val layer = ZLayer:
    for
      userRepo   <- ZIO.service[UserRepository]
      jwtService <- ZIO.service[JwtService]
    yield UserServiceLive(userRepo, jwtService)

  private object Hasher:
    def generateHash(str: String): String =
      val rng  = new SecureRandom()
      val salt = Array.ofDim[Byte](SALT_BYTE_SIZE)
      rng.nextBytes(salt)
      val hashBytes =
        pbkdf2(str.toCharArray, salt, N_ITERATIONS, HASH_BYTE_SIZE)
      s"$N_ITERATIONS:${toHex(salt)}:${toHex(hashBytes)}"

    def validateHash(test: String, hash: String): Boolean =
      val hashSections = hash.split(":")
      val nIters       = hashSections(0).toInt
      val salt         = fromHex(hashSections(1))
      val validHash    = fromHex(hashSections(2))
      val testHash     = pbkdf2(test.toCharArray, salt, nIters, HASH_BYTE_SIZE)
      compareBytes(testHash, validHash)

    private val PBKDF2_ALGORITHM: String = "PBKDF2WithHmacSHA512"
    private val N_ITERATIONS: Int        = 1000
    private val SALT_BYTE_SIZE: Int      = 24
    private val HASH_BYTE_SIZE: Int      = 24
    private val skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM)

    private def pbkdf2(
        msg: Array[Char],
        salt: Array[Byte],
        i: Int,
        nBytes: Int
    ): Array[Byte] =
      val keySpec = PBEKeySpec(msg, salt, i, nBytes * 8)
      skf.generateSecret(keySpec).getEncoded

    private def toHex(arr: Array[Byte]): String =
      arr.map("%02X".format(_)).mkString

    private def fromHex(str: String): Array[Byte] =
      str.grouped(2).toArray.map(Integer.parseInt(_, 16).toByte)

    private def compareBytes(a: Array[Byte], b: Array[Byte]): Boolean =
      val diff =
        (0 until (a.length min b.length))
          .foldLeft(a.length ^ b.length): (acc, i) =>
            acc | (a(i) ^ b(i))
      diff == 0
  end Hasher
