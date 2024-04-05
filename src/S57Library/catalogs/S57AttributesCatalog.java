package S57Library.catalogs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;


@SuppressWarnings("serial")
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * read the attributes that MUST be in data/s57attributes.csv.
 * The access to the attributes is done via the static object S57AttributesCatalog.list
 * to get a particular attribute perform a S57AttributesCatalog.list.geetByCode(int)
 */
public class S57AttributesCatalog  {
	/**
	 * the static list of all possible S57 attribute.
	 */
	public static HashMap<Integer, S57CatalogAttributeElement> intMap;

	static{
		try {
			intMap    = new HashMap<Integer, S57CatalogAttributeElement>();

			new S57AttributesCatalog("data/attributes.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public S57AttributesCatalog(String fileName) throws FileNotFoundException{

		Scanner scanner = new Scanner(new File(fileName));

		//skip header
		scanner.nextLine();
		while(scanner.hasNextLine()) {
			String[] fields = scanner.nextLine().split(";");
			S57CatalogAttributeElement a = new S57CatalogAttributeElement(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3]);
			intMap.put(a.code, a);
		}
		scanner.close();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @param code : the code of the S57 attribute to find (eg: 113)
	 * @return a S57CatalogAttributeElement enumerated item (eg: "Nature of Surface")
	 */
	public static S57CatalogAttributeElement getByCode(int code) {
		S57CatalogAttributeElement element = intMap.get(code);
		if(element == null)
			element = new S57CatalogAttributeElement(code, "Unknown Attribute", "UNKNWN", "A");
		return element;
	}

} // end class


