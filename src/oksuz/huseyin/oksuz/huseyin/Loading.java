//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oksuz.huseyin;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.SwingWorker.StateValue;

public class Loading extends JDialog {
    public JPanel panel = new JPanel();
    public JPanel jp = new JPanel();
    public boolean ileri = true;
    public Timer t = new Timer(16, new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            int x = Loading.this.jp.getX();
            if (x > 223) {
                Loading.this.ileri = false;
            }

            if (x < 2) {
                Loading.this.ileri = true;
            }

            Loading.this.jp.setLocation(Loading.this.ileri ? x + 4 : x - 4, 1);
        }
    });

    public Loading(JFrame parent, SwingWorker<Void, Void> worker) {
        super(parent, true);
        this.setResizable(false);
        setDefaultLookAndFeelDecorated(true);
        this.setUndecorated(true);
        this.setContentPane(this.panel);
        this.panel.setBorder(BorderFactory.createLineBorder(Color.black));
        this.panel.setSize(250, 30);
        this.panel.setLayout((LayoutManager)null);
        this.setSize(250, 30);
        this.jp.setSize(24, 28);
        this.jp.setLocation(1, 1);
        this.jp.setBackground(new Color(30, 144, 255));
        this.panel.add(this.jp);
        this.setLocationRelativeTo(parent);
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (StateValue.DONE == evt.getNewValue()) {
                    Loading.this.dur();
                }

            }
        });
        worker.execute();
        this.t.start();
        this.setVisible(true);
    }

    public void dur() {
        this.t.stop();
        this.setVisible(false);
    }
}
