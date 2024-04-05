/**
 * 
 */
package S57Library.fiedsRecords;

import S57Library.basics.S57FieldAnnotation;
import S57Library.fiedsRecords.descriptive.S57DDRDataDescriptiveField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * @author cyrille
 *
 */
public class S57FieldCATD extends S57DataField {
	@S57FieldAnnotation(name="FILE")
	public String fileName;
    @S57FieldAnnotation(name="LFIL")
	public String longFileName;
	@S57FieldAnnotation(name="VOLM")
	public String volume;
	@S57FieldAnnotation(name="SLAT")
	public double southLat;
	@S57FieldAnnotation(name="WLON")
	public double westLon;
	@S57FieldAnnotation(name="NLAT")
	public double northLat;
	@S57FieldAnnotation(name="ELON")
	public double eastLon;
	@S57FieldAnnotation(name="CRCS")
	public String crc;
	@S57FieldAnnotation(name="COMT")
	public String comment;

	public S57FieldCATD() {
		// TODO Auto-generated constructor stub
	}

	public S57FieldCATD(String tag, byte[] data, S57DDRDataDescriptiveField fieldDefinition) {
		super(tag, data, fieldDefinition);
		decode();
	}

	public void decode(){
		Enumeration<S57SubFieldDefinition> sfDefs;
		Field[] fields = getClass().getFields();
		while (bufferIndex < data.length -1){
			sfDefs = fieldDefinition.getSubFieldDefinitions().elements();

			while (sfDefs.hasMoreElements()){
				S57SubFieldDefinition subFieldDef = sfDefs.nextElement();

				//read always the field to have the correct buffer bufferIndex
				byte[] fieldAsBytes = getNextFieldAsBytes(subFieldDef.format);

				for (Field f:fields) {
					S57FieldAnnotation annotation = f.getAnnotation(S57FieldAnnotation.class);

					//try to set the field (find one with the correct attribute name
					if (annotation != null && annotation.name().equals(subFieldDef.name)){
						try {
							Object value = getSubFieldValue(fieldAsBytes, subFieldDef.format);
							if (value != null) // Value may be empty string
							{
								if (annotation.setter().isEmpty()){
									//no setter function, set directly the meaning
									f.set(this, value);
								}else{
									//there is a setter declared, try to use it
									Method m = this.getClass().getMethod(annotation.setter(), Integer.class);
									m.invoke(this, value);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
			updateField();
		}
	}

	public String toString(){
		return String.format("%s-%s (%s) %f/%f;%f/%f", fileName, longFileName, volume, northLat, westLon, southLat, eastLon);
	}
}
