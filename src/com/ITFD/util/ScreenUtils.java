package com.ITFD.util;

import java.awt.*;

public class ScreenUtils {
    //��ȡ������Ļ���
    public static int getScreenWidth(){
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    //��ȡ������Ļ�߶�
    public static int getScreenHeight(){
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
}
