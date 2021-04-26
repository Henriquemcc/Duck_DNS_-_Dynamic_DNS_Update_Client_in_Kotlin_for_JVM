package console

import console.subdomain.subdomainMenu
import serializableSubdomainController

/**
 * Menu that helps console user to use this program.
 */
fun console() {
    printHeader("Duck DNS IP Address Updater")

    var command: Int? = null
    val instructionMessage = StringBuilder()
    instructionMessage.appendLine("Options:")
    instructionMessage.appendLine("0 - Exit")
    instructionMessage.appendLine("1 - Subdomain menu")
    instructionMessage.appendLine("2 - Run Duck DNS IP Address Updater")
    instructionMessage.append("> ")

    while (command != 0) {
        printHeader("Main Menu")
        command = readInteger(instructionMessage.toString(), 0..2)
        println()

        when (command) {
            0 -> println("Exiting...")
            1 -> subdomainMenu()
            2 -> serializableSubdomainController?.run()
        }
    }
    serializableSubdomainController?.saveSubdomains()
}

/**
 * Menu that setups for the first time Duck DNS Subdomains
 */
fun quickSetupConsole() {

    var command: Int? = null
    val instructionMessage = StringBuilder()
    instructionMessage.appendLine("Options:")
    instructionMessage.appendLine("0 - Exit")
    instructionMessage.appendLine("1 - Create subdomains")
    instructionMessage.appendLine("2 - Test Duck DNS IP Address Updater")
    instructionMessage.appendLine("3 - Go to Main Menu")
    instructionMessage.append("> ")

    while (command != 0) {
        printHeader("Quick Setup Menu")
        command = readInteger(instructionMessage.toString(), 0..3)
        println()

        when (command) {
            0 -> println("Exiting...")
            1 -> subdomainMenu()
            2 -> serializableSubdomainController?.run()
            3 -> console()
        }
    }
    subdomainMenu()
}