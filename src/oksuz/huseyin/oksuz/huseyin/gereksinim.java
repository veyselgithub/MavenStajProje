//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oksuz.huseyin;

import java.util.ArrayList;

public class gereksinim implements Comparable<gereksinim> {
    private String code;
    private String prefix;
    private String fullName;
    private ArrayList<String> TDList = new ArrayList();
    private String queue = "|";
    private String AdimNo;
    private boolean begin = false;
    private boolean end = false;
    private boolean undefined = false;
    private boolean isChecked = false;

    public gereksinim(boolean begin, String prefix, String code, boolean end, String AdimNo) {
        this.code = code;
        this.prefix = prefix;
        this.fullName = prefix + code;
        this.begin = begin;
        this.end = end;
        this.AdimNo = AdimNo;
        if (!begin && !end) {
            this.undefined = true;
        }

        if (begin && end) {
            this.isChecked = true;
        }

    }


    public gereksinim(String prefix, String code, boolean end, String AdimNo) {
        this.code = code;
        this.prefix = prefix;
        this.fullName = prefix + code;
        this.end = end;
        this.AdimNo = AdimNo;
        if (!begin && !end) {
            this.undefined = true;
        }

        if (begin && end) {
            this.isChecked = true;
        }

    }


    public String getAdimNo() {
        return this.AdimNo;
    }

    public boolean checkAt(ArrayList<gereksinim> list) {
        if (this.isChecked) {
            return true;
        } else if (this.undefined) {
            return false;
        } else {
            for(int i = 0; i < list.size(); ++i) {
                boolean condition = this.isMatchWith((gereksinim)list.get(i));
                if (condition) {
                    return true;
                }
            }

            return false;
        }
    }

    private boolean isMatchWith(gereksinim g) {
        if (!g.isUndefined() && !g.isChecked()) {
            if (this.begin && g.isEnd() && g.getFullName().equals(this.fullName)) {
                g.checked();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isChecked() {
        return this.isChecked;
    }

    private boolean isEnd() {
        return this.end;
    }

    private boolean isBegin() {
        return this.begin;
    }

    private boolean isUndefined() {
        return this.undefined;
    }

    private void checked() {
        this.isChecked = true;
    }

    public int getCodeAsInt() {
        return Integer.parseInt(this.code);
    }

    public String getFullName() {
        return this.fullName;
    }

    private String getCodeAsString() {
        return this.code;
    }

    public void addTD(String TDName) {
        if (!this.queue.contains(TDName)) {
            this.queue = this.queue + TDName + "|";
            this.TDList.add(TDName);
        }

    }

    public int compareTo(gereksinim g) {
        return this.getFullName().compareTo(g.getFullName());
    }

    public ArrayList<String> getTDs() {
        return this.TDList;
    }
}
