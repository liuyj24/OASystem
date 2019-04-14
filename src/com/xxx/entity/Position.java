package com.xxx.entity;

public class Position {
    private int posid;
    private String pname;
    private String pdesc;

    public Position() {
    }

    public Position(int posid, String pname, String pdesc) {
        this.posid = posid;
        this.pname = pname;
        this.pdesc = pdesc;
    }

    public int getPosid() {
        return posid;
    }

    public void setPosid(int posid) {
        this.posid = posid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }
}
