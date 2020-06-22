package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	private Graph<Nerc,DefaultWeightedEdge> graph;
	private PowerOutagesDAO dao=new PowerOutagesDAO();
	private Map<Integer,Nerc> idMapNerc=new HashMap<>();
	private Simulator sim=new Simulator();
	public void creaGrafo() {
		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, dao.loadAllNercs(idMapNerc));
		List<Adiacenza> adiacenze=dao.getArchi(idMapNerc);
		for(Adiacenza a:adiacenze) {
			if(!this.graph.containsEdge(a.getN1(), a.getN2()) && !a.getN1().equals(a.getN2()) && 
					this.graph.containsVertex(a.getN1()) && this.graph.containsVertex(a.getN2())) {
				Graphs.addEdgeWithVertices(this.graph, a.getN1(), a.getN2(), a.getPeso());
			}
		}	
	}
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	public List<Nerc> getAllNerc(){
		return dao.loadAllNercs(idMapNerc);
	}
	public List<Adiacenza> visualizzaVicini(Nerc n) {
		List<DefaultWeightedEdge> vicini=new ArrayList<>(this.graph.outgoingEdgesOf(n));
		List<Adiacenza> adiacenze=new ArrayList<>();
		for(DefaultWeightedEdge e:vicini) {
			adiacenze.add(new Adiacenza(this.graph.getEdgeSource(e),this.graph.getEdgeTarget(e),this.graph.getEdgeWeight(e)));
		}
		Collections.sort(adiacenze);
		return adiacenze;
	}
	public void simula(Integer k) {
		sim.init(k,this.graph,dao.getAllOutages(idMapNerc));
		sim.run();
	}
	public Integer getCatastrofi() {
		return sim.getCatastrofi();
	}
	public Map<Nerc,Integer> getNercBonus(){
		return sim.getNercBonus();
	}
}