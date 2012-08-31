package problem3;

import java.util.LinkedList;

public class Primes {
	
	public static boolean isPrime(int num)
	{
		LinkedList<?> primes = primeList(num);
		return primes.getLast().equals(num);
	}
	public static int[] primeArray(int limit)
	{
		return null;
		
	}
	public static LinkedList<Integer> primeList(int limit)
	{
		LinkedList<Integer> primes = new LinkedList<Integer>();
		for(int i = 1; i <= limit;i++){
			
		}
		return primes;
		
	}

}
