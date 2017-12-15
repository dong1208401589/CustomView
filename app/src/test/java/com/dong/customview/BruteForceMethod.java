package com.dong.customview;

import android.support.annotation.NonNull;

import org.junit.Test;

/**
 * Created by Administrator on 2017/12/9.
 * 蛮力法
 */

public class BruteForceMethod {

    @Test
    public void test(){
        int[] a={8,9,2,4,6,1,0,5,3};
        bubbleSort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+" ");
        }

    }

    /**
     * 冒泡排序
     * @param array
     */
    public static void bubbleSort(int[] array){
        for (int i = array.length-1; i >=0 ; i--) {
            boolean flag=true;
            for (int j = 0; j <i ; j++) {
                if(array[j]>array[j+1]){
                    int temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;//交换
                    flag=false;//优化
                }
                if (flag){
                    break;
                }
            }
        }
    }
    //==================================================================
    /**
     * 多关键字选择排序
     */
    @Test
    public void testMoreKey(){
        Card[] cards={new Card(2,2),
                new Card(7,2),
                new Card(7,3),
                new Card(5,2),
                new Card(8,3),
                new Card(8,1),
                new Card(8,4)};
        bubbleSoreMoreKey(cards);
        for (int i = 0; i < cards.length; i++) {
            System.out.printf(cards[i].toString()+"\n");
        }
    }

    public static void bubbleSoreMoreKey(Card[] cards){
        for (int i = cards.length-1; i >=0; i--) {
            boolean flag=true;
            for (int j = 0; j < i ; j++) {
                if (cards[j].compareTo(cards[j+1])>0){
                    Card temp=cards[j];
                    cards[j]=cards[j+1];
                    cards[j+1]=temp;
                    flag=false;
                }
            }
            if (flag){
                break;
            }
        }
    }

    class Card implements Comparable{

        //纸牌类  有点数，花式两个属性

        public int pokerColor;
        public int cardPoint;

        public Card(int cardPoint, int pokerColor) {
            this.pokerColor = pokerColor;
            this.cardPoint = cardPoint;
    }

        @Override
        public String toString() {
            return "  cardPoint="+cardPoint+"  pokerColor="+pokerColor;
        }

        @Override
        public int compareTo(@NonNull Object o) {
            Card card= (Card) o;
            if (cardPoint>card.cardPoint)
            {
                return 1;
            }else
            if (cardPoint<card.cardPoint){
                return -1;
            }

            if (pokerColor>card.pokerColor){
                return 1;
            }else
            if (pokerColor<card.pokerColor){
                return -1;
            }

            return 0;
        }
    }

    //==================================================================
    @Test
    public void selectSortTest(){
        int[] array={4,3,6,8,1,9,3,5};
        selectSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+" ");
        }
    }

    /**
     * 选择排序
     * @param array
     */
    public void selectSort(int[] array){

        for (int i = 0; i <array.length -1; i++) {
            int index=i;
            for (int j = i; j < array.length; j++) {
                if (array[j]<array[index]){
                    index=j;
                }
            }

            if (index!=i){
                int temp=array[i];
                array[i]=array[index];
                array[index]=temp;
            }


        }

    }

}
