package com.softigress.magicsigns._system.Settings._base;

public class SettingBase<T> {

    public String n;
    public T v;
    public Class type;

    public SettingBase(String name, T value) {
        this.n = name;
        this.v = value;
        this.type = value.getClass();
    }

    public long getHash() {
        return (long)n.hashCode() + (long)v.hashCode();
    }

    public void recycle() {
        n = null;
        v = null;
        type = null;
    }
}
