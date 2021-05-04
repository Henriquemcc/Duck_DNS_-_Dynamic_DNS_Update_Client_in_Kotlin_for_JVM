package exception

/**
 * Exception thrown when Graphical User Interface is not supported.
 * @param message Exception message.
 */
class GraphicsIsNotSupportedException(message: String = "Graphical User Interface is not supported on this machine") : Exception(message)
