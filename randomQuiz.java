// random quiz
import java.util.*;
import java.time.*;
import java.sql.*;
import java.text.*;
class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int A = 0;
		int B = 0;
		int C = 0;
		int D = 0;
			
		System.out.println("What hogwarts house are you? Take this quiz to find out!");

		System.out.println(" ");

		System.out.println("What is your name?");
		String name = sc.nextLine();

		System.out.println(" ");

		System.out.println(name + ", to complete the quiz, choose the letter of the answer that best represent you :");

		System.out.println(" ");

		System.out.println("1. Which Animal represents you the most?	A. Eagle B. Lion C. Snake D. Badger");
		String animal = sc.nextLine();
		
		if (animal.equals("a") || animal.equals("A")) {
			A++;
		}
		else if (animal.equals("b") || animal.equals("B")) {
			B++;
		}
		else if (animal.equals("c") || animal.equals("C")) {
			C++;
		}
		else if (animal.equals("d") || animal.equals("D")) {
			D++;
		}
		else {
			System.out.println("You are supposed to enter a LETTER! BAD YOU!");
			return;
		}


		System.out.println(" ");

		System.out.println("2. What material would be at the core of your wand?	A. Unicorn Hair B. Phoenix Feather C. Dragon Heartstring D. Kelpie Hair");
		String core_of_wand = sc.nextLine();

		if (core_of_wand.equals("a") || core_of_wand.equals("A")) {
			A++;
		}
		else if (core_of_wand.equals("b") || core_of_wand.equals("B")) {
			B++;
		}
		else if (core_of_wand.equals("c") || core_of_wand.equals("C")) {
			C++;
		}
		else if (core_of_wand.equals("d") || core_of_wand.equals("D")) {
			D++;
		}
		else {
			System.out.println("You are supposed to enter a LETTER! BAD YOU!");
			return;
		}

		System.out.println(" ");

		System.out.println("3. You would be most hurt if a person called you...	A. Boring B. Mean C. Weak D. Disgusting");
		String most_hurt = sc.nextLine();

		if (most_hurt.equals("a") || most_hurt.equals("A")) {
			A++;
		}
		else if (most_hurt.equals("b") || most_hurt.equals("B")) {
			B++;
		}
		else if (most_hurt.equals("c") || most_hurt.equals("C")) {
			C++;
		}
		else if (most_hurt.equals("d") || most_hurt.equals("D")) {
			D++;
		}
		else {
			System.out.println("You are supposed to enter a LETTER! BAD YOU!");
			return;
		}

		System.out.println(" ");

		System.out.println("4. What is your favourite colour out of these four?	A. Blue B. Red C. Green D. Yellow");
		String fav_colour = sc.nextLine();

		if (fav_colour.equals("a") || fav_colour.equals("A")) {
			A++;
		}
		else if (fav_colour.equals("b") || fav_colour.equals("B")) {
			B++;
		}
		else if (fav_colour.equals("c") || fav_colour.equals("C")) {
			C++;
		}
		else if (fav_colour.equals("d") || fav_colour.equals("D")) {
			D++;
		}
		else {
			System.out.println("You are supposed to enter a LETTER! BAD YOU!");
			return;
		}

		System.out.println(" ");

		System.out.println("5. What trait are you most proud of?	A. Intelligence B. Bravery C. Leadership D. Persistence");
		String trait = sc.nextLine();

		if (trait.equals("a") || trait.equals("A")) {
			A++;
		}
		else if (trait.equals("b") || trait.equals("B")) {
			B++;
		}
		else if (trait.equals("c") || trait.equals("C")) {
			C++;
		}
		else if (trait.equals("d") || trait.equals("D")) {
			D++;
		}
		else {
			System.out.println("You are supposed to enter a LETTER! BAD YOU!");
			return;
		}

		System.out.println(" ");
		
		System.out.println("6. What element would you like?	A. Air B. Fire C. Water D. Ground");
		String element = sc.nextLine();

		if (element.equals("a") || element.equals("A")) {
			A++;
		}
		else if (element.equals("b") || element.equals("B")) {
			B++;
		}
		else if (element.equals("c") || element.equals("C")) {
			C++;
		}
		else if (element.equals("d") || element.equals("D")) {
			D++;
		}
		else {
			System.out.println("You are supposed to enter a LETTER! BAD YOU!");
			return;
		}
		
		System.out.println(" ");

		System.out.println("7. If your friend was getting bullied, you'll...	A. Ignore them B. Stand up to the bully C. Join the bully D. Comfort you friend after");
		String bullying = sc.nextLine();

		if (bullying.equals("a") || bullying.equals("A")) {
			A++;
		}
		else if (bullying.equals("b") || bullying.equals("B")) {
			B++;
		}
		else if (bullying.equals("c") || bullying.equals("C")) {
			C++;
		}
		else if (bullying.equals("d") || bullying.equals("D")) {
			D++;
		}
		else {
			System.out.println("You are supposed to enter a LETTER! BAD YOU!");
			return;
		}

		System.out.println(" ");

		System.out.println("8. What Spell would you like to learn? A. Wingardium Leviosa(Levitates items) B. Expelliarmus(Dis-arming spell) C. Avada Kedavra(Kills) D. Ascendio(Flings or lifts a person)");
		String spell = sc.nextLine();

		if (spell.equals("a") || spell.equals("A")) {
			A++;
		}
		else if (spell.equals("b") || spell.equals("B")) {
			B++;
		}
		else if (spell.equals("c") || spell.equals("C")) {
			C++;
		}
		else if (spell.equals("d") || spell.equals("D")) {
			D++;
		}
		else {
			System.out.println("You are supposed to enter a LETTER! BAD YOU!");
			return;
		}
		System.out.println(" ");

		System.out.println("9. When working on a team project, you will be...A. Doing the most B. The one to take charge C. The one to do as little as they can D. Doing a little of everything");
		String team = sc.nextLine();

		if (team.equals("a") || team.equals("A")) {
			A++;
		}
		else if (team.equals("b") || team.equals("B")) {
			B++;
		}
		else if (team.equals("c") || team.equals("C")) {
			C++;
		}
		else if (team.equals("d") || team.equals("D")) {
			D++;
		}
		else {
			System.out.println("You are supposed to enter a LETTER! BAD YOU!");
			return;
		}
		System.out.println(" ");

		System.out.println("10. Pick a class... A. Astronomy B. Defense againts Dark Arts C. Dark arts D. Care of magical creatures");
		String classes = sc.nextLine();

		if (classes.equals("a") || classes.equals("A")) {
			A++;
		}
		else if (classes.equals("b") || classes.equals("B")) {
			B++;
		}
		else if (classes.equals("c") || classes.equals("C")) {
			C++;
		}
		else if (classes.equals("d") || classes.equals("D")) {
			D++;
		}
		else {
			System.out.println("You are supposed to enter a LETTER! BAD YOU!");
			return;
		}
	 
		System.out.println(" ");
			
		if (A >= B && A >= C && A >= D && A != B && A != C && A != D) {
			System.out.println("You are a Ravenclaw. What matters most is intelligence, learning, wisdom and wit.");
		}
		
		if (B >= A && B >= C && B >= D && B != A && B != C && B != D) {
			System.out.println("You are a Gryffindor. What matters most is courage, bravery, nerve, and chivalry.");
		}
		
		if (C >= B && C >= A && C >= D && C != A && C != B && C != D) {
			System.out.println("You are a Slytherin. What matters most is ambition, cunning, leadership, and resourcefulness.");
		}
		
		if (D >= C && D >= B && D >= A && D != A && D != B && D != C) {
			System.out.println("You are a Hufflepuff. What matters most is hard work, patience, justice, and loyalty.");
		}

		if (A == B){
			System.out.println("Before the end of your life, you would want to... A. Learn all there is B. Travel the world");

			System.out.println(" ");

			String prefer = sc.nextLine();
			if (prefer.equals("B") || prefer.equals("B")) {
				System.out.println("You are a Gryffindor. What matters most is courage, bravery, nerve, and chivalry.");
			}
			else if ((prefer.equals("A") || prefer.equals("A"))){
				System.out.println("You are a Ravenclaw. What matters most is intelligence, learning, wisdom and wit.");
			}
			else {
				System.out.println("You are supposed to enter a LETTER! BAD YOU!");
				return;
			}
			
		}

		if (A == C) {
			System.out.println("Before the end of your life, you would want to... A. Learn all there is B. Have all the wealth in the world");

			System.out.println(" ");

			String prefer = sc.nextLine();
			if (prefer.equals("B") || prefer.equals("b")) {
				System.out.println("You are a Slytherin. What matters most is ambition, cunning, leadership, and resourcefulness.");
			}
			else if ((prefer.equals("A") || prefer.equals("A"))){
				System.out.println("You are a Ravenclaw. What matters most is intelligence, learning, wisdom and wit.");
			}
			else {
				System.out.println("You are supposed to enter a LETTER! BAD YOU!");
				return;
			}
		}

		if (A == D) {
			System.out.println("Before the end of your life, you would want to... A. Learn all there is B. Have your family beside you always");

			System.out.println(" ");

			String prefer = sc.nextLine();
			if (prefer.equals("B") || prefer.equals("b")) {
				System.out.println("You are a Hufflepuff. What matters most is hard work, patience, justice, and loyalty.");
			}
			else if ((prefer.equals("A") || prefer.equals("A"))){
				System.out.println("You are a Ravenclaw. What matters most is intelligence, learning, wisdom and wit.");
			}
			else {
				System.out.println("You are supposed to enter a LETTER! BAD YOU!");
				return;
			}
		}

		if (B == C) {
			System.out.println("Before the end of your life, you would want to... A. Travel the world B. Have all the wealth in the world");

			System.out.println(" ");

			String prefer = sc.nextLine();
			if (prefer.equals("A") || prefer.equals("a")) {
				System.out.println("You are a Gryffindor. What matters most is courage, bravery, nerve, and chivalry.");
			}
			else if ((prefer.equals("B") || prefer.equals("b"))){
				System.out.println("You are a Slytherin. What matters most is ambition, cunning, leadership, and resourcefulness.");
			}
			else {
				System.out.println("You are supposed to enter a LETTER! BAD YOU!");
				return;
			}
		}

		if (B == D) {
			System.out.println("Before the end of your life, you would want to... A. Travel the world B. Have all the wealth in the world");

			System.out.println(" ");

			String prefer = sc.nextLine();
			if (prefer.equals("A") || prefer.equals("a")) {
				System.out.println("You are a Gryffindor. What matters most is courage, bravery, nerve, and chivalry.");
			}
			else if ((prefer.equals("B") || prefer.equals("b"))){
				System.out.println("You are a Slytherin. What matters most is ambition, cunning, leadership, and resourcefulness.");
			}
			else {
				System.out.println("You are supposed to enter a LETTER! BAD YOU!");
				return;
			}
		}

		if (C == D) {
		System.out.println("Before the end of your life, you would want to... A. Have your family beside you always B. Have all the wealth in the world");
			String prefer = sc.nextLine();

			System.out.println(" ");

			if (prefer.equals("A") || prefer.equals("A")) {
				System.out.println("You are a Hufflepuff. What matters most is hard work, patience, justice, and loyalty.");
			}
			else if ((prefer.equals("B") || prefer.equals("b"))){
				System.out.println("You are a Slytherin. What matters most is ambition, cunning, leadership, and resourcefulness.");
			}
			else {
				System.out.println("You are supposed to enter a LETTER! BAD YOU!");
				return;
			}
		} 

		System.out.println(" ");

		System.out.println("We hope you enjoyed this quiz!");

	}
}