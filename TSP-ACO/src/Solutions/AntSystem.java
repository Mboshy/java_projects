package Solutions;

import Objects.*;
import java.util.*;

public class AntSystem {

	private int num_ants;
	private int generations;
	private double alpha;
	private double beta;
	private double evaporate_coeff;
	private double Q;
	
	/**
	 * @param num_ants number of ants
	 * @param generation number of generations / loop iterations
	 * @alpha coefficient of pheronome importance
	 * @beta 2nd coefficient of pheronome importance
	 * @rho pheronome evaporate coefficient
	 * @Q pheronome intensity*/
	public AntSystem(int num_ants, int generations, double alpha, double beta, double rho, double q) {
		this.num_ants = num_ants;
		this.generations = generations;
		this.alpha = alpha;
		this.beta = beta;
		this.evaporate_coeff = rho;
		this.Q = q;
	}
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	public double getAlpha() {
		return this.alpha;
	}
	
	public void setBeta(double beta) {
		this.beta = beta;
	}
	
	public double getBeta() {
		return this.beta;
	}
	
	public void setRho(double rho) {
		this.evaporate_coeff = rho;
	}
	
	public double getRho() {
		return this.evaporate_coeff;
	}
	
	public void setQ(double q) {
		this.Q = q;
	}
	
	public double getQ() {
		return this.Q;
	}
	
	public void setGenerations(int gene) {
		this.generations = gene;
	}
	
	public int getGenerations() {
		return this.generations;
	}
	
	public void setAntCount(int count) {
		this.num_ants = count;
	}
	
	public int getAnthCount() {
		return this.num_ants;
	}
	
	public void pheronomeUpdate(int length, Edges graph, ArrayList<Ant> ants) {
		for(int i=0; i<length; i++) {
			for(int j=0; j<length; j++) {
				graph.setPheronome((1-this.evaporate_coeff)*graph.getPheronome()[i][j], i, j);
				for(Ant a : ants) {
					graph.setPheronome(graph.getPheronome()[i][j]+a.getPheronomeDelta()[i][j], i, j);
				}
			}
		}
	}
}
