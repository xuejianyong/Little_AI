package level2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeliefState {

	public Map<Experience,Integer> numberOfTriedMap = new HashMap<Experience, Integer>();
	public Map<Integer,List<Integer>> numberofTriedMapInt = new HashMap<Integer,List<Integer>>();
	
	public void initial() {
		
		//this map store and update the experiences and the tried number,
		
		List<Integer> list0 = new ArrayList<Integer>();
		list0.add(0);// untested,persistent,sporadic
		list0.add(0);// tried numbers,
		
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(0);// untested,persistent,sporadic
		list1.add(0);// tried numbers,
		
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(0);// untested,persistent,sporadic
		list2.add(0);// tried numbers,
		
		List<Integer> list3 = new ArrayList<Integer>();
		list3.add(0);// untested,persistent,sporadic
		list3.add(0);// tried numbers,
		
		List<Integer> list4= new ArrayList<Integer>();
		list4.add(0);// untested,persistent,sporadic
		list4.add(0);// tried numbers,
		
		numberofTriedMapInt.put(0, list0);
		numberofTriedMapInt.put(1, list1);
		numberofTriedMapInt.put(2, list2);
		numberofTriedMapInt.put(3, list3);
		numberofTriedMapInt.put(4, list4);
		
	}
	
	public Experience persistentExperience;
	//public boolean isWellKnown = false;
	
	public BeliefState(){
		initial();
		this.persistentExperience = null;
	}
	
	public BeliefState(Experience persistentExperience){
		this.persistentExperience = persistentExperience;
	}
	
	/*
	public Experience getLeastTried(List<Experience> beliefList) {
		//List <Integer> list = new ArrayList <Integer>();
		Map<Experience,Integer> map = new HashMap<Experience,Integer>();
		for(Experience i :beliefList){
			if (map.get(i)== null) {
				map.put(i, 1);
			}else{
				map.put(i, map.get(i)+1);
			}
		}// finish the statistics of the beliefList
		
		//System.out.println(beliefList);
		//System.out.println("The Map of the current beliefList is as below:");
		//System.out.println(map);
		
		int leastTryCount = 1000;
		Experience exp = null;
		for (Experience key : map.keySet()){
			int value = (int)map.get(key); 
			if(value < leastTryCount) {
				exp = key;
				leastTryCount = value;
			}
			System.out.println("key:"+key.action+key.result+" countNum:"+value);
		}
		System.out.println(exp.action+""+exp.result); 
		return exp;
	}
	*/
	
	public int getLeastTried(BeliefState unknowBeliefState) {
		return 1;
	}
	public int leastTriedExperience(int currentBeliefState,
			BeliefState unknowBeliefState,BeliefState sporadicBeliefState,BeliefState persistentBeliefState) {
		//can use the Map to interator the tried number;
		//state of wellknown;
		int exp = 0 ;
		int min;
		switch(currentBeliefState){
		case 0://unknown
			min = (int)unknowBeliefState.numberofTriedMapInt.get(0).get(1);
			for(int key : unknowBeliefState.numberofTriedMapInt.keySet()) {
				int value = (int)unknowBeliefState.numberofTriedMapInt.get(key).get(1);
				if(value < min) {
					min = value;
					exp = key;
				}
			}
			break;
		case 1://aporadic
			min = (int)sporadicBeliefState.numberofTriedMapInt.get(0).get(1);
			for(int key : sporadicBeliefState.numberofTriedMapInt.keySet()) {
				int value = (int)sporadicBeliefState.numberofTriedMapInt.get(key).get(1);
				if(value < min) {
					min = value;
					exp = key;
				}
			}
			break;
		case 2://persistent
			min = (int)persistentBeliefState.numberofTriedMapInt.get(0).get(1);
			for(int key : persistentBeliefState.numberofTriedMapInt.keySet()) {
				int value = (int)persistentBeliefState.numberofTriedMapInt.get(key).get(1);
				if(value < min) {
					min = value;
					exp = key;
				}
			}
			break;
		}//end switch
		return exp;
	}
	
	public Experience getMaxExpectedOutcomeValence(List<Experience> beliefList) {
		//List <Integer> list = new ArrayList <Integer>();
		Map<Experience,Integer> map = new HashMap<Experience,Integer>();
		for(Experience i :beliefList){
			//System.out.println(i);
			if (map.get(i)== null) {
				map.put(i, 1);
			}else{
				map.put(i, map.get(i)+1);
			}
		}// finish the statistics of the beliefList
		//System.out.println(beliefList);
		//System.out.println(map);
		
		int maxValence = 0;
		Experience exp = null;
		for (Experience key : map.keySet()){
			int valence = (int)key.valence; 
			if(valence > maxValence) {
				exp = key;
				maxValence = valence;
			}
			//System.out.println("key:"+key.action+key.result+" value:"+value);
		}
		//System.out.println(exp); 
		return exp;
	}
	
	/*
	public Experience intentionWithMaxExpectedOutcomeValence(int currentBeliefState,List<Experience> unknownBeliefList,List<Experience> sporaticBeliefList,List<Experience> persistentBeliefList) {
		//can use the Map to interator the tried number;
		Experience exp = null;
		switch(currentBeliefState){
		case 0://unknown
			exp = getMaxExpectedOutcomeValence(unknownBeliefList);
			break;
		case 1://aporadic
			exp = getMaxExpectedOutcomeValence(sporaticBeliefList);
			break;
		case 2://persistent
			exp = getMaxExpectedOutcomeValence(persistentBeliefList);
			break;
		}//end switch
		return exp;
	}
	*/
	
	
	
	public int intentionWithMaxExpectedOutcomeValence(int currentBeliefState, Map<Integer, List<Integer>> mapPersistent,
			Map<Integer, List<Integer>> mapSporadic, Environment env) {
		// TODO Auto-generated method stub
		int intention = 0;
		
		/*
		if(env.getResult(2) != 2) {
			for(Integer key : mapPersistent.keySet()) {
				if(mapPersistent.get(key).get(1) != 0) {
					
					int resultPersistent = env.getResult(key);
					if(resultPersistent != 1) {
						for(Integer keysporadic : mapSporadic.keySet()) {
							if(mapPersistent.get(key).get(1) == mapSporadic.get(keysporadic).get(1)) {
								intention = keysporadic;
								return intention;
							}
						}
					}
					
				}
			}
		}else {
			intention = 2;
		}
		return intention;
		*/
		for(Integer key : mapPersistent.keySet()) {
			if((int)mapPersistent.get(key).get(1) == 0) {
				if(env.getResult((int)key) != 2){
					for(Integer keypersistent : mapPersistent.keySet()) {
						if((int)mapPersistent.get(keypersistent).get(1) != 0) {
							if(env.getResult(keypersistent) != 1) {
								for(Integer keysporadic : mapSporadic.keySet()) {
									if(mapPersistent.get(keypersistent).get(1) == mapSporadic.get(keysporadic).get(1) ) {
										intention = keysporadic;
										return intention;
									}
								}
							}
						}
					}
				}else {
					intention = key;
				}
			}
		}
		return intention;
	}
	public Experience intentionWithMaxExpectedOutcomeValence(int currentBeliefState,List<Experience> unknownBeliefList,List<Experience> sporaticBeliefList,List<Experience> persistentBeliefList) {
		//can use the Map to interator the tried number;
		Experience exp = null;
		switch(currentBeliefState){
		case 0://unknown
			exp = getMaxExpectedOutcomeValence(unknownBeliefList);
			break;
		case 1://aporadic
			exp = getMaxExpectedOutcomeValence(sporaticBeliefList);
			break;
		case 2://persistent
			exp = getMaxExpectedOutcomeValence(persistentBeliefList);
			break;
		}//end switch
		return exp;
	}
	
	
	
	public String printBeliefState() {
		return "";
	}
	
	public int updateAndGetBelief(Experience exp) {
		
		int updataedBeliefState = 0;
		return updataedBeliefState;
	}
	
	public int updateAndGetBeliefInt(int exp, Map<Integer, Integer> experienceMarkedMapInt) {
		int updataedBeliefState = 0;
		if((int)experienceMarkedMapInt.get(exp) == 2)updataedBeliefState = 2;
		else if((int)experienceMarkedMapInt.get(exp) == 1) updataedBeliefState = 1;
		return updataedBeliefState;
	}

	public void updateTriedNumberofExperience(int currentBeliefState, int intendedExperience,
			BeliefState unknowBeliefState,BeliefState sporadicBeliefState, BeliefState persistentBeliefState) {
		// TODO Auto-generated method stub
		int value;
		List<Integer> list = new ArrayList<Integer>();
		switch(currentBeliefState){
		case 0://unknown
			list.add((int)unknowBeliefState.numberofTriedMapInt.get(intendedExperience).get(0));
			list.add((int)unknowBeliefState.numberofTriedMapInt.get(intendedExperience).get(1)+1);
			unknowBeliefState.numberofTriedMapInt.put(intendedExperience, list);
			break;
		case 1://aporadic
			list.add((int)sporadicBeliefState.numberofTriedMapInt.get(intendedExperience).get(0));
			list.add((int)sporadicBeliefState.numberofTriedMapInt.get(intendedExperience).get(1)+1);
			sporadicBeliefState.numberofTriedMapInt.put(intendedExperience, list);
			break;
		case 2://persistent
			list.add((int)persistentBeliefState.numberofTriedMapInt.get(intendedExperience).get(0));
			list.add((int)persistentBeliefState.numberofTriedMapInt.get(intendedExperience).get(1)+1);
			persistentBeliefState.numberofTriedMapInt.put(intendedExperience, list);
			break;
		}//end switch
		
		// the code can be programmeed from the situation fo the ideas from the basic idea of the 
		// the implementation of Little_AI
		// get the code from the internet and finish the implimentation of the thinks of this implementation.
		//System.out.println(exp); 
		
	}

	public boolean isAllMarked(Map<Integer, Integer> experienceMarkedMapInt) {
		// TODO Auto-generated method stub
		boolean isAllMarked = true;
		for(Integer key : experienceMarkedMapInt.keySet()) {
			if(experienceMarkedMapInt.get(key) == 0)isAllMarked = false;
		}
		return isAllMarked;
	}

	
	
	/*
	public BeliefState updateAndGetBelief(Experience exp) {
		return null;
	}
	*/
	
}
