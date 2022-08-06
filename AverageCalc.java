import java.util.*;

class AverageCalc {
	public void average () {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("enter some numbers with a space inbetween (eg. 5.1 343 340.12 99)");
		String numbers = sc.nextLine();

		String[] n = numbers.split(" ");
		double[] numberSplit = new double[n.length];
		
		for (int i=0;i<n.length;i++) {
			numberSplit[i] = Integer.parseInt(n[i]);
		}

		double tot = 0;
		for (int i=0;i<numberSplit.length;i++) {
			tot += numberSplit[i];
		}

		double average = (tot)/(n.length);

		System.out.println(average);
	}
}