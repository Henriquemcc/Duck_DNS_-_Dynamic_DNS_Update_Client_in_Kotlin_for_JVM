package gui

import gui.subdomain.SubdomainMenu
import java.awt.GridLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * Graphical User Interface main menu that allows GUI user to choose what to do.
 */
class MainMenu {

    /**
     * Subdomains menu's window.
     */
    private val jFrame = JFrame("Duck DNS IP Address Updater")

    /**
     * Array list with all child components created by this class.
     */
    private val childComponents = ArrayList<Any>()

    /**
     * Initializes Main Menu class creating all GUI components recursively.
     */
    init {
        jFrameInit()
    }

    /**
     * Initializes buttonRunDuckDNSIPAddressUpdater.
     * @return A JButton object initialized.
     */
    private fun buttonRunDuckDNSIPAddressUpdaterOnceInit(): JButton {
        val buttonRunDuckDNSIPAddressUpdaterOnce = JButton("Run Duck DNS IP Address Updater Once")
        buttonRunDuckDNSIPAddressUpdaterOnce.font = defaultFont
        buttonRunDuckDNSIPAddressUpdaterOnce.addActionListener {
            jFrame.isVisible = false
            controller.updateIPAddress()
            jFrame.isVisible = true
        }
        return buttonRunDuckDNSIPAddressUpdaterOnce
    }

    /**
     * Initializes buttonSubdomainMenu.
     * @return A JButton initialized.
     */
    private fun buttonSubdomainMenuInit(): JButton {
        val buttonSubdomainMenu = JButton("Subdomain menu")
        buttonSubdomainMenu.font = defaultFont
        buttonSubdomainMenu.addActionListener {
            jFrame.isVisible = false
            childComponents.add(SubdomainMenu(this))
        }
        return buttonSubdomainMenu
    }

    /**
     * Initializes JFrame and it's subcomponents recursively.
     * @return A JFrame object with it's subcomponents initialized.
     */
    private fun jFrameInit(): JFrame {
        jFrame.font = defaultFont
        val jPanel = jPanelInit()
        jFrame.add(jPanel)
        jFrame.pack()
        jFrame.isVisible = true
        jFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        jFrame.location = jFrame.defaultLocation
        return jFrame

    }

    /**
     * Initializes JPanel and it's subcomponents recursively.
     * @return A JPanel object with it's subcomponents initialized.
     */
    private fun jPanelInit(): JPanel {
        val jPanel = JPanel(GridLayout(3, 1))
        jPanel.font = defaultFont
        val buttonRunDuckDNSIPAddressUpdaterOnce = buttonRunDuckDNSIPAddressUpdaterOnceInit()
        jPanel.add(buttonRunDuckDNSIPAddressUpdaterOnce)
        val buttonRunDuckDNSIPAddressUpdaterInInfiniteLoop = buttonRunDuckDNSIPAddressUpdaterInInfiniteLoopInit()
        jPanel.add(buttonRunDuckDNSIPAddressUpdaterInInfiniteLoop)
        val buttonSubdomainMenu = buttonSubdomainMenuInit()
        jPanel.add(buttonSubdomainMenu)
        return jPanel

    }

    private fun buttonRunDuckDNSIPAddressUpdaterInInfiniteLoopInit(): JButton {
        val buttonRunDuckDNSIPAddressUpdaterInInfiniteLoop = JButton("Run Duck DNS IP Address Updater in infinite loop")
        buttonRunDuckDNSIPAddressUpdaterInInfiniteLoop.font = defaultFont
        buttonRunDuckDNSIPAddressUpdaterInInfiniteLoop.addActionListener {
            jFrame.isVisible = false
            controller.updateIPAddress(true)
        }
        return buttonRunDuckDNSIPAddressUpdaterInInfiniteLoop
    }

    /**
     * Makes this menu jFrame visible.
     */
    fun makeVisible() {
        jFrame.isVisible = true
    }

    /**
     * Adds a closing operation to this menu JFrame.
     * @param runnable Action that will run when this menu JFrame window closes.
     */
    fun addCloseOperation(runnable: Runnable) {
        jFrame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                runnable.run()
                super.windowClosing(e)
            }
        })
    }


}