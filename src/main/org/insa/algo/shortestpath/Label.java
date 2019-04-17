package org.insa.algo.shortestpath;
import org.insa.graph.* ;

public class Label {
	
	private Node sommet_courrant ;

	private boolean luque ; //marque
	
	private float cout ;
	
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
	
	public void setCost(float init_cout) {
		this.cout = init_cout ;  
	}
	
	public float getCost() {
		return cout ;
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