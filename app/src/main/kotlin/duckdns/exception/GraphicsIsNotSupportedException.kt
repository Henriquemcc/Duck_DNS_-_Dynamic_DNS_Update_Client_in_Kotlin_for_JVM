package duckdns.exception

import duckdns.international.resourceBundle

/**
 * Exception thrown when Graphical User Interface is not supported.
 * @param message Exception message.
 */
class GraphicsIsNotSupportedException(message: String = resourceBundle.getString("graphical.user.interface.is.not.supported.on.this.machine").capitalize()) : Exception(message)
