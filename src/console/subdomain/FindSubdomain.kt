package console.subdomain


/**
 * Get subdomain name, checking if it's subdomain name is in the subdomains array.
 * @param instructionMessage Instruction message to print.
 */
fun getSubDomainName(instructionMessage: String? = "Subdomain name:\n> "): String {
    var subdomainName: String
    var searchAgain = false

    do {
        subdomainName = console.readString(instructionMessage)
        val subdomainFounded = controller.getSubdomains().any { it.subdomainName.equals(subdomainName, ignoreCase = true) }

        if (!subdomainFounded) {
            val subdomainNotFoundedInstructionMessage = StringBuilder()
            subdomainNotFoundedInstructionMessage.appendLine("Subdomain $subdomainName not founded")
            subdomainNotFoundedInstructionMessage.appendLine("Would you like to search again?")
            subdomainNotFoundedInstructionMessage.appendLine("Y - Yes")
            subdomainNotFoundedInstructionMessage.appendLine("N - No")
            subdomainNotFoundedInstructionMessage.append("> ")
            searchAgain = console.getConfirmation(subdomainNotFoundedInstructionMessage.toString())
            subdomainName = ""
        }
    } while (searchAgain)

    return subdomainName
}

/**
 * Get position, checking if it's position is in range.
 * @param instructionMessage Instruction message to print.
 * @return Valid integer number.
 */
fun getSubDomainPosition(instructionMessage: String? = "Subdomain position [0, ${controller.getSubdomains().size - 1}]:\n> "): Int {
    return console.readInteger(instructionMessage.toString(), IntRange(0, controller.getSubdomains().size))
}