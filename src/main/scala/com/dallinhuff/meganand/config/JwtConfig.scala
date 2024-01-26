package com.dallinhuff.meganand.config

/** Configuration for JWT generation
  * @param secret
  *   the secret used to generate/sign tokens
  * @param ttl
  *   the token time-to-live before expiring
  */
final case class JwtConfig(secret: String, ttl: Long)
