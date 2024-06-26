/**
 * 
 */
package S57Library.fiedsRecords;

import S57Library.basics.E_S57RecordType;
import S57Library.basics.S57FieldAnnotation;
import S57Library.fiedsRecords.descriptive.S57DDRDataDescriptiveField;

import java.io.Serializable;


/**
 * @author cyrille
 *
 */
public class S57FieldDSPM extends S57IdentityField implements Serializable {
	@S57FieldAnnotation(name="HDAT")
	public int horizontalDatum;
	@S57FieldAnnotation(name="VDAT")
	public int verticalDatum;
	@S57FieldAnnotation(name="SDAT")
	public int soundingDatum;
	@S57FieldAnnotation(name="CSCL")
	public int compilationScaleOfData;
	@S57FieldAnnotation(name="DUNI")
	public int unitOfDepthMeasurement;
	@S57FieldAnnotation(name="HUNI")
	public int unitOfHeightMeasurement;
	@S57FieldAnnotation(name="PUNI")
	public int unitOfPositionAccuracy;
	@S57FieldAnnotation(name="COUN")
	public int coordinateUnits;
	@S57FieldAnnotation(name="COMF")
	public int coordinateMultiplicationFactor;
	@S57FieldAnnotation(name="SOMF")
	public int soundingMultiplicationFactor;
	@S57FieldAnnotation(name="COMT")
	public String comments;
	
	public S57FieldDSPM(String tag, byte[] fieldData,
			S57DDRDataDescriptiveField fieldDefinition) throws Exception {
		super(tag, fieldData, fieldDefinition);
		decode();
	}
		
}
