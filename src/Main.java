import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

class Main {
	
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int n = readN(br);
			int[][] costs = readCosts(n, br);
			int[][] opt = minimizeCost(n, costs);
			int cost = opt[0][n-1];
			int[] shiftLengths = determineShiftLengths(opt, n);
			
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
		    writeOutput(cost, shiftLengths, wr);
		    
			br.close();
		    wr.flush();
		    wr.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static int readN(BufferedReader br) throws Exception {
		String text = br.readLine();
	    int n = Integer.parseInt(text);
	    return n;
	}
	
	static int[][] readCosts(int n, BufferedReader br) throws Exception {
		int[][] matrix = new int[n][3];
		for (int i = 0; i < n-2; i++) {
			String text = br.readLine();
			String[] parts = text.split(" ");
		    int[] costs = {Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])};
		    matrix[i] = costs;
		}
		if (n == 1) {
			String text = br.readLine();
			int[] costs = {Integer.parseInt(text)};
			matrix[0] = costs;
		}
		else {
			String text = br.readLine();
			String[] parts = text.split(" ");
		    int[] costsNMinus1 = {Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
		    matrix[n-2] = costsNMinus1;
		    text = br.readLine();
		    int[] costsN = {Integer.parseInt(text)};
		    matrix[n-1] = costsN;
		}
		return matrix;
	}
	
	static void writeOutput(int cost, int[] shiftLengths, BufferedWriter wr) throws Exception {
		wr.write(cost + "\n");
		int l = shiftLengths.length;
		for (int i = l-1; i > -1; i--) {
			if (shiftLengths[i] != 0) {
				wr.write(shiftLengths[i] + " ");
			}
		}
		wr.write("\n");
	}
	
	static int[][] minimizeCost(int n, int[][] costs) {
		int[][] opt = new int[2][n];
		opt[0][0] = costs[0][0];
		opt[1][0] = 1;
		if (n == 1) {
			return opt;
		}
		opt[0][1] = Math.min(opt[0][0] + costs[1][0], costs[0][1]);
		if (opt[0][0] + costs[1][0] > costs[0][1]) {
			opt[1][1] = 2;
		}
		else {
			opt[1][1] = 1;
		}
		if (n == 2) {
			return opt;
		}
		opt[0][2] = Math.min(costs[0][2], Math.min(opt[0][0] + costs[1][1], opt[0][1] + costs[2][0]));	
		if (opt[0][2] == costs[0][2]) {
			opt[1][2] = 3;
		}
		else if (opt[0][2] == opt[0][0] + costs[1][1]) {
			opt[1][2] = 2;
		}
		else {
			opt[1][2] = 1;
		}
		if (n == 3) {
			return opt;
		}
		for (int i = 3; i < n; i++) {
			opt[0][i] = Math.min(opt[0][i-3] + costs[i-2][2], Math.min(opt[0][i-2] + costs[i-1][1], opt[0][i-1] + costs[i][0]));
			if (opt[0][i] == opt[0][i-3] + costs[i-2][2]) {
				opt[1][i] = 3;
			}
			else if (opt[0][i] == opt[0][i-2] + costs[i-1][1]) {
				opt[1][i] = 2;
			}
			else {
				opt[1][i] = 1;
			}
		}
		return opt;
	}
	
	static int[] determineShiftLengths(int[][] opt, int n) {
		int[] shiftLengths = new int[n];
		int index = 0;
		while (n > 0) {
			int shiftLength = opt[1][n-1];
			shiftLengths[index] = shiftLength;
			index++;
			n = n - shiftLength;
		}
		return shiftLengths;
	}
}
