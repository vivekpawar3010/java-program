package vivek;
import java.util.*;
public class vivekpass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Pass;
		Scanner Sc = new System(Scanner.in);
		System.out.println("Eneter the PassWord:");
		Pass = Sc.next();
		int k = Pass.length();
		if(k < 8) {
			System.out.println("Invalid Password Please Enter the Password above 8 Digits");
		}
		else {
			System.out.println("Valid Password ");
		}
		
		
	}

}
