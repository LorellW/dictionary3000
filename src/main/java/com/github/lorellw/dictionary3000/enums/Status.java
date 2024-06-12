package com.github.lorellw.dictionary3000.enums;


public enum Status {
    NEW, ONSTUDY, STUDIED,BROKEN;

    public String getName(){
        return switch (this){
            case NEW -> "New";
            case ONSTUDY -> "On Study";
            case STUDIED -> "Studied";
            case BROKEN -> "BROKEN";
        };
    }
}
