package duckdns.console

import java.io.File

/**
 * Displays Help Menu
 */
fun helpMenu() {
    printHeader("Help Menu")
    val anyObject = object {}
    val jarFIleName = File(anyObject.javaClass.protectionDomain.codeSource.location.path).name
    println("Usage: java -jar $jarFIleName [options]")
    println("Options:")
    println("\tGetting help:")
    println("\t\tjava -jar $jarFIleName --help\tDisplay this information")
    println("\tOpening console menu:")
    println("\t\tjava -jar $jarFIleName --console")
    println("\tOpening Graphical User Interface menu:")
    println("\t\tjava -jar $jarFIleName --gui")
    println("\tRunning Duck DNS IP Address Updater just one time:")
    println("\t\tjava -jar $jarFIleName --run-once")
    println("\tRunning Duck DNS IP Address Updater in infinite loop:")
    println("\t\tjava -jar $jarFIleName --infinite-loop [time=<t>]\tRun Duck DNS IP Address Updater in infinite loop with a delay of time in <t> milliseconds")
    println()
}