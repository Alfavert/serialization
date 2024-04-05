package S57Library.objects;


import java.util.HashMap;
import java.util.Map;

public class S57ObjectsMap {
    public  Map<Long, S57Object> objMap;


    public S57ObjectsMap () {
        objMap = new HashMap<>(100);

    }

    public S57ObjectsMap (int initialSize) {
        objMap = new HashMap<>(initialSize);

    }

    public S57Object add(S57Object o){

        return objMap.put((long) o.name, o);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public S57Object searchByCode(long longName) {
        return objMap.get(longName);
    }

    public S57Object remove(long key) {
        return objMap.remove(key);
    }

} // end class
