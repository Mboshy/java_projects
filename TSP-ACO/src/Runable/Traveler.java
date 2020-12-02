package Runable;

import java.util.ArrayList;

import FileOperator.Reader;

public class Traveler {
	
    public static ArrayList<int[]> returnScanner() throws Exception {
    	/**
    	 * It just simply read the tsp file*/
    	Reader tree = new Reader();
        return tree.storing;
    }
    
    static double[][] distanceMatrix(ArrayList<int[]> storing){
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

	public static void main(String[] args) throws Exception {
		ArrayList<int[]> storing = returnScanner();
		System.out.println(storing.size());
		double[][] dist_matrix = distanceMatrix(storing);
	}

}
