import java.util.Random;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		Random r = new Random();
		int essai = 0;
		int justPrix = r.nextInt(200);
		int prix = 0;
		
		while(justPrix != prix) {
		System.out.println("Entrez un numero entre 0 et 200");
		Scanner Sc = new Scanner(System.in);
		
		if(justPrix > prix) {
			System.out.println("C'est plus !");
		}else {
			System.out.println("C'est moins !");
		}
		prix = Sc.nextInt();
		essai++;
	}
		System.out.println("Bravo ! le just prix est :"+justPrix);
		System.out.println("En "+ essai + " essais");
	}
}
