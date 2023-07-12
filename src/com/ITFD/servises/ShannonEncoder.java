package com.ITFD.servises;

import com.ITFD.components.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShannonEncoder {
    public Result encode(double[] Q){
        List<Double> freqList = new ArrayList<>();
        List<String> symbolList = new ArrayList<>();

        for (int i = 0; i < Q.length; i++) {
            if (Q[i]!=0){
                freqList.add(Q[i]);
                symbolList.add("u"+(i+1));
            }
        }

        double[] freqs = new double[freqList.size()];
        String[] symbols = new String[symbolList.size()];
        for (int i = 0; i < freqList.size(); i++) {
            freqs[i] = freqList.get(i);
            symbols[i] = symbolList.get(i);
        }

        List<SNode> nodes = getNodes(symbols,freqs);
        getLAndP(nodes);
        getCodes(nodes);
        double[] LH = getLAndH(nodes);
        double l = LH[0];
        double h = LH[1];
        double e = LH[2];
        return new Result(getTableData(nodes),l,h,e);

    }

    private double[] getLAndH(List<SNode> nodes) {
        double l = 0;
        double h = 0;
        for (SNode node : nodes) {
            double p = node.getFreq();
            l += node.getCode().length()*p;
            h += -p * Math.log(p)/Math.log(2);
        }
        return new double[]{l, h, h/l};
    }

    private Object[][] getTableData(List<SNode> nodes) {
        //生成表格数据
        Object[][] tableData = new Object[(nodes.size()*2/6)+1][6];
        Object[] newRow = {"-","-","-","-","-","-"};

        int colCount = 0;
        int rowCount = 0;

        for (SNode node : nodes) {
            if (colCount>2){
                tableData[rowCount] = newRow;
                rowCount ++;
                colCount=0;
                newRow = new Object[]{"-", "-", "-", "-", "-", "-"};
            }
            newRow[2*colCount] = node.getSymbol();
            newRow[(2*colCount)+1] = node.getCode();
            colCount ++;
        }
        tableData[rowCount] = newRow;

        return tableData;
    }

    private void getCodes(List<SNode> nodes) {
        for (SNode node : nodes) {
            StringBuilder sb = node.getCode();
            double sumFreq = node.getSumFreq();
            for (int i = 0; i < node.getCodeLength(); i++) {
                sumFreq = sumFreq*2;
                if(sumFreq>1){
                    sb.append("1");
                    sumFreq = sumFreq-1;
                }else {
                    sb.append("0");
                }
            }
            node.setCode(sb);
        }
    }

    private void getLAndP(List<SNode> nodes) {
        Collections.sort(nodes);

        double sumFreq = 0;
        for (SNode node : nodes) {
            node.setSumFreq(sumFreq);
            sumFreq += node.getFreq();

            int l = (int) Math.ceil(-Math.log(node.getFreq()) / Math.log(2));
            node.setCodeLength(l);
        }
    }

    public static List<SNode> getNodes(String[] symbols, double [] freqs) {
        List<SNode> nodes = new ArrayList<>();

        for (int i = 0; i < freqs.length; i++) {
            nodes.add(new SNode(symbols[i],freqs[i]));
        }

        return nodes;
    }
}


class SNode implements Comparable<SNode>{
    private String symbol; //存放字符本身
    private double freq; //存放权值
    private StringBuilder code = new StringBuilder();
    private double sumFreq;
    private int codeLength;


    public SNode(String symbol, double freq) {
        this.symbol = symbol;
        this.freq = freq;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getFreq() {
        return freq;
    }

    public void setFreq(double freq) {
        this.freq = freq;
    }

    public StringBuilder getCode() {
        return code;
    }

    public void setCode(StringBuilder code) {
        this.code = code;
    }

    public double getSumFreq() {
        return sumFreq;
    }

    public void setSumFreq(double sumFreq) {
        this.sumFreq = sumFreq;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public int getCompare() {
        return (int) getFreq()*10000000;
    }


    @Override
    public String toString() {
        return "Node{" +
                "symbol=" + symbol +
                ", freq=" + freq +
                ", code=" + code +
                '}';
    }

    @Override
    public int compareTo(SNode o) { //从小到大排序
        return this.getCompare() - o.getCompare();
    }
}