package com.softigress.magicsigns._Base;

import java.lang.reflect.Array;

public class ArrayRecyclableSimple<E> implements IRecycle {

    private Class<E> type;
    private int size;
    private E[] items;

    public ArrayRecyclableSimple(Class<E> type) {
        this.type = type;
        items = getArray(1);
    }

    public E[] getItems() { return items; }

    private <E> E[] getArray(int size) {
        return (E[]) Array.newInstance(this.type, size);
    }

    public <E> void add(E item) {
        E[] newItems = getArray(size + 1);
        if (items != null && size > 0)
            System.arraycopy(items, 0, newItems, 0, size);
        newItems[size] = item;
        this.size++;
        items = getArray(size);
        System.arraycopy(newItems, 0, items, 0, size);
    }

    public void recycle() {
        if (items != null) {
            for (E a : items)
                if (a != null) {
                    IRecycle aa = (IRecycle) a;
                    //if (aa != null)
                    aa.recycle();
                }
            items = null;
        }
    }
}
