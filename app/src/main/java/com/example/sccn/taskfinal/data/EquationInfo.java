package com.example.sccn.taskfinal.data;

/**
 * Created by SCCN on 10/06/2015.
 */
public class EquationInfo {
    // dot tieng anh nen note : thong tin phuong trinh
    public int id;
    public String nhietdo;
    public String apsuat;
    public String xuctac;
    public String hientuong;
    public String loai;

    private String check(String data) {
        if (data.equals(""))
            return "NULL";
        return data;
    }

    public EquationInfo(int id, String nhietdo, String apsuat, String xuctac, String hientuong, String loai) {
        this.id = id;
        this.nhietdo = check(nhietdo);
        this.apsuat = check(apsuat);
        this.xuctac = check(xuctac);
        this.hientuong = check(hientuong);
        this.loai = check(loai);
    }

    @Override
    public String toString() {
        StringBuffer data = new StringBuffer();
        if (!nhietdo.equals("NULL"))
            data.append("Nhiệt độ : " + nhietdo + "\n");
        if (!apsuat.equals("NULL"))
            data.append("Áp xuất : " + apsuat + "\n");
        if (!xuctac.equals("NULL"))
            data.append("Xúc tác : " + xuctac + "\n");
        if (!hientuong.equals("NULL"))
            data.append("Hiện tượng : " + hientuong + "\n");
        if (!loai.equals("NULL"))
            data.append("Loại : " + loai + "\n");
        return data.toString();
    }
}
