package S57Library.records;

import S57Library.fiedsRecords.*;
import S57Library.fiedsRecords.descriptive.S57DDRDataDescriptiveField;
import S57Library.fiedsRecords.descriptive.S57DDRField;
import S57Library.files.S57ByteBuffer;
import S57Library.files.S57CellModule;
import S57Library.files.S57ModuleReader;
import S57Library.objects.*;


public class S57DataRecord extends S57LogicalRecord {

	private S57ModuleReader module;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public S57DataRecord(byte[] array, S57ModuleReader module) throws Exception {
		super(array);
		this.module = module;

		readFields();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected boolean checkValidity() {
		return true;
	}
	
	@Override
	public String toString(){
		return String.format("DR/%s", super.toString(), module.fieldDefinitions.toString());
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void readFields() throws Exception {
		S57CellModule    cell     = module.getCellModule();
		S57ObjectsMap    spatials = cell.getSpatial();
		S57ObjectsVector features = cell.getFeatures();

		int width           = this.getFieldEntryWidth();
		int fieldOffsetSize = getFieldOffsetSize();
		int index           = HEADER_RECORD_SIZE;
		int fieldLengthSize = getFieldLengthSize();

		while (array[index] != S57DDRField.FIELD_TERMINATOR) {

			//cast the current object
			S57Spatial spatial = module.currentObject instanceof S57Spatial ? (S57Spatial) module.currentObject : null;
			S57Feature feature = module.currentObject instanceof S57Feature ? (S57Feature) module.currentObject : null;

			//extract the tag
			String tag = S57ByteBuffer.getString(array, index, 4);

			//search the fields definition for this tag
			S57DDRDataDescriptiveField fieldDefinition = module.fieldDefinitions.get(tag);

			if (fieldDefinition != null){
				//create a byte array of the field size 
				int length = S57ByteBuffer.getInteger(array, index + 4, fieldLengthSize);
				int pos    = S57ByteBuffer.getInteger(array, index + 4 + fieldLengthSize, fieldOffsetSize);

				//and copy the content of the buffer in this byte array
				byte[] fieldData = new byte[length];
				System.arraycopy(array, fieldAreaBaseAddress+pos, fieldData, 0, length);

				if (tag.equals("0001")) {
//					 ignored int id = new S57Field0001(tag, fieldData, fieldDefinition).id;
				}
				else if (tag.equals("CATD")) {
					module.newCatEntryCallBack(new S57FieldCATD(tag, fieldData, fieldDefinition));
				}
				else if (tag.equals("DSID")) {
					cell.setDSID(new S57FieldDSID(tag, fieldData, fieldDefinition));
					module.setDSIDavailable(true);
				}
				else if (tag.equals("DSSI")) {
					cell.setDSSI(new S57FieldDSSI(tag, fieldData, fieldDefinition));
					module.setDSSIavailable(true);
				}
				else if (tag.equals("DSPM")) {
					cell.setDSPM(new S57FieldDSPM(tag, fieldData, fieldDefinition));
					module.setDSPMavailable(true);
				}
				/*=============== SPATIAL RECORDS======================*/
				else if (tag.equals("VRID")) {
					S57FieldVRID vrid = new S57FieldVRID(tag, fieldData, fieldDefinition);
					module.currentObject = S57Spatial.buildFromVRID(vrid);
					spatial = (S57Spatial) module.currentObject;
					spatials.add(spatial);
				}
				else if (tag.equals("ATTV")){
					S57FieldATTV attv = new S57FieldATTV(tag, fieldData, fieldDefinition);
					assert spatial != null;
					spatial.addAttributes(attv.attributes);
				}
				else if (tag.equals("VRPT")){
					S57FieldVRPT vrpt = new S57FieldVRPT(tag, fieldData, fieldDefinition);

					if (spatial instanceof S57Edge edge) {
						edge.addVectors(vrpt.links, spatials);
					} else {
						throw new Exception("invalid VRPT structure");
					}
				}
				else if(tag.equals("VRPC")) {
					S57FieldVRPC vrpc = new S57FieldVRPC(tag, fieldData, fieldDefinition);
					if(spatial != null)
						spatial.updateControl = new S57UpdateControl(vrpc);
				}
				else if(tag.equals("SGCC")) {
					S57FieldSGCC sgcc = new S57FieldSGCC(tag, fieldData, fieldDefinition);
					if(spatial != null)
					   spatial.updateControl = new S57UpdateControl(sgcc);
				}
				else if (tag.equals("SG2D")) {
					S57FieldSG2D sg2d = new S57FieldSG2D(tag, fieldData, fieldDefinition, 
							                cell.getCoordinateMultiplicationFactor());
					assert spatial != null;
					spatial.addPositions(sg2d.positions);
				}
				else if (tag.equals("SG3D")) {
					S57FieldSG3D sg3d = new S57FieldSG3D(tag, fieldData, fieldDefinition, 
							cell.getCoordinateMultiplicationFactor(),
							cell.getSoundingMultiplicationFactor());

					assert spatial != null;
					spatial.addPositions(sg3d.positions);
				}
				/*================= FEATURE RECORDS ===================*/
				else if (tag.equals("FRID")) {
					module.currentObject = S57Feature.buildFromFRID(new S57FieldFRID(tag, fieldData, fieldDefinition));
					feature = (S57Feature) module.currentObject;
					if(feature.object != null)
					    features.add(module.currentObject);
				}
				else if (tag.equals("FOID")) {
					S57FieldFOID foid = new S57FieldFOID(tag, fieldData, fieldDefinition);
					if (feature != null){
						feature.setWorldWideIdentifier(foid.worldWideIdentifier);						
					}
				}
				else if (tag.equals("ATTF")) {
					S57FieldATTF attf = new S57FieldATTF(tag, fieldData, fieldDefinition);
					if (feature != null) {
						feature.addAttributes(attf.attributes);
					}
				}
				else if (tag.equals("NATF")) {
					// Not support. National attribute field has a lot of errors
/*
					S57FieldNATF natf = new S57FieldNATF(tag, fieldData, fieldDefinition);

					if (feature != null){
						feature.addAttributes(natf.attributes);						
					}
*/
				}
				else if (tag.equals("FFPT")) {
					S57FieldFFPT ffpt = new S57FieldFFPT(tag, fieldData, fieldDefinition);
					if (feature != null){
						feature.addLinkedFeatures(ffpt, features);
					}
				}
				else if (tag.equals("FSPT")) {
					S57FieldFSPT fspt = new S57FieldFSPT(tag, fieldData, fieldDefinition);
					if (feature != null){
						feature.addVectors(fspt.links);
					}
				}
				else if (tag.equals("FSPC")) {             // Update control of FSPT field
					S57FieldFSPC fspc = new S57FieldFSPC(tag, fieldData, fieldDefinition);
					if (feature != null) {
						feature.updateControl = new S57UpdateControl(fspc);
					}
				}
				else if (tag.equals("FFPC")) {
					S57FieldFFPC ffpc = new S57FieldFFPC(tag, fieldData, fieldDefinition);
					if (feature != null) {
						feature.updateControl = new S57UpdateControl(ffpc);
					}
				}
				else{
					if (feature != null)
						System.err.println(tag + " not decoded, Record " + feature.rcid);
					else
						System.err.println(tag + " not decoded");
				}
			}else{
				System.err.printf("field %s has no declared definition %n", tag);
			}

			index += width;
		}
	}
}
