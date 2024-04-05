package S57Library.fiedsRecords.descriptive;

import S57Library.fiedsRecords.S57SubFieldDefinition;
import S57Library.fiedsRecords.SubFieldDefinitionTable;
import S57Library.files.S57ByteBuffer;
import S57Library.records.S57LogicalRecord;

public class S57DDRDataDescriptiveField extends S57DDRField {
	private String fieldName;
	private SubFieldDefinitionTable subFieldDefinitions;
	public String[] formats;
	public String[] subFields;
	/*
	 * @param index
	 * @param buffer
	 * @param record
	 * @throws Exception
	 */
	public S57DDRDataDescriptiveField(int index, S57ByteBuffer buffer, S57LogicalRecord record) throws Exception {
		super(index, buffer, record);

	    fieldName           = buffer.getFieldAsString(TERMINATORS);
	    subFieldDefinitions = new SubFieldDefinitionTable(2);
	}

	public void extractSubFields(){
		subFields = super.getSubFields("!");
		formats   = super.getFormats();

		for (int i = 0; i < subFields.length; i++){
			String tag1 = subFields[i];

			if (tag1.isEmpty()){
				tag1 = this.tag;
			}

			S57SubFieldDefinition fieldDefinition = new S57SubFieldDefinition(tag1, formats[i]);
			subFieldDefinitions.add(fieldDefinition);								
		}
	}

	/*
	 * @return the subFieldDefinitions
	 */
	public SubFieldDefinitionTable getSubFieldDefinitions() {
		return subFieldDefinitions;
	}
}
