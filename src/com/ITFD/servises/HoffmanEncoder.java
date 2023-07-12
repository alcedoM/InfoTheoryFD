package com.ITFD.servises;

import com.ITFD.components.Result;

import java.util.*;

public class HoffmanEncoder {
    public Result encode(int N, int R, double[] Q){
        codeNodes = new ArrayList<>();
        List<Integer> freqList = new ArrayList<>();
        List<String> symbolList = new ArrayList<>();
        //判断重数
        switch (N) {
            case 1 -> {
                for (int i = 0; i < Q.length; i++) {
                    if (Q[i] != 0) {
                        freqList.add((int) (Q[i] * 10000000));
                        symbolList.add("u" + (i + 1));
                    }
                }
            }
            case 2 -> {
                for (int i = 0; i < Q.length; i++) {
                    if (Q[i] != 0) {
                        for (int j = 0; j < Q.length; j++) {
                            if (Q[j] != 0) {
                                freqList.add((int) (Q[i] * Q[j] * 10000000));
                                symbolList.add("u" + (i + 1) + "u" + (j + 1));
                            }
                        }
                    }
                }
            }
            case 3 -> {
                for (int i = 0; i < Q.length; i++) {
                    if (Q[i] != 0) {
                        for (int j = 0; j < Q.length; j++) {
                            if (Q[j] != 0) {
                                for (int k = 0; k < Q.length; k++) {
                                    if (Q[k] != 0) {
                                        freqList.add((int) (Q[i] * Q[j] * Q[k] * 10000000));
                                        symbolList.add("u" + (i + 1) + "u" + (j + 1) + "u" + (k + 1));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            default -> {
            }
        }

        int[] freqs = new int[freqList.size()];
        String[] symbols = new String[symbolList.size()];
        for (int i = 0; i < freqList.size(); i++) {
            freqs[i] = freqList.get(i);
            symbols[i] = symbolList.get(i);
        }

        List<Node> nodes = getNodes(symbols,freqs);
        Node root = createHoffmanTree(R,nodes);
        getCodes(R,root,"",stringBuilder);


        double[] LH = getLAndH(R);
        double l = LH[0];
        double h = LH[1];
        double e = LH[2];
        return new Result(getTableData(codeNodes),l,h,e);
    }

    public static Object[][] getTableData(List<NodeSort> nodes){
        //生成表格数据
        Collections.sort(nodes);
        Object[][] tableData = new Object[(nodes.size()*2/6)+1][6];
        Object[] newRow = {"-","-","-","-","-","-"};

        int colCount = 0;
        int rowCount = 0;

        for (Node node : nodes) {
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

    private double[] getLAndH(int R) {
        double l = 0;
        double h = 0;
        for (Node node : codeNodes) {
            double p = node.getRealFreq();
            l += node.getCode().length()*p;
            h += -p * Math.log(p)/Math.log(2);

        }
        return new double[]{l, h, h/(l*Math.log(R)/Math.log(2))};
    }

    //创建所有节点
    public static List<Node> getNodes(String[] symbols, int [] freqs) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < freqs.length; i++) {
            Node temp = new Node(symbols[i],freqs[i]);
            temp.setId(i);
            nodes.add(temp);
        }
        return nodes;
    }
    //创建霍夫曼树
    public static Node createHoffmanTree(int R,List<Node> nodes){
        while (nodes.size()>1){
            Collections.sort(nodes);//排序
            switch (R) {
                case 2 -> {
                    Node left = nodes.remove(0);
                    Node right = nodes.remove(0);
                    Node parent = new Node(null, left.getFreq() + right.getFreq());
                    parent.setLeft(left);
                    parent.setRight(right);
                    nodes.add(parent);
                }
                case 3 -> {
                    Node left3 = new Node("blank",0);
                    Node center3 = new Node("blank",0);
                    Node right3 = new Node("blank",0);

                    switch (nodes.size()){
                        case 0:break;
                        case 1:
                            left3 = nodes.remove(0);
                            break;
                        case 2:
                            left3 = nodes.remove(0);
                            center3 = nodes.remove(0);
                            break;
                        default:
                            left3 = nodes.remove(0);
                            center3 = nodes.remove(0);
                            right3 = nodes.remove(0);
                            break;
                    }

                    Node parent3 = new Node(null, left3.getFreq() + center3.getFreq() + right3.getFreq());
                    parent3.setLeft(left3);
                    parent3.setCenter(center3);
                    parent3.setRight(right3);
                    nodes.add(parent3);
                }
                case 4 -> {
                    Node left4 = new Node("blank",0);
                    Node centerL4 = new Node("blank",0);
                    Node center4 = new Node("blank",0);
                    Node right4 = new Node("blank",0);

                    switch (nodes.size()){
                        case 0:break;
                        case 1:
                            left4 = nodes.remove(0);
                            break;
                        case 2:
                            left4 = nodes.remove(0);
                            centerL4 = nodes.remove(0);
                            break;
                        case 3:
                            left4 = nodes.remove(0);
                            centerL4 = nodes.remove(0);
                            center4 = nodes.remove(0);
                            break;
                        default:
                            left4 = nodes.remove(0);
                            centerL4 = nodes.remove(0);
                            center4 = nodes.remove(0);
                            right4 = nodes.remove(0);
                            break;
                    }

                    Node parent4 = new Node(null, left4.getFreq() + centerL4.getFreq() + center4.getFreq() + right4.getFreq());
                    parent4.setLeft(left4);
                    parent4.setCenterL(centerL4);
                    parent4.setCenter(center4);
                    parent4.setRight(right4);
                    nodes.add(parent4);
                }
                case 5 -> {
                    Node left5 = new Node("blank",0);
                    Node centerL5 = new Node("blank",0);
                    Node center5 = new Node("blank",0);
                    Node centerR5 = new Node("blank",0);
                    Node right5 = new Node("blank",0);

                    switch (nodes.size()){
                        case 0:break;
                        case 1:
                            left5 = nodes.remove(0);
                            break;
                        case 2:
                            left5 = nodes.remove(0);
                            centerL5 = nodes.remove(0);
                            break;
                        case 3:
                            left5 = nodes.remove(0);
                            centerL5 = nodes.remove(0);
                            center5 = nodes.remove(0);
                            break;
                        case 4:
                            left5 = nodes.remove(0);
                            centerL5 = nodes.remove(0);
                            center5 = nodes.remove(0);
                            centerR5 = nodes.remove(0);
                            break;
                        default:
                            left5 = nodes.remove(0);
                            centerL5 = nodes.remove(0);
                            center5 = nodes.remove(0);
                            centerR5 = nodes.remove(0);
                            right5 = nodes.remove(0);
                            break;
                    }

                    Node parent5 = new Node(null, left5.getFreq() + centerL5.getFreq() + center5.getFreq() + centerR5.getFreq() + right5.getFreq());
                    parent5.setLeft(left5);
                    parent5.setCenterL(centerL5);
                    parent5.setCenter(center5);
                    parent5.setCenterR(centerR5);
                    parent5.setRight(right5);
                    nodes.add(parent5);
                }
                default -> {
                }
            }

        }
        return nodes.get(0);
    }


    //获取编码值
    static StringBuilder stringBuilder = new StringBuilder();
    static List<NodeSort> codeNodes = new ArrayList<>();

    public static void getCodes(int R,Node node, String code, StringBuilder stringBuilder) {
        StringBuilder temp = new StringBuilder(stringBuilder);
        temp.append(code);

        if (node != null) {
            if (node.getSymbol() == null) { //非叶子节点
                switch (R) {
                    case 2 -> {
                        getCodes(R, node.getLeft(), "0", temp);
                        getCodes(R, node.getRight(), "1", temp);
                    }
                    case 3 -> {
                        getCodes(R, node.getLeft(), "0", temp);
                        getCodes(R, node.getCenter(), "1", temp);
                        getCodes(R, node.getRight(), "2", temp);
                    }
                    case 4 -> {
                        getCodes(R, node.getLeft(), "0", temp);
                        getCodes(R, node.getCenterL(), "1", temp);
                        getCodes(R, node.getCenter(), "2", temp);
                        getCodes(R, node.getRight(), "3", temp);
                    }
                    case 5 -> {
                        getCodes(R, node.getLeft(), "0", temp);
                        getCodes(R, node.getCenterL(), "1", temp);
                        getCodes(R, node.getCenter(), "2", temp);
                        getCodes(R, node.getCenterR(), "3", temp);
                        getCodes(R, node.getRight(), "4", temp);
                    }
                    default -> {
                    }
                }
            } else if(!node.getSymbol().equals("blank")) {
                node.setCode(temp);
                codeNodes.add(new NodeSort(node.getSymbol(),node.getFreq(),node.getId(),node.getCode()));
            }
        }
    }

    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("树空，无法完成前序遍历！");
        }
    }
}
class Node implements Comparable<Node>{
    private String symbol; //存放字符本身
    private int freq; //存放权值
    private int id;
    private StringBuilder code = new StringBuilder();
    Node left;
    Node centerL;
    Node center;
    Node centerR;
    Node right;

    public Node(String symbol, int freq) {
        this.symbol = symbol;
        this.freq = freq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StringBuilder getCode() {
        return code;
    }

    public void setCode(StringBuilder code) {
        this.code = code;
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

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getCenterL() {
        return centerL;
    }

    public void setCenterL(Node centerL) {
        this.centerL = centerL;
    }

    public Node getCenter() {
        return center;
    }

    public void setCenter(Node center) {
        this.center = center;
    }

    public Node getCenterR() {
        return centerR;
    }

    public void setCenterR(Node centerR) {
        this.centerR = centerR;
    }

    public double getRealFreq() {
        return (double)getFreq()/10000000;
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.getLeft() != null) {
            this.getLeft().preOrder();
        }
        if (this.getCenterL() != null) {
            this.getCenterL().preOrder();
        }
        if (this.getCenter() != null) {
            this.getCenter().preOrder();
        }
        if (this.getCenterR() != null) {
            this.getCenterR().preOrder();
        }
        if (this.getRight() != null) {
            this.getRight().preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "symbol=" + symbol +
                ", freq=" + freq +
                '}';
    }

    @Override
    public int compareTo(Node o) { //从小到大排序
        return this.getFreq() - o.getFreq();
    }
}

class NodeSort extends Node{

    public NodeSort(String symbol, int freq, int id, StringBuilder code) {
        super(symbol,freq);
        this.setId(id);
        this.setCode(code);
    }

    @Override
    public int compareTo(Node o) { //从小到大排序
        return this.getId() - o.getId();
    }
}