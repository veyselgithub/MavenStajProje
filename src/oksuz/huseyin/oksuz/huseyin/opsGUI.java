//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oksuz.huseyin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class opsGUI extends JDialog {
    private JPanel cp = new JPanel();
    private GUI parent = null;
    private JComboBox<String> cbHeading = new JComboBox();
    private JComboBox<String> cbSutun = new JComboBox();
    private JComboBox<String> cbOutput = new JComboBox();
    private JComboBox<String> deliminator = new JComboBox();//kullanıcının sembol seçebilmesi için tanımlanan değişken.
    private JButton btnOk = new JButton("Kaydet");
    private JCheckBox cbKontroll = new JCheckBox();
    private Isleyici opsiyonlar = null;

    public opsGUI(GUI parent, boolean zorunluluk, String heading, int gereksinimSutunNo, String extention, int deliminator) {
        super(parent, true);
        this.parent = parent;
        this.setTitle("Opsiyon Ayarları");
        this.setResizable(false);
        setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(1);
        this.cp.setLayout((LayoutManager)null);
        this.setContentPane(this.cp);
        JLabel lblHeaderSec = new JLabel("Test Durumu için heading seçimi");
        lblHeaderSec.setBounds(10, 10, 160, 20);
        this.cp.add(lblHeaderSec);

        for(int i = 1; i <= 9; ++i) {
            this.cbHeading.addItem("Heading " + i);
        }

        this.cbHeading.addItem("Caption");
        this.cbHeading.setSelectedItem(new String(heading));
        this.cbHeading.setFocusable(false);
        this.cbHeading.setBounds(173, 10, 100, 20);
        this.cp.add(this.cbHeading);
        JLabel lblGereksinimSutun = new JLabel("Gereksinim için sütun numarası");
        lblGereksinimSutun.setBounds(10, 37, 160, 20);
        this.cp.add(lblGereksinimSutun);

        for(int i = 1; i <= 10; ++i) {
            this.cbSutun.addItem("" + i);
        }




        this.cbSutun.setSelectedIndex(gereksinimSutunNo - 1);
        this.cbSutun.setFocusable(false);
        this.cbSutun.setBounds(173, 37, 100, 20);
        this.cp.add(this.cbSutun);
        JLabel lblOutput = new JLabel("Sonuç çıktısı");
        lblOutput.setBounds(10, 64, 160, 20);
        this.cp.add(lblOutput);
        this.cbOutput.addItem("Word Dosyası");
        this.cbOutput.addItem("Excel Dosyası");
        this.cbOutput.setSelectedIndex(extention.equals(".docx") ? 0 : 1);
        this.cbOutput.setFocusable(false);
        this.cbOutput.setBounds(173, 64, 100, 20);
        this.cp.add(this.cbOutput);
        JLabel lblBypassKontrol = new JLabel("İzlenebilirlik oluşturma için kontrol zorunluluğu");
        lblBypassKontrol.setBounds(10, 91, 250, 20);
        this.cp.add(lblBypassKontrol);
        this.cbKontroll.setSelected(zorunluluk);
        this.cbKontroll.setBounds(255, 91, 20, 20);
        this.cp.add(this.cbKontroll);
        this.btnOk.setFocusable(false);
        this.btnOk.setFocusPainted(false);
        this.btnOk.setBounds(75, 130, 135, 30);
        this.onayButonuEventi();
        this.cp.add(this.btnOk);
        this.setSize(290, 180);
        this.setLocationRelativeTo(parent);
        this.cp.add(this.deliminator);//kullanıcının sembol seçebilmesini sağlayan arayüz tasarımı
        JLabel lbldeliminator = new JLabel("Sembol");
        lbldeliminator.setBounds(10, 115, 160, 20);
        this.cp.add(lbldeliminator);
        this.deliminator.addItem("*");//kullanıcının seçebilieceği semboller
        this.deliminator.addItem(";");
        this.deliminator.addItem("[");
        this.deliminator.setSelectedIndex(extention.equals(";") ? 0 : 1);
        this.deliminator.setFocusable(false);
        this.deliminator.setBounds(173, 115, 100, 20);
        this.cp.add(this.deliminator);
        

    }

    private void onayButonuEventi() {
        this.btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                opsGUI.this.parent.opsiyonAyarla(opsGUI.this.getHeading(), opsGUI.this.getZorunluluk(), opsGUI.this.getGereksinimSutunNo(), opsGUI.this.getOutputType());
                opsGUI.this.parent.getLogger().append(false, "-----------------------------------------------------------------------------------------------------------------------------");
                opsGUI.this.parent.getLogger().append(false, "\t                           OPSİYON AYARLARI");
                opsGUI.this.parent.getLogger().append(false, "-----------------------------------------------------------------------------------------------------------------------------");
                opsGUI.this.parent.getLogger().append(false, "Başlık Seçimi\t\t: " + opsGUI.this.getHeading());
                opsGUI.this.parent.getLogger().append(false, "Gereksinim Sütunu\t: " + opsGUI.this.getGereksinimSutunNo());
                opsGUI.this.parent.getLogger().append(false, "Sonuç Çıktısı\t\t: " + opsGUI.this.getOutputType());
                opsGUI.this.parent.getLogger().append(false, "Kontrol Zorunluluğu\t: " + (opsGUI.this.getZorunluluk() ? "Var" : "Yok"));
                opsGUI.this.parent.getLogger().append(false, "Bakılacak Sembol\t: " + opsGUI.this.getdeliminator());
                //ust kodda kullanıcı hangi sembolü seçtiyse ekrana yazmayı sağlıyor
                opsGUI.this.parent.getLogger().append(false, " ");
                opsGUI.this.setVisible(false);
            }
        });
    }

    private String getOutputType() {
        return (String)this.cbOutput.getSelectedItem();
    }

    private boolean getZorunluluk() {
        return this.cbKontroll.isSelected();
    }

    private String getHeading() {
        String ret = (String)this.cbHeading.getItemAt(this.cbHeading.getSelectedIndex());
        return ret;
    }

    private int getGereksinimSutunNo() {
        int a = this.cbSutun.getSelectedIndex() + 1;
        return a;
    }

    private Object getdeliminator() {
        return this.deliminator.getSelectedItem();
    } //sembolü içerisinde tutan değişkeni diğer dosyalarda elde edebilmek için 




 

        //isleyiciye bu sekilde gonder opsGUI den


}
