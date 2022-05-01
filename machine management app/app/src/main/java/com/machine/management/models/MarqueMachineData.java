package com.machine.management.models;

public class MarqueMachineData {
    String marque;
    int nbrMachine;

    public MarqueMachineData(String marque, int nbrMachine) {
        this.marque = marque;
        this.nbrMachine = nbrMachine;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getNbrMachine() {
        return nbrMachine;
    }

    public void setNbrMachine(int nbrMachine) {
        this.nbrMachine = nbrMachine;
    }
}
