//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oksuz.huseyin;

import java.io.File;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class myFileChooser extends JFileChooser {
    private String extention = "";
    private ArrayList<String> extraExtentions = new ArrayList();

    public myFileChooser(String extention) {
        this.extention = extention;
        this.setAcceptAllFileFilterUsed(false);
        this.setMultiSelectionEnabled(false);
        this.setFileFilter(new FileFilter() {
            public String getDescription() {
                return "İzin verilen : " + myFileChooser.this.extention;
            }

            public boolean accept(File f) {
                return f.isDirectory() || f.getAbsolutePath().toLowerCase().endsWith(myFileChooser.this.extention);
            }
        });
    }

    public void addExtention(final String extention) {
        this.addChoosableFileFilter(new FileFilter() {
            public String getDescription() {
                return "İzin verilen : " + extention;
            }

            public boolean accept(File f) {
                return f.isDirectory() || f.getAbsolutePath().toLowerCase().endsWith(extention);
            }
        });
        this.extraExtentions.add(extention);
    }

    public void approveSelection() {
        File f = this.getSelectedExtentionFile();
        if (f.exists() && this.getDialogType() == 1) {
            int donus = JOptionPane.showOptionDialog(this, "Dosya zaten mevcut.\nÜzerine Yazılsın mı?", "Varolan Dosya", 0, 3, (Icon)null, new String[]{"Evet", "Hayır"}, "Hayır");
            if (donus == 1) {
                return;
            }
        } else if (!f.exists() && this.getDialogType() == 0) {
            JOptionPane.showMessageDialog(this, "Dosya mevcut değil!");
            return;
        }

        super.approveSelection();
    }

    public File getSelectedExtentionFile() {
        File f = this.getSelectedFile();
        String ext = this.getFileFilter().getDescription().substring(this.getFileFilter().getDescription().lastIndexOf("."));
        String path = f.getAbsolutePath();
        if (!path.toLowerCase().endsWith(ext)) {
            path = path + ext;
            f = new File(path);
        }

        return f;
    }
}
