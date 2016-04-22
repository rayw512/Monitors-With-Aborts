import java.io.*;

public abstract class MonitorsWithAborts {
	public void savestate() throws IOException
	{
        FileOutputStream fileOut =
        new FileOutputStream("savedState.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
	}
	/**
	 * 
	 * @param <T> the type of the value
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void abort() throws IOException, ClassNotFoundException
	{
        FileInputStream fileIn = new FileInputStream("savedState.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object obj =in.readObject();
        in.close();
        fileIn.close();
		restore(obj);
	}

	public abstract void restore(Object object);
}
