package com.upupgogogo;

/**
 * Created by upupgogogo on 2018/3/19.下午6:29
 */
public interface LList<T> {

    boolean isEmpty();

    int length();

    void add(int index, T x);

    void add(T x);

    T remove(int index);

    void set(int index, T x);

    T get(int index);


}
