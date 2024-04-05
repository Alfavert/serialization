package S57Library.catalogs;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class S57ObjectsCatalog  {
	public static HashMap<Integer, E_S57Object> intMap;

	static{
		try {
			intMap    = new HashMap<Integer, E_S57Object>();

			new S57ObjectsCatalog("data/objectclasses.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public S57ObjectsCatalog(String fileName) throws IOException {

		Scanner scanner = new Scanner(new File(fileName));
			String line;
			while (scanner.hasNextLine()) {
				String[] values = scanner.nextLine().split(";");
				E_S57Object o = new E_S57Object(Integer.parseInt(values[0]), values[1], values[2]);
				intMap.put(o.code, o);
			}
			scanner.close();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static E_S57Object getByCode(int code) {
		E_S57Object object = intMap.get(code);
		if(object == null)
			object = new E_S57Object(code, "Unknown Object", "UNKNWN");
		return object;
	}

} // end class

