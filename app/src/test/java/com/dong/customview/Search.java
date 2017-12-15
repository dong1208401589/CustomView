package com.dong.customview;

import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/12/9.
 */

public class Search {

    @Test
    public void binarySerachTest(){
        int[] a={2,4,11,22,45,66,77,71,77};
        System.out.print(binarySearch(a,0,a.length,66));
    }

    /**
     * 二分查找
     * @param array
     * @param low
     * @param height
     * @param key
     * @return
     */
    public static int binarySearch(int[] array,int low,int height,int key){
        int lowIndex=low;
        int heightIndex=height-1;//注意这里要用左闭后开的区间

        while (lowIndex<=heightIndex){
            int midIndex=(heightIndex+lowIndex)>>>1;//除以2
            if (array[midIndex]<key){
                lowIndex=midIndex-1;
            }else if (array[midIndex]>key){
                heightIndex=midIndex+1;
            }else {
                return midIndex;
            }
        }
        return -(lowIndex+1);
    }

    @Test
    public void quickSortTest(){
        int[] a={2,1,54,66,43,6,54,3,33,44,55,65,9,65};
        quickSort(a,0,a.length-1);
        for (int b:a
             ) {
            System.out.print(b+"  ");
        }
    }

    /**
     * 快速排序
     */
    public void quickSort(int[] array,int begin,int end){
        if (end-begin<=1) return;
        int low=begin;
        int height=end;
        int x=array[begin];
        boolean direction=true;//表示方向值
        L1:
        while (low<height){
            if (direction){
                for (int i = height; i > low; i--) {
                    if (array[i]<=x){
                        array[low++]=array[i];
                        height=i;
                        direction=!direction;
                        continue L1;
                    }
                }
                height=low;
            }else {
                for (int i = low; i < height; i++) {
                    if (array[i]>=x){
                        array[height--]=array[i];
                        low=i;
                        direction=!direction;
                        continue L1;
                    }
                }
                low=height;

            }
        }
        array[low]=x;
        quickSort(array,begin,low-1);
        quickSort(array,low+1,end);
    }


    //归并排序 主要用在链表中  缺点对内存开销比较大

    /**
     * 对数组中前后已经排好序的数组进行排序
     * @param array
     * @param left
     * @param mid
     * @param right
     */
    public static void merge(int[] array,int left,int mid,int right){
        int leftSize=mid-left;
        int rightSize=right-mid+1;

        int[] leftArray=new int[leftSize];
        int[] rightArray=new int[rightSize];

        for (int i = left; i <mid; i++) {
            leftArray[i-left]=array[i];
        }

        for (int i = mid; i <=right; i++) {
            rightArray[i-mid]=array[i];
        }

        int i=0;
        int j=0;
        int k=left;

        while (i<leftSize&&j<rightSize){
            if (leftArray[i]<rightArray[j]){
                array[k++]=leftArray[i++];
            }else {
                array[k++]=rightArray[j++];
            }
        }

        while (i<leftSize){
            array[k++]=leftArray[i++];
        }

        while (j<rightSize){
            array[k++]=rightArray[j++];
        }
    }


    public static void mergeSort(int[] array,int left,int right){
        if (left==right){
            return;
        }else {
            int mid=(right+left)>>>1;
            mergeSort(array,left,mid);
            mergeSort(array,mid+1,right);
            merge(array,left,mid+1,right);
        }
    }
    @Test
    public void mergeSortTest(){
        int[] array={2,4};
        mergeSort(array,0,array.length-1);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+" ");
        }
    }



    //比赛日程表问题
    public static int[][] table(int k){
        int n= 1<<k;

        int[][] array=new int[n][n];

        for (int i = 0; i < n; i++) {
            array[0][i]=i+1;
        }

        for (int r = 0; r < n; r=r*2) {
            for (int i = 0; i < n; i=i+r*2) {
                copy(array,0,i,r,r+1,r);
                copy(array,0,r+1,r,i,r);
            }
        }
        

        return array;

    }


    /**
     * 对解复制数组
     * @param array
     * @param fromx 开始x点
     * @param formy 开始y点
     * @param tox 复制新开始x点
     * @param toy 复制新开始y点
     * @param r 表示对角线上元素个数
     */
    public static void copy(int[][] array,int fromx,int formy,int tox,int toy,int r){
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                array[tox+i][toy+j]=array[fromx+i][formy+j];
            }
        }

    }




    //============================================================

    //有问题
    @Test
    public void radixSortTest(){
        LinkedList<Mahjong> linkedList=new LinkedList<>();
        linkedList.add(new Mahjong(1,3));
        linkedList.add(new Mahjong(2,6));
        linkedList.add(new Mahjong(3,4));
        linkedList.add(new Mahjong(2,3));
        linkedList.add(new Mahjong(1,3));
        linkedList.add(new Mahjong(3,5));
        linkedList.add(new Mahjong(1,3));
        linkedList.add(new Mahjong(1,9));
        linkedList.add(new Mahjong(2,6));
        linkedList.add(new Mahjong(1,3));
        linkedList.add(new Mahjong(1,1));
        System.out.print(linkedList.toString()+"\n");
        radixSort(linkedList);
        System.out.print(linkedList.toString());
    }



    /**
     * 基数排序  以麻将为例
     * @param list
     */
    public static void radixSort(LinkedList<Mahjong> list){
        //排点数
        //先初始化数组
       /* LinkedList[] randList=new LinkedList[9];
        for (int i = 0; i < 9; i++) {
            randList[i]=new LinkedList();
        }
        //把麻将一个个放进数组中
        while (list.size()>0){
            Mahjong mahjong=list.remove();
            randList[mahjong.rank-1].add(mahjong);
        }

        //把数组中的数据拿出来
        for (int i = 0; i < 9; i++) {
            list.addAll(randList[i]);
        }*/

        /*//排花色
        LinkedList[] suitList=new LinkedList[3];
        for (int i = 0; i < 3; i++) {
            suitList[i]=new LinkedList();
        }
        //把麻将一个个放进数组中
        while (list.size()>0){
            Mahjong mahjong=list.remove();
            suitList[mahjong.suit-1].add(mahjong);
        }

        //把数组中的数据拿出来
        for (int i = 0; i < 3; i++) {
            list.addAll(suitList[i]);
        }*/


        //先对点数进行分组
        LinkedList[] rankList=new LinkedList[9];
        for (int i=0;i<rankList.length;i++){
            rankList[i]=new LinkedList();
        }
        //把数据一个一个的放入到对应的组中
        while(list.size()>0){
            //取一个
            Mahjong m=list.remove();
            //放到组中
            rankList[m.rank-1].add(m);
        }
        //把9个组合到一起
        for (int i = 0; i < rankList.length; i++) {
            list.addAll(rankList[i]);
        }
        System.out.print(list.toString()+"\n");
        //先花色数进行分组
        LinkedList[] suitList=new LinkedList[3];
        for (int i=0;i<suitList.length;i++){
            suitList[i]=new LinkedList();
        }
        //把数据一个一个的放入到对应的组中
        while(list.size()>0){
            //取一个
            Mahjong m=list.remove();
            //放到组中
            suitList[m.suit-1].add(m);
        }
        //把3个组合到一起
        for (int i = 0; i < suitList.length; i++) {
            list.addAll(suitList[i]);
        }
    }


    class Mahjong{
        public int suit;//花色 筒 万 索
        public int rank;//点数  一 二 三

        public Mahjong(int suit, int rank) {
            this.suit = suit;
            this.rank = rank;
        }

        @Override
        public String toString() {
            return "("+rank+" "+suit+")";
        }
    }

    //==========================================================
    @Test
    public void insertSortTest(){
        int[] array={4,33,65,22,4,66,44,3,66};
        for (int i = 0; i < array.length; i++) {
            System.out.print(" "+ array[i]);
        }
        System.out.print(" \n");
        shellSort(array,3);
        for (int i = 0; i < array.length; i++) {
            System.out.print(" "+ array[i]);
        }

        System.out.print(" \n");
        shellSort(array,1);
        for (int i = 0; i < array.length; i++) {
            System.out.print(" "+ array[i]);
        }

    }

    /**
     * 插入排序
     * @param array
     */
    public static void insertSort(int [] array){

        for (int i = 0; i < array.length; i++) {
            int j=i;
            int target=array[i];

            while (j>0&& target<array[j-1]){

                array[j]=array[j-1];
                j--;

            }

            array[j]=target;

        }
    }

    /**
     * 希尔排序
     * @param array
     * @param step  步长值  （等于1时相当于插入排序）  有人算出最优的步长值  1  4   10  等几个数
     */
    public static void shellSort(int[] array,int step){

        for (int k = 0; k < step; k++) {
            for (int i = k+step; i < array.length; i=i+step) {
                int j=i;
                int target=array[i];
                while (j>step-1 && target<array[j-step]){
                    array[j]=array[j-step];
                    j-=step;
                }

                array[j]=target;
            }
        }

    }

}
