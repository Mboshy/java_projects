package Objects;

public class Edges {
	private int rank;
	private double[][] distance_matrix;
	private double[][] pheronome;
	
	/**
	 * @param len This is rank of the distance matrix
	 * @param dist_mat This is matrix with calculated distances between cities */
	public Edges(int rank, double[][] dist_mat) throws Exception{
		this.rank = rank;
		this.distance_matrix = dist_mat;
		this.pheronome = new double[rank][rank];
//		for(int i=0; i<len; i++) {
//    		for(int j=0; j<len; j++) {
//    			this.pheronome[i][j] = 1/(len*len);
//    		}
//		}
	}
	
	public void setEank(int rank) {
		this.rank = rank;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public void setDistanceMatrix(double distance, int i, int j) {
		this.distance_matrix[i][j] = distance;
	}
	
	public double[][] getDistanceMatrix() {
		return this.distance_matrix;
	}
	
	public void setPheronome(double phero, int i, int j) {
		this.pheronome[i][j] = phero;
	}
	
	public double[][] getPheronome() {
		return this.pheronome;
	}
	
}
