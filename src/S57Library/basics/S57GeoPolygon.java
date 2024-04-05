package S57Library.basics;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

/**
 *** A container for a polygon composed of GeoPoints
 **/
public class S57GeoPolygon implements Cloneable, Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;
    private String                 name        = null; // optional
    private S57Pos2D[]             boundary    = null;

    private Vector<S57GeoPolygon>  negRings    = null;

    /**
     *** Empty constructor
     **/
    private S57GeoPolygon()
    {
        super();
    }

    /**
     *** Point constructor
     *** @param gp A list of GeoPoints
     **/
    public S57GeoPolygon(S57Pos2D... gp)
    {
        super();
        this.boundary = gp;
    }

    /**
     *** Point constructor
     *** @param gpp A list of GeoPointProviders
     **/
    public S57GeoPolygon(S57Pos2DProvider... gpp)
    {
        super();
        if (gpp != null) {
            this.boundary = new S57Pos2D[gpp.length];
            for (int i = 0; i < this.boundary.length; i++) {
                this.boundary[i] = gpp[i].getGeoPoint();
            }
        } else {
            this.boundary = null;
        }
    }

    /**
     *** Point constructor
     *** @param gpList A list of GeoPoints
     **/
    public S57GeoPolygon(java.util.List<S57Pos2D> gpList)
    {
        super();
        this.boundary = toArray(gpList, S57Pos2D.class);
    }

    /**
     *** Point constructor
     *** @param gp An array of lattitude/longitude pairs
     **/
    public S57GeoPolygon(float[][] gp)
    {
        super();
        this.boundary = new S57Pos2D[gp.length];
        for (int i = 0; i < gp.length; i++) {
            this.boundary[i] = new S57Pos2D((double)gp[i][0],(double)gp[i][1]);
        }
    }

    /**
     *** Point constructor
     *** @param gp An array of lattitude/longitude pairs
     **/
    public S57GeoPolygon(double[][] gp)
    {
        super();
        this.boundary = new S57Pos2D[gp.length];
        for (int i = 0; i < gp.length; i++) {
            this.boundary[i] = new S57Pos2D(gp[i][0],gp[i][1]);
        }
    }

    /**
     *** Name/point constructor
     *** @param name The name of the polygon
     *** @param gp A list of GeoPoints
     **/
    public S57GeoPolygon(String name, S57Pos2D... gp)
    {
        this(gp);
        this.name = name;
        // closed
    }

    /**
     *** Name/point constructor
     *** @param name The name of the polygon
     *** @param gpList A list of GeoPoints
     **/
    public S57GeoPolygon(String name, java.util.List<S57Pos2D> gpList)
    {
        this(gpList);
        this.name = name;
        // closed
    }

    /**
     *** Name/point constructor
     *** @param name The name of the polygon
     *** @param gp An array of lattitude/longitude pairs
     **/
    public S57GeoPolygon(String name, float[][] gp)
    {
        this(gp);
        this.name = name;
        // closed
    }

    /**
     *** Name/point constructor
     *** @param name The name of the polygon
     *** @param gp An array of lattitude/longitude pairs
     **/
    public S57GeoPolygon(String name, double[][] gp)
    {
        this(gp);
        this.name = name;
        // closed
    }

    /**
     *** Copy constructor
     **/
    public S57GeoPolygon(S57GeoPolygon other)
    {
        super();
        if (other != null) {
            this.name     = other.getName();
            this.boundary = other.getGeoPoints();
        }
    }

    /**
     *** Gets the name of the polygon
     *** @return The name of the polygon
     **/
    public String getName()
    {
        return this.name;
    }

    /**
     *** Sets the name of the polygon
     *** @param name The name of the polygon
     **/
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     *** Return true if this polygon is empty
     *** @return True if the polygon is empty
     **/
    public boolean isEmpty()
    {
        return isEmpty(this.boundary);
    }

    /**
     *** Return true if this polygon is valid
     *** @return True if the polygon is valid
     **/
    public boolean isValid()
    {
        if (this.boundary == null) {
            return false;
        } else
        if (S57GeoPolygon.isClosed(this.boundary)) {
            return (this.boundary.length >= 4);
        } else {
            return (this.boundary.length >= 3);
        }
    }

    /**
     *** Return the array of GeoPoints from the specified GeoPolygon
     *** @param geoPoly  The GeoPolygon
     *** @return The list of contained GeoPoints
     **/
    public static S57Pos2D[] getGeoPoints(S57GeoPolygon geoPoly)
    {
        return (geoPoly != null)? geoPoly.getGeoPoints() : null;
    }

    /**
     *** Return the array of points that make up the polygon
     *** @return The array of points that make up the polygon
     **/
    public S57Pos2D[] getGeoPoints()
    {
        return this.boundary;
    }

    public List<S57Pos2D> getPointList() {

        return Arrays.asList(this.boundary);
    }


    /**
     *** Return the GeoPoint at the specified index
     *** @param gpi  The GeoPoint index
     *** @return The GeoPoint at the specified index
     **/
    public S57Pos2D getGeoPoint(int gpi)
    {
        if (this.boundary == null) {
            return null;
        } else
        if ((gpi < 0) || (gpi >= this.boundary.length)) {
            return null;
        } else {
            return this.boundary[gpi];
        }
    }

    /**
     *** Insert point into polygon
     *** @param gp The point to insert into the polygon
     *** @param ndx The index to insert the point at
     **/
    public void insertGeoPoint(S57Pos2D gp, int ndx)
    {
        if (gp == null) {
            // ignore
        } else
        if (!gp.isValid()) {
            System.out.println("Invalid GeoPoint: " + gp);
        } else
        if (this.boundary == null) {
            // ignore index
            this.boundary = new S57Pos2D[] { gp };
        } else {
            this.boundary = insert(this.boundary, gp, ndx);
        }
    }

    /**
     *** Add point to polygon
     *** @param gp The point to add to the polygon
     **/
    public void addGeoPoint(S57Pos2D gp)
    {
        if (gp == null) {
            // ignore
        } else
        if (!gp.isValid()) {
            System.out.println("Invalid GeoPoint: " + gp);
        } else
        if (this.boundary == null) {
            this.boundary = new S57Pos2D[] { gp };
        } else {
            this.boundary = insert(this.boundary, gp, -1);
        }
    }

    /**
     *** Returns true if the specified points represent a closed polygon
     *** @param gp The set of points representing a polygon
     *** @return True if the represented polygon is closed
     **/
    public static boolean isClosed(S57Pos2D[] gp)
    {
        if (gp == null) {
            // no points
            return false;
        } else
        if (gp.length < 3) {
            // below minimum points for a closed polygon
            return false;
        } else {
            // first point equals last point?
            S57Pos2D gp0 = gp[0];
            S57Pos2D gpN = gp[gp.length - 1];
            return gp0.equals(gpN);
        }
    }

    /**
     *** Returns true if this polygon is closed
     *** @return True if this polygon is closed
     **/
    public boolean isClosed()
    {
        return S57GeoPolygon.isClosed(this.boundary);
    }

    /**
     *** Closes the polygon represented by the list of points
     *** @return A closed polygon
     **/
    public static S57Pos2D[] closePolygon(S57Pos2D[] gp)
    {
        if (isEmpty(gp)) {
            // null/empty, return as-is
            return gp;
        } else
        if (gp.length < 3) {
            // invalid number of points, return as-is
            return gp;
        } else {
            S57Pos2D gp0 = gp[0];
            S57Pos2D gpN = gp[gp.length - 1];
            if (gp0.equals(gpN)) {
                // already closed
                return gp;
            } else {
                // close and return new array
                return add(gp, gp0);
            }
        }
    }

    /**
     *** Closes this GeoPolygon (making sure last point is equal to first point)
     *** @return True if able to close GeoPolygon, false otherwise
     **/
    public boolean closePolygon()
    {
        if ((this.boundary != null) && (this.boundary.length >= 3)) {
             this.boundary = S57GeoPolygon.closePolygon(this.boundary);
            return true;
        } else {
            return false;
        }
    }

    /**
     *** Return the number of points in the polygon
     *** @return The number of points in the polygon
     **/
    public int getSize()
    {
        return (this.boundary != null)? this.boundary.length : 0;
    }

    /**
     *** @see #getSize()
     **/
    public int size()
    {
        return this.getSize();
    }

    /**
     *** Returns true if the specified point is inside the polygon,
     *** same as <code>isPointInside()</code>
     *** @param gp The point to check if is inside the polygon
     *** @return True if the specified point is inside the polygon
     **/
    public boolean containsPoint(S57Pos2D gp)
    {
        return S57GeoPolygon.isPointInside(gp, this.getGeoPoints());
    }

    /**
     *** Returns true if the specified point is inside the polygon
     *** @param gp The point to check if is inside the polygon
     *** @return True if the specified point is inside the polygon
     *** Exclude polygon holes
     **/
    public boolean isPointInside(S57Pos2D gp)
    {
        if(S57GeoPolygon.isPointInside(gp, this.getGeoPoints())) {

            List<S57GeoPolygon> rings = this.getNegativeRings();
            if(rings != null) {
                for(S57GeoPolygon hole : rings) {
                    if(hole.containsPoint(gp))
                        return false;
                }
            }

        return true;
        }

    return false;
    }

    /**
     *** Returns true if the specified point is inside the polygon formed by
     *** a specified list of points, same as <code>isPointInside()</code>  <br>
     *** NOTE: The list of points MUST represent a <strong>closed</strong> polygon.
     *** @param gp The point to check if is inside the polygon
     *** @return True if the specified point is inside the polygon
     **/
    public static boolean containsPoint(S57Pos2D gp, S57Pos2D... pp)
    {
        return S57GeoPolygon.isPointInside(gp, pp);
    }

    /**
     *** Returns true if the specified point is inside the polygon formed by
     *** a specified list of points <br>
     *** NOTE: The list of points MUST represent a <strong>closed</strong> polygon.
     *** @param gp The point to check if is inside the polygon
     *** @return True if the specified point is inside the polygon
     **/
    public static boolean isPointInside(S57Pos2D gp, S57Pos2D... pp)
    {

        /* quick argument validation */
        if ((gp == null) || (pp == null)) {
            return false;
        }

        /* close polygon (make sure last point is same as first) */
        pp = S57GeoPolygon.closePolygon(pp);

        // Uses "Winding Number" algorithm
        // Notes:
        //  - This is a very simple algorithm that compares the number of downward vectors
        //    with the number of upward vectors surrounding a specified point.
        //  - This algorithm was designed for a 2D plane and will fail for a curved surface
        //    where the distance between points is great.
        // Observations:
        //  - It appears that the state borders may have been defined by simple X/Y cooridinated
        //    based on latitude/longitude values.  The simple cases are states bordered by
        //    constant longitudes or latitudes.
        int wn = 0;                                             // the winding number counter
        for (int i = 0; i < pp.length - 1; i++) {               // edge from V[i] to V[i+1]
            if (pp[i].getY() <= gp.getY()) {                    // start y <= P.y
                if (pp[i+1].getY() > gp.getY()) {               // an upward crossing
                    if (S57GeoPolygon._isLeft(pp[i],pp[i+1],gp) > 0.0) {  // P left of edge
                        ++wn;                                   // have a valid up intersect
                    }
                }
            } else {                                            // start y > P.y (no test needed)
                if (pp[i+1].getY() <= gp.getY()) {              // a downward crossing
                    if (S57GeoPolygon._isLeft(pp[i],pp[i+1],gp) < 0.0) {  // P right of edge
                        --wn;                                   // have a valid down intersect
                    }
                }
            }
        }
        return (wn == 0)? false : true; // wn==0 if point is OUTSIDE
    }

    /**
     *** Tests if the point, <code>gpC</code>, is Left|On|Right of an infinite line
     *** formed by <code>gp0</code> and <code>gp1</code>
     *** @param gp0 First point forming the line
     *** @param gp1 Second point forming the line
     *** @return <ul>
     ****        <li> >0 for gpC left of the line through gp0 and P1</li>
     ***         <li> =0 for gpC on the line</li>
     ***         <li> <0 for gpC right of the line</li>
     **/
    private static double _isLeft(S57Pos2D gp0, S57Pos2D gp1, S57Pos2D gpC)
    {
        double val = (gp1.getX() - gp0.getX()) * (gpC.getY() - gp0.getY()) -
                (gpC.getX() - gp0.getX()) * (gp1.getY() - gp0.getY());
        return val;
    }

    // ------------------------------------------------------------------------
    // ------------------------------------------------------------------------
    // http://local.wasp.uwa.edu.au/~pbourke/geometry/clockwise/index.html
    // http://en.wikipedia.org/wiki/Curve_orientation#Orientation_of_a_simple_polygon
    // http://www.alienryderflex.com/polygon_area/

    /**
     *** Returns true if the points in this polygon are oriented clockwise.
     *** Undeterminate if polygon is invalid
     *** @return True if the polygon is oriented clockwise
     **/
    public boolean isClockwise()
    {
        return S57GeoPolygon.isClockwise(this.getGeoPoints());
    }

    /**
     *** Returns true if the points in this polygon are oriented clockwise.
     *** Indeterminate if polygon is invalid
     *** @param pp  The GeoPoints comprising the polygon
     *** @return True if the polygon is oriented clockwise, false otherwise
     **/
    public static boolean isClockwise(S57Pos2D... pp)
    {
        /* invalid Polygon */
        if ((pp == null) || (pp.length < 3)) {
            return false;
        }

        /* close polygon */
        pp = S57GeoPolygon.closePolygon(pp);

        /* calculate area 'sign' */
        double area = 0.0; // we don't care about the magnitude of this value, only the sign
        for (int i = 0; i < pp.length; i++) {
            int j = (i + 1) % pp.length;
            area += (pp[i].getLongitude() + pp[j].getLongitude()) * (pp[i].getLatitude() - pp[j].getLatitude());
        }

        /* return */
        return (area >= 0.0) ? true : false;
    }

    /**
     *** Shallow copy
     **/
    public Object clone()
    {
        return new S57GeoPolygon(this);
    }

    /**
     *** Returns a String representation of this class.<br>
     *** Currently, this simply includes the number of points this polygon contains
     *** IE. 234(5,10,6)
     *** @return A String representation of this class.
     **/
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(this.size());
        //sb.append(this.isClockwise()?":CW":":CCW");
        if (!isEmpty(this.negRings)) {
            sb.append("(");
            for (int i = 0; i < this.negRings.size(); i++) {
                S57GeoPolygon gp = this.negRings.get(i);
                if (i > 0) { sb.append(","); }
                sb.append(gp.toString());
            }
            sb.append(")");
        }
        return sb.toString();
    }

    /**
     *** Adds an inner polygon which is defines an area which is not part of
     *** the outer polygon.<br>
     *** No validation is performed to ensure that the specified polygon is a
     *** propper inner polygon.
     *** @param geoPolyList  The inner negative rings
     **/
    public void addNegativeRings(Collection<S57GeoPolygon> geoPolyList)
    {
        if (!isEmpty(geoPolyList)) {
            if (this.negRings == null) {
                this.negRings = new Vector<S57GeoPolygon>();
            }
            this.negRings.addAll(geoPolyList);
        }
    }

    /**
     *** Adds an inner polygon which is defines an area which is not part of
     *** the outer polygon.<br>
     *** No validation is performed to ensure that the specified polygon is a
     *** propper inner polygon.
     *** @param geoPoly  The inner negative ring
     **/
    public void addNegativeRing(S57GeoPolygon geoPoly)
    {
        if (geoPoly != null) {
            if (this.negRings == null) {
                this.negRings = new Vector<S57GeoPolygon>();
            }
            this.negRings.add(geoPoly);
        }
    }

    /**
     *** Gets the list of inner negative rings
     *** @return The list of inner negative rings
     **/
    public java.util.List<S57GeoPolygon> getNegativeRings()
    {
        return this.negRings;
    }

    public static <T> boolean isEmpty(T A[])
    {
        return ((A == null) || (A.length == 0));
    }
    public static boolean isEmpty(Collection<?> C)
    {
        return ((C == null) || C.isEmpty());
    }

    public static <T> T[] toArray(Collection<?> list, Class<T> type)
    {
        if (type == null) { type = (Class<T>)Object.class; }
        if (list != null) {
            T array[] = (T[]) Array.newInstance(type, list.size());  // "unchecked cast"
            return list.toArray(array);
        } else {
            return (T[])Array.newInstance(type, 0);            // "unchecked cast"
        }
    }

    public static <T> T[] insert(T[] list, T obj, int index)
    // throws ArrayStoreException
    {
        if (list != null) {
            int ndx = ((index > list.length) || (index < 0))? list.length : index;
            Class type = list.getClass().getComponentType();
            int size = (list.length > ndx)? (list.length + 1) : (ndx + 1);
            T array[] = (T[])Array.newInstance(type, size);  // "unchecked cast"
            if (ndx > 0) {
                int maxLen = (list.length >= ndx)? ndx : list.length;
                System.arraycopy(list, 0, array, 0, maxLen);
            }
            array[ndx] = obj; // <-- may throw ArrayStoreException
            if (ndx < list.length) {
                int maxLen = list.length - ndx;
                System.arraycopy(list, ndx, array, ndx + 1, maxLen);
            }
            return array;
        } else {
            return null;
        }
    }

    public static <T> T[] add(T list[], T obj)
    {
        return insert(list, obj, -1);
    }


    public  S57Pos2D getCentroid() {
        double cx = 0;
        double cy = 0;
        for (int i = 0; i < boundary.length; i++) {
            int j = (i + 1) % boundary.length;
            double n = ((boundary[i].getX() * boundary[j].getY()) - (boundary[j].getX() * boundary[i].getY()));
            cx += (boundary[i].getX() + boundary[j].getX()) * n;
            cy += (boundary[i].getY() + boundary[j].getY()) * n;
        }
        double a = getArea();
        double f = 1 / (a * 6d);
        cx *= f;
        cy *= f;
        return new S57Pos2D(cy, cx);
    }

    /**
     * Gets the area of a closed polygon.
     * The polygon must not be self intersecting, or this gives incorrect results.
     * Algorithm described here: http://local.wasp.uwa.edu.au/~pbourke/geometry/polyarea/
     *
     */
    public double getArea() {
        double a = 0;
        for (int i = 0; i < boundary.length; i++) {
            int j = (i + 1) % boundary.length;
            a += (boundary[i].getX() * boundary[j].getY());
            a -= (boundary[j].getX() * boundary[i].getY());
        }
        a *= 0.5;
        return a;
    }

} // end class
