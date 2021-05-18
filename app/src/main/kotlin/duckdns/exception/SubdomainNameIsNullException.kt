package duckdns.exception

import duckdns.international.resourceBundle
import java.util.*

class SubdomainNameIsNullException(message: String = resourceBundle.getString("subdomain.name.is.null").capitalize()): InputMismatchException(message)