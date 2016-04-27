import java.io.Serializable;
import java.io.IOException;
import java.util.Random;
import java.lang.StringBuilder;

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

}
