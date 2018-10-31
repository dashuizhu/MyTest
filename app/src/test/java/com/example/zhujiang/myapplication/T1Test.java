package com.example.zhujiang.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;

/**
 * @author zhuj 2018/10/25 下午3:47.
 */
public class T1Test {

    @Test
    public void test1() {

        System.out.println( 100 * (Math.cos( Math.PI * 0/ 180 )));
        System.out.println( 100 * (Math.cos( Math.PI * 30/ 180 )));
        System.out.println( 100 * (Math.cos( Math.PI * 45/ 180 )));
        System.out.println( 100 * (Math.cos( Math.PI * 60/ 180 )));
        System.out.println( 100 * (Math.cos( Math.PI * 90/ 180 )));
        System.out.println( 100 * (Math.cos( Math.PI * 135/ 180 )));
        System.out.println( 100 * (Math.cos( Math.PI * 180/ 180 )));
        System.out.println( 100 * (Math.cos( Math.PI * 215/ 180 )));

        System.out.println("" + addBinary("11", "1"));

        initGird();

        //System.out.println( "" +countOnes(-1));
        System.out.println(countOnes(-14));
        System.out.println(countOnes(1023));
        System.out.println(countOnes(-1));
        System.out.println(countOnes(7));

        //Random random = new Random();
        //for (int i = 0;i <10; i++) {
        //    int count = random.nextInt(30);
        //    System.out.println( count + ": -> " + countOnes(count));
        //}
    }

    public String addBinary(String a, String b) {
        // write your code here

        int a1 = string2int(a);
        int b1 = string2int(b);
        int value = a1 + b1;
        String buffer = "";

        if (value ==0) {
            buffer = "0";
        }
        while(value != 0) {
            buffer =  ((value %2) ==1 ? "1":"0") + buffer;
            value = value >>1;
        }
        return buffer.toString();

    }

    private int string2int(String a) {
        if (a == null) {
            return 0;
        }
        char cc;
        int value = 0;
        for (int i=0;i<a.length(); i++) {
            cc = a.charAt(i);
            if (cc == '1') {
                value +=1;
            }
            if (i != a.length() -1) {
                value = value << 1;
            }
        }
        return value;
    }

    private void initGird() {
        int[][] array = new int[3][4];
        System.out.println(""+array.length);
        System.out.println(""+array[0].length);


        for (int i=0; i<array.length; i++) {
            for (int j =i; j <array[i].length; j++) {

            }
        }

    }

    /*
     * @param num: An integer
     * @return: An integer
     */
    //public int countOnes(int num) {
    //    // write your code here
    //    if (num <0) {
    //        num = 1 << 31 + num;
    //    }
    //    int  count =0;
    //    //int length = num /2;
    //    int value  = num;
    //    for (int i=0; i<16; i++) {
    //        value = num >>i ;
    //        if ((value) %2 == 1) {
    //            count++;
    //        }
    //        if (value <=0) {
    //            break;
    //        }
    //    }
    //    return count;
    //}

    public int countOnes(int num) {
        // write your code here
        //if (num <0) {
        //    num = 1 << 31 + num +1 ;
        //}
        int  count =0;
        while (num != 0) {
            count++;
            num = num & (num - 1);
        }
        return count;
    }

    class ParentTreeNode {
      public ParentTreeNode parent, left, right;
  }

    public ParentTreeNode lowestCommonAncestorII(ParentTreeNode root, ParentTreeNode A, ParentTreeNode B) {
        // write your code here

        //A.getParents()
        List<ParentTreeNode> listA = getParents(root, A);
        List<ParentTreeNode> listB = getParents(root, B);

        for (int i=0; i<listA.size(); i++) {
            for (int j=0; j<listB.size(); j++) {
                if (listA.get(i) == listB.get(j)) {
                    return listA.get(i);
                }
            }
        }
        return null;
    }


    private List<ParentTreeNode> getParents(ParentTreeNode root, ParentTreeNode node) {
        List<ParentTreeNode> list = new ArrayList<>();
        while(node.parent != null) {
            node = node.parent;
            list.add(node.parent);
        }
        return list;
    }
}

