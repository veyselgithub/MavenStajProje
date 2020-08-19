//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oksuz.huseyin;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Logger extends JTextArea {
    private JScrollPane sp;

    public Logger() {
    }

    public void append(boolean timeStamp, String s) {
        if (timeStamp) {
            Date now = new Date();
            String ts = (new SimpleDateFormat("HH:mm:ss")).format(now);
            s = ts + "    " + s;
        }

        if (!s.endsWith("\n")) {
            s = s + "\n";
        }

        this.append(s);
        this.setCaretPosition(this.getDocument().getLength());
    }

    public JScrollPane getScroll() {
        this.sp = new JScrollPane(this);
        return this.sp;
    }
}
