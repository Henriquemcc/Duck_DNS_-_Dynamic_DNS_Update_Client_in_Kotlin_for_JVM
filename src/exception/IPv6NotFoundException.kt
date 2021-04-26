package exception

/**
 * This exception is thrown when no IPv6 address has been found on the machine
 */
class IPv6NotFoundException(message: String = "IPv6 address has not been found on the machine") : Exception(message)