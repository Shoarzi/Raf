package org.insa.base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
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
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
	
	
	public static void main(String[] args) throws Exception {
		
		String name = args[0] ;
		int mode = Integer.parseInt( args[1] ) ; //0 ou 2
	

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
        
        try {
        	fwd = new FileWriter(new File("toulouse_distance_100_data.txt")) ;
        	//fwt = new FileWriter( new File("toulouse_distance_100_dijkstra.txt")) ;
        	fwd.write(name + "\n");
        	fwd.write(args[1] +  "\n");
        	fwd.write("100 \n");
        	int origin = 0;
        	int dest = 0 ;
        	Random random = new Random() ;
        	for (int i=0; i<100; i++) {
        		origin= random.nextInt(nb_nodes) ;
        		dest= random.nextInt(nb_nodes) ;
        		fwd.write(origin + " " + dest + "\n" );
        	}
        	fwd.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        
	}
	}

}
