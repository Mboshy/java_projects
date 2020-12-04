package Objects;

import java.util.*;
import Solutions.*;

public class Ant {
	private AntSystem colony;
	private Edges graph;
	private double total_cost;
	private ArrayList<Integer> tabu;
	private double[][] pheronome_delta;
	private ArrayList<Boolean> allowed;
	private double[][] eta;
	private int current;
	private int rank;
	
	public Ant(int rank, AntSystem colony, Edges graph) throws Exception{
		this.rank = rank;
		this.colony = colony;
		this.graph = graph;
		this.total_cost = 0;
		this.tabu = new ArrayList<>();
		this.pheronome_delta = new double[rank][rank];
		this.eta = new double[rank][rank];
		this.allowed = new ArrayList<>();
		for(int i=0; i<graph.getRank(); i++)
			this.allowed.add(true);
		for(int i=0; i<graph.getRank(); i++) {
			for(int j=0; j<graph.getRank(); j++) {
				this.eta[i][j] = i == j ? 0 : 1/this.graph.getDistanceMatrix()[i][j];
			}
		}
		int start = (int)(Math.random() * ( graph.getRank() ));
		this.tabu.add(start);
		this.current = start;
		this.allowed.set(start, false);
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
	
	public ArrayList<Boolean> getAllowed(){
		return this.allowed;
	}
	
	public void addAllowed(Boolean allow) {
		this.allowed.add(allow);
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
		double denominator = 0;
		for(int i = 0; i<allowed.size(); i++) {
			if(this.allowed.get(i) == true) {
				denominator += Math.pow(this.graph.getPheronome()[this.current][i], this.colony.getAlpha()) * 
						Math.pow(this.eta[this.current][i], this.colony.getBeta());
			}
		}
		
		double[] probabilities = new double[this.rank];
		for(int i=0; i<this.rank; i++) {
			try {
				if(this.allowed.get(i) == true) {
					probabilities[i] = Math.pow(this.graph.getPheronome()[this.current][i], this.colony.getAlpha()) *
									Math.pow(this.eta[this.current][i], this.colony.getBeta()) / denominator;
				}
			}
			catch (Exception e) {
//				System.out.print(e);
			}
		}
		int selected = 0;
		double max = 0;
		for(int i=0; i<this.rank; i++) {
			if(max < probabilities[i]) {
				max = probabilities[i];
				selected = i;
			}
		}
//		int selected = 0;
//		double rand = Math.random();
//		for(int i=0; i<this.rank; i++) {
//			rand -= probabilities[i];
//			if(rand<=0) {
//				selected = i;
//				break;
//			}
//		}
		this.allowed.set(selected, false);
		this.tabu.add(selected);
		this.total_cost += this.graph.getDistanceMatrix()[this.current][selected];
	}
	
	public void updatePheronomeDelta() {
		this.pheronome_delta = new double[rank][rank];
		for(int k=1; k<this.tabu.size(); k++) {
			int i = this.tabu.get(k-1);
			int j = this.tabu.get(k);
			this.pheronome_delta[i][j] = this.colony.getQ() / this.graph.getDistanceMatrix()[i][j];
			this.pheronome_delta[j][i] = this.colony.getQ() / this.graph.getDistanceMatrix()[i][j];
		}
	}
	
	
}
