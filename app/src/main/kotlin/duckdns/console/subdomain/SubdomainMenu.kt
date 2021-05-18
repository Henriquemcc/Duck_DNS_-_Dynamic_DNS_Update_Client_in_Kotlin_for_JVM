package duckdns.console.subdomain

import duckdns.console.printHeader
import duckdns.console.readInteger
import duckdns.international.resourceBundle

private val headerTitle = resourceBundle.getString("subdomain.menu").capitalize()
private val optionsSubtitle = "${resourceBundle.getString("options").capitalize()}:"
private val exitOption = "0 - ${resourceBundle.getString("exit").capitalize()}"
private val addSubdomainOption = "1 - ${resourceBundle.getString("add.a.subdomain").capitalize()}"
private val retrieveSubdomainOption = "2 - ${resourceBundle.getString("retrieve.a.subdomain").capitalize()}"
private val updateSubdomainOption = "3 - ${resourceBundle.getString("update.a.subdomain").capitalize()}"
private val deleteSubdomainOption = "4 - ${resourceBundle.getString("delete.a.subdomain").capitalize()}"
private val exitingMessage = "${resourceBundle.getString("exiting").capitalize()}..."

/**
 * Menu that helps console users to choose what they want to do with subdomains objects.
 */
fun subdomainMenu() {

    var command: Int? = null
    val instructionMessage = StringBuilder()
    instructionMessage.appendLine(optionsSubtitle)
    instructionMessage.appendLine(exitOption)
    instructionMessage.appendLine(addSubdomainOption)
    instructionMessage.appendLine(retrieveSubdomainOption)
    instructionMessage.appendLine(updateSubdomainOption)
    instructionMessage.appendLine(deleteSubdomainOption)
    instructionMessage.append("> ")

    while (command != 0) {
        printHeader(headerTitle)
        command = readInteger(instructionMessage.toString(), 0..4)
        println()

        when (command) {
            0 -> println(exitingMessage)
            1 -> createSubdomain()
            2 -> printAllSubdomains()
            3 -> updateSubdomain()
            4 -> deleteSubdomain()
        }
    }
}

