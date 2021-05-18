package duckdns.console.subdomain

import duckdns.console.printHeader
import duckdns.controller.getSubdomains
import duckdns.international.resourceBundle

private val headerTitle = "${resourceBundle.getString("subdomains").capitalize()}:"

/**
 * Retrieve and prints all Duck DNS Subdomain Objects.
 */
fun printAllSubdomains() {
    printHeader(headerTitle)
    getSubdomains().forEach { println(it) }
    println()
}