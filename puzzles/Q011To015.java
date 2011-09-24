import java.util.*;
import java.lang.*;
import java.io.*;


public class Q011To015
{
	int q011;
	int q012;

	public Q011To015()
	{
		q011 = question11();
		q012 = question12();
	}

	// What is the greatest product of four adjacent numbers in any direction
	// (up, down, left, right, or diagonally) in the 20×20 grid (input file Q11.data)?
	// FOR READING FILE, the reading sequence is file-->buffer (read line by line)
	// -->ArrayList (read token by token)-->Integer Array (with checking)
	// The array convertion is done in question11() to avoid global variables.
	private List<List<Integer>> readFile()
	{
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		try
		{
			File file = new File("Q11.data");
			FileReader reader = new FileReader(file);
			BufferedReader in = new BufferedReader(reader);
			String string;
			while((string=in.readLine())!=null)
			{
				Scanner scanner = new Scanner(string);
				List<Integer> templist = new ArrayList<Integer>();
				while(scanner.hasNextInt())
				{
					templist.add(scanner.nextInt());
				}
				list.add(templist);
			}

		}
		catch(IOException ioe)
		{
			System.out.println("Exception while reading the file: "+ioe);
		}
		return list;
	}
	private int question11()
	{
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list = readFile();
		int column = list.get(0).size();
		int row = list.size();
		Integer data[][] = new Integer[row][column];

		// convert ArrayList to array to facilitate random access
		for(int i=0; i<row; i++)
		{
			if(column!=list.get(i).size())
			{
				System.out.println("The length of array in input file is irregular..\n");
				return 0;
			}
			data[i] = list.get(i).toArray(new Integer[column]);
		}
		
		// calculate the production in FOUR directions:
		// down, right, right-down, AND left-down
		int product = 0;
		int temp;
		boolean rowflag = false, columnflag = false;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<column;j++)
			{
				if(i+3<row)
				{
					temp=data[i][j]*data[i+1][j]*data[i+2][j]*data[i+3][j];
					rowflag=true;
					product = Math.max(product,temp);
				}
				if(j+3<column)
				{
					temp=data[i][j]*data[i][j+1]*data[i][j+2]*data[i][j+3];
					columnflag=true;
					product = Math.max(product,temp);
				}
				if(rowflag && columnflag)
				{
					temp=data[i][j]*data[i+1][j+1]*data[i+2][j+2]*data[i+3][j+3];
					product = Math.max(product,temp);
				}
				if(i+3<row && j-3>=0)
				{
					temp=data[i][j]*data[i+1][j-1]*data[i+2][j-2]*data[i+3][j-3];
					product = Math.max(product,temp);
				}
				rowflag=columnflag=false;
			}
		}
		return product;
	}

	// The sequence of triangle numbers is generated by adding the natural numbers.
	// The first ten terms would be: 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, ...
	// Let us list the factors of the first seven triangle numbers:
	//      1: 1
	//      3: 1,3
	//      6: 1,2,3,6
	//      10: 1,2,5,10
	//      15: 1,3,5,15
	//      21: 1,3,7,21
	//      28: 1,2,4,7,14,28
	// We can see that 28 is the first triangle number to have over five divisors.
	// What is the value of the first triangle number to have over five hundred divisors?
	// SOLUTION: the most elemetary means is to find every factor by i++, and count the number to 500.
	// Since it is too laborious, i prefer some other optimized way:
	// factorization-->combination into set (no duplicated elements)->count the number.
	private List<Integer> factorization(int val)
	{
		List<Integer> list = new ArrayList<Integer>();

		for(int i=2; i<=val/i; i++)
		{
			while(val%i==0)
			{
				list.add(i);
				val = val/i;
			}
		}
		if(val>1)
			list.add(val);

		return list;
	}
	// The solution could be as easy as the basic computation of 
	// premutation and combination.
	private int findFactorNumber(List<Integer> list)
	{
		int product = 1;
		int count = 1;
		int comparator, previous;
		Iterator<Integer> iterator = list.iterator();

		previous = iterator.next().intValue();
		while(iterator.hasNext())
		{
			comparator = iterator.next().intValue();
			if(comparator==previous)
				count++;
			else
			{
				product = product*(count+1);
				count = 1;
				previous = comparator;
			}
		}
		product = product*(count+1);
		return product;
	}

	private int question12()
	{
		int numOfDivisor = 498;		// exclude 1 and num itself
		int num = 1;
		int step = 1;	// beginning point is 55. step is the value of previous step.
		int count = 0;
		List<Integer> list = new ArrayList<Integer>();;
		Set<Integer> set = new HashSet<Integer>();

		while(count<numOfDivisor)
		{
			step++;
			num +=step;
			list = factorization(num);
			// check if # of potential largest combination of factors equals to 499
			// sum(choose i from m)=2^m-2  (i=1...m-1)
			// This is derived from the formula of (x+y)^m
			if((Math.pow(2,list.size())-2)<499)
				continue;
			count = findFactorNumber(list);
		}
		return num;
	}


	public static void main (String args[])
	{
		Q011To015 question = new Q011To015();
		System.out.printf("Q011: result = %d.\n", question.q011);
		System.out.printf("Q012: result = %d.\n", question.q012);
	}
}


