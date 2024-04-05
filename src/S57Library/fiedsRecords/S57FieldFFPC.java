package S57Library.fiedsRecords;

import S57Library.basics.S57FieldAnnotation;
import S57Library.fiedsRecords.descriptive.S57DDRDataDescriptiveField;

public class S57FieldFFPC extends S57DataField {
    @S57FieldAnnotation(name="FFUI")
    public int ffui;
    @S57FieldAnnotation(name="FFIX")
    public int ffix;
    @S57FieldAnnotation(name="NFPT")
    public int nfpt;
    public S57FieldFFPC(String tag, byte[] fieldData, S57DDRDataDescriptiveField fieldDefinition) throws Exception {
        super(tag, fieldData, fieldDefinition);
        decode();
    }

    @Override
    protected void updateField() {}

}
