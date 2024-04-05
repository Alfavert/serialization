package S57Library.objects;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.util.Vector;

import S57Library.basics.Link;
import S57Library.basics.PositionsVector;
import S57Library.basics.S57Pos2D;
import S57Library.basics.S57Pos3D;
import S57Library.fiedsRecords.S57FieldVRID;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class S57Spatial extends S57Object {
	public PositionsVector positions;
	public S57Pos2D        coordinate;

	public S57UpdateControl updateControl = null;
	public int recordVersion;
	public int updateInstruction;


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String toString() {
		return "Spatial " + getClass();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addPositions(PositionsVector listOfPos) {
		if (positions == null) {
			positions = listOfPos;
			if (positions.size() > 0){
				this.coordinate = (S57Pos2D) positions.get(0);
			}
		}else{
			if (listOfPos != null) {
				positions.addAll(listOfPos);				
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean hasPositions() {
		return positions != null && positions.size() > 0;
	}

	public boolean hasDepths() {
		return positions != null && positions.size()>0 && positions.get(0) instanceof S57Pos3D;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static S57Object buildFromVRID(S57FieldVRID vrid) {
		S57Spatial res = null;

		if (vrid.recordType != null){
			res = switch (vrid.recordType) {
				case vectorIsolatedNode ->  new S57IsolatedNode();
				case vectorConnectedNode -> new S57ConnectedNode();
				case vectorEdge ->          new S57Edge();
				default ->                  new S57Spatial();
			};
		}else{
			res = new S57Spatial();			
		}

		res.name = vrid.name;
		res.type = vrid.recordName;
		res.recordVersion     = vrid.recordVersion;
		res.updateInstruction = vrid.recordUpdateInstruction;

		return res;
	}
}
