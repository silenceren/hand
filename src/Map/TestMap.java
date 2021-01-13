package Map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HashMap遍历
 * HashMap遍历从大的方向来说可分为以下4类
 *   1.迭代器（Iterator）方式遍历
 *   2.For Each方式遍历
 *   3.Lambda表达式遍历（JDK1.8+）
 *   4.Streams API遍历（JDK1.8+）
 */

public class TestMap {

    public static void main(String[] args) {

        Map<Integer,String> map = new HashMap<Integer, String>();

        for (int i = 0 ; i < 10 ;  i ++){
            map.put(i, "test" + i);
        }

        TestMap test = new TestMap();
        test.iteratorEntrySet(map);

    }

    //迭代器EntrySet
    private void iteratorEntrySet(Map<Integer, String> map) {

        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    //迭代器KeySet
    private void iteratorKeySet(Map<Integer, String> map) {

        Iterator<Integer> iterator = map.keySet().iterator();

        while (iterator.hasNext()) {
            Integer key = iterator.next();
            System.out.println(key);
            System.out.println(map.get(key));
        }
    }

    //ForEach EntrySet
    private void forEachEntrySet(Map<Integer, String> map) {
        for (Map.Entry<Integer, String> entry: map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    //ForEach KeySet
    private void forEachKeySet(Map<Integer, String> map) {
        for (Integer key: map.keySet()) {
            System.out.println(key);
            System.out.println(map.get(key));
        }
    }

    //lambda
    private void lambda (Map<Integer, String> map) {
        map.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });
    }

    //Streams API单线程
    private void streamsAPI(Map<Integer, String> map) {
        map.entrySet().stream().forEach((entry) -> {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        });
    }

    //Streams API多线程
    private void parallelStreamAPI(Map<Integer, String> map) {
        map.entrySet().parallelStream().forEach((entry) -> {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        });
    }

}
