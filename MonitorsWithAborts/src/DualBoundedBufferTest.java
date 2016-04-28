import java.util.Random;
import java.io.IOException;
import java.lang.InterruptedException;

/*
 * this class was adapted from Professor Garg's ProducerConsumer example found at
 * https://github.com/vijaygarg1/EE-360P/blob/master/chapter3-synchronization_primitives/ProducerConsumer.java
 */

class Producer implements Runnable {
    DualBoundedBuffer b = null;
    public Producer(DualBoundedBuffer initb) {
        b = initb;
        new Thread(this).start();
    }
    public void run() {
        Integer item;
        Random r = new Random();
        while (true) {
			try {
				item = r.nextInt();
				b.depositInFirstBuffer(item);
				System.out.println("produced item added to first buffer" + item);
				Thread.sleep(300 + r.nextInt(500));
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

class Mover implements Runnable {
    DualBoundedBuffer b = null;
    public Mover(DualBoundedBuffer initb) {
        b = initb;
        new Thread(this).start();
    }
    public void run() {
        Integer item;
        Random r = new Random();
        while (true) {
			try {
				System.out.println("moving item");
				b.moveFromFirstBufferToSecond();
				Thread.sleep(100 + r.nextInt(500));
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
    DualBoundedBuffer b = null;
    public Consumer(DualBoundedBuffer initb) {
        b = initb;
        new Thread(this).start();
    }
    public void run() {
        Integer item;
        Random r = new Random();
        while (true) {
			try {
				item = (Integer) b.fetchFromSecondBuffer();
				System.out.println("fetched item from second buffer" + item);
				Thread.sleep(800 + r.nextInt(500));
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

class DualBoundedBufferTest {
    public static void main(String[] args) {
        DualBoundedBuffer dualBuffer = new DualBoundedBuffer();
        Producer producer = new Producer(dualBuffer);
		Mover mover = new Mover(dualBuffer);
        Consumer consumer = new Consumer(dualBuffer);
    }
}
