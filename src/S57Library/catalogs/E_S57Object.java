package S57Library.catalogs;

/**
 * Code	ObjectClass	Acronym	Attribute_A	Attribute_B	Attribute_C	Class	Primitives
 *
 */
public class E_S57Object {
	public int    code;
	public String objectClassName = "";
	public String accronym        = "";
	
	public E_S57Object(int code, String className, String accr){
		this.code            = code;
		this.objectClassName = className;
		this.accronym        = accr;
	}
	
	public String toString(){
		return accronym + " " + objectClassName + "("+code+")";
	}
}
