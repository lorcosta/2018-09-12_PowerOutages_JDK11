package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Nerc {
	private int id;
	private String value;
	private Boolean donazioneInCorso=false;
	private List<Donazione> listDonazioni=new ArrayList<>();
	
	public Nerc(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nerc other = (Nerc) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		return builder.toString();
	}

	public Boolean getDonazioneInCorso() {
		return donazioneInCorso;
	}

	public void setDonazioneInCorso(Boolean donazioneInCorso) {
		this.donazioneInCorso = donazioneInCorso;
	}

	public List<Donazione> getListRicevitoriDonazioni() {
		return listDonazioni;
	}

	public void addListRicevitoriDonazioni(Nerc ricevitore,LocalDateTime data) {
		this.listDonazioni.add(new Donazione(this,ricevitore,data));
	}
}
