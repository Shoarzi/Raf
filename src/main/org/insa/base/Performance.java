package org.insa.base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.algo.AbstractSolution;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithmTest;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;
import org.insa.graph.io.BinaryPathReader ;
import org.insa.graphics.drawing.Drawing;
import org.insa.graphics.drawing.components.BasicDrawing;

public class Performance {
	
	private static ShortestPathSolution calcul(String option, String name , int origin, int dest, int mode) throws Exception {
		ShortestPathSolution solution = null ;
		switch(option) {
		case "dijkstra" : 
			try {
				solution = DijkstraAlgorithmTest.getShortestPathSolution(name, origin, dest, mode, 'd') ;
			}
			catch (Exception e) {
				System.out.print("Code d'algorithme incorrect. Entrez d, b ou a") ;
			}
			
		case "astar" : 
			try {
				solution = DijkstraAlgorithmTest.getShortestPathSolution(name, origin, dest, mode, 'a') ;
			}
			catch (Exception e) {
				System.out.print("Code d'algorithme incorrect. Entrez d, b ou a") ;
			}
			
		
		default : return solution ;
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		
		String name = args[0] ;
		int mode = Integer.parseInt( args[1] ) ; //0 ou 2
		String test = args[2] ; //-d pour créer le fichier de data
							  //-p pour créer le fichier de performance
		
		String algo = args[3] ; //-d pour dijkstra
								//-a pour astar 
	

		String mapName = "D:\\Telecharger\\" + name + ".mapgr" ;

        // Create a graph reader.
        GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // Read the graph.
        Graph graph = reader.read() ;
        
        List<Node> nodes = graph.getNodes() ;
        int nb_nodes = nodes.size() ;
        
        //preparation lecture/ecriture ds fichier
        FileWriter fwd ;
        FileReader fr;
        
        switch(test) {
        case "-d":
        	try { 
            	fwd = new FileWriter(new File("toulouse_distance_100_data.txt")) ;
            	fwd.write(name + "\n");
            	fwd.write(args[1] +  "\n");
            	fwd.write("100\n");
            	int origin = 0;
            	int dest = 0 ;
            	boolean recommencer = true ;
            	Random random = new Random() ;
            	for (int i=0; i<100; i++) {
            		recommencer = true ;
            		while (recommencer) {
            			origin= random.nextInt(nb_nodes) ;
                		dest= random.nextInt(nb_nodes) ;
                		ShortestPathSolution solution = Performance.calcul(algo, name, origin, dest, mode) ;
                		recommencer = (solution.getStatus()==AbstractSolution.Status.INFEASIBLE) ;
            		}
            		fwd.write(origin + " " + dest + "\n" );
            	}
            	fwd.close();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
        	}
        	break ;
        	
        case "-p": 
        	try {
        		fr = new FileReader("toulouse_distance_100_data.txt") ;
        		fwd = new FileWriter( new File("toulouse_distance_100_" + algo +".txt")) ;
        		BufferedReader buff = new BufferedReader(fr) ;
        		String line ;
        		String map =  buff.readLine() ;
        		String[] data = new String[2] ;
        		
        		
            	
        		int origin = 0;
            	int dest = 0 ;
            	
        		mode = Integer.parseInt(buff.readLine()) ;
        		int nb_test = Integer.parseInt(buff.readLine()) ;
        		
        		fwd.write(map + "\n");
            	fwd.write(mode +  "\n");
            	fwd.write(nb_test + "\n");
            	fwd.write("//distance" + "\t"+ "nb noeud atteint" + "\t"+ "temps\n");
            	
            	long time = 0 ;
        		long startTime ;
        		
        		
        		//on fait les test
        		for(int i=0; i<nb_test;i++) {
        			line = buff.readLine() ;
        			data = line.split(" ") ; 
        			origin = Integer.parseInt(data[0]) ;
        			dest = Integer.parseInt(data[1]) ;
        			startTime = System.currentTimeMillis() ;
        			ShortestPathSolution solution = Performance.calcul(algo, name, origin, dest, mode) ;
        			time = System.currentTimeMillis()-startTime ;
        			fwd.write(solution.getPath().getLength() + "\t" + DijkstraAlgorithm.nb_node_reached + "\t" + time + "\n");
        			//System.out.print(data[0] + " "+ data[1] + " \n");
        		}
        		fwd.close();
        		fr.close();
        
        	}
        	catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
        	}
        	break ;
	    	
        
        
        
	}
	}

}
