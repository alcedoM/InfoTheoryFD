package com.ITFD.components;

import com.ITFD.components.Result;
import com.ITFD.util.ScreenUtils;

import javax.swing.*;
import java.awt.*;

public class ResultCard {
    JFrame jf = new JFrame("������");

    int screenWidth = ScreenUtils.getScreenWidth();
    int screenHeight = ScreenUtils.getScreenHeight();
    final int WIDTH = screenWidth/2;
    final int HEIGHT = screenHeight/2;
    //��ͷ
    Object[] title = {"����","����","����","����","����","����"};

    //��װ��ͼ
    public void init(Result res){
        init((screenWidth-WIDTH)/2,(screenHeight-HEIGHT)/2,res);
    }
    public void init(int x, int y, Result res){
        //���ô��ھ���
        jf.setBounds(x,y,WIDTH,HEIGHT);


        //�������
        Box box = Box.createVerticalBox();
        box.add(new JLabel("ƽ���볤��"+String.format("%.3f", res.getL())));
        box.add(new JLabel("�أ�"+String.format("%.3f", res.getH())));
        box.add(new JLabel("����Ч�ʣ�"+String.format("%.2f", res.getE()*100)+"%"));
        jf.add(box, BorderLayout.NORTH);
        JTable table = new JTable(res.getTableData(),title);
        jf.add(new JScrollPane(table));



        //��ʾ
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);


    }
}
