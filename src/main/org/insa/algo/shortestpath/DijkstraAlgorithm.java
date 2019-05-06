package org.insa.algo.shortestpath;

import java.util.List;
import java.util.Scanner;

import org.insa.graph.*;
import org.insa.algo.AbstractSolution;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.*; 
import java.util.ArrayList;
import java.util.Collections;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = new ShortestPathSolution(data);
   
        
        //creation des types
        
        List<Node> Noeuds = data.getGraph().getNodes() ; 
        Label labete[]  = new Label[Noeuds.size()]  ; 
        Node racine = data.getOrigin() ; 
        Label label_0 = new Label(racine) ;
        Node dest = data.getDestination() ; 
        //initialise racine
        labete[racine.getId()] = label_0 ; 
        
        //initialisation
        for (Node sac : Noeuds) {
        	if (!(sac.equals(racine))) {
        		labete[sac.getId()] = new Label(sac, null) ;
        	}
        }
        
        BinaryHeap<Node> grostas = new BinaryHeap<Node>() ;
        grostas.insert(racine); 
        
     // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        while (!(labete[dest.getId()].getmarque())) {
   
        	Node x = grostas.findMin();
        	grostas.remove(x) ;
        	System.out.print("label atteint : " + x.getId()+ " \n");
        	//scanf de putain de java qui marche pas
        	//Scanner scanIn = new Scanner(System.in) ;
        	labete[x.getId()].mark() ;
        	if (x.hasSuccessors()) {
        		System.out.print(x.getId() + " ");
        		for (Arc succesor : x.getSuccessors()) {
        			Node next = succesor.getDestination() ;
        			Label nextL = labete[next.getId()] ; 
        			System.out.print("voici le successeur dans lequel je suis " + labete[next.getId()] + "\n ");

        			if (!(nextL.getmarque())) {
        				float current_cost ;
        				if (Double.isInfinite(nextL.getCost())) {
        					 current_cost = succesor.getLength() ; 
        				}
        				else {
        					 current_cost = (nextL.getCost() + succesor.getLength()) ; 
        				}
        				// pour voir ce qu'on fait graphiquement
        				if (Double.isInfinite(nextL.getCost()) && Double.isFinite(current_cost)) {
                            notifyNodeReached(succesor.getDestination());
        				}
        				
        				
        				if ((nextL.getCost()) > current_cost) {
        					nextL.setCost(current_cost);
        					grostas.insert(next);
        					nextL.setpere(x);
        				}
        		
        			}
        		}
        	}
        }
        
        List<Node> Noeud_solution = new ArrayList<Node>() ;
    	Noeud_solution.add(dest) ;
        while ( !(labete[dest.getId()] == labete[racine.getId()])) {
        	Noeud_solution.add(labete[dest.getId()].getpere()) ;
        	dest = labete[dest.getId()].getpere(); 
        //	System.out.print("while 2 \n");
        }
        //Noeud_solution.add(racine) ; 
        Collections.reverse(Noeud_solution);
        
        Path chemin = Path.createShortestPathFromNodes(data.getGraph(), Noeud_solution) ;  
        
        solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin) ; 
        return solution;
    }
    



}
