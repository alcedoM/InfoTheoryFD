package com.ITFD.components;

import af.swing.AfButton;
import af.swing.layout.FreeLayout;
import af.swing.layout.Margin;
import af.swing.layout.VLayout;
import com.ITFD.servises.HoffmanEncoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import static java.lang.Math.abs;

public class HoffmanCard extends JPanel {
    public HoffmanCard(JFrame jf, int HEIGHT, JPanel encodePanel, CardLayout cardLayout){
        JTextField[] jTextFields = new JTextField[16];

        //�������
        setLayout(new FreeLayout());
        JPanel mainPanel = new JPanel(new VLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.white);
        Box inputbox = Box.createVerticalBox();

        Box qlBox = Box.createHorizontalBox();
        qlBox.add(new JLabel("������ʸ�ʽ[p1,p2,������,pn]"));
        JTextArea qTA = new JTextArea(3,20);
        qTA.setBackground(new Color(246, 246, 246));


        //ѡ����
        //N�ط���
        Box selectorBox = Box.createHorizontalBox();

        Vector<Integer> nlist = new Vector<>();
        nlist.add(1);
        nlist.add(2);
        nlist.add(3);
        JComboBox<Integer> nSelector = new JComboBox<>(nlist);

        selectorBox.add(new JLabel("���÷�������N��"));
        selectorBox.add(nSelector);
        selectorBox.add(Box.createHorizontalStrut(50));

        //����R
        Vector<Integer> rlist = new Vector<>();
        rlist.add(2);
        rlist.add(3);
        rlist.add(4);
        rlist.add(5);
        JComboBox<Integer> rSelector = new JComboBox<>(rlist);

        selectorBox.add(new JLabel("���ñ������R��"));
        selectorBox.add(rSelector);

        AfButton submit = new AfButton("����");

        JPanel outputPanel =new JPanel();
        JTextArea outputTA = new JTextArea(5,50);
        outputTA.setEditable(false);
        outputTA.setLineWrap(true);
        outputPanel.setBackground(Color.white);
        outputPanel.add(outputTA);

        //��������
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int N = (int) nSelector.getSelectedItem();
                int R = (int) rSelector.getSelectedItem();
                boolean flag = true;
                double sum = 0;
                int num = 0;

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
                    JOptionPane.showMessageDialog(jf,"������������������","��ʾ",JOptionPane.ERROR_MESSAGE);
                }


                if(flag){
                    if (abs(sum-1)>0.0001){
                        JOptionPane.showMessageDialog(jf,"����ĸ��ʺ�:"+sum+" (��Ϊ1)","��ʾ",JOptionPane.ERROR_MESSAGE);
                    }else{
                        Result res = new HoffmanEncoder().encode(N,R,qinput);
                        new ResultCard().init(jf.getX(), jf.getY(), res);
//                        JOptionPane.showMessageDialog(jf,res,"��ʾ",JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            }
        });


        inputbox.add(new JLabel("����������"));
        inputbox.add(Box.createVerticalStrut(20));
        inputbox.add(qlBox);
        inputbox.add(qTA);
        inputbox.add(Box.createVerticalStrut(20));
        inputbox.add(selectorBox);
        inputbox.add(Box.createVerticalStrut(20));
        inputbox.add(submit);
        inputPanel.add(inputbox);


        mainPanel.add(inputPanel,"100%");
        add(mainPanel,new Margin(10,10,10,10));


    }

}
