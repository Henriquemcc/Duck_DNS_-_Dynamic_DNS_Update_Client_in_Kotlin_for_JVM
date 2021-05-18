package duckdns.exception

import duckdns.international.resourceBundle

/**
 * This exception is thrown when no IPv6 address has been found on the machine.
 * @param message Exception message.
 */
class IPv6NotFoundException(message: String = resourceBundle.getString("IPv6.address.has.not.been.found.on.this.machine").capitalize()) :
    Exception(message)