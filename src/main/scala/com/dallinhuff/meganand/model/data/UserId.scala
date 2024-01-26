package com.dallinhuff.meganand.model.data

import zio.json.JsonCodec

/** A client-friendly user identifier (excludes hashed password)
  * @param id
  *   the id of the user identified
  * @param email
  *   the email of the user identified
  */
final case class UserId(id: Long, email: String) derives JsonCodec
