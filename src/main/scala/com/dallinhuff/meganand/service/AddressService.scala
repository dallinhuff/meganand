package com.dallinhuff.meganand.service

import com.dallinhuff.meganand.model.data.Address
import com.dallinhuff.meganand.model.request.CreateAddressRequest
import com.dallinhuff.meganand.repository.AddressRepository
import zio.*

/** Service/business layer for Address */
trait AddressService:
  /** create a new address
    * @param req
    *   the request containing necessary address info
    * @return
    *   the created address
    */
  def create(req: CreateAddressRequest): Task[Address]

  /** get an existing address by id
    * @param id
    *   the id to lookup with
    * @return
    *   an option of the address when it exists
    */
  def read(id: Long): Task[Option[Address]]

  /** get all addresses
    * @return
    *   all addresses found
    */
  def readAll: Task[List[Address]]

  /** update an existing address
    * @param id
    *   the id of the existing address
    * @param fn
    *   how to update the address
    * @return
    *   the updated address
    */
  def update(id: Long, fn: Address => Address): Task[Address]

  /** delete an existing address
    * @param id
    *   the id of the existing address
    * @return
    *   the deleted address
    */
  def delete(id: Long): Task[Address]

/** Implementation of AddressService that depends on AddressRepository */
final class AddressServiceLive private (
    repo: AddressRepository
) extends AddressService:
  override def create(req: CreateAddressRequest): Task[Address] =
    repo.create(
      Address(
        -1L,
        req.firstName,
        req.lastName,
        req.streetAddress,
        req.apartment,
        req.city,
        req.state,
        req.zipCode,
        req.country
      )
    )

  override def read(id: Long): Task[Option[Address]] = repo.read(id)

  override def readAll: Task[List[Address]] = repo.readAll

  override def update(id: Long, fn: Address => Address): Task[Address] =
    repo.update(id, fn)

  override def delete(id: Long): Task[Address] = repo.delete(id)
end AddressServiceLive

object AddressServiceLive:
  val layer: ZLayer[AddressRepository, Nothing, AddressServiceLive] = ZLayer:
    ZIO.service[AddressRepository].map(AddressServiceLive(_))
