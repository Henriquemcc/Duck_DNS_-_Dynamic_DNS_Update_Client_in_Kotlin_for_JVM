package duckdns.gui

import javax.swing.text.AttributeSet
import javax.swing.text.DocumentFilter

/**
 * Extension of DocumentFilter class that converts GUI user typed characters to lowerCase
 */
class LowerCaseDocumentFilter : DocumentFilter() {

    /**
     * Overrides super's insertString method, converting string to lower case.
     */
    override fun insertString(fb: FilterBypass?, offset: Int, string: String?, attr: AttributeSet?) {
        super.insertString(fb, offset, string?.toLowerCase(), attr)
    }

    /**
     * Overrides super's replace method, converting text to lower case.
     */
    override fun replace(fb: FilterBypass?, offset: Int, length: Int, text: String?, attrs: AttributeSet?) {
        super.replace(fb, offset, length, text?.toLowerCase(), attrs)
    }
}