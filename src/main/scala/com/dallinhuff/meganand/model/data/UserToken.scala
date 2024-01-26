package com.dallinhuff.meganand.model.data

import zio.json.JsonCodec

/** JWT generated for a user
  * @param email
  *   the email of the user this token is valid for
  * @param token
  *   the actual token
  * @param expires
  *   when the token expires
  */
final case class UserToken(
    email: String,
    token: String,
    expires: Long
) derives JsonCodec
