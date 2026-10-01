package utility;

import java.util.HashMap;

import net.bytebuddy.asm.Advice.This;

public class Practise {
	
	
	private String name;

    private int age;

	public Practise(String name ) {
		
		this("mangesh", 30); // this() = help to call tghe another constructor of same class
	}
	
	public Practise(String name,int age ) {
//		this.name = name;
//		this.age =age;
		
		
		System.out.println(name + "" + age);
	}
	
	
	
	public int add(int a, int b) {
		System.out.println(a+b);
		return a+b;
	}
	
	
	public void checkPrimeNumber(int number) {
		
		boolean flag = true;
		
		for(int i=2;i<=Math.sqrt(number);i++) {
			if(number%i==0) {
				flag = false;
				break;
			}
			
		}
		if(flag) {
			System.out.println(number + "it is a prime number");
		}
		else {
			System.out.println(number + "it is not a prime number");

		}
	}
	
	public void reverseNumber(int number) {
		int rev =0;
		int compare = number;
		while(number!=0) {
			int digit = number%10;//1, 
			rev = rev*10 + digit ; // 1, 
			number = number/10;
		}
		
		System.out.println(rev);
		if(rev==compare) {
			System.out.println(compare + " it is reverse number");
		}
		else {
			System.out.println(compare + " it is not reverse number");
		}
	}
	
	public void occurnceOfChar(String name) {
		
		HashMap<Character,Integer> map = new HashMap<>();
		
		for(char c :name.toCharArray() ) {
			map.put(c, map.getOrDefault(c, 0)+1);
		}
		for(char c: map.keySet()) {
			if(map.get(c)>1) {
			System.out.println(c + " Occurance of char" + map.get(c));
			}
		}
	}
	
	
	public void calculateTest() {
		
		String test = "@,12,245,MK,-76,-89";
		
		StringBuilder builder =  new StringBuilder();
	String test2[] =	test.split(",");
	for(String mm : test2) {
		if(mm.endsWith("-")) {
		builder.append(mm).append(",");}
	}
	System.out.println(" TTTT"+ builder);
		
	}
	
	
	public void findLargest() {
		int number[] = {12,56,87,98,67};
		int max = number[0];
		
		for(int i=0;i<number.length;i++) 
		{
			if(number[i]>max) 
			{
				max = number[i];
				
			}
			
		}
		System.out.println(max);
		
	}
	
	
	public void findsecondLargest() {
		int number[] = {12,56,87,98,67};
		int max = number[0];
		int second = Integer.MIN_VALUE;
		
		for(int i=0;i<number.length;i++) 
		{
			if(number[i]>max) 
			{
				second = max;
				max = number[i];
				
			}
			if(second>number[i] && second != max) 
			{
				second = number[i];
			}
			
		}
		System.out.println(second);
		
	}
	
	public void factorial() {
		int numbr = 6;
		int factorial = 1 ;
		for(int i=numbr;i>=1;i--) 
		{
			factorial = factorial* (i);
		}
		
		System.out.println(factorial);
	}
	
	public void manipulateString() {
		String name = "mMkK@123$%";
		String testy =name.replaceAll("[0-9,$,%,@]", "");
		
		System.out.println(testy.toLowerCase());
		
		HashMap<Character,Integer> map =  new HashMap<>();
		for(char ch :testy.toCharArray()) {
			map.put(ch, map.getOrDefault(ch, 0)+1);
			for(char ch2 :map.keySet()) {
				System.out.println("occurance of char" + map.get(ch2));
			}
		}
		System.out.println(map);
	}
	
	public void missingNumber() {
		int number [] =  {11,12,14,15,18};
		int length = number.length+1;
		int start =11;
		int end2 = 16;
		int end =0;
		int missingNumber = 0;
		int ActualCont = 0;
		for(int missing: number) 
		{
			ActualCont = ActualCont + missing;
		
//		int expectdCount = length*(length+1)/2;
		int expectdCount =0;
		for(int i=start;i<=end2;i++) 
		{
			expectdCount = expectdCount+ i;
		}
		missingNumber = expectdCount- ActualCont;
		
		
		
	}
		System.out.println(missingNumber);
		}
	
	
	

			
	public static void main(String[] args) {
	
	Practise pract = new Practise("mangesh");
//	Practise pract1 = new Practise("");
	
	pract.add(10,20);
	pract.checkPrimeNumber(23);
	pract.reverseNumber(123);
	pract.occurnceOfChar("Mangesh Kulkarni");
	pract.calculateTest();
	pract.findLargest();
	pract.findsecondLargest();
	pract.factorial();
	pract.manipulateString();
	pract.missingNumber();
	// USAGE OF SINGEL TONE AT CONFIG manager level
	
	
	singeltone obj1 = singeltone.getinstance();  // We are getting/retrieving the Singleton object instance
	
	
	}
	
	

}


