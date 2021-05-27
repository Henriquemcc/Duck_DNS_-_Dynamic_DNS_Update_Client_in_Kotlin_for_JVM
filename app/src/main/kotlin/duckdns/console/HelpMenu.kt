package duckdns.console

import duckdns.international.resourceBundle
import java.io.File
import java.text.MessageFormat

private val headerTitle = resourceBundle.getString("help.menu").capitalize()
private val optionsSubtitle = "${resourceBundle.getString("options").capitalize()}:"
private val gettingHelpTitle = "\t${resourceBundle.getString("getting.help").capitalize()}:"
private val openingConsoleMenuTitle = "\t${resourceBundle.getString("opening.console.menu").capitalize()}:"
private val openingGraphicalUserInterfaceMenuTitle =
    "\t${resourceBundle.getString("opening.graphical.user.interface.menu").capitalize()}:"
private val updatingSubdomainIPAddressesTitle =
    "\t${resourceBundle.getString("updating.subdomain.s.IP.addresses").capitalize()}:"
private val updatingSubdomainIPAddressesInInfiniteLoopTitle =
    "\t${resourceBundle.getString("updating.subdomain.s.IP.addresses.in.infinite.looping").capitalize()}:"
private val cleaningSubdomainsIPAddressTitle =
    "\t${resourceBundle.getString("cleaning.subdomain.s.IP.addresses").capitalize()}:"


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
    println("\t\tjava -jar $jarFIleName --help\t${resourceBundle.getString("display.this.information").capitalize()}")
    println(openingConsoleMenuTitle)
    println(
        "\t\tjava -jar $jarFIleName --console\t${
            resourceBundle.getString("run.without.graphical.user.interface").capitalize()
        }"
    )
    println(openingGraphicalUserInterfaceMenuTitle)
    println(
        "\t\tjava -jar $jarFIleName --gui\t${
            resourceBundle.getString("run.with.graphical.user.interface").capitalize()
        }"
    )
    println(updatingSubdomainIPAddressesTitle)
    println(
        "\t\tjava -jar $jarFIleName --run-once\t${
            resourceBundle.getString("update.subdomain.s.IP.addresses").capitalize()
        }"
    )
    println(updatingSubdomainIPAddressesInInfiniteLoopTitle)
    println(
        "\t\tjava -jar $jarFIleName --infinite-loop [time=<t>]\t${
            MessageFormat.format(
                resourceBundle.getString("update.subdomain.s.IP.addresses.in.infinite.looping.with.a.delay.of.time.in.0.milliseconds")
                    .capitalize(), "<t>"
            )
        }"
    )
    println(cleaningSubdomainsIPAddressTitle)
    println(
        "\t\tjava -jar $jarFIleName --clean\t${
            resourceBundle.getString("clean.subdomain.s.IP.addresses").capitalize()
        }"
    )
    println()
}