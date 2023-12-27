package com.dallinhuff.meganand
package service

import http.request.CreateAddressRequest
import model.Address
import repository.AddressRepository

import zio.*

trait AddressService:
  def create(req: CreateAddressRequest): Task[Address]
  def getById(id: Long): Task[Option[Address]]
  def getAll: Task[List[Address]]
  def delete(id: Long): Task[Address]

class AddressServiceLive private (repo: AddressRepository) extends AddressService:
  override def create(req: CreateAddressRequest): Task[Address] =
    repo.create(req.toAddress())
  override def getById(id: Long): Task[Option[Address]] =
    repo.getById(id)
  override def getAll: Task[List[Address]] =
    repo.getAll
  override def delete(id: Long): Task[Address] =
    repo.delete(id)

object AddressServiceLive:
  val layer: ZLayer[AddressRepository, Nothing, AddressServiceLive] =
    ZLayer:
      ZIO.service[AddressRepository].map(new AddressServiceLive(_))
