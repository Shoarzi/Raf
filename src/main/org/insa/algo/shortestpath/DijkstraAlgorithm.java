package org.insa.algo.shortestpath;
import java.util.List;

import org.insa.graph.*; 
import org.insa.algo.utils.*; 

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        //cration des types
        
        List<Node> Noeuds = data.getGraph().getNodes() ; 
        Label labete[]  = new Label[Noeuds.size()]  ; 
        Node racine = data.getOrigin() ; 
        Label label_0 = new Label(racine) ;
        Node dest = data.getDestination() ; 
        //initialise racine
        labete[racine.getId()] = label_0 ; 
        
        //iniatialisation
        for (Node sac : Noeuds) {
        	if (!(sac.equals(racine))) {
        		labete[sac.getId()] = new Label(sac, null) ;
        	}
        }
        
        BinaryHeap<Node> grostas = new BinaryHeap<Node>() ;
        grostas.insert(racine); 
        
        while (!(labete[dest.getId()].getmarque())) {
        	Node x = grostas.findMin() ;
        	labete[x.getId()].mark() ;
        	if (x.hasSuccessors()) {
        		for (Arc succesor : x.getSuccessors()) {
        			Node next = succesor.getDestination() ;
        			Label nextL = labete[next.getId()] ; 
        			if (!(nextL.getmarque())) {
        				float current_cost = (nextL.getCost() + succesor.getLength()) ; 
        				if ((nextL.getCost()) > current_cost) {
        					nextL.setCost(current_cost);
        					grostas.insert(next);
        					nextL.setpere(x);
        				}
        		
        			}
        		}
        	}
        }
        
        return solution;
    }

}
