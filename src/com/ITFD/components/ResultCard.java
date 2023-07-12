package com.ITFD.components;

import com.ITFD.components.Result;
import com.ITFD.util.ScreenUtils;

import javax.swing.*;
import java.awt.*;

public class ResultCard {
    JFrame jf = new JFrame("编码结果");

    int screenWidth = ScreenUtils.getScreenWidth();
    int screenHeight = ScreenUtils.getScreenHeight();
    final int WIDTH = screenWidth/2;
    final int HEIGHT = screenHeight/2;
    //表头
    Object[] title = {"符号","编码","符号","编码","符号","编码"};

    //组装视图
    public void init(Result res){
        init((screenWidth-WIDTH)/2,(screenHeight-HEIGHT)/2,res);
    }
    public void init(int x, int y, Result res){
        //设置窗口居中
        jf.setBounds(x,y,WIDTH,HEIGHT);


        //声明组件
        Box box = Box.createVerticalBox();
        box.add(new JLabel("平均码长："+String.format("%.3f", res.getL())));
        box.add(new JLabel("熵："+String.format("%.3f", res.getH())));
        box.add(new JLabel("编码效率："+String.format("%.2f", res.getE()*100)+"%"));
        jf.add(box, BorderLayout.NORTH);
        JTable table = new JTable(res.getTableData(),title);
        jf.add(new JScrollPane(table));



        //显示
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);


    }
}
