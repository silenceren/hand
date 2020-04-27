package Map;

import java.util.HashMap;
import java.util.Map;

public class TestMap {
    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0 ; i < 10 ;  i ++){
            map.put(i,i);
        }
        for(Integer key : map.keySet()){
            System.out.println(map.get(key));
        }
    }


}
