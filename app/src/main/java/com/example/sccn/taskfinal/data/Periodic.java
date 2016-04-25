package com.example.sccn.taskfinal.data;

/**
 * Created by SCCN on 14/06/2015.
 */
public class Periodic {
    public int id;
    public String kihieu;
    public String ten;
    public double ptk;
    public String che;
    public String nhom;
    public String chuki;
    public String loai;

    public Periodic(int id, String kihieu, String ten, double ptk, String che, String nhom, String chuki, String loai) {
        this.id = id;
        this.kihieu = kihieu;
        this.ten = ten;
        this.ptk = ptk;
        this.che = che;
        this.nhom = nhom;
        this.chuki = chuki;
        this.loai = loai;
    }
}
