package com.github.lorellw.dictionary3000.exeption;


import java.util.function.Supplier;

public class WebElementNotFoundException extends Exception implements Supplier<WebElementNotFoundException> {

    public WebElementNotFoundException(String text){
        super(String.format("Web element %s not found",text));
    }

    @Override
    public WebElementNotFoundException get() {
        return this;
    }
}
