package com.machine.management.models;

public class Machine {
    private String id;
    private String reference;
    private String prix;
    private String date;
    private Marque marque;

    public Machine(String id, String reference, String prix, String date, Marque marque) {
        this.id = id;
        this.reference = reference;
        this.prix = prix;
        this.date = date;
        this.marque = marque;
    }

    public Machine(String reference, String prix, String date, Marque marque) {
        this.reference = reference;
        this.prix = prix;
        this.date = date;
        this.marque = marque;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }
}
