package Runable;

import java.util.*;

import FileOperator.Reader;
import Objects.Ant;
import Objects.Edges;
import Solutions.AntSystem;

import java.io.FileWriter;
import java.io.File;
//import java.io.IOException;

public class Traveler {
	
    public static ArrayList<int[]> returnScanner() throws Exception {
    	/**
    	 * It just simply read the tsp file*/
    	Reader tree = new Reader("xqf131.tsp");
        return tree.storing;
    }
    
    static double[][] distanceMatrix(ArrayList<int[]> storing){
    	/**
    	 * @param storing This is array [n_cities x 2] 
    	 * @return This is matrix [n_cities x n_cities] with distance between cities */
    	int len = storing.size();
    	double[][] dist_matrix = new double[len][len];
    	for(int i=0; i<len; i++) {
    		for(int j=0; j<len; j++) {
    			dist_matrix[i][j] = Math.sqrt(Math.pow(storing.get(i)[0]-storing.get(j)[0], 2) + 
    					Math.pow(storing.get(i)[1]-storing.get(j)[1], 2));
    		}
    	}
    	return dist_matrix;
    }
    
	public static ArrayList<Integer> solve(int rank, Edges graph, AntSystem aco) throws Exception{
		/**
		 * This method finds best solution to TSP problem
		 * @param rank This is number of cities
		 * @param graph This is object of Edges class, it represents connections and their weigths between cities
		 * @param aco This is object of AntSystem class, it contains parameters used to find best solution
		 * @return best_solution This is ArrayList with the order of visited cities
		 */
		double best_cost = Double.MAX_VALUE;
		ArrayList<Integer> best_solution = new ArrayList<>();
		for(int gen=0; gen<aco.getGenerations(); gen++) {
			ArrayList<Ant> ants = new ArrayList<>();
			for(int i=0; i<aco.getAnthCount(); i++) {
				Ant ant = new Ant(rank, aco, graph);
				ants.add(ant);
			}
			for(Ant ant : ants) {
				for(int i=0; i<graph.getRank()-1; i++) {
					ant.select_next();
				}
				ant.setTotalCost(ant.getTotalCost() + 
						graph.getDistanceMatrix()[ ant.getTabu().get( ant.getTabu().size()-1 ) ][ ant.getTabu().get(0) ]);
				if(ant.getTotalCost() < best_cost) {
					best_cost = ant.getTotalCost();
					best_solution = new ArrayList<>(ant.getTabu());
				}
				ant.updatePheronomeDelta();
			}
			aco.pheronomeUpdate(rank, graph, ants);
		}
		return best_solution;
	}

	public static void main(String[] args) throws Exception {
		// It reads input file
		ArrayList<int[]> storing = returnScanner();
		int len = storing.size();
		System.out.println(storing.size());
		double[][] dist_matrix = distanceMatrix(storing);
		
		// Generates main objects
		AntSystem aco = new AntSystem(10, 500, 1.0, 10.0, 0.5, 10);
		Edges graph = new Edges(len, dist_matrix);
		// Finds best solution
		ArrayList<Integer> best_solution = solve(len, graph, aco);
		
		// Saves solution to csv file
		File file = new File("result/xqf131.csv");
        FileWriter fw = new FileWriter(file);

        fw.write("City,x,y\n");
        for(int i=0;i<best_solution.size();i++)
        {
        	fw.write(Integer.toString(best_solution.get(i)) + "," + 
        			Integer.toString(storing.get( best_solution.get(i) )[0]) + "," + 
        			Integer.toString(storing.get( best_solution.get(i) )[1]));
        	fw.write("\n");
        }

        fw.close();
		System.out.println("DONE");

	}

}
