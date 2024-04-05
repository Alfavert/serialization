package S57Library.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class S57ObjectsVector extends ArrayList<S57Object> {
	int lastAreaIdx = 0;

	public S57ObjectsVector(){
		super(100);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public S57ObjectsVector(int initialSize) {
		super(initialSize);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean add(S57Object o) {

		if(o instanceof S57Feature) {
			S57Feature fo = (S57Feature) o;
			if(fo.objectPrimitiveType == E_S57ObjectPrimitiveType.area) {
				this.add(lastAreaIdx,o);
				lastAreaIdx++;
				return true;
			}
		}

		return super.add(o);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public S57Object searchByCode(long longName) {
		for (S57Object o : this){
			if (o.name == longName){
				return o;
			}
		}
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public S57Object searchByLongIdentifier(long wwi) {
		for (S57Object o : this) {
			if (o instanceof S57Feature){
				S57Feature f = (S57Feature) o;
				if (f.worldWideIdentifier == wwi){
					return f;
				}
			}
		}
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String toString(){
		String res = "[";
		int bcl = 0;
		for (S57Object o : this){			
			bcl++;
			res += o.toStringShort() + (bcl < this.size()? ";" :"]");
		}
		return res;
	}

}
