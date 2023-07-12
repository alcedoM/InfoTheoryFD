package com.ITFD.components;

import af.swing.AfButton;
import af.swing.layout.FreeLayout;
import af.swing.layout.Margin;
import af.swing.layout.VLayout;
import com.ITFD.servises.FanoEncoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Math.abs;

public class FanoCard extends JPanel {
    public FanoCard(JFrame jf, int HEIGHT, JPanel encodePanel, CardLayout cardLayout){


        //声明组件
        setLayout(new FreeLayout());
        JPanel mainPanel = new JPanel(new VLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.white);
        Box inputbox = Box.createVerticalBox();

        Box qlBox = Box.createHorizontalBox();
        qlBox.add(new JLabel("输入概率格式[p1,p2,···,pn]"));
        JTextArea qTA = new JTextArea(3,20);
        qTA.setBackground(new Color(246, 246, 246));


        AfButton submit = new AfButton("编码");

        JPanel outputPanel =new JPanel();
        JTextArea outputTA = new JTextArea(5,50);
        outputTA.setEditable(false);
        outputTA.setLineWrap(true);
        outputPanel.setBackground(Color.white);
        outputPanel.add(outputTA);

        //监听函数
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = true;
                double sum = 0;

                String Q = qTA.getText();
                double[] qinput = null;
                try {
                    String[] strArr = Q.substring(1, Q.length() - 1).split(",");
                    qinput = new double[strArr.length];
                    for (int i = 0; i < strArr.length; i++) {
                        qinput[i] = Double.parseDouble(strArr[i]);
                        sum += qinput[i];
                    }
                }catch (Exception exception){
                    flag =false;
                    JOptionPane.showMessageDialog(jf,"输入有误请重新输入","提示",JOptionPane.ERROR_MESSAGE);
                }


                if(flag){
                    if (abs(sum-1)>0.0001){
                        JOptionPane.showMessageDialog(jf,"输入的概率和:"+sum+" (不为1)","提示",JOptionPane.ERROR_MESSAGE);
                    }else{
                        Result res = new FanoEncoder().encode(qinput);
                        new ResultCard().init(jf.getX(), jf.getY(), res);
                    }
                }

            }
        });

        inputbox.add(new JLabel("费诺编码"));
        inputbox.add(Box.createVerticalStrut(20));
        inputbox.add(qlBox);
        inputbox.add(qTA);
        inputbox.add(Box.createVerticalStrut(20));
        inputbox.add(submit);
        inputPanel.add(inputbox);


        mainPanel.add(inputPanel,"100%");
        add(mainPanel,new Margin(10,10,10,10));


    }

}

