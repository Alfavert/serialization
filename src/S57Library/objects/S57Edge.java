package S57Library.objects;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.util.Vector;

import S57Library.basics.Link;
import S57Library.basics.S57Pos2D;

/*
 * A one-dimensional spatial object, located by two or more coordinate pairs (or
 * two connected nodes) and optional interpolation parameters. If the parameters
 * are missing, the interpolation is defaulted to straight line segments between the
 * coordinate pairs. In the chain-node, planar graph and full topology data
 * structures, an edge must reference a connected node at both ends and must
 * not reference any other nodes
 */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class S57Edge extends S57Spatial {
	public S57ConnectedNode begin;
	public S57ConnectedNode end;

	public S57Edge() {
	}

	public boolean isValid() {
		return (begin != null) && (end != null);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addVectors(Vector links, S57ObjectsMap vectors) {

		for (Object obj : links) {
			Link lnk = (Link) obj;

			if (lnk.isBeginingNode()) {
				begin = (S57ConnectedNode) vectors.searchByCode(lnk.name);

				if (begin == null) {
					System.out.println("Invalid vector - begin = null ");
				}
			} else if (lnk.isEndNode()) {
				end = (S57ConnectedNode) vectors.searchByCode(lnk.name);

				if (end == null) {
					System.out.println("Invalid vector - end = null ");
				}
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String toString() {
		return "Edge " + getClass();
	}
} // end class
