package FileOperator;

import java.io.File;
import java.util.*;

public class Reader {
	public ArrayList<int[]> storing;

	/**
	 * This method reads tsp file. It omits headers and add values to list
	 * The values are passed to n x 2 array list*/
    public Reader() throws Exception {
        File file = new File("bcl380.tsp");
        Scanner sc = new Scanner(file);
        storing = new ArrayList<int[]>();
        String nextValue = null;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if("NODE_COORD_SECTION".equals(line)){
                while (sc.hasNextLine()) {
                	int[] coor = new int[2];
                    nextValue = sc.nextLine();
                    if(!nextValue.equals("EOF")) {
	                    coor[0] = Integer.parseInt(nextValue.trim().split(" ")[1]);
	                    coor[1] = Integer.parseInt(nextValue.trim().split(" ")[2]);
	                    storing.add(coor);
                    }
                }
            }
        }
        sc.close();
    }
}
