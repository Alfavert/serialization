/**
 * 
 */
package S57Library.fiedsRecords.descriptive;

import S57Library.files.S57ByteBuffer;
import S57Library.records.S57LogicalRecord;

public class S57DDRField0000 extends S57DDRField{
	
	private String listOfPairedTags;
	private String temp="";

	public S57DDRField0000(int index, S57ByteBuffer buffer, S57LogicalRecord record) throws Exception{
		super(index, buffer, record);
	    //read the external fileName (unused in S-57)
	    @SuppressWarnings("unused")
		String externalFileName = buffer.getFieldAsString(TERMINATORS);
	    //get the arrayDescriptor
	    listOfPairedTags = buffer.getFieldAsString(TERMINATORS);
	}


	public String toString(){
		return super.toString() + String.format(" paired tags:%s", listOfPairedTags);
	}
}
