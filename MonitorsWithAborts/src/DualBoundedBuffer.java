import java.io.Serializable;
import java.io.IOException;
import java.util.Random;
import java.lang.StringBuilder;

class DualBoundedBuffer extends MonitorsWithAborts implements Serializable {

	BoundedBuffer fromBuf;
	BoundedBuffer toBuf;

	public DualBoundedBuffer(){
		fromBuf = new BoundedBuffer();
		toBuf = new BoundedBuffer();
	}

	// put an object in the fromBuf
    public synchronized void depositInFirstBuffer(Object value) throws InterruptedException, IOException, ClassNotFoundException{
		
        while (!fromBuf.deposit(value)) // buffer full
            wait();
		notifyAll();
    }

	// fetch from fromBuf and try to deposit in toBuf
	// abort if toBuf full, restore state of fromBuf
	public synchronized void moveFromFirstBufferToSecond() throws InterruptedException, IOException, ClassNotFoundException {
		saveState();
		Object obj = fromBuf.fetch();
		if (obj != null){
			if (!toBuf.deposit(obj)){
				System.out.println("aborting..");
				System.out.println("before abort");
				abort();
				System.out.println("after abort");
			}
		}
	}

    public synchronized Object fetchFromSecondBuffer() throws InterruptedException, IOException, ClassNotFoundException{
        Object value;
        while ((value = toBuf.fetch()) == null) // buffer empty
            wait();

		notifyAll();

        return value;
    }

	@Override
	public void restore(Object object){
		DualBoundedBuffer saved = (DualBoundedBuffer) object;
		this.fromBuf = saved.fromBuf;
		this.toBuf = saved.toBuf;
	}

}

