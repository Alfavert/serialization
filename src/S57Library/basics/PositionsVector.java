/**
 * 
 */
package S57Library.basics;

import java.util.ArrayList;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class PositionsVector extends ArrayList<S57Pos2D> {
	private static final int MAX_PRINT = 20;

	public boolean add(S57Pos2D pos){
		return super.add(pos);
	}
	public void addAll(PositionsVector otherVector){
		super.addAll(otherVector);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String toString(){
		String res = "";
		int nbPrint = 0;

		for (S57Pos2D pos : this){
			res += pos.toString() + ";";

			if (nbPrint >= MAX_PRINT){
				res += ("...(" + this.size()+")");			
			}			
		}

		return res;
	}

} // end class
