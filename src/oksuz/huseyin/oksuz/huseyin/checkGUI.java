//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oksuz.huseyin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class checkGUI extends JDialog {
    private String title = "TPr vs YGS/SGS Doğrulaması";
    private JPanel contentPane = new JPanel();
    private String eskiText = "";

    public checkGUI(JFrame parent, final File inputFile, final Logger paneLog, final int gereksinimSutunNoIndex, final String headingStyle, final char deliminator) {
        this.setTitle(this.title);
        this.setAlwaysOnTop(true);
        this.setContentPane(this.contentPane);
        this.setSize(320, 313);
        this.setResizable(false);
        this.setLocationRelativeTo(parent);
        this.contentPane.setLayout((LayoutManager)null);
        JLabel lblSurukle = new JLabel("Gereksinim Listesini aşağıya kopyalayın.");
        lblSurukle.setBounds(10, 5, 285, 20);
        this.contentPane.add(lblSurukle);
        final JTextArea txtGirdi = new JTextArea();
        txtGirdi.setBounds(10, 25, 295, 220);
        txtGirdi.setBackground(new Color(220, 220, 220));
        txtGirdi.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        txtGirdi.setFont(new Font("Arial", 0, 14));
        JScrollPane sp = new JScrollPane(txtGirdi);
        sp.setBounds(10, 25, 295, 220);
        this.contentPane.add(sp);
        final JButton btnOK = new JButton("Doğrula");
        btnOK.setBounds(10, 250, 295, 30);
        btnOK.setFocusable(false);
        btnOK.setFocusPainted(false);
        this.contentPane.add(btnOK);
        btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (btnOK.getText().equals("Doğrula")) {
                    checkGUI.this.eskiText = txtGirdi.getText();
                    String str = txtGirdi.getText().trim().replace(" ", "").replace("\t", "");
                    if (str.length() < 1) {
                        return;
                    }

                    String[] spt = str.split("\n");
                    ArrayList<String> nameList = new ArrayList();

                    String name;
                    for(int i = 0; i < spt.length; ++i) {
                        name = spt[i].trim();
                        if (name.length() > 0) {
                            nameList.add(spt[i]);
                        }
                    }

                    Isleyici isleyici = new Isleyici(paneLog, gereksinimSutunNoIndex, headingStyle, deliminator);
                    name = isleyici.YGSSGS(inputFile, nameList);
                    txtGirdi.setText(name);
                    txtGirdi.setCaretPosition(0);
                    btnOK.setText("Geri Dön");
                } else {
                    txtGirdi.setText(checkGUI.this.eskiText);
                    btnOK.setText("Doğrula");
                }

            }
        });
    }
}
