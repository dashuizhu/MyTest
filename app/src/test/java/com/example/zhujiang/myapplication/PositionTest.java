package com.example.zhujiang.myapplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

/**
 * 计算棋盘路线
 * @author zhuj 2018/9/13 上午10:38.
 */
public class PositionTest {


    public boolean poTest() {

        StringBuffer sb = new StringBuffer();
        StringBuffer sbRight = new StringBuffer();

        Random random = new Random();
        List<Integer> set = new ArrayList<>();
        int start = 0;
        int end  = (1+random.nextInt(3)) * 6 -1;

        int middle = 0;
        while (middle == 0 || middle ==end) {
            middle = random.nextInt(24);
            if (middle % 6 ==5) {
                middle = middle -1;
            }
        }

        set.add(start);

        System.out.println("middle  " + middle + "   end: " + end);
        //sbRight.append("\n");

        int rightStep = (middle -0) % 6;
        int verStep = (middle -0) /6;
        int next = start;
        while (rightStep>0 || verStep>0) {

            boolean isRight = random.nextBoolean();
            if (isRight && rightStep>0) {
                next += 1;
                rightStep --;
            } else if (verStep>0) {
                next += 6;
                verStep --;
            }  else {
                continue;
            }
            set.add(next);
        }


        rightStep = (end%6 - middle%6);
        verStep = (end/6 - middle/6);
        while ( (rightStep !=0 || verStep !=0)) {
            boolean isRight = random.nextBoolean();
            if (isRight && rightStep>0) {
                next +=1;
                rightStep --;
                //System.out.println(  "add -> " + next + " isR:"+ isRight + " rStep:"+rightStep + " vstep:"+verStep);
                set.add(next);
            } else if (verStep != 0){
                int newValue = next;
                if (verStep>0) {
                    newValue +=6;
                } else  {
                    newValue -=6;
                }

                boolean isContains = false;
                for (int a : set) {
                    if (a == newValue) {
                        isContains = true;
                        break;
                    }
                }
                //System.out.println(  "iscontans -> " + newValue + " isR:"+ isRight + " rStep:"+rightStep + " vstep:"+verStep);
                if (!isContains) {
                    if (newValue > next) {
                        verStep --;
                    } else {
                        verStep ++;
                    }
                    //System.out.println(  "add -> " + newValue + " isR:"+ isRight + " rStep:"+rightStep + " vstep:"+verStep);
                    //sb.append("\n");

                    next = newValue;
                    set.add(newValue);
                }
            }
        }


        boolean isRepeat = set.size() != new HashSet<Integer>(set).size();
        for (Integer integer : set) {
            sbRight.append( integer + " -> ");
        }
        boolean isWrong =  set.get(set.size()-1) != end;
        //if (isRepeat || isWrong) {
        //}
        System.out.println(sbRight.toString());
        //System.out.println(sb.toString());
        return isRepeat || isWrong;
    }


    @Test
    public void testRandom() {
        int errorCount = 0;
        for (int i=0; i< 500000; i ++) {
            if (poTest()) {
                errorCount++;
            }
        }
        System.out.println("失败次数 " + errorCount);
    }

}
