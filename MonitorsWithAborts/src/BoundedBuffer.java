import java.io.Serializable;
import java.io.IOException;
import java.util.Random;
import java.lang.StringBuilder;

/*
 * this class was adapted from Professor Garg's BoundedBufferMonitor example found at
 * https://github.com/vijaygarg1/EE-360P/blob/master/chapter3-synchronization_primitives/BoundedBufferMonitor.java
 */

class BoundedBuffer implements Serializable {

    final int size = 10;
    Object[] buffer = new Object[size];
    int inBuf = 0, outBuf = 0, count = 0;

    public boolean deposit(Object value){
        if (count == size) // buffer full
            return false;
        buffer[inBuf] = value;
        inBuf = (inBuf + 1) % size;
        count++;
		return true;
    }

    public Object fetch() {
        Object value;
        if (count == 0) // buffer empty
            return null;
        value = buffer[outBuf];
        outBuf = (outBuf + 1) % size;
        count--;
		return value;
    }
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("count: " + count + " items: ");
		for (int  i = outBuf; i < count + outBuf; i++){
			if (i == outBuf){
				// first item
				sb.append("{");
			}
			sb.append(buffer[i % size]);
			if (i == count + outBuf - 1){
				// last item
				sb.append("}");
			} else {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

}
