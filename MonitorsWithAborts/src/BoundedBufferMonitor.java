import java.util.*;

import javax.rmi.CORBA.Util;


class BoundedBufferMonitor {
    final int size = 10;
    Object[] buffer = new Object[size];
    int inBuf = 0, outBuf = 0, count = 0;
    public synchronized void deposit(Object value) throws InterruptedException {
        while (count == size) // buffer full
            this.wait();
        buffer[inBuf] = value;
        inBuf = (inBuf + 1) % size;
        count++;
        if (count == 1) // items available for fetch
            notify();
    }
    public synchronized Object fetch() throws InterruptedException {
        Object value;
        while (count == 0) // buffer empty
           this.wait();
        value = buffer[outBuf];
        outBuf = (outBuf + 1) % size;
        count--;
        if (count == size - 1) // empty slots available
            notify();
        return value;
    }
}