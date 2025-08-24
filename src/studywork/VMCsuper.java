package studywork;

class Animal{
	int X = 10;
	void Makesound() {
		System.out.println("Animal Make The Sound :");
	}
	void Animal() {
		System.out.println("Animal constructor: ");
	}
}
class cat extends Animal{
	int X = 5;
	
	void Makesound() {
		System.out.println("Cat: Meow");
		super.Makesound();
		System.out.println(" X + X :"+ X+X);
		System.out.println(" X + super X :"+ X+super.X);
	}
	void car() {
		System.out.println("car Constructor");
		super.Animal();
	}
}
public class VMCsuper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		cat c = new cat();//For the super method
		c.Makesound();//For the super method
//		c.Animal();
		c.car();//by using super for parent constructor
	}
//	c.close();

}
