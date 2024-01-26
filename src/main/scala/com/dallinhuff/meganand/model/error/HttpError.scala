package com.dallinhuff.meganand.model.error

import sttp.model.StatusCode

/** An error that ocurred while servicing an http request
  * @param statusCode
  *   the http status code corresponding with the error
  * @param message
  *   the error sent back in the response
  * @param cause
  *   the original error that occurred in the application
  */
final case class HttpError(
    statusCode: StatusCode,
    message: String,
    cause: Throwable
) extends RuntimeException(message, cause)

object HttpError:
  def decode(tuple: (StatusCode, String)): HttpError =
    tuple match
      case (code @ StatusCode.Unauthorized, msg) =>
        HttpError(code, msg, UnauthorizedError(msg))
      case (code @ StatusCode.NotFound, msg) =>
        HttpError(code, msg, NotFoundError(msg))
      case (code, msg) =>
        HttpError(code, msg, ApplicationError(msg))

  def encode(err: Throwable): (StatusCode, String) =
    err match
      case UnauthorizedError(msg) => (StatusCode.Unauthorized, msg)
      case NotFoundError(msg)     => (StatusCode.NotFound, msg)
      case _ =>
        (StatusCode.InternalServerError, err.getMessage)
