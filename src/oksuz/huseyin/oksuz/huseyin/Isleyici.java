//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oksuz.huseyin;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Isleyici<DeployFileOutputStream> {
    public Logger paneLog = null;
    public XWPFDocument document = null;
    private File inputFile = null;
    public ArrayList<String> tekrarEdenTDs = new ArrayList();
    private int gereksinimSutunNoIndex = 5;
    private String headingStyle = "heading3";
    private char deliminator;
    private String dil = "turkce";
    private ArrayList<String[]> TD_Names_Details;
    private List<XWPFTable> tableList;
    private List<XWPFTable> tdTableList;
    private ArrayList<gereksinim> gerekList;
    private ArrayList<String[]> hatalar;
    ArrayList<ArrayList<gereksinim>> NotUniqueGereksinimListLists;
    ArrayList<TD> tdList;
    ArrayList<gereksinim> uniqueGereksinimList;
    private File outputFile;
    private String extention;
    private DeployFileOutputStream doc;

    public Isleyici(Logger paneLog, int gereksinimSutunNoIndex, String headingStyle, char deliminator) {
        this.gereksinimSutunNoIndex = gereksinimSutunNoIndex;
        this.paneLog = paneLog;
        this.headingStyle = headingStyle.replace(" ", "").toLowerCase();
        this.deliminator = deliminator;
    }


    //buraya bi public isleyici daha yaz
    public String YGSSGS(File inputFile, ArrayList<String> karsilastirilacak) {
        this.inputFile = inputFile;

        try {
            this.openInputFile();
            this.findTestDurumuTablolari();
            this.getNotUniqueGereksinimListLists();
            this.createUniqueGereksinimList();
            this.closeIt(this.document);
            return this.checkUniqueListByNameList(this.uniqueGereksinimList, karsilastirilacak);
        } catch (Exception var4) {
            var4.printStackTrace();
            this.closeIt(this.document);
            return "Doğrulama sırasında hata oluştu!";
        }
    }

    public boolean[] kontrol(File inputFile) {
        this.inputFile = inputFile;
        boolean[] ret = new boolean[2];

        try {
            this.openInputFile();
            this.paneLog.append(true, this.inputFile.getName() + " doğrulanıyor...");
            this.findTDNamesDetails();
            this.printBulunanTestDurumlari();
            this.findTestDurumuTablolari();
            this.testDurumlariniTablolarlaEslestir();
            ret[1] = true;
            this.parantezHatalariniBul();
            this.parantezHatalariniYazdir();
        } catch (Exception var4) {
            this.paneLog.append(true, "Hata: " + var4.getMessage());
            this.closeIt(this.document);
            return ret;
        }

        ret[0] = true;
        this.closeIt(this.document);
        return ret;
    }

    public void isle(File inputFile, File outputFile, String extention) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.extention = extention;

        try {
            this.openInputFile();
            this.paneLog.append(true, this.inputFile.getName() + " doğrulanıyor...");
            this.findTDNamesDetails();
            this.findTestDurumuTablolari();
            this.getNotUniqueGereksinimListLists();
            this.testDurumlariniTablolarlaEslestir();
            this.createTDs();
            this.createUniqueGereksinimList();
            this.addTDsToGereksinimLists();
            this.createOutput();
        } catch (Exception var5) {
            this.paneLog.append(true, "Hata: " + var5.getMessage());
        }

        this.closeIt(this.document);
    }

    private void printBulunanTestDurumlari() {
        this.paneLog.append(false, "-----------------------------------------------------------------------------------------------------------------------------");
        this.paneLog.append(false, "\t                   BULUNAN TEST DURUMLARI");
        this.paneLog.append(false, "-----------------------------------------------------------------------------------------------------------------------------");

        for(int i = 0; i < this.TD_Names_Details.size(); ++i) {
            this.paneLog.append(true, ((String[])this.TD_Names_Details.get(i))[0] + ", " + ((String[])this.TD_Names_Details.get(i))[1]);
        }

        this.paneLog.append(false, " ");


    }

    private String checkUniqueListByNameList(ArrayList<gereksinim> uniqueList, ArrayList<String> nameList) {
        Collections.sort(nameList);
        ArrayList<String> fazlalar = new ArrayList();
        String fazlaKuyruk = "|";

        int i;
        for(i = 0; i < nameList.size(); ++i) {
            fazlaKuyruk = fazlaKuyruk + (String)nameList.get(i) + "|";
        }

        String eksikKuyruk;
        for(i = 0; i < uniqueList.size(); ++i) {
            eksikKuyruk = ((gereksinim)uniqueList.get(i)).getFullName();
            if (!fazlaKuyruk.contains(eksikKuyruk)) {
                fazlalar.add(eksikKuyruk);
            }
        }

        ArrayList<String> eksikler = new ArrayList();
        eksikKuyruk = "|";

        for(i = 0; i < uniqueList.size(); ++i) {
            eksikKuyruk = eksikKuyruk + ((gereksinim)uniqueList.get(i)).getFullName() + "|";
        }

        for(i = 0; i < nameList.size(); ++i) {
            String nameReq = (String)nameList.get(i);
            if (!eksikKuyruk.contains(nameReq)) {
                eksikler.add(nameReq);
            }
        }

        String sonuc = "";
        if (fazlalar.size() > 0) {
            sonuc = sonuc + "Test Prosedüründeki Fazlalıklar :\n";
        }

        int total;
        for(total = 0; total < fazlalar.size(); ++total) {
            sonuc = sonuc + (String)fazlalar.get(total) + "\n";
        }

        if (eksikler.size() > 0) {
            sonuc = sonuc + "Test Prosedüründeki Eksiklikler :\n";
        }

        for(total = 0; total < eksikler.size(); ++total) {
            sonuc = sonuc + (String)eksikler.get(total) + "\n";
        }

        total = fazlalar.size() + eksikler.size();
        if (total > 0) {
            sonuc = sonuc + "Toplam " + total + " adet fark bulundu.";
        } else {
            sonuc = sonuc + "Test Prosedürü ile girilen liste arasında fark bulunamadı.";
        }

        System.out.println(sonuc);
        return sonuc;
    }

    private void openInputFile() throws Exception {
        try {
            this.document = new XWPFDocument(new FileInputStream(this.inputFile));
        } catch (Exception var2) {
            throw new Exception("Word dosyası açılamadı.");
        }
    }

    private void findTestDurumuTablolari() throws Exception {
        try {
            this.tableList = ((IBodyElement)this.document.getBodyElementsIterator().next()).getBody().getTables();
            this.tdTableList = new ArrayList();
            Iterator var2 = this.tableList.iterator();

            while(var2.hasNext()) {
                XWPFTable table = (XWPFTable)var2.next();
                String tabloBaslangici = table.getText().trim().toLowerCase().replaceAll("[\t\\s+]", " ");
                if (tabloBaslangici.startsWith("adım no test adımı ") || tabloBaslangici.startsWith("step no ")) {
                    this.tdTableList.add(table);
                }
            }
        } catch (Exception var4) {
            throw new Exception("Test Adımları tabloları okunamadı.");
        }

        if (this.tdTableList.size() < 1) {
            throw new Exception("Test Adımları tabloları bulunamadı.");
        }
    }

    private void testDurumlariniTablolarlaEslestir() throws Exception {
        if (this.TD_Names_Details.size() != this.tdTableList.size()) {
            this.paneLog.append(true, "Test Durumu sayısı ile tablo sayısı uyuşmazlığı bulundu.");
            this.paneLog.append(true, "Bulunan Test Durumu Sayısı: " + this.TD_Names_Details.size() + ", Bulunan Tablo Sayısı: " + this.tdTableList.size());
            if (this.tekrarEdenTDs.size() > 0) {
                String sirala = "";

                for(int i = 0; i < this.tekrarEdenTDs.size(); ++i) {
                    sirala = sirala + ", " + (String)this.tekrarEdenTDs.get(i);
                }

                sirala = sirala.substring(2);
                this.paneLog.append(true, "Tekrar eden test durumları var: " + sirala);
            }

            throw new Exception("Test durumları tablolarla eşleşemedi.");
        }
    }

    private void parantezHatalariniBul() throws Exception {
        this.gerekList = new ArrayList();
        this.hatalar = new ArrayList();
            switch (deliminator){
                case '[':
                    try {
                        for(int ii = 0; ii < this.tdTableList.size(); ++ii) {
                            this.gerekList = this.getGereksinimListAtTable((XWPFTable)this.tdTableList.get(ii), ((String[])this.TD_Names_Details.get(ii))[0]);

                            for(int i = 0; i < this.gerekList.size(); ++i) {
                                boolean dogrula = false;

                                try {
                                    dogrula = ((gereksinim)this.gerekList.get(i)).checkAt(this.gerekList);
                                } catch (Exception var5) {
                                    throw new Exception("Gereksinimler doğrulanırken beklenmedik hata.");
                                }

                                if (!dogrula) {
                                    this.hatalar.add(new String[]{((gereksinim)this.gerekList.get(i)).getFullName(), "" + ii, ((gereksinim)this.gerekList.get(i)).getAdimNo()});
                                }
                            }
                        }

                    } catch (Exception var6) {
                        throw new Exception("Gereksinim okuma başarısız. (" + var6.getMessage() + ")");
                    }
                    break;
                case ';':

            }

    }

    private void parantezHatalariniYazdir() throws Exception {
        this.paneLog.append(false, "-----------------------------------------------------------------------------------------------------------------------------");
        this.paneLog.append(false, "\t                   GEREKSİNİM DOĞRULAMA RAPORU");
        this.paneLog.append(false, "-----------------------------------------------------------------------------------------------------------------------------");
        int hataBoyu = this.hatalar.size();

        try {
            for(int i = 0; i < hataBoyu; ++i) {
                int cc = Integer.parseInt(((String[])this.hatalar.get(i))[1]);
                this.paneLog.append(true, ((String[])this.hatalar.get(i))[0] + " doğrulaması hatalı. (" + ((String[])this.TD_Names_Details.get(cc))[0] + ", Adım No:" + ((String[])this.hatalar.get(i))[2] + ")\n");
                if (i > 19 && hataBoyu > 20) {
                    break;
                }
            }
        } catch (Exception var4) {
            throw new Exception("Bulunan hatalar ekrana yazdırılamadı.");
        }

        if (this.hatalar.size() > 1) {
            throw new Exception("Doğrulama hataları bulundu. (Toplam: " + hataBoyu + ")");
        } else if (this.hatalar.size() > 0) {
            throw new Exception("Doğrulama hatası bulundu.");
        } else {
            this.paneLog.append(true, "Doğrulama başarıyla sonuçlandı.");
        }
    }

    private void getNotUniqueGereksinimListLists() throws Exception {
        this.NotUniqueGereksinimListLists = new ArrayList();

        try {
            for(int i = 0; i < this.tdTableList.size(); ++i) {
                this.NotUniqueGereksinimListLists.add(this.getGereksinimListAtTable((XWPFTable)this.tdTableList.get(i), (String)null));
            }

        } catch (Exception var2) {
            throw new Exception("Gereksinim okuma başarısız.");
        }
    }

    private void createUniqueGereksinimList() throws Exception {
        this.uniqueGereksinimList = new ArrayList();

        try {
            for(int i = 0; i < this.NotUniqueGereksinimListLists.size(); ++i) {
                this.uniqueGereksinimList.addAll((Collection)this.NotUniqueGereksinimListLists.get(i));
            }

            this.uniqueGereksinimList = (new TD("", "", (ArrayList)null)).removeRepetition(this.uniqueGereksinimList);
            Collections.sort(this.uniqueGereksinimList);
        } catch (Exception var2) {
            throw new Exception("Gereksinimlerin sıralanması sırasında hata oluştu.");
        }
    }

    private void addTDsToGereksinimLists() throws Exception {
        try {
            for(int i = 0; i < this.uniqueGereksinimList.size(); ++i) {
                for(int j = 0; j < this.tdList.size(); ++j) {
                    for(int k = 0; k < ((TD)this.tdList.get(j)).getGereksinimList().size(); ++k) {
                        if (((gereksinim)this.uniqueGereksinimList.get(i)).getCodeAsInt() == ((gereksinim)((TD)this.tdList.get(j)).getGereksinimList().get(k)).getCodeAsInt()) {
                            ((gereksinim)this.uniqueGereksinimList.get(i)).addTD(((TD)this.tdList.get(j)).getName());
                        }
                    }
                }
            }

        } catch (Exception var4) {
            throw new Exception("Gereksinimler Test Durumları ile eşlenemedi.");
        }
    }

    private void createOutput() throws Exception {
        try {
            FileOutputStream out = new FileOutputStream(this.outputFile);
            if (this.extention.equals(".docx")) {
                XWPFDocument doc = this.createWordTableFile(this.tdList, this.uniqueGereksinimList);
                doc.write(out);
                this.closeIt(out);
                this.closeIt(doc);
            } else {
                XSSFWorkbook xls = this.createExcelTableFile(this.tdList, this.uniqueGereksinimList);
                xls.write(out);
                this.closeIt(out);
                this.closeIt(xls);
            }

            Runtime.getRuntime().exec("cmd.exe /c cmd.exe /c \"" + this.outputFile.getAbsolutePath() + "\"");
        } catch (Exception var3) {
            throw new Exception("Dosya yazma başarısız.");
        }
    }

    private void createTDs() {
        this.tdList = new ArrayList();

        for(int i = 0; i < this.TD_Names_Details.size(); ++i) {
            this.tdList.add(new TD(((String[])this.TD_Names_Details.get(i))[0], ((String[])this.TD_Names_Details.get(i))[1], (ArrayList)this.NotUniqueGereksinimListLists.get(i)));
        }

    }

    private void closeIt(XWPFDocument doc) {
        try {
            doc.close();
        } catch (IOException var3) {
        }

    }

    private void closeIt(XSSFWorkbook wb) {
        try {
            wb.close();
        } catch (IOException var3) {
        }

    }

    private void closeIt(FileOutputStream fos) {
        try {
            fos.close();
        } catch (IOException var3) {
        }

    }

    private XWPFDocument createWordTableFile(ArrayList<TD> tdList, ArrayList<gereksinim> gereksinimList) throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFTable table = doc.createTable();
        XWPFTableRow tableRowOne = table.getRow(0);
        if (this.dil.equals("turkce")) {
            tableRowOne.getCell(0).setText("Test Durumu");
            tableRowOne.addNewTableCell().setText("Gereksinim");
        } else {
            tableRowOne.getCell(0).setText("Test Case");
            tableRowOne.addNewTableCell().setText("Requirement");
        }

        XWPFTableRow following;
        int i;
        for(i = 0; i < tdList.size(); ++i) {
            following = null;
            Collections.sort(((TD)tdList.get(i)).getGereksinimList());

            for(i = 0; i < ((TD)tdList.get(i)).getGereksinimList().size(); ++i) {
                following = table.createRow();
                following.getCell(0).setText(((TD)tdList.get(i)).getName());
                following.getCell(1).setText(((gereksinim)((TD)tdList.get(i)).getGereksinimList().get(i)).getFullName());
                if (i == 0) {
                    following.getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
                } else {
                    following.getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                }
            }
        }

        doc.createParagraph().createRun().addBreak();
        XWPFTable table2 = doc.createTable();
        following = table2.getRow(0);
        if (this.dil.equals("turkce")) {
            following.getCell(0).setText("Gereksinim");
            following.addNewTableCell().setText("Test Durumu");
        } else {
            following.getCell(0).setText("Requirement");
            following.addNewTableCell().setText("Test Case");
        }

        for(i = 0; i < gereksinimList.size(); ++i) {
            following = null;

            for(int j = 0; j < ((gereksinim)gereksinimList.get(i)).getTDs().size(); ++j) {
                following = table2.createRow();
                following.getCell(0).setText(((gereksinim)gereksinimList.get(i)).getFullName());
                following.getCell(1).setText((String)((gereksinim)gereksinimList.get(i)).getTDs().get(j));
                if (j == 0) {
                    following.getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
                } else {
                    following.getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                }
            }
        }

        return doc;
    }

    private XSSFWorkbook createExcelTableFile(ArrayList<TD> tdList, ArrayList<gereksinim> gereksinimList) throws Exception {
        XSSFWorkbook xls = new XSSFWorkbook();
        XSSFSheet sheet = xls.createSheet("Test->Gerek");
        XSSFCellStyle topleft = xls.createCellStyle();
        topleft.setAlignment(HorizontalAlignment.forInt((short)1));
        topleft.setVerticalAlignment(VerticalAlignment.forInt((short)0));
        XSSFRow headerRow = sheet.createRow(0);
        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 8000);
        if (this.dil.equals("turkce")) {
            headerRow.createCell(0).setCellValue("Test Durumu");
            headerRow.createCell(1).setCellValue("Gereksinim");
        } else {
            headerRow.createCell(0).setCellValue("Test Case");
            headerRow.createCell(1).setCellValue("Requirement");
        }

        int rowNumber = 1;

        int i;
        XSSFRow following;
        int mergeBitis;
        for(i = 0; i < tdList.size(); ++i) {
            following = null;
            Collections.sort(((TD)tdList.get(i)).getGereksinimList());

            for(mergeBitis = 0; mergeBitis < ((TD)tdList.get(i)).getGereksinimList().size(); ++mergeBitis) {
                following = sheet.createRow(rowNumber++);
                if (mergeBitis == 0) {
                    following.createCell(0).setCellValue(((TD)tdList.get(i)).getName());
                }

                following.createCell(1).setCellValue(((gereksinim)((TD)tdList.get(i)).getGereksinimList().get(mergeBitis)).getFullName());
            }

            mergeBitis = rowNumber + ((TD)tdList.get(i)).getGereksinimList().size() - 1;
            if (mergeBitis > rowNumber) {
                sheet.addMergedRegion(new CellRangeAddress(rowNumber, mergeBitis, 0, 0));
                sheet.getRow(rowNumber).getCell(0).setCellStyle(topleft);
            }
        }

        sheet = xls.createSheet("Gerek->Test");
        headerRow = sheet.createRow(0);
        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 8000);
        if (this.dil.equals("turkce")) {
            headerRow.createCell(0).setCellValue("Gereksinim");
            headerRow.createCell(1).setCellValue("Test Durumu");
        } else {
            headerRow.createCell(0).setCellValue("Requirement");
            headerRow.createCell(1).setCellValue("Test Case");
        }

        rowNumber = 1;

        for(i = 0; i < gereksinimList.size(); ++i) {
            following = null;

            for(mergeBitis = 0; mergeBitis < ((gereksinim)gereksinimList.get(i)).getTDs().size(); ++mergeBitis) {
                following = sheet.createRow(rowNumber++);
                if (mergeBitis == 0) {
                    following.createCell(0).setCellValue(((gereksinim)gereksinimList.get(i)).getFullName());
                }

                following.createCell(1).setCellValue((String)((gereksinim)gereksinimList.get(i)).getTDs().get(mergeBitis));
            }

            mergeBitis = rowNumber + ((gereksinim)gereksinimList.get(i)).getTDs().size() - 1;
            if (mergeBitis > rowNumber) {
                sheet.addMergedRegion(new CellRangeAddress(rowNumber, mergeBitis, 0, 0));
                sheet.getRow(rowNumber).getCell(0).setCellStyle(topleft);
            }
        }

        return xls;
    }

    private ArrayList<gereksinim> getGereksinimListAtTable(XWPFTable table, String TDName) throws Exception {
        String adimNo = "";
        String str = "";
        int rowCount = table.getNumberOfRows();
        ArrayList<gereksinim> gerCheckList = new ArrayList();

        for(int i = 2; i < rowCount; ++i) {
            if (table.getRow(i) != null) {
                adimNo = "" + (i - 1);

                try {
                    try {
                        str = table.getRow(i).getCell(this.gereksinimSutunNoIndex).getTextRecursively().trim();
                    } catch (Exception var18) {
                        continue;
                    }

                    if (str.length() > 0) {
                        str = str.replaceAll("\\s+", " ");
                        String regex2 = "(\\[?)(([A-Za-z0-9sışçöğüİŞĞÜÖÇ]+ ?[\\-])+ ?)([A-Za-zsışçöğüİŞĞÜÖÇ]*[\\d+\\.]+[A-Za-zsışçöğüİŞĞÜÖÇ]*)(\\]?)";
                        String afterRegex = str.replaceAll(regex2, "|||$1;;;$2;;;$4;;;$5\n");
                        if (!str.equals(afterRegex)) {
                            String[] hucredekiGereksinimler = afterRegex.trim().replace(" ", "").split("\n");

                            for(int j = 0; j < hucredekiGereksinimler.length; ++j) {
                                String myData = "";

                                try {
                                    if (TDName != null && hucredekiGereksinimler[j].indexOf("|||") > 0) {
                                        this.paneLog.append(true, "UYARI: Fazla karakter tespit edildi. (" + TDName + ", Adım No:" + (i - 1) + ")\n");
                                    }

                                    myData = hucredekiGereksinimler[j].substring(hucredekiGereksinimler[j].indexOf("|||") + 3);
                                } catch (Exception var19) {
                                    if (TDName != null) {
                                        this.paneLog.append(true, "UYARI: Fazla karakter tespit edildi. (" + TDName + ", Adım No:" + (i - 1) + ")\n");
                                    }
                                    continue;
                                }

                                String[] detaylar = myData.split(";;;", 4);
                                if (this.deliminator == '[') {
                                    boolean ilkParantez = detaylar[0].equals("[");
                                    boolean sonParantez = detaylar[3].equals("]");
                                    String prefix = detaylar[1];
                                    String kod = detaylar[2];
                                    gerCheckList.add(new gereksinim(ilkParantez, prefix, kod, sonParantez, adimNo));
                                }
                                if (this.deliminator == ';') {
                                    boolean noktalivurgul = detaylar[3].equals(";");
                                    String prefix = detaylar[1];
                                    String kod = detaylar[2];
                                    gerCheckList.add(new gereksinim(prefix, kod, noktalivurgul, adimNo));
                                }

                            }
                        }
                    }
                } catch (Exception var20) {
                    if (TDName != null) {
                        throw new Exception(TDName + ", Adım No:" + (i - 1), var20);
                    }

                    throw new Exception("", var20);
                }
            }
        }

        return gerCheckList;
    }

    private void findTDNamesDetails() throws Exception {
        this.TD_Names_Details = new ArrayList();
        this.tekrarEdenTDs = new ArrayList();
        String queue = "|";
        List paragraphs = null;

        try {
            paragraphs = this.document.getParagraphs();
        } catch (Exception var13) {
            throw new Exception("Word Dosyası paragrafları okunamadı.");
        }

        this.paneLog.append(true, "İçindekiler tablosunda test durumları aranıyor.");

        int j;
        XWPFParagraph paragraph;
        String style;
        String satir;
        String tdName;
        String aciklama;
        int baslangic;
        int bitis;
        String onTaraf;
        String geriKalan;
        for(j = 0; j < paragraphs.size(); ++j) {
            paragraph = (XWPFParagraph)paragraphs.get(j);
            style = paragraph.getStyle() != null ? paragraph.getStyle() : "null";
            if (style.toLowerCase().contains("toc")) {
                satir = paragraph.getText().replaceAll("\\s+", " ").trim();
                tdName = satir.replaceAll("(.*?)(([A-Za-z0-9sışçöğüİŞĞÜÖÇ]+ ?[-])+ ?[A-Za-zsışçöğüİŞĞÜÖÇ]*[\\d+\\.]+[A-Za-zsışçöğüİŞĞÜÖÇ]*)(.*)", "$2").replaceAll("\\s?\\-\\s?", "-");
                aciklama = satir.replaceAll("(.*?)((\\d+\\.)+\\d+)(.*?)(\\s\\d)(\\d*)", "$4").trim().replaceAll("\\s?\\-\\s?", "-");
                if (aciklama.contains(tdName)) {
                    baslangic = aciklama.indexOf(tdName);
                    bitis = baslangic + tdName.length();
                    onTaraf = aciklama.substring(0, baslangic);
                    geriKalan = aciklama.substring(bitis);
                    aciklama = (onTaraf + geriKalan).replaceAll("[^A-Za-z sışçöğüİŞĞÜÖÇ]", "").trim();
                }

                if (!tdName.equals(satir)) {
                    if (!queue.contains(tdName)) {
                        queue = queue + tdName + "|";
                        this.TD_Names_Details.add(new String[]{tdName, aciklama});
                    } else {
                        this.tekrarEdenTDs.add(tdName);
                    }
                }
            }
        }

        if (this.TD_Names_Details.size() < 1) {
            this.paneLog.append(true, this.headingStyle.toUpperCase().substring(0, 1) + this.headingStyle.substring(1) + " başlıklarında test durumları aranıyor.");

            for(j = 0; j < paragraphs.size(); ++j) {
                paragraph = (XWPFParagraph)paragraphs.get(j);
                style = paragraph.getStyle() != null ? paragraph.getStyle() : "null";
                satir = paragraph.getParagraphText();
                if (style.toLowerCase().equals(this.headingStyle)) {
                    satir = paragraph.getText().replaceAll("\\s+", " ").trim();
                    tdName = satir.replaceAll("(.*?)(([A-Za-z0-9sışçöğüİŞĞÜÖÇ]+ ?[-])+ ?[A-Za-zsışçöğüİŞĞÜÖÇ]*[\\d\\.]+[A-Za-zsışçöğüİŞĞÜÖÇ]*)(.*)", "$2").replaceAll("\\s?\\-\\s?", "-");
                    aciklama = satir.replaceAll("(.*?)((\\d+\\.)+\\d+)(.*?)(\\s\\d)(\\d*)", "$4").trim().replaceAll("\\s?\\-\\s?", "-");
                    if (aciklama.contains(tdName)) {
                        baslangic = aciklama.indexOf(tdName);
                        bitis = baslangic + tdName.length();
                        onTaraf = aciklama.substring(0, baslangic);
                        geriKalan = aciklama.substring(bitis);
                        aciklama = (onTaraf + geriKalan).replaceAll("[^A-Za-z sışçöğüİŞĞÜÖÇ]", "").trim();
                    }

                    if (!tdName.equals(satir) && !queue.contains(tdName)) {
                        queue = queue + tdName + "|";
                        this.TD_Names_Details.add(new String[]{tdName, aciklama});
                    }
                }
            }

            if (this.TD_Names_Details.size() < 1) {
                throw new Exception("Test Durumları bulunamadı.");
            }
        }

    }
}
