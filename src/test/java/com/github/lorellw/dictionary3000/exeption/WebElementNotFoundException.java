package com.github.lorellw.dictionary3000.exeption;

import org.openqa.selenium.WebElement;

import java.util.function.Supplier;

public class WebElementNotFoundException extends Exception implements Supplier<WebElementNotFoundException> {

    public WebElementNotFoundException(){
        super("Web element no found");
    }
    public WebElementNotFoundException(String text){
        super(String.format("Web element %s not found",text));
    }

    @Override
    public WebElementNotFoundException get() {
        return this;
    }
}
