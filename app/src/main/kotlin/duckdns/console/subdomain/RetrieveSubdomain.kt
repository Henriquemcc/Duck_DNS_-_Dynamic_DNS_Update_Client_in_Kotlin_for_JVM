package duckdns.console.subdomain

import duckdns.console.printHeader
import duckdns.controller.getSubdomains

/**
 * Retrieve and prints all Duck DNS Subdomain Objects.
 */
fun printAllSubdomains() {
    printHeader("Subdomains:")
    getSubdomains().forEach { println(it) }
    println()
}