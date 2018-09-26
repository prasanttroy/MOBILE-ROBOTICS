import java.io.*;

public class filetest {
	
	public static void main(String args[])
	{
		File file = new File("Myfile.txt");
		FileWriter os = null;
		try {
			
			os = new FileWriter(file);
			os.write("Hi");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
