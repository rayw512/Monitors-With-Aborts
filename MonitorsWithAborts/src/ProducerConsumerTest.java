import java.util.Random;
import java.io.IOException;
import java.lang.InterruptedException;

class Producer implements Runnable {
    BoundedBufferAbort b = null;
    public Producer(BoundedBufferAbort initb) {
        b = initb;
        new Thread(this).start();
    }
    public void run() {
        Double item;
        Random r = new Random();
        while (true) {
			try {
				item = r.nextDouble();
				System.out.println("produced item " + item);
				b.deposit(item);
				Thread.sleep(200);
			} catch (InterruptedException e){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e){
				e.printStackTrace();
			}
			
        }
    }
}

class Consumer implements Runnable {
    BoundedBufferAbort b = null;
    public Consumer(BoundedBufferAbort initb) {
        b = initb;
        new Thread(this).start();
    }
    public void run() {
        Double item;
        while (true) {
			try {
				item = (Double) b.fetch();
				System.out.println("fetched item " + item);
				Thread.sleep(500);
			} catch (InterruptedException e){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e){
				e.printStackTrace();
			}
        }
    }
}

class ProducerConsumerTest {
    public static void main(String[] args) {
        BoundedBufferAbort buffer = new BoundedBufferAbort();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
    }
}
