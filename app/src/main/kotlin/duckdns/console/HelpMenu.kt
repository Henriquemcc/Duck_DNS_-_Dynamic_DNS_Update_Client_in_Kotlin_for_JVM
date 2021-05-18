package duckdns.console

import duckdns.international.resourceBundle
import java.io.File
import java.text.MessageFormat

private val headerTitle = resourceBundle.getString("help.menu").capitalize()
private val optionsSubtitle = "${resourceBundle.getString("options").capitalize()}:"
private val gettingHelpTitle = "\t${resourceBundle.getString("getting.help").capitalize()}:"
private val openingConsoleMenuTitle = "\t${resourceBundle.getString("opening.console.menu").capitalize()}:"
private val openingGraphicalUserInterfaceMenuTitle = "\t${resourceBundle.getString("opening.graphical.user.interface.menu").capitalize()}:"
private val runningJustOneTimeTitle = "\t${MessageFormat.format(
    resourceBundle.getString("running.Duck.DNS.IP.Address.Updater.just.one.time").capitalize(),
    resourceBundle.getString("duck.dns.ip.address.updater")
)}:"
private val runningInInfiniteLoopTitle = "\t${MessageFormat.format(
    resourceBundle.getString("running.Duck.DNS.IP.Address.Updater.in.infinite.loop").capitalize(),
    resourceBundle.getString("duck.dns.ip.address.updater")
)}:"

/**
 * Displays Help Menu
 */
fun helpMenu() {
    printHeader(headerTitle)
    val anyObject = object {}
    val jarFIleName = File(anyObject.javaClass.protectionDomain.codeSource.location.path).name
    println("Usage: java -jar $jarFIleName [options]")
    println(optionsSubtitle)
    println(gettingHelpTitle)
    println("\t\tjava -jar $jarFIleName --help\tDisplay this information")
    println(openingConsoleMenuTitle)
    println("\t\tjava -jar $jarFIleName --console")
    println(openingGraphicalUserInterfaceMenuTitle)
    println("\t\tjava -jar $jarFIleName --gui")
    println(runningJustOneTimeTitle)
    println("\t\tjava -jar $jarFIleName --run-once")
    println(runningInInfiniteLoopTitle)
    println("\t\tjava -jar $jarFIleName --infinite-loop [time=<t>]\tRun Duck DNS IP Address Updater in infinite loop with a delay of time in <t> milliseconds")
    println()
}