package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class Test01 {
	
	public static void main(String[] args) {
		Map<Integer, String> map = new LinkedHashMap<>();
		map.put(1, "aaa");
		map.put(6, "bbb");
		map.put(12, "ccc");
		map.put(3, "ddd");
		map.put(7, "eee");
		map.put(5, "fff");
		
		System.out.println(map);
		
		Set<Integer> keySet = map.keySet();
		System.out.println(keySet);
		System.out.println(Arrays.toString(keySet.toArray(new Integer[1])));
	}
}
