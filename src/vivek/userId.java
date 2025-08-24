package vivek;
import java.util.Scanner;
public class userId {
	public long Id;
	public String Password;
	public void getdata() {
	
		Scanner ok = new Scanner(System.in);
		System.out.println("Enter the UserId:");
		Id = ok.nextInt();
		System.out.println("Enter the Password:");
		Password = ok.next();
		ok.close();
	}
	public void cheakdata() {
		
		long cId = 122345;
		String cPassword = "King@3010";
		if(Id == cId && Password == cPassword) {
			System.out.println("Valid UserName & Password");
		}
		else {
			System.out.println("Inalid UserName & Password");
		}
		
	}
	
	public static void main(String[] args) {
		userId U = new userId();
		U.getdata();
		U.cheakdata();

	}

}
