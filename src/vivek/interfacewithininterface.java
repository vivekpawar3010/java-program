package vivek;
interface outer{
	public void display();
	interface inner{
		public void show();
	}
}
public class interfacewithininterface implements outer,outer.inner {

	public void display() {
		System.out.println("Outer Interface method");
	}
	public void show() {
		System.out.println("Inner Interface Method");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		outer.inner io = new interfacewithininterface();
		io.show();
		interfacewithininterface ifif = new interfacewithininterface();
		ifif.display();
	}

}
