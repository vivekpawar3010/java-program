package vivek;
import java.util.Scanner;
public class fullName {
	fullName(){
		String fName, mName, lName;
		Scanner Ok= new Scanner(System.in);
		System.out.println("Enter Only First Name:");
		fName = Ok.next();
		System.out.println("Enter Only Middle Name:");
		mName = Ok.next();
		System.out.println("Enter Only Last Name:");
		lName = Ok.next();
		System.out.println("Your Full Name:"+fName+"  "+mName+"  "+lName);
		Ok.close();
	}
	fullName(String y){
		String mName, lName;
		String fName = y;
		Scanner Ok= new Scanner(System.in);
		System.out.println("You Entered Only First Name Now Enter the Middle and Lasr Name:");;
		System.out.println("Enter Only Middle Name:");
		mName = Ok.next();
		System.out.println("Enter Only Last Name:");
		lName = Ok.next();
		System.out.println("Your Full Name:"+fName+"  "+mName+"  "+lName);
		Ok.close();
	}
	fullName(String a, String b){
		String  lName;
		String fName = a;
		Stirng mName = b;
		Scanner Ok= new Scanner(System.in);
		System.out.println("You Entered Only First Name & Middle Now Enter the Lasr Name:");;
		System.out.println("Enter Only Last Name:");
		lName = Ok.next();
		System.out.println("Your Full Name:"+fName+"  "+mName+"  "+lName);
	}
	fullName(String p, String q,String r){
		String lName = r;
		String fName = p;
		Stirng mName = q;
		Scanner Ok= new Scanner(System.in);
		System.out.println("You Entered All First Name , Middle & Lasr Name:");
		System.out.println("Your Full Name:"+fName+"  "+mName+"  "+lName);
		Ok.close();
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}

}
