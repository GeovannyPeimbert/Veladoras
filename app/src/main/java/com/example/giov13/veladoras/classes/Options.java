package com.example.giov13.veladoras.classes;

public class Options {
    private String textOption;
    private int imageOption;

    public Options(){}
    public Options(String textOption, int imageOption)
    {
        setTextOption(textOption);
        setImageOption(imageOption);
    }
    public void setTextOption(String textOption) {
        this.textOption = textOption;
    }
    public void setImageOption(int imageOption) {
        this.imageOption = imageOption;
    }
    public String getTextOption() {
        return textOption;
    }
    public int getImageOption() {
        return imageOption;
    }
}
