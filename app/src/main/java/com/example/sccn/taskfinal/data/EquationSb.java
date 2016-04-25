package com.example.sccn.taskfinal.data;

/**
 * Created by SCCN on 10/06/2015.
 */
public class EquationSb {
    // dot tieng anh nen note : chat tham gia phu
    public int id;
    public String kihieu;
    public String heso;
    public String trangthai;
    public String mausac;

    private String check(String data) {
        if (data.equals(""))
            return "NULL";
        return data;
    }

    public EquationSb(int id, String kihieu, String heso, String trangthai, String mausac) {
        this.id = id;
        this.kihieu = kihieu;
        this.heso = heso;
        this.trangthai = check(trangthai);
        this.mausac = check(mausac);

    }
}