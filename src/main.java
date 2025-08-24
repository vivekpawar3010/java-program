
class start{
	void enter() {
		System.out.println("Thjis is the class 1");
	}
}
class start1 extends start{
	void enter1() {
		System.out.println("This is the class 2");
		super.enter();
	}
}
interface start3{
	void enter3();
}
interface start4{
	void enter4();
}
class forinterface{
	void enter3(){
		System.out.println("This is the interface 1");
	}
	void enter4(){
		System.out.println("This is the interface 2");
	}
}

public class main {

	public main() {
		// TODO Auto-generated constructor stub
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		start1 i = new start1();
		i.enter1();//also use the inheritance
		forinterface j = new forinterface();
		j.enter3();
		j.enter4();

	}

}
