package duckdns.console.subdomain

import duckdns.console.getConfirmation
import duckdns.console.printHeader
import duckdns.console.readInteger
import duckdns.controller.getSubdomains
import duckdns.controller.remove
import duckdns.international.resourceBundle
import duckdns.model.Subdomain
import java.text.MessageFormat

private val headerTitleMainDeletionMenu = resourceBundle.getString("subdomain.deletion.menu").capitalize()
private val headerTitleDeletionByPositionMenu = resourceBundle.getString("subdomain.deletion.by.position.menu").capitalize()
private val headerTitleDeletionByNameMenu = resourceBundle.getString("subdomain.deletion.by.name").capitalize()
private val headerTitleSubdomainDeleteAllMenu = resourceBundle.getString("subdomain.delete.all.menu").capitalize()
private val optionsSubtitle = "${resourceBundle.getString("options").capitalize()}:"
private val exitOption = "0 - ${resourceBundle.getString("exit").capitalize()}"
private val listSubdomainsOption = "1 - ${resourceBundle.getString("list.subdomains").capitalize()}"
private val deleteSubdomainByPositionOption = "2 - ${resourceBundle.getString("delete.subdomain.by.position").capitalize()}"
private val deleteSubdomainByNameOption = "3 - ${resourceBundle.getString("delete.subdomain.by.name").capitalize()}"
private val deleteAllSubdomainsOption = "4 - ${resourceBundle.getString("delete.all.subdomains").capitalize()}"
private val exitingMessage = "${resourceBundle.getString("exiting").capitalize()}..."
private val deletionConfirmationMessageSingleSubdomainObject = "${MessageFormat.format(
    resourceBundle.getString("are.you.sure.you.want.to.delete.this.Duck.DNS.subdomain.object").capitalize(),
    resourceBundle.getString("duck.dns")
)}:"
private val deletionConfirmationMessageAllSubdomainsObjects = "${MessageFormat.format(
    resourceBundle.getString("are.you.sure.you.want.to.delete.all.Duck.DNS.subdomains").capitalize(),
    resourceBundle.getString("duck.dns")
)}?"
private val yesOption = resourceBundle.getString("y.yes").capitalize()
private val noOption = resourceBundle.getString("n.no").capitalize()

/**
 * Menu that helps console user to delete a Duck DNS Subdomain Object.
 */
fun deleteSubdomain() {

    var command: Int? = null

    val instructionMessage = StringBuilder()
    instructionMessage.appendLine(optionsSubtitle)
    instructionMessage.appendLine(exitOption)
    instructionMessage.appendLine(listSubdomainsOption)
    instructionMessage.appendLine(deleteSubdomainByPositionOption)
    instructionMessage.appendLine(deleteSubdomainByNameOption)
    instructionMessage.appendLine(deleteAllSubdomainsOption)
    instructionMessage.append("> ")

    while (command != 0) {
        printHeader(headerTitleMainDeletionMenu)
        command = readInteger(instructionMessage.toString(), 0..4)
        println()

        when (command) {
            0 -> println(exitingMessage)
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

    printHeader(headerTitleDeletionByPositionMenu)
    val position = getSubDomainPosition()
    val subdomain = getSubdomains()[position]
    delete(subdomain)


}

/**
 * Menu that asks console user whether they are certain to delete a Duck DNS Subdomain object.
 * @param subdomain Duck DNS Subdomain object to be removed.
 */
private fun delete(subdomain: Subdomain) {
    val deleteConfirmationMessage = StringBuilder()
    deleteConfirmationMessage.appendLine(deletionConfirmationMessageSingleSubdomainObject)
    deleteConfirmationMessage.appendLine(subdomain.toString())
    deleteConfirmationMessage.appendLine(" ?")
    deleteConfirmationMessage.appendLine(yesOption)
    deleteConfirmationMessage.appendLine(noOption)
    deleteConfirmationMessage.append("> ")

    val confirmation = getConfirmation(deleteConfirmationMessage.toString())
    println()

    if (confirmation)
        remove(subdomain)
}

/**
 * Menu that helps console user to delete a Duck DNS Subdomain Object by it's domain name.
 */
private fun deleteByName() {

    printHeader(headerTitleDeletionByNameMenu)
    val subdomainName = getSubDomainName()
    val subDomain = getSubdomains().find {
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

    printHeader(headerTitleSubdomainDeleteAllMenu)
    val deleteInstructionMessage = StringBuilder()
    deleteInstructionMessage.appendLine(deletionConfirmationMessageAllSubdomainsObjects)
    deleteInstructionMessage.appendLine(yesOption)
    deleteInstructionMessage.appendLine(noOption)
    deleteInstructionMessage.append("> ")
    val confirmation = getConfirmation(deleteInstructionMessage.toString())
    println()

    if (confirmation)
        getSubdomains().clear()
}