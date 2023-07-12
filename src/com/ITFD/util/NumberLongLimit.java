package com.ITFD.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class NumberLongLimit extends PlainDocument {

    private int limit;  //���Ƶĳ���

    public NumberLongLimit(int limit) {
        super(); //���ø��๹��
        this.limit = limit;
    }
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if(str == null) return;

        //������ж�������Ϊ�Լ���Ҫ���Ƶ��������ɣ�����Ϊ��������ĳ���
        if((getLength() + str.length()) <= limit) {
            if (this.isNumeric(str)) {
                super.insertString(offset, str, attr);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

    }
    private boolean isNumeric(String var1) {
        try {
            Long.valueOf(var1);
            return true;
        } catch (NumberFormatException var3) {
            if (var1.equals(".")){
                return true;
            }
            return false;
        }
    }

}
