package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graph.Graph;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.RoadInformation.RoadType;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraAlgorithmTest {
	
	private static String Toulouse, Aveyron ;
	
	private static ArrayList<ShortestPathSolution> DATA ;
	
	@BeforeClass
    public static void initAll() throws IOException {
		
		DATA = new ArrayList<ShortestPathSolution>() ;
		
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
	    	Algo = new BellmanFordAlgorithm(new ShortestPathData(graph, graph.get(originId), graph.get(originId), ArcInspectorFactory.getAllFilters().get(mode)));
	    	break;
	    case 'd':
	    	Algo = new DijkstraAlgorithm(new ShortestPathData(graph, graph.get(originId), graph.get(originId), ArcInspectorFactory.getAllFilters().get(mode)));
	    	break;
	    case 'a':
	    	Algo = new AStarAlgorithm(new ShortestPathData(graph, graph.get(originId), graph.get(originId), ArcInspectorFactory.getAllFilters().get(mode)));
	    	break;
	    default:
	    	throw new Exception("Code d'algorithme incorrect. Entrez d, b ou a");
	    }
	    return Algo.run();
	}
	
	
    
	@Test
	public void chemin_nul() {
		try {
			DATA.add(getShortestPathSolution(Toulouse, 0, 0, 0, 'd'));
			DATA.add(getShortestPathSolution(Aveyron, 0, 0, 0, 'd'));
			DATA.add(getShortestPathSolution(Toulouse, 0, 0, 2, 'd'));
			DATA.add(getShortestPathSolution(Aveyron, 0, 0, 2, 'd'));
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
	
	
	@Test
	public void testCheminOracle() {
		try {
			DATA.add(getShortestPathSolution(Toulouse, 22382, 27539, 0, 'd'));
			DATA.add(getShortestPathSolution(Toulouse, 22382, 27539, 0, 'b'));
			DATA.add(getShortestPathSolution(Toulouse, 22382, 27539, 2, 'd'));
			DATA.add(getShortestPathSolution(Toulouse, 22382, 27539, 2, 'b'));
			DATA.add(getShortestPathSolution(Aveyron, 64819, 118899, 0, 'd'));
			DATA.add(getShortestPathSolution(Aveyron, 64819, 118899, 0, 'b'));
			DATA.add(getShortestPathSolution(Aveyron, 64819, 118899, 2, 'd'));
			DATA.add(getShortestPathSolution(Aveyron, 64819, 118899, 2, 'b'));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals((int)DATA.get(0).getPath().getLength(), (int)(DATA.get(1).getPath().getLength()));
		assertEquals((int)DATA.get(2).getPath().getLength(), (int)(DATA.get(3).getPath().getLength()));
		assertEquals((int)DATA.get(4).getPath().getMinimumTravelTime(), (int)DATA.get(5).getPath().getMinimumTravelTime());
		assertEquals((int)DATA.get(6).getPath().getMinimumTravelTime(), (int)DATA.get(7).getPath().getMinimumTravelTime());
		DATA.clear();
	}

}
