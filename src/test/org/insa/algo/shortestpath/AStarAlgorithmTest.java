package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.algo.AbstractSolution;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class AStarAlgorithmTest  {
	
private static String Toulouse, Aveyron ;
	
	// Small graph use for tests
    private static Graph graphsimple;

    // List of nodes
    private static Node[] nodes;
    
 // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b,a2d, a2f, a2h, b2c, b2d, d2c, d2e, d2f, d2g, f2g, f2h, h2g, h2i,  c2j, c2e, e2j, e2g, g2j, g2i, i2j ;
	
	//private static ArrayList<ShortestPathSolution> DATA ;
	
	@BeforeClass
    public static void initAll() throws IOException {
		
		//DATA = new ArrayList<ShortestPathSolution>() ;
		
		 // Create nodes
        nodes = new Node[10];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }
       
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, false, 36, "") ;
        
        a2b = Node.linkNodes(nodes[0], nodes[1], 1, speed10, null);
        a2d = Node.linkNodes(nodes[0], nodes[2], 11, speed10, null);
        a2f = Node.linkNodes(nodes[0], nodes[3], 6, speed10, null);
        a2h = Node.linkNodes(nodes[0], nodes[4], 3, speed10, null);
        b2c = Node.linkNodes(nodes[1], nodes[5], 10, speed10, null);
        b2d = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        d2c = Node.linkNodes(nodes[2], nodes[5], 1, speed10, null);
        d2e = Node.linkNodes(nodes[2], nodes[6], 4, speed10, null);
        d2g = Node.linkNodes(nodes[2], nodes[7], 1, speed10, null);
        d2f = Node.linkNodes(nodes[2], nodes[3], 5, speed10, null);
        f2g = Node.linkNodes(nodes[3], nodes[7], 3, speed10, null);
        f2h = Node.linkNodes(nodes[3], nodes[4], 2, speed10, null);
        h2g = Node.linkNodes(nodes[4], nodes[7], 6, speed10, null);
        h2i = Node.linkNodes(nodes[4], nodes[8], 8, speed10, null);
        c2j = Node.linkNodes(nodes[5], nodes[9], 2, speed10, null);
        c2e = Node.linkNodes(nodes[5], nodes[6], 2, speed10, null);
        e2j = Node.linkNodes(nodes[6], nodes[9], 5, speed10, null);
        e2g = Node.linkNodes(nodes[6], nodes[7], 5, speed10, null);
        g2j = Node.linkNodes(nodes[7], nodes[9], 8, speed10, null);
        g2i = Node.linkNodes(nodes[7], nodes[8], 3, speed10, null);
        i2j = Node.linkNodes(nodes[8], nodes[9], 5, speed10, null);
        
        graphsimple = new Graph("ID", "", Arrays.asList(nodes), null);
        
		Toulouse = "toulouse" ;
		Aveyron = "aveyron" ;	      
	}
	
	public static ShortestPathSolution getShortestPathSolution(String mapName, int originId, int destId, int mode, char algo) throws Exception {
		String mapPath="D:\\Telecharger\\" + mapName + ".mapgr";
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapPath))));
		Graph graph = reader.read();
		ShortestPathAlgorithm Algo ;
	    switch(algo) {
	    case 'b':
	    	Algo = new BellmanFordAlgorithm(new ShortestPathData(graph, graph.get(originId), graph.get(destId), ArcInspectorFactory.getAllFilters().get(mode)));
	    	break;
	    case 'd':
	    	Algo = new DijkstraAlgorithm(new ShortestPathData(graph, graph.get(originId), graph.get(destId), ArcInspectorFactory.getAllFilters().get(mode)));
	    	break;
	    case 'a':
	    	Algo = new AStarAlgorithm(new ShortestPathData(graph, graph.get(originId), graph.get(destId), ArcInspectorFactory.getAllFilters().get(mode)));
	    	break;
	    default:
	    	throw new Exception("Code d'algorithme incorrect. Entrez d, b ou a");
	    }
	    return Algo.run();
	}
	
	
    @Test
    public void testSImple() {
    	ArrayList<ShortestPathSolution> DATA = new ArrayList<ShortestPathSolution>() ;
		try {
			ShortestPathAlgorithm Algo ;
			Algo = new DijkstraAlgorithm(new ShortestPathData(graphsimple, nodes[0], nodes[9] , ArcInspectorFactory.getAllFilters().get(0)));
			DATA.add(Algo.run());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(12, (int)DATA.get(0).getPath().getLength());
		DATA.clear();
		
    }
    
    @Test
	public void NoeudPasConnexe() {
		ArrayList<ShortestPathSolution> DATA = new ArrayList<ShortestPathSolution>() ;
		try {
			
			DATA.add(getShortestPathSolution(Toulouse, 22345, 28413, 0, 'a'));
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(AbstractSolution.Status.INFEASIBLE, DATA.get(0).getStatus());
		DATA.clear();	
	}
	
	
	@Test
	public void chemin_nul() {
		ArrayList<ShortestPathSolution> DATA = new ArrayList<ShortestPathSolution>() ;
		try {
			
			DATA.add(getShortestPathSolution(Toulouse, 0, 0, 0, 'a'));
			DATA.add(getShortestPathSolution(Aveyron, 0, 0, 0, 'a'));
			DATA.add(getShortestPathSolution(Toulouse, 0, 0, 2, 'a'));
			DATA.add(getShortestPathSolution(Aveyron, 0, 0, 2, 'a'));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, (int)DATA.get(0).getPath().getLength());
		assertEquals(0, (int)DATA.get(1).getPath().getLength());
		assertEquals(0, (int)DATA.get(2).getPath().getMinimumTravelTime() );
		assertEquals(0, (int)DATA.get(3).getPath().getMinimumTravelTime());
		DATA.clear();
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testSommetInexistant() {
		ArrayList<ShortestPathSolution> DATA = new ArrayList<ShortestPathSolution>() ;
		try {
			String mapPath="D:\\Telecharger\\" + Toulouse + ".mapgr";
			GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapPath))));
			Graph graph = reader.read();
			int node = graph.getNodes().size()-2 ;
			ShortestPathAlgorithm Algo ;
			Algo = new DijkstraAlgorithm(new ShortestPathData(graph, graph.get(node), null, ArcInspectorFactory.getAllFilters().get(0)));
			DATA.add(Algo.run());
			Algo = new DijkstraAlgorithm(new ShortestPathData(graph, null, graph.get(node), ArcInspectorFactory.getAllFilters().get(0)));
			DATA.add(Algo.run());
			Algo = new DijkstraAlgorithm(new ShortestPathData(graph, null, null, ArcInspectorFactory.getAllFilters().get(0)));
			DATA.add(Algo.run());
			
			}
		catch (Exception e) {
			e.printStackTrace();
			}
		assertEquals(null, DATA.get(0).getPath().getLength());
		assertEquals(null, DATA.get(1).getPath().getLength());
		assertEquals(null, DATA.get(2).getPath().getLength());
		DATA.clear();
		
	}
	
	@Test
	public void testSansOracle() {
		ArrayList<ShortestPathSolution> DATA = new ArrayList<ShortestPathSolution>() ;
		try {
			
			DATA.add(getShortestPathSolution(Toulouse, 555, 123, 0, 'a'));
			DATA.add(getShortestPathSolution(Toulouse, 555, 123, 2, 'a'));
			DATA.add(getShortestPathSolution(Aveyron, 5874, 496, 0, 'a'));
			DATA.add(getShortestPathSolution(Aveyron, 5874, 496, 2, 'a'));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("mode : 0 " + "distance : " + DATA.get(0).getPath().getLength() + " temps : " + DATA.get(0).getPath().getMinimumTravelTime() + "\n" );
		System.out.print("mode : 2 " +"distance : " + DATA.get(1).getPath().getLength() + " temps : " + DATA.get(1).getPath().getMinimumTravelTime() + "\n" );
		System.out.print("mode : 0 " +"distance : " + DATA.get(2).getPath().getLength() + " temps : " + DATA.get(2).getPath().getMinimumTravelTime() + "\n" );
		System.out.print("mode : 2 " +"distance : " + DATA.get(3).getPath().getLength() + " temps : " + DATA.get(3).getPath().getMinimumTravelTime() + "\n" );
		DATA.clear();	
	}
	
	
	
	@Test
	public void testCheminOracle() {
		ArrayList<ShortestPathSolution> DATA = new ArrayList<ShortestPathSolution>() ;
		try {
			DATA.add(getShortestPathSolution(Toulouse, 22382, 27539, 0, 'a'));
			DATA.add(getShortestPathSolution(Toulouse, 22382, 27539, 0, 'b'));
			DATA.add(getShortestPathSolution(Aveyron, 64819, 118899, 0, 'a'));
			DATA.add(getShortestPathSolution(Aveyron, 64819, 118899, 0, 'b'));
			DATA.add(getShortestPathSolution(Toulouse, 22382, 27539, 2, 'a'));
			DATA.add(getShortestPathSolution(Toulouse, 22382, 27539, 2, 'b'));
			DATA.add(getShortestPathSolution(Aveyron, 64819, 118862, 2, 'a'));
			DATA.add(getShortestPathSolution(Aveyron, 64819, 118862, 2, 'b'));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals((int)DATA.get(0).getPath().getLength(), (int)(DATA.get(1).getPath().getLength()));
		assertEquals((int)DATA.get(2).getPath().getLength(), (int)(DATA.get(3).getPath().getLength()));
		assertEquals(DATA.get(4).getPath().getMinimumTravelTime(), DATA.get(5).getPath().getMinimumTravelTime(), 0.05);
		assertEquals((double)(DATA.get(6).getPath().getMinimumTravelTime()), (double)DATA.get(7).getPath().getMinimumTravelTime(), 0.01);
		DATA.clear();
		}
		
	
	@Test
	public void testSamePathOracle() {
		ArrayList<ShortestPathSolution> DATA = new ArrayList<ShortestPathSolution>() ;
		try {
			DATA.add(getShortestPathSolution(Aveyron, 64819, 118862, 2, 'd'));
			DATA.add(getShortestPathSolution(Aveyron, 64819, 118862, 2, 'b'));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		boolean same = true ;
		Path oracle = DATA.get(1).getPath() ;
		Path way = DATA.get(0).getPath() ;
		System.out.print(DATA.get(1).getPath().size());
		if (DATA.get(1).getPath().size()== DATA.get(0).getPath().size()) {
			for (int i =0; i<DATA.get(0).getPath().size(); i++) {
				same = same && DATA.get(1).getPath().getArcs().get(i).equals(DATA.get(0).getPath().getArcs().get(i)) ;
			}
		} 
		else {
			same = false ;
		}
		assertEquals(true, same);
		DATA.clear();
	}
	
	

}
