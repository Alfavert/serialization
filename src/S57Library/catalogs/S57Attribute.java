package S57Library.catalogs;
/*
 * A characteristic of an object. It is implemented by a defined attribute label/code,
 * acronym, definition and applicable values (see Appendix A, IHO Object Catalogue). 
 * In the data structure, the attribute is identified by its label/code. 
 * The acronym is only used as a quick reference in related documents and product
 * specifications. Attributes are either qualitative or quantitative.
 */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class S57Attribute {
	public S57CatalogAttributeElement name;
	public String                     value;

	public String toString() {
		if(!isValid())
			return "INVALID";

		if(value != null)
		  return name.accronym + "("+name.code+")"+ "=" + value + ";";

	return name.accronym + "("+name.code+")" + ";";
	}
	/*
	 * @return the meaning of the attribute as int or 0 in case of error.
	 */
	public int intValue() {
		try{
			return Integer.parseInt(value);
		}catch (Exception e){
			return 0;
		}
	}
	
	/*
	 * @return the meaning of the attribute as float or 0f in case of error.
	 */
	public float floatValue() {
		try{
			return Float.parseFloat(value);
		}catch (Exception e){
			return 0f;
		}
	}
	
	/*
	 * @return the meaning of the attribute as double or 0 in case of error.
	 */
	public double doubleValue() {
		try{
			return Double.parseDouble(value);
		}catch (Exception e){
			return 0;
		}
	}

	public boolean isValid() {
		return (name != null );
	}
}