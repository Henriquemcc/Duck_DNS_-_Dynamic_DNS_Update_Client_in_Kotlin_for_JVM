package duckdns.exception

import duckdns.international.resourceBundle
import java.util.*

class TokenIsNullException(message: String = resourceBundle.getString("token.is.null").capitalize()): InputMismatchException(message)