/**
 * 
 */
package S57Library.objects;

import java.awt.geom.Path2D;

import S57Library.catalogs.AttributesList;
import S57Library.catalogs.S57Attribute;
import S57Library.fiedsRecords.S57FieldVRID;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class S57Object{
	public long name      = -1;
	public int  type      = -1;
	public int  priority  = 0;              //lowest priority will be drawn first

	public AttributesList attributes;

	public S57Object() {
		super();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addAttributes(AttributesList attr) {
		if (attributes == null){
			attributes = new AttributesList(1);
		}
		attributes.addAll(attr);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * A special toString for debugging purpose.
	 * @return the short className and the object pointer (name).
	 */
	public String toStringShort(){
		return String.format("name :%d",  name);
	}
	/*
	 * For debugging purpose.
	 * @return the short className, the object pointer (name).
	 */
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String toString(){
		return String.format("name :%d", name);
	}

}
