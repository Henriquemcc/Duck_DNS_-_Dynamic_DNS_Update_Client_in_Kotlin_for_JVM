package console.subdomain

import console.printHeader
import console.readInteger
import serializableSubdomainController

/**
 * Menu that helps console user to retrieve and print a Duck DNS Subdomain Object.
 */
fun retrieveSubdomain() {
    var command: Int? = null
    val instructionMessage = StringBuilder()
    instructionMessage.appendLine("Options:")
    instructionMessage.appendLine("0 - Exit")
    instructionMessage.appendLine("1 - Retrieve and print all Subdomains")
    instructionMessage.appendLine("2 - Retrieve and print a specific Subdomain")
    instructionMessage.append("> ")

    while (command != 0) {
        printHeader("Subdomain retrieval menu")
        command = readInteger(instructionMessage.toString(), 0..2)
        println()

        when (command) {
            0 -> println("Exiting...")
            1 -> printAllSubdomains()
            2 -> TODO("Not yet implemented")
        }
    }
}

/**
 * Retrieve and prints all Duck DNS Subdomain Objects.
 */
fun printAllSubdomains() {
    printHeader("Subdomains:")
    serializableSubdomainController?.subdomains?.forEach { println(it) }
    println()
}