package it.polito.tdp.poweroutages.model;

public class Adiacenza implements Comparable<Adiacenza>{
	private Nerc n1;
	private Nerc n2;
	private Double peso;
	/**
	 * @param n1
	 * @param n2
	 * @param d
	 */
	public Adiacenza(Nerc n1, Nerc n2, Double d) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.peso = d;
	}
	public Nerc getN1() {
		return n1;
	}
	public void setN1(Nerc n1) {
		this.n1 = n1;
	}
	public Nerc getN2() {
		return n2;
	}
	public void setN2(Nerc n2) {
		this.n2 = n2;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenza o) {
		return -this.peso.compareTo(o.getPeso());
	}
	
}
