package problem2;

public class Problem2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(add(fibbonacci(4000000)));

	}
	public static int[] fibbonacci(int limit)
	{
		int[] fib = new int[limit];
		fib[0] = 1;
		fib[1] = 2;
		for(int i = 2; fib[i]+fib[i-1] < limit  && fib[i-1] > 0; i++)
		{
			fib[i] = fib[i-1] + fib[i-2];
			System.out.println(fib[i] + "   count: " + i);
			
			if(fib[i] >= limit)
				fib[i] = 0;
		}
		System.out.println(fib[32]);
		
		return fib;
	}
	public static int add(int[] list)
	{
		int sum = 0;
		for(int i = 0; i < list.length && list[i] > 0; i++)
			if(list[i]%2 == 0)
				sum+=list[i];
		return sum;
	}

}
