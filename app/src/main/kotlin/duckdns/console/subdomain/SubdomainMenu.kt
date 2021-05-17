package duckdns.console.subdomain

import duckdns.console.printHeader
import duckdns.console.readInteger

/**
 * Menu that helps console users to choose what they want to do with subdomains objects.
 */
fun subdomainMenu() {

    var command: Int? = null
    val instructionMessage = StringBuilder()
    instructionMessage.appendLine("Options:")
    instructionMessage.appendLine("0 - Exit")
    instructionMessage.appendLine("1 - Add a subdomain")
    instructionMessage.appendLine("2 - Retrieve a subdomain")
    instructionMessage.appendLine("3 - Update a subdomain")
    instructionMessage.appendLine("4 - Delete a subdomain")
    instructionMessage.append("> ")

    while (command != 0) {
        printHeader("Subdomain Menu")
        command = readInteger(instructionMessage.toString(), 0..4)
        println()

        when (command) {
            0 -> println("Exiting...")
            1 -> createSubdomain()
            2 -> printAllSubdomains()
            3 -> updateSubdomain()
            4 -> deleteSubdomain()
        }
    }
}

