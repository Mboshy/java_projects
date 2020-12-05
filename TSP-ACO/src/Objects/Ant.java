package Objects;

import java.util.*;
import Solutions.*;

public class Ant {
	private AntSystem colony;
	private Edges graph;
	private double total_cost;
	private ArrayList<Integer> tabu;
	private double[][] pheronome_delta;
	private boolean[] allowed;
	private double[][] eta;
	private int current;
	private int rank;
	
	public Ant(int rank, AntSystem colony, Edges graph) throws Exception{
		/**
		 * @param rank This is number of cities
		 * @param colony This is object of AntSystem class with parameters used in solution
		 * @param graph This is object of Edges class, it represents connections and their weigths between cities
		 */
		this.rank = rank;
		this.colony = colony;
		this.graph = graph;
		this.total_cost = 0;
		this.tabu = new ArrayList<>();
		this.pheronome_delta = new double[rank][rank];
		this.eta = new double[rank][rank];
		this.allowed = new boolean[rank];
		for(int i=0; i<graph.getRank(); i++)
			this.allowed[i] = true;
		for(int i=0; i<graph.getRank(); i++) {
			for(int j=0; j<graph.getRank(); j++) {
				this.eta[i][j] = i == j ? 0 : 1/this.graph.getDistanceMatrix()[i][j];
			}
		}
		int start = (int)(Math.random() * ( graph.getRank() ));
		this.tabu.add(start);
		this.current = start;
		this.allowed[start] = false;
	}
	
	public AntSystem getColony() {
		return this.colony;
	}
	
	public void setColony(AntSystem a) {
		this.colony = a;
	}
	
	public Edges getGraph() {
		return this.graph;
	}
	
	public void setGraph(Edges e) {
		this.graph = e;
	}
	
	public double getTotalCost() {
		return this.total_cost;
	}
	
	public void setTotalCost(double total_cost) {
		this.total_cost = total_cost;
	}
	
	public ArrayList<Integer> getTabu(){
		return this.tabu;
	}
	
	public void addTabu(int tab) {
		this.tabu.add(tab);
	}
	
	public boolean[] getAllowed(){
		return this.allowed;
	}
	
	public void setAllowed(Boolean allow, int i) {
		this.allowed[i] = allow;
	}
	
	public double[][] getPheronomeDelta(){
		return this.pheronome_delta;
	}
	
	public void setPheronomeDelta(double phero, int i, int j) {
		this.pheronome_delta[i][j] = phero;
		this.pheronome_delta[j][i] = phero;
	}
	
	public int getCurrent() {
		return this.current;
	}
	
	public void setCurrent(int curr) {
		this.current = curr;
	}
	
	public double[][] getEta(){
		return this.eta;
	}
	
	public void setEta(double e, int i, int j) {
		this.eta[i][j] = e;
		this.eta[j][i] = e;
	}
	
	public void select_next() {
		/**
		 * This function searches for best connection to next city
		 */
		double denominator = 0.0;
		for(int i=0; i<this.rank; i++) {
			if(this.allowed[i] == true) {
				denominator += Math.pow(this.graph.getPheronome()[this.current][i], this.colony.getAlpha()) *
						Math.pow(this.eta[this.current][i], this.colony.getBeta());
			}
		}
		double[] probabilities = new double[this.rank];
		for(int i=0; i<this.rank; i++) {
			if(this.allowed[i] == true) {
				probabilities[i] =  (Math.pow(this.graph.getPheronome()[this.current][i], this.colony.getAlpha()) *
						Math.pow(this.eta[this.current][i], this.colony.getBeta())) / denominator;
			}
		}
//		Random rand = new Random();
//		int selected = 0;
//		double random_value = rand.nextDouble();
//		for(int i=0; i<this.rank; i++) {
//			random_value -= probabilities[i];
//			if(random_value <= 0) {
//				selected = i;
//				break;
//			}
//		}
		int selected = 0;
		double max = 0;
		for(int i=0; i<this.rank; i++) {
			if(max < probabilities[i]) {
				selected = i;
				max = probabilities[i];
			}
		}
		this.allowed[selected] = false;
		this.tabu.add(selected);
		this.total_cost += this.graph.getDistanceMatrix()[this.current][selected];
		this.current = selected;
	}
	
	public void updatePheronomeDelta() {
		/**
		 * This method updates pheronome which was left in one iteration of traversal
		 */
		this.pheronome_delta = new double[rank][rank];
		for(int k=1; k<this.tabu.size(); k++) {
			int i = this.tabu.get(k-1);
			int j = this.tabu.get(k);
			this.pheronome_delta[i][j] = this.colony.getQ() / this.graph.getDistanceMatrix()[i][j];
			this.pheronome_delta[j][i] = this.colony.getQ() / this.graph.getDistanceMatrix()[j][i];
		}
	}
	
	
}
