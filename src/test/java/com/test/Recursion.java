package com.test;

public class Recursion {
    // find the sum of an array without using iteration.
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 1, 1};
        int sum = getSum(nums, 0, 0);
        System.out.println(sum);
    }

    private static int getSum(int[] nums, int counter, int sum) {
        sum = sum + nums[counter++];
        if (nums.length <= counter) {
            return sum;
        }
        return getSum(nums, counter, sum);
    }
}
