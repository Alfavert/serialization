package S57Library.files;

public class S57ChartFile {

    private final String fileName;
    private final String fullPath;
    private String crcCode = "";
    protected String dataSetName;              // Data set name  DSNM
    private   String editionNumber;            // Edition number EDTN
    private   String updateNumber;             // Update number  UPDN
    private   String updateDate;               // Update date   UADT
    private   String issueDate;                // Issue date    ISDT

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public  S57ChartFile(String name, String path, String crc) {
        fileName = name;
        fullPath = path;
        crcCode  = crc;
    }

    public void getInfoFrom(S57CellModule cell) {
        dataSetName = cell.getDataSetName();
        editionNumber = cell.getEditionNumber();
        updateNumber = cell.getUpdateNumber();
        updateDate = cell.getUpdateDate();
        issueDate = cell.getIssueDate();
    }

    public long getCRC() {
        return Long.parseLong(crcCode, 16);
    }

    public String getFileName() {
        return fileName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getCrcCode() {
        return crcCode;
    }

    public String getExtension() {
        return getFileExtension(fileName);
    }

    public String getName() {
        return getBaseName(fileName);
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public String getEditionNumber() {
        return editionNumber;
    }

    public String getUpdateNumber() {
        return updateNumber;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName must not be null!");
        }

        String extension = "";
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }

        return extension;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String getBaseName(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }


}// end class
