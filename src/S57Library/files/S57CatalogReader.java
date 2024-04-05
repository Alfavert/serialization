package S57Library.files;

import S57Library.fiedsRecords.S57FieldCATD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class S57CatalogReader extends S57ModuleReader {
    public ArrayList<String>     catalogPathList;
    public ArrayList<S57Catalog> cellList;

    public File catalogDir;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57CatalogReader(File startDirPath) {
        super(new S57CellModule());
        catalogPathList = new ArrayList<String>();
        cellList        = new ArrayList<S57Catalog>();
        catalogDir      = startDirPath;

        getCatalogList(catalogDir);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57CatalogReader(String filenameWithPath) {
        super(new S57CellModule());

        catalogPathList = new ArrayList<String>();
        cellList        = new ArrayList<S57Catalog>();

        catalogPathList.add(filenameWithPath);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getCatalogList(File dir) {
        try {
            File[] files = dir.listFiles();
            assert files != null;

            for (File file : files) {
                if (file.isDirectory()) {
                    getCatalogList(file);
                } else {
                    String fileName = file.getCanonicalPath();
                    if(S57ChartFile.getFileExtension(fileName).equals("031")) {
                        catalogPathList.add(fileName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void loadCatalog() {
        // Find all catalog files .031 and handle CATD tag to collect chart files in chartList
        for(String path : catalogPathList) {
            try {
                load(path);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void newCatEntryCallBack(S57FieldCATD catd) {
        String directory = Paths.get(fileName).getParent().toString();
        String fullPath = directory + "\\" + catd.fileName;

        if(S57ChartFile.getFileExtension(catd.fileName).equals("000")) {
            S57Catalog catalog = new S57Catalog();
            catalog.setVolumeName(catd.volume);
            catalog.setComment(catd.comment);

            catalog.setSouthLat(catd.southLat);
            catalog.setWestLon(catd.westLon);
            catalog.setNorthLat(catd.northLat);
            catalog.setEastLon(catd.eastLon);

            S57ModuleReader tmpReader = new S57ModuleReader(new S57CellModule());
            try {
                tmpReader.extractGeneralInformation(fullPath);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            S57ChartFile cellFile = new S57ChartFile(catd.fileName, fullPath, catd.crc );
            cellFile.getInfoFrom(tmpReader.getCellModule());

            catalog.setCellFile(cellFile);
            cellList.add(catalog);
        }
        else {
            S57Catalog cell = getCell(catd.fileName);
            if(cell != null) {
                S57ModuleReader tmpReader = new S57ModuleReader(new S57CellModule());
                try {
                    tmpReader.extractGeneralInformation(fullPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                S57ChartFile cellFile = new S57ChartFile(catd.fileName, fullPath, catd.crc );
                cellFile.getInfoFrom(tmpReader.getCellModule());

                cell.addUpdate(cellFile);

            }
        }
   }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57Catalog getCell(String cellName) {

        for(S57Catalog cell : cellList) {
            if(S57ChartFile.getBaseName(cellName).equalsIgnoreCase(cell.getName()))
                return cell;
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long getCRC(String name) {

        for(S57Catalog cn : cellList) {
            String nm = cn.getFileName();
            if(nm.equals(name))
                return cn.getCRC();
        }

        return 0;
    }

} // end class
