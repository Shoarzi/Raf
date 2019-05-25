package org.insa.algo.shortestpath;

import org.insa.algo.AbstractInputData;
import org.insa.graph.Node;

public class LabelStar extends Label implements Comparable<Label> {
	
	private double cout_estime ; 
	
	public LabelStar(Node init_sommet ,Node init_vador, Node dest, ShortestPathData data) {
		super(init_sommet, init_vador) ; 
		if (data.getMode()== AbstractInputData.Mode.LENGTH) {
			this.cout_estime =  this.getNode().getPoint().distanceTo(dest.getPoint()) ;
		}
		else {
			int vitesse = Math.max(data.getGraph().getGraphInformation().getMaximumSpeed(),data.getMaximumSpeed()) ;
			this.cout_estime =  this.getNode().getPoint().distanceTo(dest.getPoint())/(vitesse*1000.0f/3600.0f) ;
		}
		
	}
	
	public LabelStar(Node init_sommet, Node dest, ShortestPathData data) {
		super(init_sommet) ; 
		if (data.getMode()== AbstractInputData.Mode.LENGTH) {
			this.cout_estime =  this.getNode().getPoint().distanceTo(dest.getPoint()) ;
		}
		else {
			int vitesse = Math.max(data.getGraph().getGraphInformation().getMaximumSpeed(),data.getMaximumSpeed()) ;
			this.cout_estime =  this.getNode().getPoint().distanceTo(dest.getPoint())/(vitesse*1000.0f/3600.0f) ;
		}
		
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
