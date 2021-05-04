package exception

/**
 * This exception is thrown when no IPv6 address has been found on the machine.
 * @param message Exception message.
 */
class IPv6NotFoundException(message: String = "IPv6 address has not been found on the machine") :
    Exception(message)