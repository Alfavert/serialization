package S57Library.fiedsRecords;

import S57Library.basics.S57FieldAnnotation;
import S57Library.fiedsRecords.descriptive.S57DDRDataDescriptiveField;


public class S57FieldVRPC extends S57DataField {
    @S57FieldAnnotation(name="VPUI")
    public int vpui;
    @S57FieldAnnotation(name="VPIX")
    public int vpix;
    @S57FieldAnnotation(name="NVPT")
    public int nvpt;
    public S57FieldVRPC(String tag, byte[] fieldData, S57DDRDataDescriptiveField fieldDefinition) throws Exception {
        super(tag, fieldData, fieldDefinition);
        decode();
    }

    @Override
    protected void updateField() {}

}

