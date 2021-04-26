import console.console
import console.quickSetupConsole
import controller.SerializableSubdomainController
import java.awt.GraphicsEnvironment
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Subdomain object controller
 */
var serializableSubdomainController: SerializableSubdomainController? = null

/**
 * Chooses what interface will be used for communicating to the user
 */
fun main(args: Array<String>) {

    if (serializableSubdomainController == null)
        try {
            serializableSubdomainController = SerializableSubdomainController(true)
        } catch (e: FileNotFoundException) {
            serializableSubdomainController = SerializableSubdomainController()
            serializableSubdomainController?.saveSubdomains()

            if (GraphicsEnvironment.isHeadless()) quickSetupConsole()
            else TODO("Not yet implemented")
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    if (args.isNotEmpty() && args.contains("console"))
        console()
    else if (GraphicsEnvironment.isHeadless())
        console()
    else if (args.isNotEmpty() && args.contains("gui"))
        TODO("Not yet implemented")
    else
        serializableSubdomainController?.run()
}
