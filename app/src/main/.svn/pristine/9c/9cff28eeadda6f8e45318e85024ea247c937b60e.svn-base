package com.softigress.magicsigns.Activities._base;

class PointerInfo {

    public boolean isInTouch;
    public int x1;
    public int y1;
    private int x2;
    private int y2;
    public int dx;
    public int dy;

    public PointerInfo() { }

    public void Touch(int x, int y) {
        dx = 0;
        dy = 0;
        x1 = x;
        y1 = y;
        isInTouch = true;
    }

    public void Move(int x, int y) {
        x2 = x;
        y2 = y;
        dx = x2 - x1;
        dy = y2 - y1;
        x1 = x2;
        y1 = y2;
    }

    public void TouchUp() {
        isInTouch = false;
    }
}