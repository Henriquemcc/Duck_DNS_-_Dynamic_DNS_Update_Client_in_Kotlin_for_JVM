package console.subdomain

import model.Subdomain

/**
 * Menu that helps console user to delete a Duck DNS Subdomain Object.
 */
fun deleteSubdomain() {

    var command: Int? = null

    val instructionMessage = StringBuilder()
    instructionMessage.appendLine("Options:")
    instructionMessage.appendLine("0 - Exit")
    instructionMessage.appendLine("1 - List Subdomains")
    instructionMessage.appendLine("2 - Select Subdomain by it's position and delete it")
    instructionMessage.appendLine("3 - Select Subdomain by it's name and delete it")
    instructionMessage.appendLine("4 - Delete all Subdomains")
    instructionMessage.append("> ")

    while (command != 0) {
        console.printHeader("Subdomain deletion menu")
        command = console.readInteger(instructionMessage.toString(), 0..4)
        println()

        when (command) {
            0 -> println("Exiting...")
            1 -> printAllSubdomains()
            2 -> deleteByPosition()
            3 -> deleteByName()
            4 -> deleteAll()
        }
    }
}

/**
 * Menu that helps console user to delete a Duck DNS Subdomain Object by it's position.
 */
private fun deleteByPosition() {

    console.printHeader("Subdomain delete by position menu")
    val position = getSubDomainPosition()
    val subdomain = controller.getSubdomains()[position]
    delete(subdomain)


}

/**
 * Menu that asks console user whether they are certain to delete a Duck DNS Subdomain object.
 * @param subdomain Duck DNS Subdomain object to be removed.
 */
private fun delete(subdomain: Subdomain) {
    val deleteConfirmationMessage = StringBuilder()
    deleteConfirmationMessage.appendLine("Are you sure you want to delete this Duck DNS Subdomain object:")
    deleteConfirmationMessage.appendLine(subdomain.toString())
    deleteConfirmationMessage.appendLine(" ?")
    deleteConfirmationMessage.appendLine("Y - Yes")
    deleteConfirmationMessage.appendLine("N - No")
    deleteConfirmationMessage.append("> ")

    val confirmation = console.getConfirmation(deleteConfirmationMessage.toString())
    println()

    if (confirmation)
        controller.remove(subdomain)
}

/**
 * Menu that helps console user to delete a Duck DNS Subdomain Object by it's domain name.
 */
private fun deleteByName() {

    console.printHeader("Subdomain delete by name menu")
    val subdomainName = getSubDomainName()
    val subDomain = controller.getSubdomains().find {
        it.subdomainName.equals(
            subdomainName,
            ignoreCase = true
        )
    }
    subDomain?.let { delete(it) }
}

/**
 * Menu that asks console user whether they are certain to delete all Duck DNS Subdomain objects.
 */
private fun deleteAll() {

    console.printHeader("Subdomain delete all menu")
    val deleteInstructionMessage = StringBuilder()
    deleteInstructionMessage.appendLine("Are you sure you want to delete all Duck DNS Subdomains?")
    deleteInstructionMessage.appendLine("Y - Yes")
    deleteInstructionMessage.appendLine("N - No")
    deleteInstructionMessage.append("> ")
    val confirmation = console.getConfirmation(deleteInstructionMessage.toString())
    println()

    if (confirmation)
        controller.getSubdomains().clear()
}