package S57Library.objects;

import S57Library.fiedsRecords.S57FieldFFPC;
import S57Library.fiedsRecords.S57FieldFSPC;
import S57Library.fiedsRecords.S57FieldSGCC;
import S57Library.fiedsRecords.S57FieldVRPC;

public class S57UpdateControl {
    private String tag;
    private int command;
    private int index;
    private int number;
    // Default constructor
    public S57UpdateControl() {}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57UpdateControl(S57FieldFSPC fspc) {
        tag     = fspc.getTag();
        command = fspc.fsui;
        index   = fspc.fsix;
        number  = fspc.nspt;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57UpdateControl(S57FieldFFPC ffpc) {
        tag     = ffpc.getTag();
        command = ffpc.ffui;
        index   = ffpc.ffix;
        number  = ffpc.nfpt;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57UpdateControl(S57FieldVRPC vrpc) {
        tag     = vrpc.getTag();
        command = vrpc.vpui;
        index   = vrpc.vpix;
        number  = vrpc.nvpt;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57UpdateControl(S57FieldSGCC sgcc) {
        tag     = sgcc.getTag();
        command = sgcc.ccui;
        index   = sgcc.ccix;
        number  = sgcc.ccnc;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String toString(){
        return "[" + tag + " cmd: " + command + " idx: " + index + " num: " + number;
    }

    public String getTag() {
        return tag;
    }

    public int getCommand() {
        return command;
    }

    public int getIndex() {
        return index;
    }

    public int getNumber() {
        return number;
    }
} // end class
