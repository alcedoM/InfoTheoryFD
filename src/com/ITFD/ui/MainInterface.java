package com.ITFD.ui;

import af.swing.AfButton;
import af.swing.layout.HLayout;
import af.swing.layout.VLayout;
import com.ITFD.components.ShannonCard;
import com.ITFD.components.HoffmanCard;
import com.ITFD.components.FanoCard;
import com.ITFD.util.ScreenUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainInterface {
    JFrame jf = new JFrame("信源编码");

    int screenWidth = ScreenUtils.getScreenWidth();
    int screenHeight = ScreenUtils.getScreenHeight();
    final int WIDTH = screenWidth/2;
    final int HEIGHT = screenHeight/2;

    //组装视图
    public void init(){
        //设置窗口居中
        jf.setBounds((screenWidth-WIDTH)/2,(screenHeight-HEIGHT)/2,WIDTH,HEIGHT);
        ImageIcon icon = new ImageIcon("data/icon.png");
        jf.setIconImage(icon.getImage());

        //声明组件
        JPanel mainPanel = new JPanel(new HLayout());

        //左边栏
        JPanel leftBar = new JPanel(new VLayout());
        leftBar.setBackground(Color.white);

        AfButton hoffmanBtn = new AfButton("霍夫曼编码");
        AfButton fanoBtn = new AfButton("费诺编码");
        AfButton shannonBtn = new AfButton("香农编码");

        leftBar.add(hoffmanBtn,"10%");
        leftBar.add(fanoBtn,"10%");
        leftBar.add(shannonBtn,"10%");

        //设置卡片布局
        JPanel encodePenal = new JPanel();
        CardLayout cardLayout = new CardLayout();
        encodePenal.setLayout(cardLayout);

        //插入编码卡片
        encodePenal.add(new HoffmanCard(jf,HEIGHT,encodePenal,cardLayout),"hoffmanCard");
        encodePenal.add(new FanoCard(jf,HEIGHT,encodePenal,cardLayout),"fanoCard");
        encodePenal.add(new ShannonCard(jf,HEIGHT,encodePenal,cardLayout),"shannonCard");


        hoffmanBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(encodePenal,"hoffmanCard");
            }
        });
        fanoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(encodePenal,"fanoCard");
            }
        });
        shannonBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(encodePenal,"shannonCard");
            }
        });



        mainPanel.add(leftBar,"20%");
        mainPanel.add(encodePenal,"80%");

        //插入登录/注册卡片

        jf.add(mainPanel);

        //显示
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);


    }
    //程序主入口
    public static void main(String[] args) {
        new MainInterface().init();
    }
}
