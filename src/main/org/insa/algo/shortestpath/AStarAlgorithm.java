package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    public Label[] Initialisation( ShortestPathData data) {
    	nb_nodes = 0 ;
        Node dest = data.getDestination() ;  
    	List<Node> Noeuds = data.getGraph().getNodes() ; 
        LabelStar labete[]  = new LabelStar[Noeuds.size()]  ; 
        Node racine = data.getOrigin() ; 
        LabelStar label_0 = new LabelStar(racine, dest, data) ;
        
        //initialise racine
        labete[racine.getId()] = label_0 ; 
        
        //initialisation
        for (Node sac : Noeuds) {
        	if (!(sac.equals(racine))) {
        		labete[sac.getId()] = new LabelStar(sac, null, dest, data) ;
        	}
        }
        return labete ;
    }





}
