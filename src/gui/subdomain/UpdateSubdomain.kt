package gui.subdomain

import gui.LowerCaseDocumentFilter
import gui.defaultFont
import gui.defaultLocation
import model.Subdomain
import java.awt.GridLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*
import javax.swing.text.AbstractDocument

/**
 * Graphical User Interface menu that allows GUI user to update a subdomain.
 * @param subdomainMenu Subdomain menu to refresh JTable when a Subdomain object is modified in subdomains objects array list.
 */
class UpdateSubdomain(private val subdomainMenu: SubdomainMenu, private val oldSubdomain: Subdomain) {

    /**
     * Text field that will obtain from GUI user the new Duck DNS subdomain's address.
     */
    private val jTextFieldSubdomain = JTextField(oldSubdomain.subdomainName)

    /**
     * Text field that will obtain from GUI user the new Duck DNS subdomain's token.
     */
    private val jTextFieldToken = JTextField(oldSubdomain.token)

    /**
     * Checkbox that will obtain from GUI user the new Duck DNS subdomain's enable IPv6 attribute.
     */
    private val jCheckBoxEnableIPv6 = JCheckBox("Enable IPv6", oldSubdomain.enableIPv6)

    /**
     * Checkbox that will obtain from GUI user the new Duck DNS subdomain's enable IPv4 attribute.
     */
    private val jCheckBoxEnableIPv4 = JCheckBox("Enable IPv4", oldSubdomain.enableIPv4)

    /**
     * JFrame which will store jPanel with other GUI components.
     */
    private val jFrame = JFrame("Subdomain update menu")

    /**
     * Initializes UpdateSubdomain class creating all GUI components recursively.
     */
    init {
        jFrameInit()
    }

    /**
     * Initializes jFrame and it's subcomponents recursively.
     * @return A JFrame object with it's subcomponents initialized.
     */
    private fun jFrameInit(): JFrame {
        val jPanel = jPanelInit()
        jFrame.add(jPanel)
        jFrame.isVisible = true
        jFrame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        jFrame.location = jFrame.defaultLocation
        jFrame.addWindowListener(
            object : WindowAdapter() {
                override fun windowClosed(e: WindowEvent?) {
                    subdomainMenu.makeVisible()
                    super.windowClosed(e)
                }
            }
        )
        jFrame.pack()

        return jFrame

    }

    /**
     * Initializes jPanel and it's subcomponents recursively.
     * @return A JPanel object with it's subcomponents initialized.
     */
    private fun jPanelInit(): JPanel {
        val jPanel = JPanel(GridLayout(5, 1))
        val jTextFieldSubdomain = jTextFieldSubdomainInit()
        jPanel.add(jTextFieldSubdomain)
        val jTextFieldToken = jTextFieldTokenInit()
        jPanel.add(jTextFieldToken)
        val jCheckBoxEnableIPv4 = jCheckBoxEnableIPv4Init()
        jPanel.add(jCheckBoxEnableIPv4)
        val jCheckBoxEnableIPv6 = jCheckBoxEnableIPv6Init()
        jPanel.add(jCheckBoxEnableIPv6)
        val jButtonAddSubdomain = jButtonUpdateSubdomainInit()
        jPanel.add(jButtonAddSubdomain)


        return jPanel
    }

    /**
     * Initializes jButtonUpdateSubdomain.
     * @return A JButton object initialized.
     */
    private fun jButtonUpdateSubdomainInit(): JButton {
        val jButtonUpdateSubdomain = JButton("Update Subdomain")
        jButtonUpdateSubdomain.font = defaultFont
        jButtonUpdateSubdomain.addActionListener {
            val subdomain = Subdomain(jTextFieldSubdomain.text.toLowerCase(), jCheckBoxEnableIPv4.isSelected, jCheckBoxEnableIPv6.isSelected, jTextFieldToken.text)
            controller.update(oldSubdomain, subdomain)
            jFrame.dispatchEvent(WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING))
            subdomainMenu.refreshTableSubdomains()
        }
        return jButtonUpdateSubdomain
    }

    /**
     * Initializes jCheckBoxEnableIPv6.
     * @return JCheckBox object initialized.
     */
    private fun jCheckBoxEnableIPv6Init(): JCheckBox {
        jCheckBoxEnableIPv6.font = defaultFont
        return jCheckBoxEnableIPv6
    }

    /**
     * Initializes jCheckBoxEnableIPv4.
     * @return JCheckbox object initialized.
     */
    private fun jCheckBoxEnableIPv4Init(): JCheckBox {
        jCheckBoxEnableIPv4.font = defaultFont
        return jCheckBoxEnableIPv4
    }

    /**
     * Initializes jTextFieldToken.
     * @return JTextField object initialized.
     */
    private fun jTextFieldTokenInit(): JTextField {
        jTextFieldToken.font = defaultFont
        jTextFieldToken.isEditable = true
        return jTextFieldToken
    }

    /**
     * Initializes jTextFieldSubdomain.
     * @return JTextField object initialized.
     */
    private fun jTextFieldSubdomainInit(): JTextField {
        jTextFieldSubdomain.font = defaultFont
        jTextFieldSubdomain.isEditable = true
        (jTextFieldSubdomain.document as AbstractDocument).documentFilter = LowerCaseDocumentFilter()
        return jTextFieldSubdomain
    }
}