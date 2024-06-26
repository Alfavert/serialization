package S57Library.fiedsRecords.descriptive;

import S57Library.files.S57ByteBuffer;
import S57Library.records.S57LogicalRecord;


public class S57DataDescriptiveFieldFactory {
 	
	public static S57DDRField build(int index, byte[] array, S57LogicalRecord record) throws Exception {
		int fieldEntryWidth = record.getFieldEntryWidth();
		int offset          = record.getHeaderSize() + index * fieldEntryWidth;
		String tag          = S57ByteBuffer.getString(array, offset, record.getFieldTagSize());

		if (tag.equals("0000")){
			return new S57DDRField0000(index, new S57ByteBuffer(array), record);	
		}
		else {
			return new S57DDRDataDescriptiveField(index, new S57ByteBuffer(array), record);			
		}
	}
	

}
