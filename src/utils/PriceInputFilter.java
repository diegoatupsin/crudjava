package utils;

import javax.swing.text.*;

public class PriceInputFilter extends DocumentFilter {
    private int maxLength;
    private int maxDecimals;

    public PriceInputFilter(int maxLength, int maxDecimals) {
        this.maxLength = maxLength;
        this.maxDecimals = maxDecimals;
    }

    private boolean isValidPrice(String text) {
        if (text.isEmpty()) return true;
        // Allow digits and only one decimal point
        if (!text.matches("\\d*(\\.\\d{0," + maxDecimals + "})?")) return false;
        return true;
    }

    private boolean withinMaxLength(String text) {
        return text.length() <= maxLength;
    }

    private boolean isValid(String fullText) {
        return withinMaxLength(fullText) && isValidPrice(fullText);
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;

        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + string + currentText.substring(offset);
        if (isValid(newText)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) return;

        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
        if (isValid(newText)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
