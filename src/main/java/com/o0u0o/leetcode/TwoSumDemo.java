package com.o0u0o.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums和一个整数目标值 target，
 * 请你在该数组中找出 和为目标值 target的那两个整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * 你可以按任意顺序返回答案。
 *
 *
 * 示例 1：
 *
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 示例 2：
 *
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * 示例 3：
 *
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 *
 * @author o0u0o
 * @date 2021/6/10 9:53 下午
 */
public class TwoSumDemo {

    /**
     * 暴力破解法：通过双重循环遍历数组中所有的元素的两两组合，
     * 当出现符合的和时返回两个元素的下标
     * @param nums
     * @param target
     * @return
     */
    public static int[] toSum1(int[] nums, int target){
        for (int i = 0; i < nums.length; i++){
            for (int j = i + 1; j < nums.length; i++){
                if (target - nums[i] == nums[j]){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * 使用hashmap
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum2(int[] nums, int target){
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int temp = target - nums[i];
            //如果map是否含该数字
            if (map.containsKey(temp)){
                return new int[]{map.get(temp), i};
            }
            //利用k、v
            map.put(nums[i], i);
        }
        return null;
    }


    public static void main(String[] args) {
        int [] nums = new int[]{2, 7, 11, 15};
        int target = 17;

        int[]  myIndex = twoSum2(nums, target);
        for (int e : myIndex){
            System.out.println(e);
        }

    }
}
