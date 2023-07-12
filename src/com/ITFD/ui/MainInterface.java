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
    JFrame jf = new JFrame("��Դ����");

    int screenWidth = ScreenUtils.getScreenWidth();
    int screenHeight = ScreenUtils.getScreenHeight();
    final int WIDTH = screenWidth/2;
    final int HEIGHT = screenHeight/2;

    //��װ��ͼ
    public void init(){
        //���ô��ھ���
        jf.setBounds((screenWidth-WIDTH)/2,(screenHeight-HEIGHT)/2,WIDTH,HEIGHT);
        ImageIcon icon = new ImageIcon("data/icon.png");
        jf.setIconImage(icon.getImage());

        //�������
        JPanel mainPanel = new JPanel(new HLayout());

        //�����
        JPanel leftBar = new JPanel(new VLayout());
        leftBar.setBackground(Color.white);

        AfButton hoffmanBtn = new AfButton("����������");
        AfButton fanoBtn = new AfButton("��ŵ����");
        AfButton shannonBtn = new AfButton("��ũ����");

        leftBar.add(hoffmanBtn,"10%");
        leftBar.add(fanoBtn,"10%");
        leftBar.add(shannonBtn,"10%");

        //���ÿ�Ƭ����
        JPanel encodePenal = new JPanel();
        CardLayout cardLayout = new CardLayout();
        encodePenal.setLayout(cardLayout);

        //������뿨Ƭ
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

        //�����¼/ע�ῨƬ

        jf.add(mainPanel);

        //��ʾ
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);


    }
    //���������
    public static void main(String[] args) {
        new MainInterface().init();
    }
}
