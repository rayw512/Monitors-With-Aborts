import java.io.*;

public abstract class MonitorsWithAbortsNotSerialize {

	Object savedObj;

	public void savestate() throws IOException
	{
		savedObj = save();
	}
	/**
	 * 
	 * @param <T> the type of the value
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void abort() throws IOException, ClassNotFoundException
	{
		restore(savedObj);
	}

	public abstract Object save();

	public abstract void restore(Object object);
}
