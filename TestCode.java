package level2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//BeliefState beliefState = new BeliefState();
		//Experience exp00 = new Experience(0,0,0);
		//System.out.println(beliefState.numberOfTriedMap.get(exp00));
		/*
		Map<Integer,Integer> unknowNumberOfTriedMap = new HashMap<Integer,Integer>();
		numberOfTriedMap.put(0, 0);
		numberOfTriedMap.put(1, 1);
		numberOfTriedMap.put(2, 0);
		numberOfTriedMap.put(3, 0);
		numberOfTriedMap.put(4, 0);
		
		//BeliefState bs = new BeliefState();
		for (Integer key : numberOfTriedMap.keySet()){
			int value = (int)numberOfTriedMap.get(key); 
			
			System.out.println("key:"+key+" countNum:"+value);
		}
		
		int q = 1;
		System.out.println("the key value is :"+numberOfTriedMap.get(1));
		*/
		int resultPersistent;
		int resultSporadic;
		
		
		Map<Integer,Integer> experienceMarkedMapInt = new HashMap<Integer,Integer>();
		experienceMarkedMapInt.put(0, 2);
		experienceMarkedMapInt.put(1, 1);
		experienceMarkedMapInt.put(2, 2);
		experienceMarkedMapInt.put(3, 2);
		experienceMarkedMapInt.put(4, 1);
		
		
		Map<Integer,List<Integer>> mapPersistent = new HashMap<Integer, List<Integer>>();
		Map<Integer,List<Integer>> mapSporadic = new HashMap<Integer, List<Integer>>();
		for(Integer key : experienceMarkedMapInt.keySet()) {
			if(experienceMarkedMapInt.get(key) == 2) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(2);
				list.add(0);
				mapPersistent.put(key, list);
			}else{
				List<Integer> list = new ArrayList<Integer>();
				list.add(1);
				list.add(0);
				mapSporadic.put(key, list);
			}
		}
		System.out.println(mapPersistent);
		System.out.println(mapSporadic);
		System.out.println("-----------------------------------------");
		Environment env = new Environment();
		int i = 1;
		for(Integer key : mapSporadic.keySet()) {
			List<Integer> list = mapSporadic.get(key);
			list.remove(1);
			list.add(1, i);
			i++;
		}
		
		List<Integer> compareList = new ArrayList<Integer>();
		for(Integer keySporadic : mapSporadic.keySet()) {
			env.phenomenonLeft = false;
			env.phenomenonRight = false;
			int temp;
			int flagi = 1;
			for(Integer keyPersistent : mapPersistent.keySet()) {
				if(flagi ==1) {
					resultPersistent = env.getResult(keyPersistent);
					temp = resultPersistent;
					resultSporadic = env.getResult(keySporadic);
					resultPersistent = env.getResult(keyPersistent);
					if(temp != resultPersistent) {
						List<Integer> listPersistent = mapPersistent.get(keyPersistent);
						listPersistent.remove(1);
						listPersistent.add(mapSporadic.get(keySporadic).get(1));
						mapPersistent.put(keyPersistent, listPersistent);
						compareList.add(keyPersistent);
					}
					flagi++;
				}else {
					resultPersistent = env.getResult(keyPersistent);
					temp = resultPersistent;
					resultSporadic = env.getResult(keySporadic);
					resultPersistent = env.getResult(keyPersistent);
					if(temp != resultPersistent) {
						if(compareList.contains(keyPersistent)) {
							List<Integer> listPersistent = mapPersistent.get(keyPersistent);
							listPersistent.remove(1);
							listPersistent.add(0);
							mapPersistent.put(keyPersistent, listPersistent);
						}else {
							List<Integer> listPersistent = mapPersistent.get(keyPersistent);
							listPersistent.remove(1);
							listPersistent.add(mapSporadic.get(keySporadic).get(1));
							mapPersistent.put(keyPersistent, listPersistent);
							compareList.add(keyPersistent);
						}
					}
				}
				env.phenomenonLeft = false;
				env.phenomenonRight = false;
			}
		}
		
		
		System.out.println(compareList);
		System.out.println(mapPersistent);
		System.out.println(mapSporadic);
		
		
	}//main

}
