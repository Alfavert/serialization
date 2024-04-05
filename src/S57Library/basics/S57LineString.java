package S57Library.basics;

import S57Library.basics.S57Pos2D;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class S57LineString implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<S57Pos2D> positions;

    public S57LineString() {
        this.positions = new ArrayList<S57Pos2D>();
    }

    public S57LineString( List<S57Pos2D> newPositions ) {
        this.positions = new ArrayList<S57Pos2D>();
        this.positions.addAll(newPositions);
    }

    public List<S57Pos2D> getPositions() {
        return positions;
    }
    public void           setPositions(List<S57Pos2D> positions) {
        this.positions = positions;
    }
    public void     addGeoPoint(S57Pos2D point) { positions.add(point); }
    public int      size()                      { return positions.size(); }
    public S57Pos2D get(int idx)                { return positions.get(idx); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static S57LineString getSimplified(S57LineString allPoints) {
        S57Pos2D[] ptArray = allPoints.positions.toArray(new S57Pos2D[0]);
        Simplify<S57Pos2D> simplify = new Simplify<S57Pos2D>(new S57Pos2D[0]);

        S57Pos2D[] lessPoints = simplify.simplify(ptArray, 0.0008, false);

        return new S57LineString(Arrays.asList(lessPoints));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57Pos2D getLineCenter() {
        S57Pos2D segmentStart;
        S57Pos2D segmentEnd;

        int points = positions.size();
        if(points == 0) {
            return null;
        }

        if(points % 2 == 0) {
            // Even number
            segmentStart = positions.get((points/2) - 1);
            segmentEnd   = positions.get( (points/2)  );
            return getMidpoint(segmentStart, segmentEnd);
        }
        else {
            // Odd number
            return new S57Pos2D(positions.get(points/2));
        }
    }

    private S57Pos2D getMidpoint(S57Pos2D start, S57Pos2D end) {
        double x = (start.getX() + end.getX()) / 2;
        double y = (start.getY() + end.getY()) / 2;
    return new S57Pos2D(y, x);
    }





} // end class
