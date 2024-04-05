package S57Library.catalogs;

/*
 * This enumeration provide a means to get the name, accronym, type and class for a given code.
 * These values are read from the file data/s57attributes.csv
 */
public class S57CatalogAttributeElement {
	/* the attribute code */
	public int    code;

	/* the attribute name */
	public String name;

	/* the accronym of the attribute */
	public String accronym;

	public String atype;
	
	public S57CatalogAttributeElement(int code, String name, String accronym, String type){
		this.code     = code;
		this.name     = name;
		this.accronym = accronym;
		this.atype    = type;
	}
	
	public String toString(){
		return name;
	}

	public boolean isEqual(S57CatalogAttributeElement test) {
		if(this.code == test.code && this.accronym.equals(test.accronym))
			return true;

		return false;
	}
}
