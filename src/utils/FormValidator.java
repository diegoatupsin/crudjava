package utils;

import java.awt.*;
import java.util.Set;
import javax.swing.*;

public class FormValidator {
    public static boolean validarCampos(Component[] components, Set<Component> exclude) {
        for (Component comp : components) {
            if (exclude.contains(comp)) {
                continue;
            }

            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (field.getText().trim().isEmpty()) {
                    return false;
                }

            } else if (comp instanceof JComboBox) {
                JComboBox<?> combo = (JComboBox<?>) comp;
                if (combo.getSelectedItem() == null || combo.getSelectedIndex() == 0) {
                    return false;
                }

            } else if (comp instanceof JList) {
                JList<?> list = (JList<?>) comp;
                if (list.getModel().getSize() == 0) {
                    return false;
                }

            } else if (comp instanceof Container) {
                if (!validarCampos(((Container) comp).getComponents(), exclude)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validarCampos(Component[] components) {
        return validarCampos(components, Set.of()); // empty exclusion set
    }
}