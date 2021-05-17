package duckdns.controller

import duckdns.exception.ObjectAlreadyExistsException
import duckdns.model.Subdomain
import java.io.*
import java.lang.Thread.sleep
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

/**
 * File which will store Duck DNS IP Address Updater Settings.
 */
private val configurationFile = Paths.get(System.getProperty("user.home"), ".duckdnsipupdater", "settings.bin").toFile()

/**
 * Array list which will store Duck DNS Subdomains objects.
 */
private var subdomains: ArrayList<Subdomain> = ArrayList()

/**
 * Retrieves the subdomain array list.
 * @return An ArrayList with subdomain objects.
 */
fun getSubdomains(): ArrayList<Subdomain> = subdomains

/**
 * Initializes subdomain controller.
 */
fun initialize() {
    for (i in 0..100)
        try {
            load()
            break
        } catch (e: Exception) {
            e.printStackTrace()
        }
}

/**
 * Runs Duck DNS IP Address Updater for all Duck DNS Subdomains objects on array list subdomains.
 * @param infiniteLoop Whether the Duck DNS IP Address Updater has to run in infinite loop.
 * @param delayedTime Waiting time period in milliseconds.
 */
fun updateIPAddress(infiniteLoop: Boolean = false, delayedTime: Long = TimeUnit.MINUTES.toMillis(5)) {
    if (infiniteLoop)
        while (true) {
            for (subdomain in subdomains)
                subdomain.updateIPAddress()
            sleep(delayedTime)
        }
    else
        for (subdomain in subdomains)
            subdomain.updateIPAddress()
}

/**
 * Loads Duck DNS subdomains array list from configuration file.
 */
private fun load() {
    val fileInputStream = FileInputStream(configurationFile)
    val objectInputStream = ObjectInputStream(fileInputStream)
    val readObject = objectInputStream.readObject()

    if (readObject is ArrayList<*>) {
        subdomains = readObject.filterIsInstance<Subdomain>() as ArrayList<Subdomain>
    }

    objectInputStream.close()
    fileInputStream.close()
}

/**
 * Saves Duck DNS subdomains array list to configuration file.
 */
private fun save() {
    while (true)
        try {
            val fileOutputStream = FileOutputStream(configurationFile)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(subdomains)
            objectOutputStream.close()
            fileOutputStream.close()
            break

        } catch (e: FileNotFoundException) {
            configurationFile.parentFile.mkdirs()
            configurationFile.createNewFile()
            e.printStackTrace()
        }
}

/**
 * Removes a subdomain from Duck DNS Subdomains object array list.
 * @param subdomain Subdomain object which will be removed.
 */
fun remove(subdomain: Subdomain) {
    subdomains.remove(subdomain)
}

/**
 * Adds a Duck DNS Subdomain to subdomains ArrayList.
 * @param subdomain Subdomain which will be added to subdomains array list.
 */
fun create(subdomain: Subdomain) {
    if (subdomains.any { it.subdomainName.equals(subdomain.subdomainName, ignoreCase = true) })
        throw ObjectAlreadyExistsException()

    subdomains.add(subdomain)
}

/**
 * Updates a Subdomain object in subdomains ArrayList.
 * @param oldSubdomain Subdomain which will be replaced by newSubdomain.
 * @param newSubdomain Subdomain which will replace oldSubdomain.
 */
fun update(oldSubdomain: Subdomain, newSubdomain: Subdomain) {
    remove(oldSubdomain)
    create(newSubdomain)
}

/**
 * Runs Subdomain Controller finalization tasks.
 */
fun finalize() {
    save()
}