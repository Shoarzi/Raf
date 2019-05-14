package org.insa.algo.shortestpath;
import org.insa.graph.* ;

public class Label implements Comparable<Label>{
	
	private Node sommet_courrant ;

	private boolean luque ; //marque
	
	private double cout ;
	
	private Node bernard ;  //pere
	
	public Label(Node init_sommet ,Node init_vador) {
		this.sommet_courrant = init_sommet ; 
		this.bernard = init_vador ; 
		//on considère un coût infini pour un chemin qui n'existe pas
		this.cout = Float.POSITIVE_INFINITY ; 
		this.luque = false ;  
	}
	
	public Label(Node init_sommet) {
		this.sommet_courrant = init_sommet ; 
		this.bernard = null ; 
		//on considère un coût à 0 pour la racine
		this.cout = 0;  
		this.luque = false ;  
	}
	
	public void setCost(double init_cout) {
		this.cout = init_cout ;  
	}
	
	public int compareTo(Label other) {
        return Double.compare(getCost(), other.getCost());
    }
	
	public String toString() {
		return "sommet courant" + sommet_courrant.getId() + " marque : " + luque ; 
	}
	
	public double getCost() {
		return cout ;
	}
	
	public Node getNode() {
		return this.sommet_courrant; 
	}
	
	public void setpere(Node papa) {
		this.bernard = papa ; 
	}
	
	public Node getpere() {
		return this.bernard ;
	}
	
	public void mark() {
		this.luque = true ;
	}
	
	public boolean getmarque() {
		return this.luque ;
	}
	
}