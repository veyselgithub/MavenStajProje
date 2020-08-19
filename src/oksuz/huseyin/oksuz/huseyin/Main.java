//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oksuz.huseyin;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main extends JFrame {
    private static final long serialVersionUID = 4648172894076113183L;

    public Main() {
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception var2) {
        }

        GUI myFrame = new GUI();
        myFrame.setVisible(true);
    }
}
