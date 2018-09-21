package com.huzb.splike;

import com.huzb.splike.controller.DemoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

/**
 * @author huzb
 * @version v1.0.0
 * @date 2018/9/8
 */
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) throws Exception {
        class Solution {
            public List<List<Integer>> fourSum(int[] nums, int target) {
                if(nums.length<4){
                    return new ArrayList();
                }
                Arrays.sort(nums);
                List<List<Integer>> result = new ArrayList<List<Integer>>();
                for(int i = 1;i<nums.length-2;i++){
                    int starta = 0;
                    while(starta<i){
                        int startc = i+1;
                        int startd = nums.length-1;
                        while(startc<startd){
                            int a = nums[starta];
                            int b = nums[i];
                            int c = nums[startc];
                            int d = nums[startd];
                            if(a+b+c+d<target){
                                startc++;
                            }
                            else if(a+b+c+d>target){
                                startd--;
                            }else{
                                List<Integer> item = new ArrayList<Integer>();
                                item.add(a);
                                item.add(b);
                                item.add(c);
                                item.add(d);
                                result.add(item);
                                startc++;
                            }
                        }
                        starta++;
                    }
                }
                int i = 0;
                Set<Integer> index = new TreeSet<>();
                while(i<result.size()-1){
                    int j = i+1;
                    while(j<result.size()){
                        if(result.get(i).get(0).equals(result.get(j).get(0))
                                && result.get(i).get(1).equals(result.get(j).get(1))
                                && result.get(i).get(2).equals(result.get(j).get(2))
                                && result.get(i).get(3).equals(result.get(j).get(3))){
                            index.add(j);
                        }
                        j++;
                    }
                    i++;
                }
                for(int k=index.size()-1;k>=0;k--){
                    result.remove((int)((TreeSet<Integer>) index).pollLast());
                }
                return result;
            }
        }
        Solution s = new Solution();
        int[] nums = {-5,5,4,-3,0,0,4,-2};
        System.out.print(s.fourSum(nums,4));
        //SpringApplication.run(MainApplication.class, args);
    }
}
