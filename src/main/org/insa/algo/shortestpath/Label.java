package org.insa.algo.shortestpath;
import org.insa.graph.* ;

public class Label implements Comparable<Label>{
	
	private Node sommet_courrant ;

	private boolean mark ; 
	
	private double cout ;
	
	private Node pere ;  
	
	public Label(Node init_sommet ,Node init_vador) {
		this.sommet_courrant = init_sommet ; 
		this.pere = init_vador ; 
		//on considère un coût infini pour un chemin qui n'existe pas
		this.cout = Float.POSITIVE_INFINITY ; 
		this.mark = false ;  
	}
	
	public Label(Node init_sommet) {
		this.sommet_courrant = init_sommet ; 
		this.pere = null ; 
		//on considère un coût à 0 pour la racine
		this.cout = 0;  
		this.mark = false ;  
	}
	
	public void setCost(double init_cout) {
		this.cout = init_cout ;  
	}
	
	public int compareTo(Label other) {
        return Double.compare(getCost(), other.getCost());
    }
	
	public String toString() {
		return "sommet courant" + sommet_courrant.getId() + " marque : " + mark ; 
	}
	
	public double getCost() {
		return cout ;
	}
	
	public Node getNode() {
		return this.sommet_courrant; 
	}
	
	public void setpere(Node papa) {
		this.pere = papa ; 
	}
	
	public Node getpere() {
		return this.pere ;
	}
	
	public void mark() {
		this.mark = true ;
	}
	
	public boolean getmarque() {
		return this.mark ;
	}
	
}