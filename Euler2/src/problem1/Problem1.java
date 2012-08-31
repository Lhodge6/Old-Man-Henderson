package problem1;

import java.util.LinkedList;
import java.util.Collections;

public class Problem1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int LIMIT = 1000;
		int sum = 0;
		LinkedList<Integer> threes = new LinkedList<Integer>();
		LinkedList<Integer> fives = new LinkedList<Integer>();
		LinkedList<Integer> both = new LinkedList<Integer>();
		
		int t = 3;
		int f = 5;
		
		while(t < LIMIT)
		{
			
			
			if(t<LIMIT)
			{
				threes.add(t);
				t+=3;
			}
				
			if(f<LIMIT)
			{
				fives.add(f);
				f+=5;
			}
		}
		both = threes;
		for(Integer number:fives)
		{
			if(!threes.contains(number))
				both.add(number);
		}
		Collections.sort(both);
		for(Integer number:both)
		{
			System.out.println("number: " + number + "    Sum: " + sum);
			sum += (int)number;
		}
		System.out.println(sum);
	}	
}
