package com.softigress.magicsigns._Base;

import java.util.ArrayList;

public class ArrayRecyclable<E> extends ArrayList<E> implements IRecycle{

    public ArrayRecyclable() {
        super();
    }

    public void recycle() {
        for (E a : this)
            if (a != null) {
                IRecycle aa = (IRecycle) a;
                //if (aa != null)
                    aa.recycle();
            }
        clear();
    }
}
