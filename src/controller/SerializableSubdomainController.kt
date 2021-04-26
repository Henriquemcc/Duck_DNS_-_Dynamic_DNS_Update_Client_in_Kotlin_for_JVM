package controller

import model.Subdomain
import java.io.*

private const val configurationFile = "subdomains.config"

/**
 * Controls storage of Subdomains objects
 */
class SerializableSubdomainController(loadFile: Boolean = false) {

    /**
     * Duck DNS Subdomains Objects
     */
    var subdomains: ArrayList<Subdomain>? = null

    init {
        if (loadFile)
            loadSubdomains()
        else
            subdomains = ArrayList()
    }

    /**
     * Updates subdomains IP address
     */
    fun run() {
        for (subdomain in subdomains ?: return)
            subdomain.update()
    }

    /**
     * Load Duck DNS Subdomains from configuration file
     * @throws IOException
     */
    private fun loadSubdomains() {
        val fileInputStream = FileInputStream(configurationFile)
        val objectInputStream = ObjectInputStream(fileInputStream)
        val readObject = objectInputStream.readObject()
        subdomains = readObject as ArrayList<Subdomain>?
        objectInputStream.close()
        fileInputStream.close()
    }

    /**
     * Save Duck DNS Subdomains from configuration file
     * @throws IOException
     */
    fun saveSubdomains() {
        val fileOutputStream = FileOutputStream(configurationFile)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(subdomains)
        objectOutputStream.close()
        fileOutputStream.close()
    }
}