package duckdns.console

import duckdns.console.subdomain.subdomainMenu
import duckdns.controller.updateIPAddress
import duckdns.international.resourceBundle
import java.text.MessageFormat

private val headerTitleProgram = resourceBundle.getString("duck.dns.ip.address.updater").capitalize()
private val optionsSubtitle = "${resourceBundle.getString("options").capitalize()}:"
private val exitOption = "0 - ${resourceBundle.getString("exit").capitalize()}"
private val subdomainMenuOption = "1 - ${resourceBundle.getString("subdomain.menu").capitalize()}"
private val runningJustOneTimeOption = "2 - ${
    MessageFormat.format(
        resourceBundle.getString("run.Duck.DNS.IP.Address.Updater.just.one.time").capitalize(),
        resourceBundle.getString("duck.dns.ip.address.updater")
    )
}"
private val runningInInfiniteLoopOption = "3 - ${
    MessageFormat.format(
        resourceBundle.getString("run.Duck.DNS.IP.Address.Updater.in.infinite.loop").capitalize(),
        resourceBundle.getString("duck.dns.ip.address.updater")
    )
}"
private val headerTitleMainMenu = resourceBundle.getString("main.menu").capitalize()
private val exitingMessage = "${resourceBundle.getString("exiting").capitalize()}..."

/**
 * Menu that helps console user to use this program.
 */
fun console() {
    printHeader(headerTitleProgram)

    var command: Int? = null
    val instructionMessage = StringBuilder()
    instructionMessage.appendLine(optionsSubtitle)
    instructionMessage.appendLine(exitOption)
    instructionMessage.appendLine(subdomainMenuOption)
    instructionMessage.appendLine(runningJustOneTimeOption)
    instructionMessage.appendLine(runningInInfiniteLoopOption)
    instructionMessage.append("> ")

    while (command != 0) {
        printHeader(headerTitleMainMenu)
        command = readInteger(instructionMessage.toString(), 0..3)
        println()

        when (command) {
            0 -> println(exitingMessage)
            1 -> subdomainMenu()
            2 -> updateIPAddress()
            3 -> updateIPAddress(true)
        }
    }
}