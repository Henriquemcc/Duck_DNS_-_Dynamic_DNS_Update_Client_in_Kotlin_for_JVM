package duckdns.console.subdomain

import duckdns.console.printHeader
import duckdns.console.readInteger
import duckdns.console.readString
import duckdns.controller.create
import duckdns.exception.ObjectAlreadyExistsException
import duckdns.exception.SubdomainNameIsNullException
import duckdns.exception.TokenIsNullException
import duckdns.international.resourceBundle
import duckdns.model.Subdomain
import java.util.*

private val headerTitle = resourceBundle.getString("subdomain.creation.menu").capitalize()
private val optionsSubtitle = "${resourceBundle.getString("options").capitalize()}:"
private val exitOption = "0 - ${resourceBundle.getString("exit").capitalize()}"
private val saveAndExitOption = "1 - ${resourceBundle.getString("save.and.exit").capitalize()}"
private val subdomainNameOption = "2 - ${resourceBundle.getString("subdomain.name").capitalize()} = "
private val tokenOption = "3 - ${resourceBundle.getString("token").capitalize()} = "
private val enableIPv4Option = "4 - ${resourceBundle.getString("enable.IPv4").capitalize()} = "
private val enableIPv6Option = "5 - ${resourceBundle.getString("enable.IPv6").capitalize()} = "
private val exitingMessage = "${resourceBundle.getString("exiting").capitalize()}..."
private val savingAndExitingMessage = "${resourceBundle.getString("saving.and.exiting").capitalize()}..."
private val subdomainNameMessage = "${resourceBundle.getString("subdomain.name").capitalize()}: "
private val tokenMessage = "${resourceBundle.getString("token").capitalize()}: "


/**
 * Menu that helps the console user to create a Duck DNS Subdomain Object.
 */
fun createSubdomain() {

    var command: Int? = null

    val duckDNSNewSubdomainData = object {
        var subdomainName: String? = null
        var token: String? = null
        var enableIPv4 = true
        var enableIPv6 = true
    }

    while (command != 0) {
        printHeader(headerTitle)
        val instructionMessage = StringBuilder()
        instructionMessage.appendLine(optionsSubtitle)
        instructionMessage.appendLine(exitOption)
        instructionMessage.appendLine(saveAndExitOption)

        instructionMessage.append(subdomainNameOption)
        if (duckDNSNewSubdomainData.subdomainName == null) instructionMessage.appendLine(
            "${duckDNSNewSubdomainData.subdomainName}"
        )
        else instructionMessage.appendLine("\"${duckDNSNewSubdomainData.subdomainName}\"")

        instructionMessage.append(tokenOption)
        if (duckDNSNewSubdomainData.token == null) instructionMessage.appendLine("${duckDNSNewSubdomainData.token}")
        else instructionMessage.appendLine("\"${duckDNSNewSubdomainData.token}\"")

        instructionMessage.appendLine("${enableIPv4Option}${duckDNSNewSubdomainData.enableIPv4}")
        instructionMessage.appendLine("${enableIPv6Option}${duckDNSNewSubdomainData.enableIPv6}")
        instructionMessage.append("> ")
        command = readInteger(instructionMessage.toString(), 0..5)
        println()

        when (command) {
            0 -> println(exitingMessage)
            1 -> try {
                if (duckDNSNewSubdomainData.subdomainName == null)
                    throw SubdomainNameIsNullException()
                if (duckDNSNewSubdomainData.token == null)
                    throw TokenIsNullException()

                println(savingAndExitingMessage)
                create(
                    Subdomain(
                        duckDNSNewSubdomainData.subdomainName ?: return,
                        duckDNSNewSubdomainData.enableIPv4,
                        duckDNSNewSubdomainData.enableIPv6,
                        duckDNSNewSubdomainData.token ?: return
                    )
                )

                command = 0
            } catch (e: InputMismatchException) {
                e.printStackTrace()
            } catch (e: ObjectAlreadyExistsException) {
                e.printStackTrace()
            }
            2 -> duckDNSNewSubdomainData.subdomainName =
                readString(subdomainNameMessage).toLowerCase()
            3 -> duckDNSNewSubdomainData.token = readString(tokenMessage)
            4 -> duckDNSNewSubdomainData.enableIPv4 =
                !duckDNSNewSubdomainData.enableIPv4
            5 -> duckDNSNewSubdomainData.enableIPv6 =
                !duckDNSNewSubdomainData.enableIPv6
        }
    }
}