package com.example.sccn.taskfinal.data;

import java.util.ArrayList;

/**
 * Created by SCCN on 16/06/2015.
 */
public class ReactionEquation {
    public int id;
    public EquationInfo info;
    public ArrayList<EquationSb> Pu;
    public ArrayList<EquationSb> Sp;
    public StringBuffer datatext = new StringBuffer();
    public StringBuffer infotext = new StringBuffer();

    public ReactionEquation(int id, EquationInfo info, ArrayList<EquationSb> pu, ArrayList<EquationSb> sp) {
        this.id = id;
        this.info = info;
        Pu = pu;
        Sp = sp;
        show();
    }

    public ReactionEquation() {
        super();
        Pu = new ArrayList<EquationSb>();
        Sp = new ArrayList<EquationSb>();
    }

    public void show() {

        for (int i = 0; i < Pu.size(); i++) {
            if (!Pu.get(i).heso.equals("1"))
                datatext.append(Pu.get(i).heso);
            datatext.append(Pu.get(i).kihieu);
            datatext.append(" + ");
            if (!Pu.get(i).mausac.equals("NULL")) {
                infotext.append(Pu.get(i).kihieu);
                infotext.append(" : ");
                infotext.append(Pu.get(i).mausac);
                infotext.append("\n");
            }
            if (!Pu.get(i).trangthai.equals("NULL")) {
                infotext.append(Pu.get(i).kihieu);
                infotext.append(" : ");
                infotext.append(Pu.get(i).trangthai);
                infotext.append("\n");
            }
        }

        datatext.delete(datatext.length() - 2, datatext.length());
        datatext.append(" = ");

        for (int i = 0; i < Sp.size(); i++) {
            if (!Sp.get(i).heso.equals("1"))
                datatext.append(Sp.get(i).heso);
            datatext.append(Sp.get(i).kihieu);
            datatext.append(" + ");
            if (!Sp.get(i).mausac.equals("NULL")) {
                infotext.append(Sp.get(i).kihieu);
                infotext.append(" : ");
                infotext.append(Sp.get(i).mausac);
                infotext.append("\n");
            }
            if (!Sp.get(i).trangthai.equals("NULL")) {
                infotext.append(Sp.get(i).kihieu);
                infotext.append(" : ");
                infotext.append(Sp.get(i).trangthai);
                infotext.append("\n");
            }
        }

        infotext.append(info.toString());
        datatext.delete(datatext.length() - 2, datatext.length());
    }

}

