
import java.io.*;

public class x{
	public static void writeFile2() throws IOException {
		FileWriter fw = new FileWriter("out.txt");
	 
		for (int i = 0; i < 10; i++) {
			fw.write("something");
		}
	 
		fw.close();
	}
}