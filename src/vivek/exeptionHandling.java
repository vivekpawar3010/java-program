package vivek;

public class exeptionHandling {

	public static void main(String[] args) {
		int a = 99;
		int b  =20;
//		int b = 0; //to show the exeption in the program
		System.out.println("ADD = " + (a+b));
		System.out.println("SUB = " + (a-b));
		System.out.println("MUL = " + (a*b));
		try {
			System.out.println("DIV = " + (double)(a/b));
		}
		catch(Exception e) {
			System.out.println("Can not be Divide By Zero");
		}
		
	}

}
