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
	
	 
	 protected int nb_nodes ;
	

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    
    public int getNbNodesReached() {
    	System.out.print(nb_nodes + "\n");
    	return nb_nodes ;
    }

    
    public Label[] Initialisation( ShortestPathData data)
    {
    	List<Node> Noeuds = data.getGraph().getNodes() ; 
        Label labete[]  = new Label[Noeuds.size()]  ; 
        Node racine = data.getOrigin() ; 
        Label label_0 = new Label(racine) ;
        Node dest = data.getDestination() ;
        nb_nodes = 0 ;
        
        //initialise racine
        labete[racine.getId()] = label_0 ; 
        
        //initialisation
        for (Node sac : Noeuds) {
        	if (!(sac.equals(racine))) {
        		labete[sac.getId()] = new Label(sac, null) ;
        	}
        }
        return labete ;
    	
    }
    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = new ShortestPathSolution(data);
   
        int nb = 0 ;
        nb_nodes = 0 ;
        //creation des types
        
        List<Node> Noeuds = data.getGraph().getNodes() ; 
        Node racine = data.getOrigin() ; 
        Node dest = data.getDestination() ; 
        boolean possible = true ; 
        if ((racine==null) || (dest==null) ) {
        	System.out.print("Error : Nodes Not Found \n");
        	possible = false ;
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE) ; 
        }
        else {
        
        
        Label[] labete = Initialisation(data) ;
        //variables pour tests
        double cout_label_act = 0 ; 
        double cout_label_prec = -1 ;
        int iteration = 0 ;

        
        
        
        BinaryHeap<Label> grostas = new BinaryHeap<Label>() ;
        grostas.insert(labete[racine.getId()]); 
        
     // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        while (possible && !(labete[dest.getId()].getmarque())) { 
        	nb ++ ;
        	if (grostas.isEmpty()) {
        		System.out.print("Tas vide \n");
        		solution = new ShortestPathSolution(data, Status.INFEASIBLE) ; 
        		possible = false ; 
        	}
        	else {
        		
	        	Label x = grostas.deleteMin() ;
	        	
	        	//evolution de taiile du tas
	        	//System.out.print("size heap : " + grostas.size() + "\n");
	        	//System.out.print("valid : "+ grostas.isValid(0) + "\n");
	        	
	        	//System.out.print("label atteint : " + x.getId()+ " \n");
	        	
	        	if (x.getmarque()) {
	        		System.out.print("element deja marqu� y a un probl�me, merci mr Le Botlan \n"); 
	        	}
        		x.mark() ; 
        		
        		
        		//pour afficher les valeurs finales des labels
        		/*
        		if(x.getpere()!=null) {
        			System.out.print(x.getNode().getId() + " cout : " + x.getTotalCost() + " pere : " + x.getpere().getId() + "\n") ;
        		}
        		else {
        			System.out.print(x.getNode().getId() + " cout : " + x.getTotalCost() + " pere : " + x.getpere() + "\n") ;
        		}
        		*/
	        	
	        	
	        	//verif cout 
        		cout_label_act = x.getTotalCost(); 
	        	if (cout_label_act < cout_label_prec) {
	        		//System.out.print(nb + " " + "act : " + cout_label_act + " " + "prec : " + cout_label_prec + " cout chocolatine \n");	
	        	}
	        	cout_label_prec = cout_label_act ; 
	   
	        	notifyNodeMarked(x.getNode()) ; 
	        	if (x.getNode().hasSuccessors()) {
		        	
	        		//System.out.print(x.getId() + " ");
	        		for (Arc succesor : x.getNode().getSuccessors()) {
	        			
	        			// Small test to check allowed roads...
	                    if (!data.isAllowed(succesor)) {
	                        continue;
	                    }
	                    
	        			Node next = succesor.getDestination() ;
	        			Label nextL = labete[next.getId()] ; 
	        			//System.out.print("voici le successeur dans lequel je suis " + labete[next.getId()] + "\n ");
	
	        			if (!(nextL.getmarque())) {
	        				double current_cost ;
	        
	        				current_cost = (x.getCost() + data.getCost(succesor)) ; 
	      
	        				// pour voir ce qu'on fait graphiquement
	        				if (Double.isInfinite(nextL.getCost()) && Double.isFinite(current_cost)) {
	                            notifyNodeReached(succesor.getDestination());
	                            nb_nodes++ ;
	        				}
	        				
	        				
	        				if ((nextL.getCost() > current_cost)  ){
	        					
	        					if (!(nextL.getvisite())) {
	        						nextL.visite();
	        					}
	        					else {
	        						grostas.remove(nextL);
	        					}
	        					nextL.setCost(current_cost);
		        				nextL.setpere(x.getNode());
		        				grostas.insert(nextL);
	        					
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
    	int nb_node = 0;
        while (possible && !(labete[dest.getId()] == labete[racine.getId()]) ) {
        	nb++ ;
        	//System.out.print(labete[dest.getId()].getpere().getId() + " "); 
        	Noeud_solution.add(labete[dest.getId()].getpere()) ;
        	dest = labete[dest.getId()].getpere(); 
        	nb_node ++ ;
        	//System.out.print("while 2 \n");
        }
         
        Collections.reverse(Noeud_solution);
        
        Path chemin = Path.createShortestPathFromNodes(data.getGraph(), Noeud_solution) ;  
        if (solution.getStatus() != Status.INFEASIBLE) {
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin) ;
        }
        //System.out.print("nb iteration : " + nb + "\n");
        //System.out.print("nb arc chemin : " + (nb_node-1) + "\n");
        }
        return solution;

    }




}
