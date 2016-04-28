import java.util.Random;
import java.io.IOException;
import java.lang.InterruptedException;

/*
 * this class was adapted from Professor Garg's ProducerConsumer example found at
 * https://github.com/vijaygarg1/EE-360P/blob/master/chapter3-synchronization_primitives/ProducerConsumer.java
 */

class Producer implements Runnable {
    BoundedBufferAbortNotSerialize b = null;
    public Producer(BoundedBufferAbortNotSerialize initb) {
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
    BoundedBufferAbortNotSerialize b = null;
    public Consumer(BoundedBufferAbortNotSerialize initb) {
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

class ProducerConsumerTestNotSerialize {
    public static void main(String[] args) {
        BoundedBufferAbortNotSerialize buffer = new BoundedBufferAbortNotSerialize();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
    }
}
