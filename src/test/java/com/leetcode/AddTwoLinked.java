package com.leetcode;

import lombok.ToString;

/*
You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order,
and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

    Input: l1 = [2,4,3], l2 = [5,6,4]
    Output: [7,0,8]
    Explanation: 342 + 465 = 807.
* */
public class AddTwoLinked {
    public static void main(String[] args) {
        ListNode first = new ListNode(9, null);
        ListNode second = new ListNode(9, first);
        ListNode third = new ListNode(9, second);
        ListNode fourth = new ListNode(9, third);
        ListNode fifth = new ListNode(9, fourth);
        ListNode sixth = new ListNode(9, fifth);
        ListNode seventh = new ListNode(9, sixth);

        ListNode first2 = new ListNode(9, null);
        ListNode second2 = new ListNode(9, first2);
        ListNode third2 = new ListNode(9, second2);
        ListNode fourth2 = new ListNode(9, third2);

        ListNode result = addTwoNumbers(seventh, fourth2);
        System.out.println(result);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //iterate through both lists until either is finished.
        //add two numbers one by one from both the lists.
        //keep a variable outside for the remainder(if any).
        //add the remainder in each iteration if it is greater than one. if added, reassign 0 for it.

        int remainder = 0;
        ListNode first = new ListNode();
        ListNode last = first;
        while (l1 != null || l2 != null) {
            if (l1 != null && l2 != null) {
                int sum = l1.val + l2.val + remainder;
                remainder = appendNextResult(sum, first, l1.next != null? l1.next : l2.next);
                l1 = l1.next;
                l2 = l2.next;
            } else if (l1 == null) {
                int sum = l2.val + remainder;
                remainder = appendNextResult(sum, first, l2.next);
                l2 = l2.next;
            } else {
                int sum = l1.val + remainder;
                remainder = appendNextResult(sum, first, l1.next);
                l1 = l1.next;
            }
            if (first.next != null) {
                first = first.next;
            }
        }
        if (remainder > 0) {
            first.next = new ListNode(remainder);
        }
        return last;
    }

    private static int appendNextResult(int sum, ListNode first, ListNode current) {
        int remainder;
        if (sum > 9) {
            first.val = sum % 10;
            remainder = 1; //assuming all the values are not exceeding 9
        } else {
            first.val = sum;
            remainder = 0;
        }
        if (current != null) {
            first.next = new ListNode();
        }
        return remainder;
    }
}

@ToString
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
