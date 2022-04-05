package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List <Esame> esami;
	private Set <Esame> migliore;
	private double mediaMigliore;
	
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.esami= dao.getTuttiEsami();
		
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int m) { //INPUT
		
		//ripristino soluzione migliore quando l'utente ripreme il bottone
		this.migliore= new HashSet<Esame>();
		this.mediaMigliore= 0.0;
		
		Set <Esame> parziale = new HashSet<Esame>();
	//funzione che scende nei vari livelli e che cercherà di riempire la nostra lista da ritornare
		cerca1(parziale, 0, m);
		
		
		System.out.println("TODO!");
		return migliore;	//OUTPUT
	}

	
	private void cerca1(Set<Esame> parziale, int Livello, int m) {
		//1) Controllare i casi terminali
		int sommaCrediti = this.sommaCrediti(parziale);
		
		if (sommaCrediti > m)
			return;//fail, soluzione non valida
		
		if (sommaCrediti == m) {
			//soluzione valida ma devo vedere se è la migliore
			double mediaVoti = this.calcolaMedia(parziale);
			if(mediaVoti > this.mediaMigliore) {
				migliore = new HashSet <Esame>(parziale); //così sto facendo una copia di parziale
				this.mediaMigliore=mediaVoti;
			}
			return;
		}
		
		//se siamo qui, i crediti saranno minori di m
		
		if(Livello == esami.size()) {
			//finiamo gli esami, siamo alla fine di un ramo (L=Num Esami), ho raggiunto il fondo
			return;
		}
		
		
		
		
		//2) GENERARE SOTTOPROBLEMI
		for(Esame e : esami) {
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca1(parziale, Livello+1, m); //backtracking
				parziale.remove(e);
			}
		}
	}

	public double calcolaMedia(Set<Esame> esami) { //calcolo la media dato un insieme di dati
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
