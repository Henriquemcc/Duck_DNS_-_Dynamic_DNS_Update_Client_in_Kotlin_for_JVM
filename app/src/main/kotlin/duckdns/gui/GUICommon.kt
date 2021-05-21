package duckdns.gui

import duckdns.international.resourceBundle
import java.awt.Font
import javax.swing.JFrame
import javax.swing.JOptionPane

/**
 * Default font that will be used in all GUI components.
 */
val defaultFont: Font = Font("Arial", Font.PLAIN, 20)

/**
 * Gets from GUI user a confirmation using a confirmation dialog box.
 * @param message Message that confirmation dialog box will display to GUI user.
 * @return Boolean value whether final user confirm that action.
 */
fun getConfirmation(message: String = ""): Boolean {
    val jFrame = JFrame(resourceBundle.getString("confirmation").capitalize())
    jFrame.defaultCloseOperation = JFrame.DO_NOTHING_ON_CLOSE
    jFrame.font = defaultFont
    val confirmation = JOptionPane.showConfirmDialog(jFrame, message)

    return confirmation == JOptionPane.YES_OPTION
}