import java.util.Random;

class Test1 extends Thread{
		int K;
		public Test1(int K){
			this.K = K;
		}

        public void run(){
        	int a;
         	try{
	         	for(int i = 1; i <= K; i++){
	         		a = Aufgabe1.n;
	         		Aufgabe1.n = a + 1;
	         		
	         		if(Aufgabe1.n == 4){ // Defines the starting state for Test2
	         			this.sleep(1000); // Wait so the other Thread is calle and caches n
	         		}
	         		
	         		System.out.println("Addition: n = " + Aufgabe1.n);
	         	}
	         } catch(Exception e) {
	         	System.out.println(e);
	         }
         }
     }

class Test2 extends Thread{
		int K;
		public Test2(int K){
			this.K = K;
		}

        public void run(){
         	int a;
	        try{
 				this.sleep(500); //  Wait for initial loops of Test 1
	         	
	         	for(int i = 1; i <= K; i++){
	         		a = Aufgabe1.n;
	         		this.sleep(1000); // Wait so Test 1 continues and finishes
	         		Aufgabe1.n = a - 1;
	         		System.out.println("Subtraktion: n = " + Aufgabe1.n);
	         	}
	         } catch(Exception e) {
	         	System.out.println(e);
	         }
         }
     }

public class Aufgabe1 {
	
	public static int n = 0;

	public static void main(String[] args) {

		int K = 10;

		Test1 Thread1 = new Test1(K);
		Test2 Thread2 = new Test2(K);
		
		Thread1.start();
		Thread2.start();

		try{	// Wait for Threads to finish
			Thread1.join();
			Thread2.join();
		} catch (InterruptedException e){
			System.out.println(e);
		}
		
		System.out.println("n = " + n);
	}
}
