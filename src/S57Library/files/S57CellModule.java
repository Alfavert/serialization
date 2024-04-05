package S57Library.files;

import S57Library.basics.E_DataStructure;
import S57Library.basics.E_VerticalDatum;
import S57Library.fiedsRecords.S57FieldDSID;
import S57Library.fiedsRecords.S57FieldDSPM;
import S57Library.fiedsRecords.S57FieldDSSI;
import S57Library.objects.S57ObjectsMap;
import S57Library.objects.S57ObjectsVector;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class S57CellModule {
    //
    // ////////////////   DSID Data //////////////////////////
    //
    private   String intendedUsage;            // INTU
    protected String dataSetName;              // Data set name  DSNM
    private   String editionNumber;            // Edition number EDTN
    private   String updateNumber;             // Update number  UPDN
    private   String updateDate;               // Update date   UADT
    private   String issueDate;                // Issue date    ISDT
    //
    // ////////////////   DSSI Data //////////////////////////
    //
    private E_DataStructure dataStructure;
    private int lexicalLevel;
    private int nationalLexicalLevel;
    public int numberOfMetaRecords;
    public int numberOfCartographicRecords;
    public int numberOfGeoRecords;
    public int numberOfCollectionrecords;
    public int numberOfEdgeRecords;
    public int numberOfFaceRecords;
    public int numberOfIsolatedNodes;
    public int numberOfConnectedNodes;
    //
    // ////////////////   DSPM Data //////////////////////////
    //
    private E_VerticalDatum verticalDatum;
    protected double coordinateMultiplicationFactor;
    protected double soundingMultiplicationFactor;
    protected int    scale;

    protected S57ObjectsMap    vectors;
    protected S57ObjectsVector features;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57CellModule() {
        super();

        vectors          = new S57ObjectsMap();
        features         = new S57ObjectsVector();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57ObjectsMap getSpatial() {
        return vectors;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57ObjectsVector getFeatures() {
        return features;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * Update the record with informations found in the DSID field.
     *see IHO S-57 section 6.3.2.1 for more details on DSID
     * @param dsid a DSID field
     */
    public void setDSID(S57FieldDSID dsid) {
        setIntendedUsage(dsid.intendedUsage.name());
        setDataSetName(dsid.datasetName);

        setEditionNumber(dsid.editionNumber);
        setUpdateNumber(dsid.updateNumber);
        setUpdateDate(dsid.updateApplicationDate);
        setIssueDate(dsid.issueDate);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * Update the record with informations found in the DSPM field.<br>
     *see IHO S-57 section 6.3.2.3 for more details on DSPM
     * @param dspm a DSPM field
     */
    public void setDSPM(S57FieldDSPM dspm) {
        verticalDatum = E_VerticalDatum.byCode(dspm.verticalDatum);

        setCoordinateMultiplicationFactor((double)(1 / (double)dspm.coordinateMultiplicationFactor) );
        setSoundingMultiplicationFactor( (double) (1 / (double)dspm.soundingMultiplicationFactor) );
        setScale(dspm.compilationScaleOfData);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
     * Update the record with informations found in the DSSI field.<br>
     *see IHO S-57 section 7.3.1.2 for more details on DSSI
     * @param dssi a DSSI field
     */
    public void setDSSI(S57FieldDSSI dssi) {
        setDataStructure(E_DataStructure.byCode(dssi.dataStructure));
        setLexicalLevel(dssi.attfLexicalLevel);
        setNationalLexicalLevel(dssi.nationalLexicalLevel);

        numberOfMetaRecords         = dssi.numberOfMetaRecords;
        numberOfCartographicRecords = dssi.numberOfCartographicRecords;
        numberOfGeoRecords          = dssi.numberOfGeoRecords;
        numberOfCollectionrecords   = dssi.numberOfCollectionrecords;
        numberOfIsolatedNodes       = dssi.numberOfIsolatedNodes;
        numberOfConnectedNodes      = dssi.numberOfConnectedNodes;
        numberOfEdgeRecords         = dssi.numberOfEdgeRecords;
        numberOfFaceRecords         = dssi.numberOfFaceRecords;
    }

    public String getIntendedUsage() {
        return intendedUsage;
    }

    public void setIntendedUsage(String intendedUsage) {
        this.intendedUsage = intendedUsage;
    }

    public String getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(String editionNumber) {
        this.editionNumber = editionNumber;
    }

    public String getUpdateNumber() {
        return updateNumber;
    }

    public void setUpdateNumber(String updateNumber) {
        this.updateNumber = updateNumber;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public E_DataStructure getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(E_DataStructure dataStructure) {
        this.dataStructure = dataStructure;
    }

    public int getLexicalLevel() {
        return lexicalLevel;
    }

    public void setLexicalLevel(int lexicalLevel) {
        this.lexicalLevel = lexicalLevel;
    }

    public int getNationalLexicalLevel() {
        return nationalLexicalLevel;
    }

    public void setNationalLexicalLevel(int nationalLexicalLevel) {
        this.nationalLexicalLevel = nationalLexicalLevel;
    }

    public E_VerticalDatum getVerticalDatum() {
        return verticalDatum;
    }

    public void setVerticalDatum(E_VerticalDatum verticalDatum) {
        this.verticalDatum = verticalDatum;
    }

    public double getCoordinateMultiplicationFactor() {
        return coordinateMultiplicationFactor;
    }

    public void setCoordinateMultiplicationFactor(double coordinateMultiplicationFactor) {
        this.coordinateMultiplicationFactor = coordinateMultiplicationFactor;
    }

    public double getSoundingMultiplicationFactor() {
        return soundingMultiplicationFactor;
    }

    public void setSoundingMultiplicationFactor(double soundingMultiplicationFactor) {
        this.soundingMultiplicationFactor = soundingMultiplicationFactor;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getUpdateIntNumber() {
       return Integer.parseInt(getUpdateNumber());
    }
} // end class
