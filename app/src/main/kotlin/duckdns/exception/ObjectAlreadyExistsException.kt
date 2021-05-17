package duckdns.exception

/**
 * Exception that occurs when an object already exists in an array.
 * @param message Exception message.
 */
class ObjectAlreadyExistsException(message: String = "Object already exists") :
    Exception(message)