import java.io.Serializable;
import java.io.IOException;
import java.util.Random;

/*
 * this class was adapted from Professor Garg's BoundedBufferMonitor example found at
 * https://github.com/vijaygarg1/EE-360P/blob/master/chapter3-synchronization_primitives/BoundedBufferMonitor.java
 */

class BoundedBufferAbortNotSerialize extends MonitorsWithAbortsNotSerialize {

    final int size = 10;
    Object[] buffer = new Object[size];
    int inBuf = 0, outBuf = 0, count = 0;

    public synchronized void deposit(Object value) throws InterruptedException, IOException, ClassNotFoundException{
		savestate();
        while (count == size) // buffer full
            wait();
        buffer[inBuf] = value;
        inBuf = (inBuf + 1) % size;
        count++;

		if (randomlyDecideIfWeShouldAbort()){
			System.out.println("before abort: " + this);
			abort();
			System.out.println("after abort: " + this);
			return;
		}
		System.out.println("thread didn't decide to abort");

		if (count == 1) // items available for fetch
			notify();
    }

    public synchronized Object fetch() throws InterruptedException, IOException, ClassNotFoundException{
		savestate();
        Object value;
        while (count == 0) // buffer empty
            wait();
        value = buffer[outBuf];
        outBuf = (outBuf + 1) % size;
        count--;

		if (randomlyDecideIfWeShouldAbort()){
			System.out.println("before abort: " + this);
			abort();
			System.out.println("after abort: " + this);
			return null;
		}

        if (count == size - 1) // empty slots available
            notify();
        return value;
    }

	private boolean randomlyDecideIfWeShouldAbort(){
		Random rand = new Random();
		if (rand.nextInt(5) == 0){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object save(){
		BoundedBufferAbortNotSerialize save = new BoundedBufferAbortNotSerialize();
		Object[] buf = new Object[size];
		for (int i = 0; i < buf.length; i++){
			buf[i] = buffer[i];
		}
		save.buffer = buf;
		save.inBuf = this.inBuf;
		save.outBuf = this.outBuf;
		save.count = this.count;
		return save;
	}

	@Override
	public void restore(Object object){
		BoundedBufferAbortNotSerialize saved = (BoundedBufferAbortNotSerialize) object;
		this.buffer = saved.buffer;
		this.inBuf = saved.inBuf;
		this.outBuf = saved.outBuf;
		this.count = saved.count;
	}

	@Override
	public String toString(){
		StringBuilder string = new StringBuilder("buffer: {");
		for(int i = 0; i < buffer.length; i++){
			string.append(buffer[i] + ", ");
		}
		string.append("} inBuf:" + inBuf + " outBuf:" + outBuf + " count:" + count);
		return string.toString();
	}
	
}
