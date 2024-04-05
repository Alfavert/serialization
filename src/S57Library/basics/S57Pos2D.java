package S57Library.basics;

import java.io.Serial;
import java.io.Serializable;

import static java.lang.Math.*;


public class S57Pos2D implements Cloneable, S57Pos2DProvider, Point, Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	public  static final double PI                      = Math.PI;
	public  static final double RADIANS                 = PI / 180.0;

	public  static final double EPSILON                 = 1.0E-7;

	public  static final double MAX_LATITUDE            = 90.0;
	public  static final double MIN_LATITUDE            = -90.0;

	public  static final double MAX_LONGITUDE           = 180.0;
	public  static final double MIN_LONGITUDE           = -180.0;
	public double   longitude = 0;
	public double   latitude  = 0;
	private boolean immutable = false;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 *** Constructor.
	 *** This creates a GeoPoint with latitude=0.0, and longitude=0.0
	 **/
	public S57Pos2D()
	{
		super();
		this.latitude  = 0.0;
		this.longitude = 0.0;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 *** Copy Constructor.
	 *** This copies the specified argument GeoPoint to this constructed GeoPoint
	 *** @param gp  The GeoPoint to copy to this constructed GeoPoint
	 **/
	public S57Pos2D(S57Pos2D gp)
	{
		this();
		this.setLatitude(gp.getLatitude());
		this.setLongitude(gp.getLongitude());
		// Note: does not clone "immutability"
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 *** Constructor.
	 *** This creates a new GeoPoint with the specified latitude/longitude.
	 *** @param latitude  The latitude
	 *** @param longitude The longitude
	 **/
	public S57Pos2D(double latitude, double longitude)
	{
		this();
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 *** Constructor.
	 *** This creates a new GeoPoint with the specified latitude/longitude.
	 *** @param latDeg    The latitude degrees
	 *** @param latMin    The latitude minutes
	 *** @param latSec    The latitude seconds
	 *** @param lonDeg    The longitude degrees
	 *** @param lonMin    The longitude minutes
	 *** @param lonSec    The longitude seconds
	 **/
	public S57Pos2D(
			double latDeg, double latMin, double latSec,
			double lonDeg, double lonMin, double lonSec)
	{
		this();
		this.setLatitude( latDeg, latMin, latSec);
		this.setLongitude(lonDeg, lonMin, lonSec);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setLatitude(double deg, double min, double sec)
	{
		this.setLatitude(S57Pos2D.convertDmsToDec(deg, min, sec));
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setLatitude(double lat)
	{
		// immutable?
		if (this.isImmutable()) {
			System.out.println("This GeoPoint is immutable, changing Latitude denied!");
		} else {
			this.latitude = lat;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setLongitude(double deg, double min, double sec)
	{
		this.setLongitude(S57Pos2D.convertDmsToDec(deg, min, sec));
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setLongitude(double lon)
	{
		if (this.isImmutable()) {
			System.out.println("This GeoPoint is immutable, changing Longitude denied!");
		} else {
			this.longitude = lon;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static double convertDmsToDec(int deg, int min, int sec)
	{
		return S57Pos2D.convertDmsToDec((double)deg, (double)min, (double)sec);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static double convertDmsToDec(double deg, double min, double sec)
	{
		double sign = (deg >= 0.0)? 1.0 : -1.0;
		double d = Math.abs(deg);
		double m = Math.abs(min / 60.0);
		double s = Math.abs(sec / 3600.0);
		return sign * (d + m + s);
	}
	public Object clone()
	{
		return new S57Pos2D(this); // does NOT clone immutability
	}

	public S57Pos2D setImmutable()
	{
		this.immutable = true;
		return this; // to allow chaining
	}

	public boolean isImmutable()
	{
		return this.immutable;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// *** GeoPointProvider interface inplementation
	public S57Pos2D getGeoPoint()
	{
		return this;
	}

	public boolean isValid()
	{
		return S57Pos2D.isValid(this.getLatitude(), this.getLongitude());
	}

	public double getLatitude()
	{
		return this.latitude;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public double getLatitudeRadians()
	{
		return this.getLatitude() * RADIANS;
	}

	public double getLongitude()
	{
		return this.longitude;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 *** Returns true if this GeoPoint is equivalent to the other Object
	 *** @return True if this GeoPoint is equivalent to the other Object
	 **/
	public boolean equals(Object other)
	{
		if (other instanceof S57Pos2D) {
			S57Pos2D gp = (S57Pos2D)other;
			double deltaLat = Math.abs(gp.getLatitude()  - this.getLatitude() );
			double deltaLon = Math.abs(gp.getLongitude() - this.getLongitude());
			return ((deltaLat < EPSILON) && (deltaLon < EPSILON));
		} else {
			return false;
		}
	}

	public double getLongitudeRadians()
	{
		return this.getLongitude() * RADIANS;
	}

	public String toString(){
		return String.format("%s%f/%s%f", (longitude>0?"E" : "W"), longitude, (latitude>0?"N" : "S"), latitude);
	}

	public  static final S57Pos2D INVALID_GEOPOINT  = new S57Pos2D(0.0,0.0).setImmutable();

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean isOrigin(S57Pos2D gp)
	{
		if (gp == null) {
			return false;
		} else {
			double latAbs = Math.abs(gp.getLatitude());
			double lonAbs = Math.abs(gp.getLongitude());
			if ((latAbs <= 0.0001) && (lonAbs <= 0.0001)) {
				// small square off the coast of Africa (Ghana)
				return true;
			} else {
				return false;
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean isOrigin(double lat, double lon)
	{
		double latAbs = Math.abs(lat);
		double lonAbs = Math.abs(lon);
		if ((latAbs <= 0.0001) && (lonAbs <= 0.0001)) {
			// small square off the coast of Africa (Ghana)
			return true;
		} else {
			return false;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 *** @return True if the specified latitude/longitude is within valid bounds, false otherwise
	 **/
	public static boolean isValidBounds(double lat, double lon)
	{
		if ((lat >= MAX_LATITUDE) || (lat <= -MAX_LATITUDE)) {
			// invalid latitude
			return false;
		} else
		if ((lon >= MAX_LONGITUDE) || (lon <= -MAX_LONGITUDE)) {
			// invalid longitude
			return false;
		} else {
			return true;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean isValid(double lat, double lon)
	{
		double latAbs = Math.abs(lat);
		double lonAbs = Math.abs(lon);
		if (latAbs >= MAX_LATITUDE) {
			// invalid latitude
			return false;
		} else
		if (lonAbs >= MAX_LONGITUDE) {
			// invalid longitude
			return false;
		} else
		if ((latAbs <= 0.0001) && (lonAbs <= 0.0001)) {
			// small square off the coast of Africa (Ghana)
			return false;
		} else {
			return true;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean isValid(S57Pos2D gp)
	{
		return (gp != null)? gp.isValid() : false;
	}

	/**
	 *** Returns true if the specified GeoPointProvider is valid, false otherwise
	 *** @param gpp  The GeoPointProvider
	 *** @return True if the specified GeoPointProvider is valid, false otherwise
	 **/
	public static boolean isValid(S57Pos2DProvider gpp)
	{
		return (gpp != null)? gpp.getGeoPoint().isValid() : false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public double getX() {
		return this.longitude;
	}

	@Override
	public double getY() {
		return this.latitude;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public S57Pos2D normalizeLongitude() {
		return new S57Pos2D(latitude, to180(longitude));
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static double to180(double d) {
		if (d < 0)
			return -to180(abs(d));
		else {
			if (d > 180) {
				long n = round(floor((d + 180) / 360.0));
				return d - n * 360;
			} else
				return d;
		}
	}

	} // end class ////////////////////////////////////////////////////////////////////////////////////////////////////////

