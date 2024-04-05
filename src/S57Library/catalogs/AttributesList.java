package S57Library.catalogs;
import java.util.ArrayList;


/*
 * the list of attributes for a S57 object.
 */
public class AttributesList extends ArrayList<S57Attribute> {

	public AttributesList(int size){
		super(size);
	}
	
	public boolean add(S57Attribute a){
		return super.add(a);
	}
	public void    addAll(AttributesList otherList){
		super.addAll(otherList);
	}
	public S57Attribute find(S57Attribute test) {
		for(S57Attribute a : this) {
			if(a.name.isEqual(test.name))
				return a;
		}
		return null;
	}

} // end class
