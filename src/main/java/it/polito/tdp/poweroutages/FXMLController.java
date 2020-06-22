package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Nerc> cmbBoxNerc;

    @FXML
    private Button btnVisualizzaVicini;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	model.creaGrafo();
    	Integer vertici=model.getNumVertici(), archi=model.getNumArchi();
    	if(vertici==0 || archi.equals(0)) {
    		this.txtResult.appendText("ATTENZIONE! Qualcosa e' andato storto nella creazione del grafo.\n");
    		return;
    	}
    	this.txtResult.appendText("GRAFO CREATO!\n #VERTICI: "+vertici+" e #ARCHI: "+archi+"\n");
    	this.cmbBoxNerc.getItems().addAll(model.getAllNerc());
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	String kString=this.txtK.getText();
    	Integer k=null;
    	try {
    		k=Integer.parseInt(kString);
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! Il valore 'k' inserito non e' un numero intero.\n");
    		return;
    	}
    	model.simula(k);
    	this.txtResult.appendText("I giorni di catastrofe sono stati: "+model.getCatastrofi()+"\n");
    	Map<Nerc,Integer> nercBonus=model.getNercBonus();
    	/*if(nercBonus.size()==0) {
    		this.txtResult.appendText("ATTENZIONE! Errore nella simulazione.\n");
    		return;
    	}*/
    	this.txtResult.appendText("I nerc con i relativi giorni di bonus sono:\n");
    	for(Nerc n:nercBonus.keySet()) {
    		this.txtResult.appendText(n+"-->"+nercBonus.get(n)+"\n");
    	}
    }

    @FXML
    void doVisualizzaVicini(ActionEvent event) {
    	this.txtResult.clear();
    	Nerc n=this.cmbBoxNerc.getValue();
    	if(n==null) {
    		this.txtResult.appendText("ATTENZIONE! Selezionare un valore per visualizzare i vicini.\n");
    		return;
    	}
    	List<Adiacenza> vicini=model.visualizzaVicini(n);
    	if(vicini==null || vicini.size()==0) {
    		this.txtResult.appendText("ATTENZIONE! Errore nella ricerca dei vertici vicini!\n");
    		return;
    	}
    	this.txtResult.appendText("I vicini del vertice selezionato con il relativo peso sono:\n");
    	for(Adiacenza vicino:vicini) {
    		this.txtResult.appendText(vicino.getN2()+"-->"+vicino.getPeso()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cmbBoxNerc != null : "fx:id=\"cmbBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnVisualizzaVicini != null : "fx:id=\"btnVisualizzaVicini\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
