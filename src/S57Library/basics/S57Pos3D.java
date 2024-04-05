package S57Library.basics;

import java.io.Serial;
import java.io.Serializable;

/**
 * class used to store 3D position for S-57 charts format.
 * @author crosay 2010
 */
public class S57Pos3D extends S57Pos2D implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	public double depth = 0;
	
	/**
	 * instantiate a pos3D with longitude and latitude set.
	 * depth is set to 0
	 * @param longitude
	 * @param latitude
	 */
	public S57Pos3D(double latitude, double longitude) {
		super(latitude, longitude);
		depth = 0;
	}

	public S57Pos3D(double latitude, double longitude, double depth){
		super(latitude, longitude);
		this.depth = depth;
	}

	public String toString(){
		return super.toString() + String.format("%fm", depth);
	}
}
