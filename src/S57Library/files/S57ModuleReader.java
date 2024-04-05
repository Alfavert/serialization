package S57Library.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.CRC32;

import S57Library.fiedsRecords.S57FieldCATD;
import S57Library.fiedsRecords.S57FieldDefinitionTable;
import S57Library.objects.*;
import S57Library.records.S57DataDescriptiveRecord;
import S57Library.records.S57DataRecord;
import S57Library.records.S57LogicalRecord;

public class S57ModuleReader {
	//
	// ////////////////   Used for reading //////////////////////////
	//
    // Files and catalog handling
	protected String     fileName;
	// Catalog data file
	protected S57Catalog cellCatalog = null;
	// Reading byte buffer
	protected ByteBuffer buffer;
	public S57FieldDefinitionTable fieldDefinitions;
	//used to store the current object being analysed in the file
	public S57Object currentObject = null;
    // Mark read records
	protected boolean DSIDavailable = false;
	protected boolean DSPMavailable = false;
	protected boolean DSSIavailable = false;
	//
	// ////////////////   General Data //////////////////////////
	//
	protected S57CellModule    cellModule;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public S57ModuleReader(S57CellModule cell) {
		super();

		fieldDefinitions = new S57FieldDefinitionTable(50);
		cellModule = cell;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long load(String aFileName) throws Exception {
		this.fileName = aFileName;
		int index     = 0;
		int recNum    = 0;
		int size      = (int) new File(aFileName).length();

		FileInputStream fis = new FileInputStream(aFileName);
		FileChannel     fc  = fis.getChannel();
		CRC32           crc = new CRC32();

		buffer = ByteBuffer.allocate(size);
		callBackInit(size);
		//read the whole file into the byteBuffer
		fc.read(buffer);
		fc.close();
		buffer.rewind();

		while ( buffer.position() < size ) {
			S57LogicalRecord record = readRecordAtIndex(index);
			if(!record.isValid()) {
				throw new S57ReadException(String.format("invalid record at rec N: %d (@%d)", recNum, index));
			}
			recNum++;
			index += record.getRecordLength();
			callBackWhileLoading(record, index);
		}
		callBackEnd();

		// Calculate file CRC
		crc.update(buffer.array());
		return crc.getValue();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void extractGeneralInformation(String aFileName) throws Exception {
		int index  = 0;
		int recNum = 0;
		int size   = (int) new File(aFileName).length();

		FileInputStream fis = new FileInputStream(aFileName);
		FileChannel     fc  = fis.getChannel();
		buffer              = ByteBuffer.allocate(size);

		fc.read(buffer);
		fc.close();
		buffer.rewind();

		while ( buffer.position() < size ) {
			S57LogicalRecord record = readRecordAtIndex(index);
			if (DSIDavailable ) {
				break;
			}
			index += record.getRecordLength();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * reads the LogicalRecord at the index position.
	 * the first 5 bytes at the index must give the size of the LogicalRecord.
	 * the first record is the Data Descriptive Record.
	 * all the following ones are Data record.
	 * the fields read from data record are organised and classified in the field objects.
	 * @param index : position in the file where should start a LogicalRecord 
	 * @return a LogicalRecord class
	 * @throws Exception 
	 */
	protected S57LogicalRecord readRecordAtIndex(int index) throws Exception {
		int size     = extractRecordSize(index);
		byte[] array = new byte[size];

		buffer.get(array);
		if (index == 0){
			return new S57DataDescriptiveRecord(array, fieldDefinitions);
		}

		return new S57DataRecord(array, this);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * extract the record size from the file.
	 * then return to the position. This is used locally to allocate the exact size needed
	 * for a record.
	 * @param index
	 * @return an integer representing the size of the record to read.
	 * @throws IOException
	 */
	protected int extractRecordSize(int index) throws IOException {
		byte[] b   = new byte[5];
		int oldPos = buffer.position();

		buffer.position(index);
		buffer.get(b);
		buffer.position(oldPos);

		String s = S57ByteBuffer.getString(b, 5);
		return Integer.parseInt(s);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	public S57CellModule    getCellModule() { return cellModule; }

	protected void callBackInit(int size) {
		//do nothing here
	}
	/*
	 * Override this method to do something particular 
	 * during the load (progress window update, for example)
	 * @param record 
	 * @param index : the actual position in the file		
	 */
	protected void callBackWhileLoading(S57LogicalRecord record, int index) {
		//do nothing here
	}
	/*
	 * Override this method to do something particular 
	 * at the end of the load process (terminate progress info)		
	 */
	protected void callBackEnd() {
		//do nothing here
	}	

	public void newCatEntryCallBack(S57FieldCATD catd) {
		// do nothing, called by S57DataRecord
	}

	public S57Catalog getCellCatalog() {
		return cellCatalog;
	}

	public void setCellCatalog(S57Catalog cellCatalog) {
		this.cellCatalog = cellCatalog;
	}

	public void setDSIDavailable(boolean DSIDavailable) {
		this.DSIDavailable = DSIDavailable;
	}

	public void setDSPMavailable(boolean DSPMavailable) {
		this.DSPMavailable = DSPMavailable;
	}

	public void setDSSIavailable(boolean DSSIavailable) {
		this.DSSIavailable = DSSIavailable;
	}
}