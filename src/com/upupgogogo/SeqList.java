package com.upupgogogo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by upupgogogo on 2018/3/19.下午6:34
 */

/**
 * 顺序表
 * @param <T>
 */
public class SeqList<T> implements LList<T> {


    private Object[] element; //数据保存数组，初始化时会给它一个默认大大小

    private int size;   //记录数据的元素，初始化为0

    private static int DEFAULT_CAPACITY = 10;  //给数组的一个默认大小

    public SeqList(){
        this.element = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public SeqList(int capacity){
        if (capacity < 0)
            throw new NegativeArraySizeException("Array length anomaly");
        this.element = new Object[capacity];
        this.size = 0;
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            throw new NegativeArraySizeException("index out of bounds");

        return (T)this.element[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int length() {
        return this.size;
    }

    /**
     * 1.判断参数的可行性
     * 2.判断是否扩容
     * 3.从最后一个元素向后移一个下标，直到当前插入的下标
     * 4.赋值给给当前插入的下标
     * 5.容量长度+1
     * @param index 数组下标
     * @param x
     */
    public void add(int index, T x) {
        if (index < 0 || index > size)
            throw new NegativeArraySizeException("index out of bounds: index: " + index + " size:  " + size);

        if (size == element.length){
            Object[] temp = element;
            this.element = new Object[size*2];
            for (int j = 0; j < temp.length; j++)
                this.element[j] = temp[j];
        }

        for(int j = this.size-1; j >= index; j--)
            element[j+1] = element[j];
        element[index] = x;

        this.size++;
    }

    public void add(T x) {
        this.add(this.size,x);
    }

    public T remove(int index) {
        if (index < 0 || index >= size)
            throw new NegativeArraySizeException("index out of bounds");
        T old = (T)this.element[index];
        for (int j = index; j < this.size-1; j++)
            element[j] = element[j+1];
        element[this.size-1] = null;
        this.size--;
        return old;
    }

    public void set(int index, T x) {
        if (index < 0 || index >= size)
            throw new NegativeArraySizeException("index out of bounds: index: " + index + " size:  " + size);
        this.element[index] = x;

    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.size; i++)
            str += element[i].toString()+",";
        return "["+str+"]";
    }

    public static void main(String[] args) {
//        LList lList = new SeqList();
//        lList.add("11");
//        lList.remove(0);
//        System.out.println(lList);
        List list = new LinkedList();

        System.out.println(list.get(1));
    }
}
