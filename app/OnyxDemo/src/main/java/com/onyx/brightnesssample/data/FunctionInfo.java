package com.onyx.brightnesssample.data;

public class FunctionInfo {

    public Integer tag;
    public String title;
    public String subTitle;

    public FunctionInfo(Integer tag, String title) {
        this.tag = tag;
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public FunctionInfo setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }
}
