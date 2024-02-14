import java.util.Scanner;
public class Main
{
	public static void main(String[] args) {
	    Scanner in=new Scanner(System.in);
	    System.out.print("Enter the three number to get the largest one:");
	    int a=in.nextInt();
	    int b=in.nextInt();
	    int c=in.nextInt();
		System.out.print("Enter the way which you want to follow: \n 1.if&else \n 2.if,else&ifelse \n 3.Ternary Operator\n");
	    int choice=in.nextInt();
	    switch(choice){
	        case(1):
	          if(a>b){
	              if(a>c){
	                  System.out.print("the largest number is:"+a);
	              }
	              else{
	                  System.out.print("the largest number is:"+c);
	              }
	          }
	          else{
	               if(b>c){
	                  System.out.print("the largest number is:"+b);
	              }
	              else{
	                  System.out.print("the largest number is:"+c);
	              }
	          }
              break;
	          case(2):
	              if(a>b&&a>c){
	                  System.out.print("the largest number is:"+a);
	              }
	              else if(b>c){
	                  System.out.print("the largest number is:"+b);
	              }
	              else{
	                  System.out.print("the largest number is:"+c);
	              }
	              break;
	          case(3):
	              int x=(a>b)?((a>c)?a:c):((b>c)?b:c);
	              System.out.print("the largest number is:"+x);
	           
	              break;
	          default:
	              System.out.println("Enter the valid input:");
	          
	    }
	    
	}
}
