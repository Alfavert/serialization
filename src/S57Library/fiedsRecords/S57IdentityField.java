/**
 * 
 */
package S57Library.fiedsRecords;

import S57Library.basics.E_S57RecordType;
import S57Library.basics.S57FieldAnnotation;
import S57Library.fiedsRecords.S57SubFieldFormat.FormatType;
import S57Library.fiedsRecords.descriptive.S57DDRDataDescriptiveField;


public class S57IdentityField extends S57DataField {
	@S57FieldAnnotation(name="RCNM", setter="setRCNM")
	public int recordName;
	@S57FieldAnnotation(name="RCID", setter="setRCID")
	public int recordIdentificationNumber;
	
	/** the setRCNM set this meaning based on recordName */
	public E_S57RecordType recordType;

	public long name;
	
	public S57IdentityField(String tag, byte[] fieldData, S57DDRDataDescriptiveField fieldDefinition) {
		super(tag, fieldData, fieldDefinition);
		//do not call decode here, children must do it
	}

	public void setRCNM(Integer nm){
		recordName = nm;
		recordType = E_S57RecordType.byCode(nm);		
	}
	
	public void setRCID(Integer id){
		recordIdentificationNumber = id;
	}
	
	@Override
	protected void updateField(){
		//the name is composed of the RCNM plus rgz RCID together

	  	name = (Long) this.getSubFieldValue(data, new S57SubFieldFormat(FormatType.binaryUnsignedInteger, 5));
		long lname = (long) recordName << 16 |  recordIdentificationNumber;
	}
	
	public String toString(){
		return "rcid:" + recordIdentificationNumber + "\n" + 
		super.toString() + (recordType != null ? recordType.toString() : "null (" + recordName + ")");
	}

}
