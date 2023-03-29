package com.softigress.magicsigns.Game.Cells.Membrane;

class MembraneCharger {

    private int chargeFullAmount = 200;
    private int chargeCurrentAmount = 0;
    private float chargePercent = 0f;

    public MembraneCharger() { }

    public void setChargeFullAmount(int amount) { chargeFullAmount = amount; }

    public void reset() {
        chargeCurrentAmount = 0;
        chargePercent = 0f;
    }

    public float charge(int amount) {
        chargeCurrentAmount += amount;
        float percent = (float) chargeCurrentAmount / chargeFullAmount;
        if (percent > 1f)
            percent = 1f;
        chargePercent = percent;
        return percent;
    }

    public boolean isCharged() { return chargePercent >= 1f; }
}
