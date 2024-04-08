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
import S57Library.serialize.S57CellModuleSerialization;

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
            S57CellModuleSerialization.serialization();
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
}// end Main
