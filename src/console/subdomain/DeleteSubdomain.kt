package console.subdomain

import console.getConfirmation
import console.printHeader
import console.readInteger
import model.Subdomain
import serializableSubdomainController

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
        printHeader("Subdomain deletion menu")
        command = readInteger(instructionMessage.toString(), 0..4)
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

    printHeader("Subdomain delete by position menu")
    val position = getSubDomainPosition()
    val subdomain = serializableSubdomainController?.subdomains?.get(position)
    subdomain?.let { delete(it) }

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

    val confirmation = getConfirmation(deleteConfirmationMessage.toString())
    println()

    if (confirmation)
        serializableSubdomainController?.subdomains?.remove(subdomain)
}

/**
 * Menu that helps console user to delete a Duck DNS Subdomain Object by it's domain name.
 */
private fun deleteByName() {

    printHeader("Subdomain delete by name menu")
    val subdomainName = getSubDomainName()
    val subDomain = serializableSubdomainController?.subdomains?.find { it.subdomainName.equals(subdomainName, ignoreCase = true) }
    subDomain?.let { delete(it) }
}

/**
 * Menu that asks console user whether they are certain to delete all Duck DNS Subdomain objects.
 */
private fun deleteAll() {

    printHeader("Subdomain delete all menu")
    val deleteInstructionMessage = StringBuilder()
    deleteInstructionMessage.appendLine("Are you sure you want to delete all Duck DNS Subdomains?")
    deleteInstructionMessage.appendLine("Y - Yes")
    deleteInstructionMessage.appendLine("N - No")
    deleteInstructionMessage.append("> ")
    val confirmation = getConfirmation(deleteInstructionMessage.toString())
    println()

    if (confirmation)
        serializableSubdomainController?.subdomains?.clear()
}