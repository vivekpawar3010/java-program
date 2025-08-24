package vivek;
class outer1 {
	int x = 10;
	public void display() {
		System.out.println("outer -> Display Method");
	}
	class inner {
		public void show() {
			System.out.println("x = "+x);
			System.out.println("Inner -> Show Method");
		}
	}
}
public class nestedClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		outer1 out=new outer1();
		outer1.inner in = out.new inner();
		out.display();
		in.show();
				
	}

}
