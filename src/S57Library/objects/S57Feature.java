/**
 * 
 */
package S57Library.objects;

import java.util.Vector;

import S57Library.basics.E_S57RecordType;
import S57Library.basics.Link;
import S57Library.catalogs.AttributesList;
import S57Library.catalogs.E_S57Object;
import S57Library.catalogs.S57Attribute;
import S57Library.fiedsRecords.S57FieldFFPT;
import S57Library.fiedsRecords.S57FieldFRID;


/*
 * An object which contains the non-locational information about real world entities.
 * Feature objects are defined in Appendix A, IHO Object Catalogue.
 * in capcode, features are weighted by a priority code to specify the Z-order of the feature. 
 * The lower is the code, the earlier the object will be displayable and maybe overlapped by others.   
 */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class S57Feature extends S57Object {
	// Record identification/ For debug purpose
	public int rcid;

	// stores the object information (code, className, etc)
	public E_S57Object object;

	// stores the object primitive type (area, line, point)
	public E_S57ObjectPrimitiveType objectPrimitiveType;

	/* feature must have a worldwide unique identifier (see 7.6.2 FOID)*/
	public long worldWideIdentifier;
	// store record version RVER contains the serial number of the record edition
	public int  recordVersion;
	// store record update instruction "I" {1} Insert "D" {2} Delete "M" {3} Modify
	public int updateInstruction;
	// Feature update applied
	public int updateNumber;
	// Update command instruction (used only with updated dataset)
	public S57UpdateControl updateControl = null;
	/* == attributes common to most features == */
	/* the possible colors of an object */
	/*  SCAMIN the minimum scale at which the object should be visible */
	private int scaleMinimum = 0;

	// From Lookup table
	public int displayPriority    = 8;
	public int viewGroup          = 11000;
	public String radarPriority   = "O";
	public String displayCategory = "";
	public String symbologyInstruction = "";

	/* link to the associated features */
	public S57ObjectsVector linkedFeatures;
	public S57Feature       upLink      = null;          // Link to master
	public boolean          bSymbolized = false;

	/* link to the associated connected nodes (eg area and lines)*/
	public Vector<Link>    linkToSpatials;
	
	/*
	 * creates a Feature with a priority of 0
	 */
	public S57Feature(){
		super();
	}

	/*
	 * set the world wide identifier of the feature.
	 * this is call only by the S-57 parser when FOID field is decoded.
	 * see IHO S-57 specification 7.6.2 (FOID)
	 */
	public void setWorldWideIdentifier(long wwi){
		worldWideIdentifier = wwi;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String toString() {

		return super.toString() 
		+ (object != null ? ";object:" + object : "")
		+ (objectPrimitiveType != null ? ";primitive:" + objectPrimitiveType : "")
		+ (linkedFeatures != null? linkedFeatures : "");
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addLinkedFeatures(S57FieldFFPT fpt, S57ObjectsVector objects) {
		for (Object o : fpt.names) {
			Long i = (Long)o;

			S57Object so = objects.searchByLongIdentifier(i);

			if (so != null) {
				if(so instanceof S57Feature) {
					S57Feature f = (S57Feature) so;
					((S57Feature) so).upLink = this;
				}
				linkedFeatures().add(so);
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private S57ObjectsVector linkedFeatures() {
		if( linkedFeatures == null){
			linkedFeatures = new S57ObjectsVector(1);
		}
		return linkedFeatures;
	}

	/*
	 * @param scaleMinimum the scaleMinimum to set
	 */
	public void setScaleMinimum(int scaleMinimum) {
		this.scaleMinimum = scaleMinimum;
	}
	/*
	 * @return the scaleMinimum
	 */
	public int getScaleMinimum() {
		return scaleMinimum;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addVectors(Vector links) {
		//keep the link for later (draw the shape of the feature for example)
		if (linkToSpatials == null){
			linkToSpatials = new Vector<Link>();
		}

		linkToSpatials.addAll(links);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addAttributes(AttributesList attrList) {
		super.addAttributes(attrList);

		for(S57Attribute attr57 : attributes) {
			if(attr57.isValid() && attr57.name.code == 133) {                              // SCAMIN
				setScaleMinimum(attr57.intValue());
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static S57Feature buildFromFRID(S57FieldFRID frid) throws Exception {
		S57Feature res = null;
		if (frid.recordType == E_S57RecordType.feature) {
				res                     = new S57Feature();
				res.object              = frid.object;
				res.objectPrimitiveType = frid.objectPrimitiveType;
				res.rcid                = frid.recordIdentificationNumber;
				res.recordVersion       = frid.recordVersion;
				res.updateInstruction   = frid.recordUpdateInstruction;
		}else{
			throw new S57WrongTypeException(frid.recordType + " not treated");				
		}
		res.updateNumber = 0;
		res.name = frid.name;

	return res;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public S57Attribute getAttribute(String name) {
		if(attributes == null)
			return null;

		for (S57Attribute att : attributes) {
			if(att.name.accronym.equals(name) )
			   return att;
		}
    return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean hasAttribute(String name) {
		return getAttribute(name) != null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean hasAttribute(String name, String value) {
		S57Attribute att = getAttribute( name);
		if(att != null && att.value != null) {
           if(value.equals(att.value))
			   return true;
		}
	return false;
	}

/*
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setTableData(LookUpTable tab) {
		displayPriority      = tab.DPRI;

		if(!tab.LUCM.isEmpty())
			viewGroup  = Integer.parseInt(tab.LUCM);

		radarPriority        = tab.RPRI;
		symbologyInstruction = tab.INST;
		displayCategory      = tab.DISC;
	}

	public String getAttributeString(String attrName) {
		S57Attribute attr  = getAttribute(attrName);
		if(attr != null && attr.value != null) {
			return attr.value;
		}

	return null;
	}
*/

} // en class
