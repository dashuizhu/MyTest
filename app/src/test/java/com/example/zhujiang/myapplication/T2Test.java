package com.example.zhujiang.myapplication;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * @author zhuj 2018/10/29 下午5:08.
 */
public class T2Test {

    @Test
    public void onTest() {

        ListNode l1 = new ListNode(2);
        l1.next = null;

        ListNode l2 = new ListNode(0);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(3);
        l2.next.next.next = new ListNode(5);


        mergeTwoLists(l1, l2);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // write your code here
        if(l1 == null) {
            return l2;
        }
        if(l2 == null) {
            return l1;
        }
        ListNode newNode;
        if (l1.val < l2.val) {
            newNode = l1;
            l1 = l1.next;
        } else {
            newNode = l2;
            l2 = l2.next;
        }


        ListNode newNodeNext = newNode;
        while(l1 != null || l2 != null) {
            if (l1 ==null) {
                newNodeNext.next = l2;
                l2 = l2.next;

                newNodeNext = newNodeNext.next;
                continue;
            }
            if (l2==null || l1.val < l2.val) {
                newNodeNext.next = l1;
                l1 = l1.next;
                newNodeNext = newNodeNext.next;
            } else {
                newNodeNext.next = l2;
                l2 = l2.next;
                newNodeNext = newNodeNext.next;
            }

        }
        return newNode;

    }

    public class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
  }

  @Test
  public void test2() {
        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);

        isBalanced(node);
  }


  public class TreeNode {
     public int val;
     public TreeNode left, right;
     public TreeNode(int val) {
         this.val = val;
         this.left = this.right = null;
     }
 }

    public boolean isBalanced(TreeNode root) {
        // write your code here
        if (root == null) {
            return false;
        }
        int leftCount = getCount(1, root.left);
        int rightCount = getCount(1, root.right);
        int value = leftCount - rightCount;
        return ( value > 1 || value < -1);
    }

    private int getCount(int count , TreeNode root) {
        if (root == null) {
            return count;
        }
        count ++;
        int leftCount = getCount(count, root.left);
        int rightCount = getCount(count, root.right);
        return Math.max(leftCount, rightCount);
    }




}
