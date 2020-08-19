//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oksuz.huseyin;

import java.util.ArrayList;

public class TD {
    private ArrayList<gereksinim> gereksinimList;
    private String name;
    private String aciklama;

    public String getAciklama() {
        return this.aciklama;
    }

    public TD(String name, String aciklama, ArrayList<gereksinim> gereksinimList) {
        this.gereksinimList = gereksinimList == null ? null : this.removeRepetition(gereksinimList);
        this.name = name;
        this.aciklama = aciklama;
    }

    public ArrayList<gereksinim> removeRepetition(ArrayList<gereksinim> gereksinimList) {
        ArrayList<gereksinim> newList = new ArrayList();
        String queue = "|";

        for(int i = 0; i < gereksinimList.size(); ++i) {
            if (!queue.contains(((gereksinim)gereksinimList.get(i)).getFullName())) {
                newList.add((gereksinim)gereksinimList.get(i));
                queue = queue + ((gereksinim)gereksinimList.get(i)).getFullName() + "|";
            }
        }

        return newList;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<gereksinim> getGereksinimList() {
        return this.gereksinimList;
    }
}
