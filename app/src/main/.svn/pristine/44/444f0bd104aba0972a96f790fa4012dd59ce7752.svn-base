package com.softigress.magicsigns.Game.Cells.Membrane;

class MembraneActivatorCollection {

    private static final int activatorCount = 10;
    public final MembraneActivator[] activators = new MembraneActivator[activatorCount];

    private final float activatorFr;
    private final int activatorPodCount;
    private int addIndex = 0;

    public MembraneActivatorCollection(float fr, int podCount) {
        this.activatorFr = fr;
        this.activatorPodCount = podCount;
    }

    public void activate(int duration) { addActivator(true, duration); }
    public void deactivate(int duration) { addActivator(false, duration); }

    private void addActivator(boolean isActivate, int duration) {
        MembraneActivator a = new MembraneActivator(activatorFr, activatorPodCount);
        addIndex++;
        if (addIndex >= activatorCount)
            addIndex = 0;
        activators[addIndex] = a;
        if (isActivate)
            a.activate(activatorFr, duration);
        else
            a.deactivate(activatorFr, duration);
    }

    public void recycle() {
        for (MembraneActivator a: activators)
            if (a != null)
                a.recycle();
    }
}
