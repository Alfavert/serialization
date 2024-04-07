import S57Library.basics.E_S57IntededUsage;
import S57Library.basics.E_VerticalDatum;
import S57Library.basics.Link;
import S57Library.catalogs.S57Attribute;
import S57Library.fiedsRecords.S57FieldDSID;
import S57Library.fiedsRecords.S57FieldDSPM;
import S57Library.fiedsRecords.S57FieldDSSI;
import S57Library.fiedsRecords.descriptive.S57DDRDataDescriptiveField;
import S57Library.files.S57ByteBuffer;
import S57Library.files.S57CellModule;
import S57Library.files.S57ModuleReader;
import S57Library.objects.*;
import S57Library.records.S57DataRecordEntryMap;
import S57Library.records.S57LogicalRecord;
import S57Library.basics.E_S57IntededUsage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.Random;
import java.nio.ByteBuffer;


public class Main {

    public static void main(String[] args) throws IOException {

        S57CellModule cell = new S57CellModule();
        S57ModuleReader reader = new S57ModuleReader(cell);

        String appPath = new File(".").getCanonicalPath();
        String pathToFile = Paths.get(appPath, "RU3NTKQ0.000").toString();
        try {
            reader.load(pathToFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        S57ObjectsVector featureList = cell.getFeatures();
        S57ObjectsMap    spatialList = cell.getSpatial();

        for ( S57Object obj : featureList ) {
            S57Feature feature = (S57Feature) obj;

            PrintFeature(feature);
            PrintSpatial(feature, spatialList);
        }
        try {
            serialization();
        }catch (Exception e){
            throw new RuntimeException(e);
    }

    }

    public static void PrintFeature(S57Feature ft) {
        System.out.println("********************** RCID: " + ft.rcid + " "
                + ft.object.accronym
                + "(" + ft.object.code +") **********************");
        System.out.println(ft.object.objectClassName);
        System.out.println("Id name: " + ft.name + " Id longname: " + ft.worldWideIdentifier);
        System.out.println("Geom primitive:   "+ ft.objectPrimitiveType.toString());
        System.out.println("Display priority: "+ ft.displayPriority);
        System.out.println("Viewing group:    "+ ft.viewGroup);
        System.out.println("Display category: "+ ft.displayCategory);
        System.out.println("Symb Instruction: "+ ft.symbologyInstruction);
        System.out.println("Update instruction: "+ ft.updateInstruction);
        System.out.println("Update Number: "+ ft.updateNumber);
        if(ft.updateControl != null)
            System.out.println(ft.updateControl);

        if(ft.attributes != null) {
            String an = "Atribute: ";
            for (S57Attribute att : ft.attributes) {
                an += att.toString();
            }
            System.out.println(an);
        }

        if(ft.linkedFeatures != null) {
            String ln = "Linked to: ";

            for(S57Object o : ft.linkedFeatures) {
                if(o instanceof S57Feature) {
                    S57Feature f = (S57Feature) o;
                    ln += "[" + f.worldWideIdentifier + "];";
                }
            }
            System.out.println(ln);
        }
    }

    public static void PrintSpatial( S57Feature feature, S57ObjectsMap geomList ) {
        if (feature.linkToSpatials == null)
            return;

        for(Link lnk : feature.linkToSpatials) {
            S57Spatial spatial = (S57Spatial) geomList.searchByCode(lnk.name);
            System.out.println(lnk.toString());

            if(spatial != null)
                System.out.println("Spatial " + spatial.toString() + " " + spatial.getClass().getSimpleName() );
            else
                System.out.println("Not found");
        }
    }

    public static void serialization() throws IOException {
        try {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("S57CellModule.txt"));
        S57CellModule cellModule = new S57CellModule();
//        cellModule.setIntendedUsage("dfgsdfg");
//        String tag = "dfgsdfg";
//        byte[] fieldData = new byte [1];
//        S57ByteBuffer buffer = new S57ByteBuffer(1);
//
//        S57LogicalRecord logicalRecord = new S57LogicalRecord() {
//            @Override
//            protected boolean checkValidity() {
//                return false;
//            }
//        };
//        S57DDRDataDescriptiveField fieldDefinition = new S57DDRDataDescriptiveField(1, buffer, logicalRecord);
//        S57FieldDSID s57FieldDSID = new S57FieldDSID();
//
//        cellModule.setDSID();
//
//
//        cellModule
        int lg = 100;
        S57ByteBuffer buffer = new S57ByteBuffer(lg);
//        byte[] array = new byte[20];
//        new Random().nextBytes(array);
        byte[] array = new byte[20];
        Random random = new Random();
        ByteBuffer buffer1 = ByteBuffer.wrap(array);
        for (int i = 0; i < array.length; i += 4) {
            int randomIntValue = random.nextInt(); // генерируем случайное int значение
            buffer1.putInt(randomIntValue);
        }

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
        }catch (Exception e){
            throw new RuntimeException(e);
        }



    }
}// end Main
