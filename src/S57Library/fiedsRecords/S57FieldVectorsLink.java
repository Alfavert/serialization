/**
 * 
 */
package S57Library.fiedsRecords;

import java.util.Enumeration;
import java.util.Vector;

import S57Library.basics.Link;
import S57Library.fiedsRecords.descriptive.S57DDRDataDescriptiveField;

/**
Field Tag: VRPT Field Name: Vector Record Pointer<br>
Name *NAME A(12) B(40) an Foreign pointer (see 2.2)<br>
Orientation ORNT A(1) b11 <br>
	"F" {1} Forward <br>
	"R" {2} Reverse <br>
	"N" {255} NULL (see 5.1.3)<br>
Usage indicator USAG A(1) b11 <br>
	{1} Exterior "I" <br>
	{2} Interior "E" <br>
	{3} Exterior boundary truncated by the data limit "C"<br>
	"N" {255} NULL (see 5.1.3)<br>
Topology indicator TOPI A(1) b11<br> 
	"B" {1} Beginning node<br>
	"E" {2} End node<br>
	"S" {3} Left face<br>
	"D" {4} Right face<br>
	"F" {5} Containing face<br>
	"N" {255} NULL (see 5.1.3)<br>
Masking indicator MASK A(1) b11<br> 
	"M" {1} Mask<br>
	"S" {2} Show<br>
	"N" {255} NULL<br>
(see 5.1.3) <br>
*/
public class S57FieldVectorsLink extends S57DataField {
	public Vector<Link> links;
	
	public S57FieldVectorsLink(String tag, byte[] fieldData, S57DDRDataDescriptiveField fieldDefinition) throws Exception {
		super(tag, fieldData, fieldDefinition);

		links = new Vector<Link>();
		decode();	
	}
	
	@Override
	public void decode(){
		Enumeration<S57SubFieldDefinition> sfDefs;

		while (bufferIndex < data.length -1) {
			sfDefs = fieldDefinition.getSubFieldDefinitions().elements();
			long name = 0;
			int  ornt = 0;
			int  usag = 0;
			int  topi = 0;
			int  mask = 0;

			while (sfDefs.hasMoreElements()) {
				S57SubFieldDefinition subFieldDef = sfDefs.nextElement();

				//read always the field to have the correct buffer bufferIndex
				byte[] fieldAsBytes = getNextFieldAsBytes(subFieldDef.format);
				Object value = getSubFieldValue(fieldAsBytes, subFieldDef.format);

				if (subFieldDef.name.equals("*NAME")){
					name = (Long) value;
				}else if (subFieldDef.name.equals("ORNT")){
					ornt = (Integer) value;
				}else if (subFieldDef.name.equals("USAG")){
					usag = (Integer) value;
				}else if (subFieldDef.name.equals("TOPI")){
					topi = (Integer) value;
				}else if (subFieldDef.name.equals("MASK")){
					mask = (Integer) value;
				}
			}

			Link link = new Link();
			link.name = name;
			link.ornt = ornt;
			link.usag = usag;
			link.topi = topi;
			link.mask = mask;		
			links.add(link);
		}
	}

	public String toString(){
		return super.toString() + links;
	}
}
