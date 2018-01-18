package level2;

import java.util.Random;

public class LittleAI_Level200 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * show the sequence action, result and the value.
		 */
		int min = 0;
		int max = 4;
		int score = 0;
		int winScore = 10;
		
		boolean phenomenonLeft = false;
		boolean phenomenonRight = false;
		int result = 0;
		
		int historicalDepth = 10;

		int experiences[][] = {
				{0,0,0,2},{0,1,0,1},
				{1,0,0,2},{1,1,0,1},
				{2,0,0,2},{2,1,0,4},{2,2,10,1},
				{3,0,0,2},{3,1,0,1},
				{4,0,0,2},{4,1,0,1}};
		String actions[]= {"Feel Left","Swap Left","Feel Both","Feel Right","Swap Right"};
		/* array[i][j]  j={actionType,resultNumber,Value,colorIndex} 
		 * 
		 * 0:feel left
		 * 1:swap left
		 * 2:feel both
		 * 3:feel right
		 * 4:swap right
		 */

		/*
		 * int ramdomAction = getRamdomNumber(min, max);
		 * Random ram = new Random();
		 * 
		 * when the program starts, we can initial the actions
		 * but when the learn sequence is stable 
		 * or has already get the wanted action result 
		 */
		
		/*while(true) {
			int ramdomAction = ram.nextInt((max - min) + 1) + min;
			System.out.println(ramdomAction);
		}*/
		int countNum = 100;
		while( countNum-- > 0 ) {
			
			Random random = new Random();
			int actionType = random.nextInt((max - min) + 1) + min;//put the random value of the sequence been the action sequence
			
			/*
			for(int actionType = 0; actionType<5; actionType++) {
				
			}
			*/
			switch(actionType){
			case 0://feel left
				if( phenomenonLeft)result = 1;
				break;
			case 1://swap left
				phenomenonLeft = !phenomenonLeft;
				if( phenomenonLeft)result = 1;
				break;
			case 2://feel both
				if(phenomenonLeft != phenomenonRight)result = 1;
				if(phenomenonLeft && phenomenonRight)result = 2;
				break;
			case 3://feel right
				if(phenomenonRight)result = 1;
				break;
			case 4://swap right
				phenomenonRight = !phenomenonRight;
				if(phenomenonRight)result = 1;
				break;
			default:
				break;
			}
		
			for(int i=0;i<11;i++){
				if(experiences[i][0] == actionType) {
					if(experiences[i][1]==result) {
						int temp = experiences[i][2];
						score = score+temp;
					};
				}else if(experiences[i][0] > actionType)break;
			}
			System.out.println("Action:"+actions[actionType]+"  Phenomenon:"+result+"  Score:"+score);
		}
	}

}
