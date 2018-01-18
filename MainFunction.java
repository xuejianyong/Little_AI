package level2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainFunction {
	
	/* array[i][j]  j={actionType,resultNumber,Value,colorIndex} 
	 * 0:feel left
	 * 1:swap left
	 * 2:feel both
	 * 3:feel right
	 * 4:swap right
	 */
	public int experiences[][] = {
			{0,0,0,2},{0,1,0,1},
			{1,0,0,2},{1,1,0,1},
			{2,0,0,2},{2,1,0,4},{2,2,10,1},
			{3,0,0,2},{3,1,0,1},
			{4,0,0,2},{4,1,0,1}};
	
	public int getScore(int actionType,int result) {
		// get the score from the experiences
		int score = 0;
		for(int i=0;i<11;i++){
			if(experiences[i][0] == actionType) {
				if(experiences[i][1]==result) {
					score = experiences[i][2];
				};
			}else if(experiences[i][0] > actionType)break;
		}
		return score;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* show the sequence action, result and the value.
		 */
		int min = 0;
		int max = 4;
		int historicalDepth = 10;
		
		//**************************************** the preparing work for implementation ****************************************************
		int threshold = 5;
		int winScore = 10;
		int score = 0;
		String experienceLabels[]= {"Feel Left","Swap Left","Feel Both","Feel Right","Swap Right"};
		String states[] = {"unknown","sporadic","persistent"}; // unknown 0, sopradic 1, persistent 2
		String moodLabels[] = {"curious","hedonist","excited"};// curious 0, hedonist 1, excited 2
		
		int currentBeliefState = 0; // unknown
		int mood = 0;//curious
		
		BeliefState unknowBeliefState = new BeliefState();
		BeliefState persistentBeliefState = new BeliefState();
		BeliefState sporadicBeliefState = new BeliefState();
		
		
		BeliefState beliefState = new BeliefState(); // used to call functions from BeliefState
		//ArrayList<BeliefState> beliefStateList = new ArrayList<BeliefState>();//save the new created belief state
		//beliefStateList.add(beliefState);// ----------------i don't know why should we put this object into the beliefState
		
		//********************************************* Part of the environment code ************************************
		Environment env = new Environment();
		Experience exp00 = new Experience(0,0,0);
		Experience exp01 = new Experience(0,1,0);
		Experience exp10 = new Experience(1,0,0);
		Experience exp11 = new Experience(1,1,0);
		Experience exp20 = new Experience(2,0,0);
		Experience exp21 = new Experience(2,1,0);
		Experience exp22 = new Experience(2,2,10);
		Experience exp30 = new Experience(3,0,0);
		Experience exp31 = new Experience(3,1,0);
		Experience exp40 = new Experience(4,0,0);
		Experience exp41 = new Experience(4,1,0);
		Experience Experiences[][] = {{exp00,exp01},{exp10,exp11},{exp20,exp21,exp22},{exp30,exp31},{exp40,exp41}};

		List<Experience> experienceList = new ArrayList<Experience>();//record the experienced experience
		List<Integer> experienceListInt = new ArrayList<Integer>();//record the experienced experience
		
		/*
		 * based on the current belief state to chose the corresponding beliefList
		 * maybe here have some misunderstanding about the belief.
		 */
		//List<Experience> targetList = new ArrayList<Experience>();// what does this veriable be used for?
		
		/*
		 * to save the mark of 11 different kinds of experiences 
		 * the key is the experience 
		 * the value is the marked
		 */
		Map<Experience,Integer> experienceMarkedMap = new HashMap<Experience, Integer>();
		experienceMarkedMap.put(exp00, 0);
		experienceMarkedMap.put(exp01, 0);
		experienceMarkedMap.put(exp10, 0);
		experienceMarkedMap.put(exp11, 0);
		experienceMarkedMap.put(exp20, 0);
		experienceMarkedMap.put(exp21, 0);
		experienceMarkedMap.put(exp22, 0);
		experienceMarkedMap.put(exp30, 0);
		experienceMarkedMap.put(exp31, 0);
		experienceMarkedMap.put(exp40, 0);
		experienceMarkedMap.put(exp41, 0);
		
		Map<Integer,Integer> experienceMarkedMapInt = new HashMap<Integer, Integer>();
		experienceMarkedMapInt.put(0, 0);
		experienceMarkedMapInt.put(1, 0);
		experienceMarkedMapInt.put(2, 0);
		experienceMarkedMapInt.put(3, 0);
		experienceMarkedMapInt.put(4, 0);
		boolean isAllMarked = false;
		boolean isConnectionFinish = false;
		
		Map<Integer,List<Integer>> mapPersistent = new HashMap<Integer, List<Integer>>();
		Map<Integer,List<Integer>> mapSporadic = new HashMap<Integer, List<Integer>>();
		
		Map<Integer,Integer> mapPersistentSim = new HashMap<Integer, Integer>();
		Map<Integer,Integer> mapSporadicSim = new HashMap<Integer, Integer>();
		
		//************************************************ the initial work *****************************************
		int excitementCount = 0;          //the number of excitement
		int excitementThreshold = 5;
		//Experience intendedExperience = null; //to initial the experiences --- can i initial the blief state of the experience is unknown?
		//Experience enactedExperience = null; // we don't know the initial enactedExperiences is, so it's null.
		
		int intendedExperience = 0; //to initial the experiences --- can i initial the blief state of the experience is unknown?
		int enactedExperience = 0; // we don't know the initial enactedExperiences is, so it's null.
				
		
		int countNum = 0;                  // the end of the interactions, when the cound is ended, that means the interacting is ended.
		boolean isInitial = true;
		List<Integer> tempList = new ArrayList<Integer>();//for update the marked state

		//********************************************** start the algorithm *****************************************
		int result = 0;
		int previousResult = 0;
		while(countNum++ < 100) {
			System.out.println("The current countNum is:"+countNum);
			//the function of this switch is to chose the next experience
			switch(mood){
			case 0://curious
				if(isInitial){
					intendedExperience = 0;
					isInitial = false;
				}else{
					intendedExperience = beliefState.leastTriedExperience(currentBeliefState,
							unknowBeliefState, sporadicBeliefState, persistentBeliefState);
				}
				break;
			case 1://hedonist
					intendedExperience = beliefState.intentionWithMaxExpectedOutcomeValence(currentBeliefState,
							mapPersistent,mapSporadic,env);
				break;
			case 2://excited------------------------------------------------------------
				intendedExperience = enactedExperience;
				break;
			}//end switch
			
			result = env.getResult(intendedExperience);
			enactedExperience = Experiences[intendedExperience][result].action;
			score = score + Experiences[intendedExperience][result].valence;
			System.out.println("EnactedExperience :("+ enactedExperience+", "+result+", "+
					Experiences[intendedExperience][result].valence+")");
			experienceListInt.add(intendedExperience);
			
			//enter the stage 2ed
			if(mood == 2) {
				if(result != previousResult) {
					experienceMarkedMapInt.put(intendedExperience,1);
					mood = 0;
					//currentBeliefState = 1; // now the atitute for the envoronment is sporadic 对环境处于疑惑状态
				}else if(excitementCount > excitementThreshold) {
					experienceMarkedMapInt.put(enactedExperience,2);// mark the experience is persistent and put into correspondance map
					mood = 0;
					excitementCount = 0;
					// Create a new BeliefState List to store the current observer to the post-experience.
					
					//currentBeliefState = 2; // now the attitute for the environment is sporadic. 对环境很相信。
				}else{
					excitementCount++;
				}
			}//end if(mood = 2)
			
			beliefState.updateTriedNumberofExperience(currentBeliefState,intendedExperience,unknowBeliefState, sporadicBeliefState,persistentBeliefState);
			
			isAllMarked = beliefState.isAllMarked(experienceMarkedMapInt);
			System.out.println("The marked statu is:"+experienceMarkedMapInt);
			int resultPersistent;
			int resultSporadic;
			if(isAllMarked) {
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
				
				int sporadicCount = 1;
				for(Integer key : mapSporadic.keySet()) {
					List<Integer> list = mapSporadic.get(key);
					list.remove(1);
					list.add(1, sporadicCount);
					sporadicCount++;
				}
				
				List<Integer> compareList = new ArrayList<Integer>();
				for(Integer keySporadic : mapSporadic.keySet()) {
					Environment envTest = new Environment();
					envTest.phenomenonLeft = false;
					envTest.phenomenonRight = false;
					int temp;
					int flagi = 1;
					for(Integer keyPersistent : mapPersistent.keySet()) {
						if(flagi ==1) {
							resultPersistent = envTest.getResult(keyPersistent);
							temp = resultPersistent;
							resultSporadic = envTest.getResult(keySporadic);
							resultPersistent = envTest.getResult(keyPersistent);
							if(temp != resultPersistent) {
								List<Integer> listPersistent = mapPersistent.get(keyPersistent);
								listPersistent.remove(1);
								listPersistent.add(mapSporadic.get(keySporadic).get(1));
								mapPersistent.put(keyPersistent, listPersistent);
								compareList.add(keyPersistent);
							}
							flagi++;
						}else {
							resultPersistent = envTest.getResult(keyPersistent);
							temp = resultPersistent;
							resultSporadic = envTest.getResult(keySporadic);
							resultPersistent = envTest.getResult(keyPersistent);
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
						envTest.phenomenonLeft = false;
						envTest.phenomenonRight = false;
					}
				}
				
				System.out.println("The Persistent Map is:"+mapPersistent);
				System.out.println("The Sporadic Map is:"+mapSporadic);
				isConnectionFinish = true;
				
			}//end if(isAllMarked)
			
			
			//when finish the mark and the connection construct ,
			//the agent can intent for the max valence.
			if(isAllMarked && isConnectionFinish) {
				mood = 1;
			}
			
			int mark = (int)experienceMarkedMapInt.get(enactedExperience);
			if(mark == 0)mood = 2;//the enactedExperience is not marked, the agent gets excited. also the belief state is unknown.
			System.out.println("The current mood is:"+mood);
			previousResult = result;
			System.out.println("The current Score is:"+score);
			//System.out.println(result);
			//System.out.println(intendedExperience);
			
			currentBeliefState = beliefState.updateAndGetBeliefInt(enactedExperience,experienceMarkedMapInt);
			System.out.println("***********************************************");
			/*
			int mark = (int)experienceMarkedMap.get(enactedExperience);//get the Marked-State(Belief State) of the experience
			int mark = (int)experienceMarkedMap.get(intendedExperience);//get the Marked-State(Belief State) of the experience
			if(mark == 0)mood = 2;//the belief state of this experience is neither yet marked sporadic or persistent, the IS get excited.
			Olivier plus this
			if(enactedExperience.persistency == Persistency.UNTESTED)mood = 2;//这个含义的意思是表示 产生的动作还没有被标记，

			if(mark == 1) {// the 
				mood = 0;
				currentBeliefState = 0;
			}

			currentBeliefState = beliefState.updateAndGetBelief(enactedExperience);//this function to update belief, but i don't know what's the meaning of this function.
			if(mood != 2)mood = 1;
			if(unknownBeliefList.contains(enactedExperience))mood = 2;
			if all the experiences have not yet been tried yet in the context of this belief, mood turns curious
			if(mood != 2)mood = 1;//if the mood is not exicited ,then it's hedonist
			currentBeliefState = beliefState.updateAndGetBelief(enactedExperience);//this function to update
			
			else mood = 0;
			*/
			
		}//end while
		
		
		
		
	}//end main

}
