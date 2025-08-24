package vivek;

class one extends Thread{
	public synchronized void display(int n) {
		int i;
		
			for(i=0;i<=10;i++) {
				System.out.println("Ok");
			}
			try {
				Thread.sleep(200);
			}
			catch(Exception e) {
				System.out.println(e);
			}
	}
}
class MyThread1 extends Thread{
	one t;
	MyThread1(one t){
		this.t =t;
	}
	public void run() {
		t.display(10);
	}
}
class MyThread2 extends Thread{
	one t;
	MyThread2(one t){
		this.t =t;
	}
	public void run() {
		t.display(5);
	}
}
public class SyncronizatinDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		one io = new one();
		MyThread1 ok = new MyThread1(io);
		MyThread2 on = new MyThread2(io);
		ok.start();
		on.start();
	}

}
