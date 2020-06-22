package it.polito.tdp.poweroutages.model;

import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.poweroutages.model.Event.Type;

public class Simulator {
	
	private Queue<Event> queue;
	private Graph<Nerc,DefaultWeightedEdge> graph;
	private List<PowerOutage> outages;
	private Integer k;
	private Integer catastrofi=0;
	private Map<Nerc,Integer> nercBonus;
	
	public void init(Integer k,Graph<Nerc,DefaultWeightedEdge> graph,List<PowerOutage> outages) {
		this.queue=new PriorityQueue<>();
		this.graph=graph;
		this.k=k;
		this.catastrofi=0;
		this.nercBonus=new HashMap<>();
		this.outages=outages;
		for(PowerOutage o:this.outages) {
			this.queue.add(new Event(o.getN(),o.getInizio(),o.getFine(),Type.INIZIO_OUTAGE));
		}//aggiunti tutti gli eventi alla coda
		
		
	}

	public void run() {
		while(!this.queue.isEmpty()) {
			Event e=this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch(e.getType()) {
		case CATASTROFE:
			this.catastrofi++;
			break;
		case FINE_OUTAGE:
			Integer giorni;
			if(!this.nercBonus.containsKey(e.getNerc())) {
				giorni=Period.between(e.getInizio().toLocalDate(),e.getFine().toLocalDate()).getDays();
				this.nercBonus.put(e.getNerc(),giorni);
			}else {
				giorni=this.nercBonus.get(e.getNerc());
				this.nercBonus.remove(e.getNerc());
				this.nercBonus.put(e.getNerc(),
						giorni+Period.between(e.getInizio().toLocalDate(),e.getFine().toLocalDate()).getDays());
			}
			break;
		case INIZIO_OUTAGE:
			if(cercaVicino(e)!=null) {
				//c'è un vicino al quale ho donato energia negli ultimi k mesi, fare un metodo che cerca un vicino al quale ho donato energia negli ultimi k mesi e che ritorna il vicino in questione altrimenti null
				List<Nerc> donatori=cercaVicino(e);
				if(donatori.size()==1) {
					//ho 1 solo donatore
					Nerc donatore=donatori.get(0);
					donatore.setDonazioneInCorso(true);
					donatore.addListRicevitoriDonazioni(e.getNerc(),e.getInizio());
					this.queue.add(new Event(e.getNerc(),e.getInizio(),e.getFine(),Type.FINE_OUTAGE));
				}else if(donatori.size()>1) {
					//ho più di un donatore perciò scelgo donatore con indice di correlazione minore
					Nerc donatore=correlazioneMinore(e.getNerc());
					donatore.setDonazioneInCorso(true);
					donatore.addListRicevitoriDonazioni(e.getNerc(),e.getInizio());					
				}
			}else if(correlazioneMinore(e.getNerc())!=null){
				//prendo il vicino con indice di correlazione (PESO) più basso
				Nerc donatore=correlazioneMinore(e.getNerc());
				donatore.setDonazioneInCorso(true);
				donatore.addListRicevitoriDonazioni(e.getNerc(),e.getInizio());	
			}else {
				//aggiungo evento catastrofe
				this.queue.add(new Event(e.getNerc(),e.getInizio(),e.getFine(),Type.CATASTROFE));
			}
			break;
		default:
			break;
		
		}
	}

	private Nerc correlazioneMinore(Nerc nerc) {
		Double pesoMinimo=Double.MAX_VALUE;
		Nerc bestVicino = null;
		for(Nerc n:Graphs.neighborListOf(this.graph, nerc)){
			if(this.graph.getEdgeWeight(this.graph.getEdge(nerc,n))<pesoMinimo) {
				pesoMinimo=this.graph.getEdgeWeight(this.graph.getEdge(nerc,n));
				bestVicino=n;
			}
		}
		return bestVicino;
		
	}

	private List<Nerc> cercaVicino(Event e) {
		List<Nerc> listDonatori=new ArrayList<>();
		for(Nerc n: Graphs.neighborListOf(this.graph, e.getNerc())) {
			if(donazioneNegliUltimiKMesi(n,e.getNerc(),e)==false && n.getDonazioneInCorso()==false) {
				listDonatori.add(n);
			}
		}
		//controllo da lasciare dentro questo metodo
	 	if(listDonatori.size()>=1){
	 		return listDonatori;
	 	}else return null;
	}

	private boolean donazioneNegliUltimiKMesi(Nerc donatore, Nerc ricevitore, Event e) {
		for(Donazione d:donatore.getListRicevitoriDonazioni()) {
			if(d.getRicevitore().equals(ricevitore) && Period.between(d.getData().toLocalDate(),e.getInizio().toLocalDate()).getMonths()<this.k) {
				return false;
			}
		}
		return true;
	}

	public Integer getCatastrofi() {
		return catastrofi;
	}

	public Map<Nerc,Integer> getNercBonus() {
		return nercBonus;
	}

}
