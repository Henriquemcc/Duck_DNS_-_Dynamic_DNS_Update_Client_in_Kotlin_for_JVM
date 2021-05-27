package duckdns

import duckdns.console.console
import duckdns.console.helpMenu
import duckdns.controller.cleanIPAddresses
import duckdns.controller.finalize
import duckdns.controller.initialize
import duckdns.controller.updateIPAddress
import duckdns.exception.GraphicsIsNotSupportedException
import duckdns.gui.MainMenu
import java.awt.GraphicsEnvironment

/**
 * Main function. It Chooses what interface will be used for communicating to the user.
 * @param args Execution parameters.
 */
fun main(args: Array<String>) {
    initialize()

    when {
        args.isNotEmpty() && args.contains("--console") -> {
            runConsole()
        }
        args.isNotEmpty() && args.contains("--gui") -> {
            runGUI()
        }
        args.isNotEmpty() && args.contains("--run-once") -> {
            updateIPAddress()
        }
        args.isNotEmpty() && args.any { it.contains("--infinite-loop") } -> {
            runDuckDNSIPUpdaterInfiniteLoop(args)
        }
        args.isNotEmpty() && args.contains("--clean") -> {
            cleanIPAddresses()
        }
        args.isNotEmpty() && args.contains("--help") -> {
            helpMenu()
        }
        !GraphicsEnvironment.isHeadless() -> {
            runGUI()
        }
        else -> {
            helpMenu()
        }
    }
}

/**
 * Runs Duck DNS IP Address Updater in infinite loop.
 */
private fun runDuckDNSIPUpdaterInfiniteLoop(args: Array<String>) {
    var time: Long = 1
    val timeStr = args.find { it.contains("time=") }
    timeStr?.let {
        time = it.substring(it.indexOf("time=") + 5, it.length - 1).toLong()
    }
    updateIPAddress(true, time)
}

/**
 * Runs Graphical User Interface
 */
private fun runGUI() {
    if (GraphicsEnvironment.isHeadless())
        throw GraphicsIsNotSupportedException()

    val mainMenu = MainMenu()
    mainMenu.addCloseOperation {
        finalize()
    }
}

/**
 * Runs non-graphical user interface
 */
private fun runConsole() {
    console()
    finalize()
}