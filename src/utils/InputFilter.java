package utils;

import javax.swing.text.*;

public class InputFilter extends DocumentFilter {
    private int maxLength;
    private boolean onlyLetters;
    private boolean onlyNumbers;

    public InputFilter(int maxLength, boolean onlyLetters, boolean onlyNumbers) {
        this.maxLength = maxLength;
        this.onlyLetters = onlyLetters;
        this.onlyNumbers = onlyNumbers;
    }

    private boolean isValid(String text) {
            // permite espacios en blanco y letras
            if (onlyLetters && !text.matches("[a-zA-Z\\s]*")) return false;
            if (onlyNumbers && !text.matches("\\d*")) return false;
        return true;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
            throws BadLocationException {
        if (string == null) return;
        String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
        if (newText.length() <= maxLength && isValid(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
            throws BadLocationException {
        if (text == null) return;
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
        if (newText.length() <= maxLength && isValid(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}