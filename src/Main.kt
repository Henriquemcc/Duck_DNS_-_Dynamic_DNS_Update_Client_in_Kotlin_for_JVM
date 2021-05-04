import console.helpMenu
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
        console.console()
        controller.finalize()
    } else if (args.isNotEmpty() && args.contains("--gui")) {
        if (GraphicsEnvironment.isHeadless())
            throw GraphicsIsNotSupportedException()
        val mainMenu = MainMenu()
        mainMenu.addCloseOperation {
            controller.finalize()
        }


    } else if (args.isNotEmpty() && args.contains("--run-once"))
        controller.updateIPAddress()
    else if (args.isNotEmpty() && args.any { it.contains("--infinite-loop") }) {
        var time: Long = 1
        val timeStr = args.find { it.contains("time=") }
        timeStr?.let {
            time = it.substring(it.indexOf("time=") + 5, it.length - 1).toLong()
        }
        controller.updateIPAddress(true, time)
    } else {
        helpMenu()
    }
}
