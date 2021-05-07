package console

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
    instructionMessage.appendLine("2 - Run Duck DNS IP Address Updater once")
    instructionMessage.appendLine("3 - Run Duck DNS IP Address Updater in infinite loop")
    instructionMessage.append("> ")

    while (command != 0) {
        printHeader("Main Menu")
        command = readInteger(instructionMessage.toString(), 0..3)
        println()

        when (command) {
            0 -> println("Exiting...")
            1 -> console.subdomain.subdomainMenu()
            2 -> controller.updateIPAddress()
            3 -> controller.updateIPAddress(true)
        }
    }
}