import java.io.*;
import java.lang.InterruptedException;

public abstract class MonitorsWithAborts {
	public void saveState() throws IOException {
		FileOutputStream fileOut = new FileOutputStream("savedState.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();

	}

	public void abort() throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream("savedState.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		Object obj = in.readObject();
		restore(obj);
		in.close();
		fileIn.close();
	}

	public void abortWait() throws IOException, InterruptedException {
		wait();
		saveState();
	}

	public abstract void restore(Object object);
}
