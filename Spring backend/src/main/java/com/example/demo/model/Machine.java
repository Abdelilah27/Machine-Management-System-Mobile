package com.example.demo.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




@Entity
@Table(name="machine")
public class Machine {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String reference;
	@Temporal(TemporalType.DATE)
	private Date dateAchat;
	private double prix;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Marque marque;
	
	public Machine() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getDateAchat() {
		return dateAchat;
	}

	public void setDateAchat(Date dateAchat) {
		this.dateAchat = dateAchat;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Marque getMarque() {
		return marque;
	}

	public void setMarque(Marque marque) {
		this.marque = marque;
	}

	@Override
	public String toString() {
		return "Machine [id=" + id + ", reference=" + reference + ", dateAchat=" + dateAchat + ", prix=" + prix
				+ ", marque=" + marque + "]";
	}
	
	
	

}
