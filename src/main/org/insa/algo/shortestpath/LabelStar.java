package org.insa.algo.shortestpath;

import org.insa.graph.Node;

public class LabelStar extends Label implements Comparable<Label> {
	
	private double cout_estime ; 
	
	public LabelStar(Node init_sommet ,Node init_vador, Node dest) {
		super(init_sommet, init_vador) ; 
		this.cout_estime =  this.getNode().getPoint().distanceTo(dest.getPoint()) ;
	}
	
	public LabelStar(Node init_sommet, Node dest) {
		super(init_sommet) ; 
		this.cout_estime =  this.getNode().getPoint().distanceTo(dest.getPoint()) ;
	}
	
	public double getEstimatedCost() {
		return this.cout_estime ; 
	}
	
	public double getTotalCost() {
		//System.out.print("je suis une star ! \n");
		return (this.getCost() + this.getEstimatedCost()) ; 
	}
	
	public void setEstimatedCost(double cout) {
		this.cout_estime = cout ; 
	}
	
	/*
	public int CompareTo( LabelStar autre) {
        return Double.compare(getEstimatedCost(), autre.getEstimatedCost());
	}
	*/

}
