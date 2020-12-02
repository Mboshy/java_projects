package Objects;

import java.util.*;
import Solutions.*;

public class Ant {
	private AntSystem colony;
	private Edges graph;
	private double total_cost;
	private ArrayList<Integer> tabu;
	private ArrayList<Double> pheronome_delta;
	private ArrayList<Integer> allowed;
	private double[][] eta;
	private int current;
	
	
	
	public Ant(AntSystem colony, Edges graph) throws Exception{
		this.colony = colony;
		this.graph = graph;
		this.total_cost = 0;
		this.tabu = new ArrayList<>();
		this.pheronome_delta = new ArrayList<>();
		this.allowed = new ArrayList<>();
		for(int i=0; i<graph.getRank(); i++)
			this.allowed.add(i);
		for(int i=0; i<graph.getRank(); i++) {
			for(int j=0; j<graph.getRank(); j++) {
				this.eta[i][j] = i == j ? 0 : 1/this.graph.getDistanceMatrix()[i][j];
			}
		}
		int start = (int)(Math.random() * ((graph.getRank() + 1)));
		this.tabu.add(start);
		this.current = start;
		this.allowed.remove(start);
	}
	
	
}
