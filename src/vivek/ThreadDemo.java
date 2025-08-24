package vivek;
class Table implements Runnable{
	public void run() {
		int i = 0;
//		int n = 3;
		try {
		for(i=0;i<=10;i++) {
			Thread.sleep(20);
			System.out.println();
		}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
public class ThreadDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Table s = new Table();
		Thread th = new Thread(s);
		th.start();
//		th.suspend();
//		th.resume();
	}

}
