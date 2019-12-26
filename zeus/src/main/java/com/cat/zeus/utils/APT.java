package com.cat.zeus.utils;


import com.cat.zeus.annotation.ZeusPage;

public class APT {
    public static int getTitleBarLayoutByAnnotation(Class clazz) {
        ZeusPage page = (ZeusPage) clazz.getAnnotation(ZeusPage.class);
        if (page != null) {
            return page.titleBarLayout();
        }
        return 0;
    }

    public static int getLayoutByAnnotation(Class clazz) {
        ZeusPage page = (ZeusPage) clazz.getAnnotation(ZeusPage.class);
        if (page != null) {
            return page.layout();
        }
        return 0;
    }

    public static String getTitleTextByAnnotation(Class clazz) {
        ZeusPage page = (ZeusPage) clazz.getAnnotation(ZeusPage.class);
        if (null != page) {
            return page.title();
        }
        return null;
    }
}
