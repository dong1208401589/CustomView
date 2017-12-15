package com.dong.customview;

import org.junit.Test;

/**
 * Created by Administrator on 2017/12/9.
 * 递归算法
 */

public class Recursion {
    @Test
    public void fibonacciSequenceTest(){
        System.out.print(fibonacciSequence(30));
    }

    /**
     * 斐波那契数列 f(n)=f(n-1)+f(n-2)   1  1   2   3   5   8   13   21  34   55    89
     * @param n
     */
    public static int fibonacciSequence(int n){
        if (n==1||n==2){
            return 1;
        }else {
            //类似二叉树模型
            /**
             *                              f(5)
             *                          /            \
             *                 f(4)                      f(3)
             *           f(3)        f(2)             f(2)      f(1)
             *   f(2)      f(1)      1                1           1
             */
            return fibonacciSequence(n-1)+fibonacciSequence(n-2);
        }
    }

    @Test
    public void hanoiTest(){
        hanoi(3,1,2,3);
    }

    /**
     * 汉诺塔
     * @param n 盘片数量
     * @param start 第一根柱子
     * @param mid  第二根柱子
     * @param end  第三根柱子
     */
    public static void hanoi(int n,int start,int mid,int end){
        if (n<=1){
            System.out.printf(start+"-------------->"+end+"\n");
        }else {
            hanoi(n-1,start,end,mid);
            System.out.printf(start+"-------------->"+end+"\n");
            hanoi(n-1,mid,start,end);
        }

    }
}
