package duckdns.exception

import duckdns.international.resourceBundle

/**
 * Exception that occurs when an object already exists in an array.
 * @param message Exception message.
 */
class ObjectAlreadyExistsException(message: String = resourceBundle.getString("object.already.exists").capitalize()) :
    Exception(message)