package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	public enum Type{
		INIZIO_OUTAGE,
		FINE_OUTAGE,
		CATASTROFE
	}
	private Nerc nerc;
	private LocalDateTime inizio;
	private Type type;
	private LocalDateTime fine;
	/**
	 * @param nerc
	 * @param inizio
	 * @param fine
	 */
	public Event(Nerc nerc, LocalDateTime inizio,LocalDateTime fine,Type type) {
		super();
		this.nerc = nerc;
		this.inizio = inizio;
		this.fine=fine;
		this.type=type;
		
	}
	public Nerc getNerc() {
		return nerc;
	}
	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}
	public LocalDateTime getInizio() {
		return inizio;
	}
	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}
	@Override
	public int compareTo(Event other) {
		if(other.getType()==Type.INIZIO_OUTAGE) {
			return this.inizio.compareTo(other.getInizio());
		}else if(other.getType()==Type.FINE_OUTAGE) {
			return this.fine.compareTo(other.getFine());
		}else {
			return this.fine.compareTo(other.getFine());
		}
		
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public LocalDateTime getFine() {
		return fine;
	}
	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}
	
}
