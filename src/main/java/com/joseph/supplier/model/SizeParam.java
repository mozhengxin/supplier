package com.joseph.supplier.model;

public class SizeParam {

    private int S;
    private int M;
    private int L;
    private int XL;
    private int XXL;
    private int XXXL;

    public int getS() {
        return S;
    }

    public void setS(int s) {
        S = s;
    }

    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public int getXL() {
        return XL;
    }

    public void setXL(int XL) {
        this.XL = XL;
    }

    public int getXXL() {
        return XXL;
    }

    public void setXXL(int XXL) {
        this.XXL = XXL;
    }

    public int getXXXL() {
        return XXXL;
    }

    public void setXXXL(int XXXL) {
        this.XXXL = XXXL;
    }

    @Override
    public String toString() {
        return  S + "-" +
                M + "-" +
                L + "-" +
                XL + "-" +
                XXL + "-" +
                XXXL;
    }
}
