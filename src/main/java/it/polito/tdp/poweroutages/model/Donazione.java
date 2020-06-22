package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Donazione {
	private Nerc donatore;
	private Nerc ricevitore;
	private LocalDateTime data;
	/**
	 * @param donatore
	 * @param ricevitore
	 * @param data
	 */
	public Donazione(Nerc donatore, Nerc ricevitore, LocalDateTime data) {
		super();
		this.donatore = donatore;
		this.ricevitore = ricevitore;
		this.data = data;
	}
	public Nerc getDonatore() {
		return donatore;
	}
	public void setDonatore(Nerc donatore) {
		this.donatore = donatore;
	}
	public Nerc getRicevitore() {
		return ricevitore;
	}
	public void setRicevitore(Nerc ricevitore) {
		this.ricevitore = ricevitore;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
}
