import java.util.*;
class Main {
  	public static void main(String[] args) {
  	
	  	Scanner sc = new Scanner(System.in);
	  	Scanner double_sc = new Scanner(System.in);
	  	
	    System.out.println("Start the Calculator? (Yes/No)");
	    String start = (sc.nextLine()).toLowerCase();
	    switch (start){
	    	case "yes":
	    		System.out.println("Enter your first number:");
	    		double first_num = double_sc.nextDouble();
	    		System.out.println("Enter your second number:");
	    		double second_num = double_sc.nextDouble();
	    		
	    		System.out.println("Enter your operation(+(add), -(minus), *(mulitply), /(divide), %(remainder), ^(power), 3(cube root), square root:");
	    		String operation = (sc.nextLine()).toLowerCase();
	    			switch (operation) {
	    				case "+":
	    					System.out.println("The answer is : " + (first_num+second_num));
	    					break;
	    				case "-":
	    					System.out.println("The answer is : " + (first_num-second_num));
	    					break;
	    				case "*":
	    					System.out.println("The answer is : " + (first_num*second_num));
	    					break;
	    				case "/":
	    					System.out.println("The answer is : " + (first_num/second_num));
	    					break;
	    				case "%":
	    					System.out.println("The answer is : " + (first_num%second_num));
	    					break;
	    				case "^":
	    					System.out.println("The answer is : " + (Math.pow(first_num,second_num)));
	    					break;
	    				case "3":
	    					System.out.println("Do you want to cube the first or second number?(Type 1 or 2)");
	    					double which_num_cube = double_sc.nextDouble();
	    					if (which_num_cube==1) {
	    						System.out.println("The answer is : " + (Math.cbrt(first_num)));
	    						break;
	    					}
	    					else if (which_num_cube==2){
	    						System.out.println("The answer is : " + (Math.cbrt(second_num)));
	    						break;
	    					}
	    					else {
	    						System.out.println("Error");
	    						break;
	    					}
	    				case "square root":
	    					System.out.println("Do you want to square root the first or second number?(Type 1 or 2)");
	    					double which_num_sqrt = double_sc.nextDouble();
	    					if (which_num_sqrt==1) {
	    						System.out.println("The answer is : " + (Math.sqrt(first_num)));
	    						break;
	    					}
	    					else if (which_num_sqrt==2){
	    						System.out.println("The answer is : " + (Math.sqrt(second_num)));
	    						break;
	    					}
	    					else {
	    						System.out.println("Error");
	    						break;
	    					}
	    				default:
	    					System.out.println("There are several reasons to your problem:");
	    					System.out.println("1. You didn't enter the operator, but entered the word(though if it is square root it is fine)");
	    					System.out.println("2. The computer didn't recognize the operator");
	    					System.out.println("3. Spelling error");
	    					System.out.println("4. Unknown Error");
	    			}
	    }
  	}
}