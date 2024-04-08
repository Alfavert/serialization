package S57Library.serialize;

import S57Library.fiedsRecords.S57FieldDSID;
import S57Library.fiedsRecords.S57FieldDSPM;
import S57Library.fiedsRecords.S57FieldDSSI;
import S57Library.fiedsRecords.descriptive.S57DDRDataDescriptiveField;
import S57Library.files.S57ByteBuffer;
import S57Library.files.S57CellModule;
import S57Library.records.S57DataRecordEntryMap;
import S57Library.records.S57LogicalRecord;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class S57CellModuleSerialization implements Serializable {
    public static void serialization() throws IOException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("S57CellModule.txt"));
            S57CellModule cellModule = new S57CellModule();
            int lg = 23;
            S57ByteBuffer buffer = new S57ByteBuffer(lg);
            byte[] array = new byte[23];
            Arrays.fill(array, (byte) 2);

            S57LogicalRecord record = new S57LogicalRecord(array) {
                @Override
                protected boolean checkValidity() {
                    return false;
                }
            };
            int pos = 20;
            S57DataRecordEntryMap dataRecordEntryMap = new S57DataRecordEntryMap(array, pos);
            int index = 1;
            S57DDRDataDescriptiveField fieldDefinition = new S57DDRDataDescriptiveField(index, buffer, record);
            String tag = "testTag";
            byte[] fieldData = new byte[50];
            new Random().nextBytes(fieldData);


            S57FieldDSID fieldDSID = new S57FieldDSID(tag, fieldData, fieldDefinition);
            fieldDSID.intendedUsage.name();
            fieldDSID.datasetName = "123";
            fieldDSID.editionNumber = "wad";
            fieldDSID.updateNumber = "fre";
            fieldDSID.updateApplicationDate = "fre";
            fieldDSID.issueDate = "fre";
            fieldDSID.decode();
            cellModule.setDSID(fieldDSID);

            S57FieldDSPM dspm = new S57FieldDSPM(tag, fieldData, fieldDefinition);
            dspm.verticalDatum = 3;
            dspm.coordinateMultiplicationFactor = 4;
            dspm.soundingMultiplicationFactor = 5;
            dspm.compilationScaleOfData = 2;

            S57FieldDSSI fieldDSSI = new S57FieldDSSI(tag, fieldData, fieldDefinition);
            fieldDSSI.dataStructure = 123;
            fieldDSSI.attfLexicalLevel = 321;
            fieldDSSI.nationalLexicalLevel = 222;
            fieldDSSI.numberOfMetaRecords = 111;
            fieldDSSI.numberOfCartographicRecords = 333;
            fieldDSSI.numberOfGeoRecords = 213;
            fieldDSSI.numberOfCollectionrecords = 321;
            objectOutputStream.writeObject(cellModule);
            objectOutputStream.writeObject(buffer);
            objectOutputStream.writeObject(dataRecordEntryMap);
            objectOutputStream.writeObject(fieldDefinition);
            objectOutputStream.writeObject(fieldDSID);
            objectOutputStream.writeObject(dspm);
            objectOutputStream.writeObject(fieldDSSI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}