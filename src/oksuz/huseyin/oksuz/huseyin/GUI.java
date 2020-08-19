//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oksuz.huseyin;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class GUI extends JFrame {
    private JLabel lblDosyaSec = new JLabel("Test Prosedürü Dosyası Seçin : ");
    private JTextField txtDosyaSec = new JTextField("Dosyaları sürüklemeyi deneyin.");
    private JScrollPane sp;
    private JButton btnDosyaSec = new JButton("Dosya Seç");
    private JButton btnKaydet = new JButton("İzlenebilirlik Oluştur");
    private JButton btnKontrol = new JButton("Kontrol Et");
    private JButton btnTemizle = new JButton("Temizle");
    private JButton btnOpsiyon = new JButton("Ayarlar");
    private Logger paneLog = new Logger();
    private File inputFile;
    private File outputFile;
    private opsGUI opsiyonlar = null;
    private String searchHeading = "Heading 3";
    private String frameTitle = "Test Prosedürü Analizi v2.9 - HAVELSAN  ";
    private boolean kontrolZorunluluk = true;
    private int gereksinimSutunNo = 6;
    private boolean exportAsExcel = true;
    private char deliminator = '[';

    public GUI() {
        this.setTitle(this.frameTitle);
        this.setBounds(100, 100, 545, 400);
        this.setResizable(false);
        this.setLayout((LayoutManager)null);
        setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(3);
        this.lblDosyaSec.setBounds(10, 10, 400, 13);
        this.add(this.lblDosyaSec);
        JLabel lblInfo = new JLabel("?");
        lblInfo.setForeground(Color.blue);
        lblInfo.setFont(new Font("Arial", 1, 11));
        lblInfo.setBounds(this.lblDosyaSec.getX() + this.lblDosyaSec.getWidth() + 109, -3, 20, 20);
        String info = "<html><br>";
        info = info + "     <u><b>Kullanma Talimatları</b></u><br>";
        info = info + "     1) Test Prosedürü Dosyası seçilir<br>";
        info = info + "     2) Test Prosedürü Dosyasının uygunluğu, Kontrol Et butonuyla doğrulanır.<br>";
        info = info + "     3) Hata meydana gelirse, karşılaşılan sorunlar, kullanıcı tarafından giderilir.     <br>";
        info = info + "     4) Doğrulama hatasız bir şekilde sonuçlandığında, İzlenebilirlik Oluştur<br>";
        info = info + "<center>butonu ile izlenebilirlik dosyası oluşturulur.</center><br>";
        info = info + "</html>";
        lblInfo.setToolTipText(info);
        this.add(lblInfo);
        this.txtDosyaSec.setBounds(10, this.lblDosyaSec.getY() + this.lblDosyaSec.getHeight() + 2, 400, 23);
        this.txtDosyaSec.setEditable(false);
        this.txtDosyaSec.setEnabled(false);
        this.add(this.txtDosyaSec);
        this.btnDosyaSec.setBounds(this.lblDosyaSec.getX() + this.lblDosyaSec.getWidth() + 7, this.lblDosyaSec.getY() + this.lblDosyaSec.getHeight() - 4, 103, 30);
        this.btnDosyaSec.setFocusable(false);
        this.btnDosyaSec.setFocusPainted(false);
        this.btnDosyaSec.setToolTipText("Test Prosedürü Dosyası seçmek için tıklayın.");
        this.add(this.btnDosyaSec);
        this.paneLog.setBounds(10, this.txtDosyaSec.getY() + this.txtDosyaSec.getHeight() + 7, 510, 300);
        this.paneLog.setBackground(new Color(240, 240, 240));
        this.paneLog.setFont(new Font("Arial", 0, 12));
        this.paneLog.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        this.paneLog.setEditable(false);
        this.sp = this.paneLog.getScroll();
        this.sp.setBounds(10, this.txtDosyaSec.getY() + this.txtDosyaSec.getHeight() + 7, 510, 300);
        this.add(this.sp);
        this.btnKontrol.setBounds(10, this.paneLog.getY() + this.paneLog.getHeight() + 4, 133, 30);
        this.btnKontrol.setFocusable(false);
        this.btnKontrol.setFocusPainted(false);
        this.btnKontrol.setEnabled(false);
        this.add(this.btnKontrol);
        this.btnKaydet.setBounds(this.lblDosyaSec.getX() + this.lblDosyaSec.getWidth() + 7 - 30, this.paneLog.getY() + this.paneLog.getHeight() + 4, 133, 30);
        this.btnKaydet.setFocusable(false);
        this.btnKaydet.setFocusPainted(false);
        this.btnKaydet.setEnabled(false);
        this.btnKaydet.setToolTipText("Test Prosedürü İzlenebilirlik Dosyası oluşturmak için tıklayın.");
        this.add(this.btnKaydet);
        this.btnTemizle.setBounds(this.btnKontrol.getX() + this.btnKontrol.getWidth() + 7, this.paneLog.getY() + this.paneLog.getHeight() + 4, 68, 30);
        this.btnTemizle.setFocusable(false);
        this.btnTemizle.setFocusPainted(false);
        this.btnTemizle.setToolTipText("Bilgilendirme Ekranını temizlemek için tıklayın.");
        this.add(this.btnTemizle);
        this.btnOpsiyon.setBounds(this.btnTemizle.getX() + this.btnTemizle.getWidth() + 7, this.paneLog.getY() + this.paneLog.getHeight() + 4, 68, 30);
        this.btnOpsiyon.setFocusable(false);
        this.btnOpsiyon.setFocusPainted(false);
        this.btnOpsiyon.setToolTipText("Opsiyon Ayarlarını değiştirmek için tıklayın.");
        this.add(this.btnOpsiyon);
        this.setSize(this.btnKaydet.getX() + this.btnKaydet.getWidth() + 15, this.btnKaydet.getY() + this.btnKaydet.getHeight() + 35);
        this.btnDosyaSecEventiAyarla();
        this.btnKaydetEventiAyarla();
        this.btnKontrolEventiAyarla();
        this.btnTemizleEventiAyarla();
        this.btnOpsiyonEventiAyarla();
    }

    public void opsiyonAyarla(String heading, boolean zorunluluk, int gereksinimSutunNo, String outputType) {
        this.searchHeading = heading;
        this.kontrolZorunluluk = zorunluluk;
        this.gereksinimSutunNo = gereksinimSutunNo;
        if (this.inputFile != null) {
            this.btnKaydet.setEnabled(!this.kontrolZorunluluk);
        }

        this.exportAsExcel = outputType.startsWith("Excel");
    }

    public Logger getLogger() {
        return this.paneLog;
    }

    private String getOpsiyonHeading() {
        return this.searchHeading;
    }

    private int getOpsiyonGereksinimSutunNoIndex() {
        return this.gereksinimSutunNo - 1;
    }





    private int getDeliminator(){
        return deliminator;
    }





    private void btnOpsiyonEventiAyarla() {
        this.btnOpsiyon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.opsiyonlar = new opsGUI(GUI.this, GUI.this.kontrolZorunluluk, GUI.this.searchHeading, GUI.this.gereksinimSutunNo, GUI.this.getOutputExtention(), GUI.this.getDeliminator());
                GUI.this.opsiyonlar.setVisible(true);
            }
        });
    }

    private void btnTemizleEventiAyarla() {
        this.btnTemizle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.this.paneLog.setText("");
            }
        });
    }

    private void btnDosyaSecEventiAyarla() {
        this.btnDosyaSec.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myFileChooser fileopener = new myFileChooser(".docx");
                int ret = fileopener.showOpenDialog(GUI.this);
                if (ret == 0) {
                    GUI.this.inputFile = fileopener.getSelectedExtentionFile();
                    GUI.this.txtDosyaSec.setText(GUI.this.inputFile.getAbsolutePath());
                    GUI.this.btnKontrol.setEnabled(true);
                    GUI.this.btnKaydet.setEnabled(!GUI.this.kontrolZorunluluk);
                    GUI.this.paneLog.append(true, "Seçildi: " + GUI.this.inputFile.getName());
                } else {
                    GUI.this.inputFile = null;
                    GUI.this.txtDosyaSec.setText("");
                    GUI.this.btnKaydet.setEnabled(false);
                    GUI.this.btnKontrol.setEnabled(false);
                }

            }
        });
        DropTarget dte = new DropTarget() {
            public void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(1);
                    List<File> droppedFiles = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    Iterator var4 = droppedFiles.iterator();

                    while(var4.hasNext()) {
                        File file = (File)var4.next();
                        if (!file.getAbsolutePath().toLowerCase().endsWith(".docx")) {
                            GUI.this.inputFile = null;
                            GUI.this.txtDosyaSec.setText("");
                            GUI.this.btnKaydet.setEnabled(false);
                            GUI.this.btnKontrol.setEnabled(false);
                            GUI.this.paneLog.append(false, "İzin verilen: '.docx' ");
                        } else {
                            GUI.this.inputFile = file;
                            GUI.this.txtDosyaSec.setText(file.getAbsolutePath());
                            GUI.this.btnKontrol.setEnabled(true);
                            GUI.this.btnKaydet.setEnabled(!GUI.this.kontrolZorunluluk);
                            GUI.this.paneLog.append(true, "Seçildi: " + file.getName());
                        }
                    }
                } catch (Exception var5) {
                    GUI.this.inputFile = null;
                    GUI.this.txtDosyaSec.setText("");
                    GUI.this.btnKaydet.setEnabled(false);
                    GUI.this.btnKontrol.setEnabled(false);
                }

            }
        };
        this.paneLog.setDropTarget(dte);
    }

    private void btnKaydetEventiAyarla() {
        this.btnKaydet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myFileChooser fileopener = new myFileChooser(GUI.this.getOutputExtention());
                int ret = fileopener.showSaveDialog(GUI.this);
                if (ret == 0) {
                    GUI.this.outputFile = fileopener.getSelectedExtentionFile();
                    if (GUI.this.inputFile != null) {
                        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                            protected Void doInBackground() throws Exception {
                                Isleyici isleyici = new Isleyici(GUI.this.paneLog, GUI.this.getOpsiyonGereksinimSutunNoIndex(), GUI.this.getOpsiyonHeading(), (char) GUI.this.getDeliminator());
                                isleyici.isle(GUI.this.inputFile, GUI.this.outputFile, GUI.this.getOutputExtention());
                                return null;

                                //isleyiciye bu sekilde gonder opsGUI den


                            }
                        };
                        new Loading(GUI.this, worker);
                    } else {
                        GUI.this.paneLog.append(false, GUI.this.paneLog.getText() + "Dosya Seçin !" + "\n");
                    }
                }

            }
        });
    }

    private String getOutputExtention() {
        return this.exportAsExcel ? ".xlsx" : ".docx";
    }

    private void btnKontrolEventiAyarla() {
        this.btnKontrol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (GUI.this.inputFile != null) {
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        protected Void doInBackground() throws Exception {
                            Isleyici isleyici = new Isleyici(GUI.this.paneLog, GUI.this.getOpsiyonGereksinimSutunNoIndex(), GUI.this.getOpsiyonHeading(), (char) GUI.this.getDeliminator());
                            boolean[] flag = isleyici.kontrol(GUI.this.inputFile);
                            if (GUI.this.kontrolZorunluluk) {
                                GUI.this.btnKaydet.setEnabled(flag[0]);
                            }

                            if (flag[1]) {
                                int r = JOptionPane.showConfirmDialog(GUI.this, "Dosya içerisindeki gereksinimler başarıyla okundu.\nDoğrulama yapmak ister misiniz ?", "YGS/SGS Doğrulaması", 0);
                                if (r == 0) {
                                    (new checkGUI(GUI.this, GUI.this.inputFile, GUI.this.paneLog, GUI.this.getOpsiyonGereksinimSutunNoIndex(), GUI.this.getOpsiyonHeading(), (char) GUI.this.getDeliminator())).setVisible(true);
                                }
                            }

                            return null;
                        }
                    };
                    new Loading(GUI.this, worker);
                } else {
                    GUI.this.paneLog.append(false, GUI.this.paneLog.getText() + "Dosya Seçin !" + "\n");
                }

            }
        });
    }
}
