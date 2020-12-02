package Solutions;

import Objects.*;

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
	
	public void pheronomeUpdate(int length, Edges graph, Ant ants) {
		for(int i=0; i<length; i++) {
			for(int j=0; j<length; j++) {
				graph.setPheronome(1-this.evaporate_coeff, i, j);
			}
		}
	}
}
