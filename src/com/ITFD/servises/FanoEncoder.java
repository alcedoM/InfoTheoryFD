package com.ITFD.servises;

import com.ITFD.components.Result;

import java.util.*;

import static java.lang.Math.abs;

public class FanoEncoder {
    public Result encode(double[] Q){
        List<Integer> freqList = new ArrayList<>();
        List<String> symbolList = new ArrayList<>();

        for (int i = 0; i < Q.length; i++) {
            if (Q[i]!=0){
                freqList.add((int)(Q[i]*10000000));
                symbolList.add("u"+(i+1));
            }
        }

        int[] freqs = new int[freqList.size()];
        String[] symbols = new String[symbolList.size()];
        for (int i = 0; i < freqList.size(); i++) {
            freqs[i] = freqList.get(i);
            symbols[i] = symbolList.get(i);
        }

        FanoNode root = getNodes(symbols,freqs);
        createFanoTree(root);
        getCodes(root);
        double[] LH = getLAndH(root);
        double l = LH[0];
        double h = LH[1];
        double e = LH[2];
        return new Result(getTableData(root),l,h,e);

    }

    private double[] getLAndH(FanoNode root) {
        List<FanoNode> nodes = root.getChildren();
        double l = 0;
        double h = 0;
        for (FanoNode node : nodes) {
            double p = node.getRealFreq();
            l += node.getCode().length()*p;
            h += -p * Math.log(p)/Math.log(2);
        }
        return new double[]{l, h, h/l};
    }

    //创建所有节点
    public static FanoNode getNodes(String[] symbols, int [] freqs) {
        List<FanoNode> nodes = new ArrayList<>();
        int total=0;
        for (int i = 0; i < freqs.length; i++) {
            nodes.add(new FanoNode(symbols[i],freqs[i]));
            total+= freqs[i];
        }
        FanoNode root = new FanoNode(null,total);
        root.setChildren(nodes);
        return root;
    }
    //创建费诺树
    public static void createFanoTree(FanoNode fanoNode){
        List<FanoNode> children = fanoNode.getChildren();
        if (children.size()>1){
            Collections.sort(children);

            int half = fanoNode.getFreq()/2;
            int sum = 0;
            int differ = half ;
            int split =0;
            FanoNode node1 = new FanoNode(null,0);
            FanoNode node0 = new FanoNode(null,0);
            List<FanoNode> children1 = new ArrayList<>();
            List<FanoNode> children0 = new ArrayList<>();
            int freq1 = 0;
            int freq0 = 0;

            for (int i = 0; i < children.size(); i++) {
                sum += children.get(i).getFreq();
                if (abs(sum-half)<differ){
                    split = i;
                    differ = abs(sum-half);
                }
            }

            for (int i = 0; i < children.size(); i++) {
                if (i <= split) {
                    children0.add(children.get(i));
                    freq0 += children.get(i).getFreq();
                } else {
                    children1.add(children.get(i));
                    freq1 += children.get(i).getFreq();
                }
            }

            node1.setFreq(freq1);
            node0.setFreq(freq0);
            node1.setChildren(children1);
            node0.setChildren(children0);

            fanoNode.setNode0(node0);
            fanoNode.setNode1(node1);

            createFanoTree(node0);
            createFanoTree(node1);
        }
    }

    //获取编码
    public static void getCodes(FanoNode fanoNode) {
        if (fanoNode.getChildren().size()>1){
            FanoNode node0 = fanoNode.getNode0();
            FanoNode node1 = fanoNode.getNode1();
            List<FanoNode> children0 = node0.getChildren();
            List<FanoNode> children1 = node1.getChildren();
            if(node1.getFreq()>node0.getFreq()){
                for (FanoNode node : children0) {
                    StringBuilder sb = node.getCode();
                    sb.append("1");
                    node.setCode(sb);
                }
                for (FanoNode node : children1) {
                    StringBuilder sb = node.getCode();
                    sb.append("0");
                    node.setCode(sb);
                }
            }else{
                for (FanoNode node : children0) {
                    StringBuilder sb = node.getCode();
                    sb.append("0");
                    node.setCode(sb);
                }
                for (FanoNode node : children1) {
                    StringBuilder sb = node.getCode();
                    sb.append("1");
                    node.setCode(sb);
                }
            }

            getCodes(node0);
            getCodes(node1);
        }

    }

    public static Object[][] getTableData(FanoNode root){
        //生成表格数据
        Object[][] tableData = new Object[(root.getChildren().size()*2/6)+1][6];
        Object[] newRow = {"-","-","-","-","-","-"};

        int colCount = 0;
        int rowCount = 0;

        for (FanoNode child : root.getChildren()) {
            if (colCount>2){
                tableData[rowCount] = newRow;
                rowCount ++;
                colCount=0;
                newRow = new Object[]{"-", "-", "-", "-", "-", "-"};
            }
            newRow[2*colCount] = child.getSymbol();
            newRow[(2*colCount)+1] = child.getCode();
            colCount ++;
        }
        tableData[rowCount] = newRow;

        return tableData;
    }

//    public static void main(String[] args) {
//        int[] freqs = {2000,1900,1800,1700,1500,1000,100};
//        String[] symbols = {"u1","u2","u3","u4","u5","u6","u7"};
//        FanoNode root = getNodes(symbols,freqs);
//        createFanoTree(root);
//        getCodes(root);
//        for (FanoNode child : root.getChildren()) {
//            System.out.println(child);
//        }
//    }

}

class FanoNode implements Comparable<FanoNode>{
    private String symbol; //存放字符本身
    private int freq; //存放权值
    private StringBuilder code = new StringBuilder();
    private List<FanoNode> children = new ArrayList<>();
    FanoNode node1;
    FanoNode node0;

    public FanoNode(String symbol, int freq) {
        this.symbol = symbol;
        this.freq = freq;
    }

    public FanoNode getNode1() {
        return node1;
    }

    public void setNode1(FanoNode node1) {
        this.node1 = node1;
    }

    public FanoNode getNode0() {
        return node0;
    }

    public void setNode0(FanoNode node0) {
        this.node0 = node0;
    }

    public List<FanoNode> getChildren() {
        return children;
    }

    public void setChildren(List<FanoNode> children) {
        this.children = children;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public StringBuilder getCode() {
        return code;
    }

    public void setCode(StringBuilder code) {
        this.code = code;
    }

    public double getRealFreq() {
        return (double)getFreq()/10000000;
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
    public int compareTo(FanoNode o) { //从小到大排序
        return this.getFreq() - o.getFreq();
    }
}
