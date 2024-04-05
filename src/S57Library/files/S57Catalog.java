package S57Library.files;

import java.util.ArrayList;

public class S57Catalog {
    private String volumeName;
    private String comment;
    private double southLat;
    private double westLon;
    private double northLat;
    private double eastLon;
    private S57ChartFile            cellFile = null;
    private ArrayList<S57ChartFile> updatetList;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57Catalog() {
        updatetList = new ArrayList<S57ChartFile>();
    }

    public void setCellFile(S57ChartFile cellFile) {
        this.cellFile = cellFile;
    }

    public long getCRC() {
        if(cellFile != null)
            return cellFile.getCRC();
        return 0;
    }

    public double getSouthLat() {
        return southLat;
    }

    public void setSouthLat(double southLat) {
        this.southLat = southLat;
    }

    public double getWestLon() {
        return westLon;
    }

    public void setWestLon(double westLon) {
        this.westLon = westLon;
    }

    public double getNorthLat() {
        return northLat;
    }

    public void setNorthLat(double northLat) {
        this.northLat = northLat;
    }

    public double getEastLon() {
        return eastLon;
    }

    public void setEastLon(double eastLon) {
        this.eastLon = eastLon;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFileName() {
        return cellFile.getFileName();
    }

    public String getFullPath() {
        return cellFile.getFullPath();
    }

    public String getCrcCode() {
        return cellFile.getCrcCode();
    }

    public String getExtension() {
        return cellFile.getExtension();
    }

    public String getName() {
        return cellFile.getName();
    }

    public ArrayList<S57ChartFile> getUpdatetList() {
        return updatetList;
    }

    public void addUpdate(S57ChartFile file) {
        updatetList.add(file);
    }
} // end class
