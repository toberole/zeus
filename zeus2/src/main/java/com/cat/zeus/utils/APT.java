package com.cat.zeus.utils;

import com.org.zeus.annotation.Page;

public class APT {
    public static int getTitleBarLayoutByAnnotation(Class clazz) {
        Page page = (Page) clazz.getAnnotation(Page.class);
        if (page != null) {
            return page.titleBarLayout();
        }
        return 0;
    }

    public static int getLayoutByAnnotation(Class clazz) {
        Page page = (Page) clazz.getAnnotation(Page.class);
        if (page != null) {
            return page.layout();
        }
        return 0;
    }

    public static String getTitleTextByAnnotation(Class clazz) {
        Page page = (Page) clazz.getAnnotation(Page.class);
        if (null != page) {
            return page.title();
        }
        return null;
    }
}
