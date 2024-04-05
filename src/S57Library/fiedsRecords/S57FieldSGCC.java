package S57Library.fiedsRecords;

import S57Library.basics.S57FieldAnnotation;
import S57Library.fiedsRecords.descriptive.S57DDRDataDescriptiveField;


public class S57FieldSGCC extends S57DataField {
    @S57FieldAnnotation(name="CCUI")
    public int ccui;
    @S57FieldAnnotation(name="CCIX")
    public int ccix;
    @S57FieldAnnotation(name="CCNC")
    public int ccnc;
    public S57FieldSGCC(String tag, byte[] fieldData, S57DDRDataDescriptiveField fieldDefinition) throws Exception {
        super(tag, fieldData, fieldDefinition);
        decode();
    }

    @Override
    protected void updateField() {}

}
