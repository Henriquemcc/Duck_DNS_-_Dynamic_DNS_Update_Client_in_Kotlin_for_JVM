import console.helpMenu
import controller.updateIPAddress
import exception.GraphicsIsNotSupportedException
import gui.MainMenu
import java.awt.GraphicsEnvironment

/**
 * Main function. It Chooses what interface will be used for communicating to the user.
 * @param args Execution parameters.
 */
fun main(args: Array<String>) {
    controller.initialize()

    if (args.isNotEmpty() && args.contains("--console")) {
        runConsole()
    } else if (args.isNotEmpty() && args.contains("--gui")) {
        runGUI()
    } else if (args.isNotEmpty() && args.contains("--run-once")) {
        updateIPAddress()
    } else if (args.isNotEmpty() && args.any { it.contains("--infinite-loop") }) {
        runDuckDNSIPUpdaterInfiniteLoop(args)
    } else if (args.isNotEmpty() && args.contains("--help")) {
        helpMenu()
    } else if (!GraphicsEnvironment.isHeadless()) {
        runGUI()
    } else {
        helpMenu()
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
        controller.finalize()
    }
}

/**
 * Runs non-graphical user interface
 */
private fun runConsole() {
    console.console()
    controller.finalize()
}