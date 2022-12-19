package com.github.lorellw.dictionary3000.enums;


public enum Status {
    NEW, ONSTUDY, STUDIED,BROKEN;

    public String getName(){
        if (this.equals(NEW)) return "New";
        if (this.equals(ONSTUDY)) return "On Study";
        if (this.equals(STUDIED)) return "Studied";
        return "BROKEN";
    }
}
