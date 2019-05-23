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
   
        int nb = 0 ;
        //creation des types
        
        List<Node> Noeuds = data.getGraph().getNodes() ; 
        Label labete[]  = new Label[Noeuds.size()]  ; 
        Node racine = data.getOrigin() ; 
        Label label_0 = new Label(racine) ;
        Node dest = data.getDestination() ; 
        boolean possible = true ; 
        
        //variables pour tests
        double cout_label_act = 0 ; 
        double cout_label_prec = -1 ;
        int taille = 0 ;

        
        //initialise racine
        labete[racine.getId()] = label_0 ; 
        
        //initialisation
        for (Node sac : Noeuds) {
        	if (!(sac.equals(racine))) {
        		labete[sac.getId()] = new Label(sac, null) ;
        	}
        }
        
        BinaryHeap<Label> grostas = new BinaryHeap<Label>() ;
        grostas.insert(label_0); 
        
     // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        while (!(labete[dest.getId()].getmarque()) && possible) { 
        	nb ++ ;
        	if (grostas.isEmpty()) {
        		System.out.print("Tas vide \n");
        		solution = new ShortestPathSolution(data, Status.INFEASIBLE) ; 
        		possible = false ; 
        	}
        	else {
        		
	        	Label x = grostas.deleteMin() ;
	        	
	        	//evolution de taiile du tas
	        	System.out.print("size heap : " + grostas.size() + "\n");
	        	
	        	//System.out.print("label atteint : " + x.getId()+ " \n");
	        	if (x.getmarque()) {
	        		System.out.print("element deja marqué y a un problème, merci mr Le Botlan \n"); 
	        	}
        		x.mark() ;
	        	
	        	
	        	//verif cout 
        		cout_label_act = x.getCost(); 
	        	if (cout_label_act >= cout_label_prec) {
	        		cout_label_prec = cout_label_act ; 
	        		
	        	}
	        	else {
	        		System.out.print(nb + " " + "act : " + cout_label_act + " " + "prec : " + cout_label_prec + " cout chocolatine \n");
	        	}
	        	notifyNodeMarked(x.getNode()) ; 
	        	if (x.getNode().hasSuccessors()) {
		        	
	        		//System.out.print(x.getId() + " ");
	        		for (Arc succesor : x.getNode().getSuccessors()) {
	        			Node next = succesor.getDestination() ;
	        			Label nextL = labete[next.getId()] ; 
	        			//System.out.print("voici le successeur dans lequel je suis " + labete[next.getId()] + "\n ");
	
	        			if (!(nextL.getmarque())) {
	        				double current_cost ;
	        
	        				current_cost = (x.getCost() + succesor.getLength()) ; 
	      
	        				// pour voir ce qu'on fait graphiquement
	        				if (Double.isInfinite(nextL.getCost()) && Double.isFinite(current_cost)) {
	                            notifyNodeReached(succesor.getDestination());
	        				}
	        				
	        				
	        				if ((nextL.getCost()) > current_cost) {
	        					
	        					////////////////////////////////////////////////////////////////////
	        					//voir si element deja present
	        					if (grostas.find(nextL)==-1) {
	        						nextL.setCost(current_cost);
		        					grostas.insert(nextL);
			        				nextL.setpere(x.getNode());
	        					}
	        					/////////////////////////////////////////////////////////////////// 
	        					
	        				}
	        					//System.out.print("mise a jour \n");
	        			}
	        		
	        		}
	        	}
	        }
        }
        
        notifyDestinationReached(dest) ; 
        List<Node> Noeud_solution = new ArrayList<Node>() ;
    	Noeud_solution.add(dest) ;
    	//System.out.print(dest.getId() + " ") ; 
        while ( !(labete[dest.getId()] == labete[racine.getId()]) && possible) {
        	//System.out.print(labete[dest.getId()].getpere().getId() + " "); 
        	Noeud_solution.add(labete[dest.getId()].getpere()) ;
        	dest = labete[dest.getId()].getpere(); 
        	//System.out.print("while 2 \n");
        }
         
        Collections.reverse(Noeud_solution);
        
        Path chemin = Path.createShortestPathFromNodes(data.getGraph(), Noeud_solution) ;  
        if (solution.getStatus() != Status.INFEASIBLE) {
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin) ;
        }
        return solution;

    }




}
