package com.leetcode;

import java.util.Arrays;

/*
Input: nums = [2,5,1,3,4,7], n = 3
Output: [2,3,5,4,1,7]
Explanation: Since x1=2, x2=5, x3=1, y1=3, y2=4, y3=7 then the answer is [2,3,5,4,1,7].
* */
public class ShuffleArray {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 4, 3, 2, 1};
        int[] shuffle = shuffle(nums, 4);
        System.out.println(Arrays.toString(shuffle));
    }

    public static int[] shuffle(int[] nums, int n) {
        int[] shuffledNums = new int[nums.length];
        int shuffledCount = 0;
        for (int i = 0; i < nums.length / 2; i++) {
            shuffledNums[shuffledCount] = nums[i];
            shuffledNums[shuffledCount + 1] = nums[n++];
            shuffledCount = shuffledCount + 2;
        }
        return shuffledNums;
    }
}
