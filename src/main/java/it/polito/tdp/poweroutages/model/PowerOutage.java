package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutage {
	private Integer id;
	private Nerc n;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	/**
	 * @param id
	 * @param n
	 * @param inizio
	 * @param fine
	 */
	public PowerOutage(Integer id, Nerc n, LocalDateTime inizio, LocalDateTime fine) {
		super();
		this.id = id;
		this.n = n;
		this.inizio = inizio;
		this.fine = fine;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Nerc getN() {
		return n;
	}
	public void setN(Nerc n) {
		this.n = n;
	}
	public LocalDateTime getInizio() {
		return inizio;
	}
	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}
	public LocalDateTime getFine() {
		return fine;
	}
	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PowerOutage other = (PowerOutage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
