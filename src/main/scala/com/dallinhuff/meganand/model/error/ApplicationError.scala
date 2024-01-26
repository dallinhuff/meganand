package com.dallinhuff.meganand.model.error

/** Error/exception that can occur in this application, usually in a ZIO error
  * channel or surfaced out as an HttpError
  * @param msg
  *   the message indicating why this error occurred
  */
sealed class ApplicationError(msg: String) extends Throwable(msg)

/** Application error that occurs when a client attempts to make an unauthorized
  * request
  * @param msg
  *   the message indicating why this error occurred
  */
final case class UnauthorizedError(msg: String) extends ApplicationError(msg)

/** Application error that occurs when a client attempts to access a resource
  * that doesn't exist
  * @param msg
  *   the message indicating why this error occurred
  */
final case class NotFoundError(msg: String) extends ApplicationError(msg)
