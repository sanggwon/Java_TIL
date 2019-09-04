package com.javalec.proceEx;

public class ProceEx {
	public static void main(String[] args) {
		
		int sum = 0;
		int result = 0;
		
//		for (int i = 0; i <= 10; i++) {
//			sum = sum + i;
//		}
		
		ProceEx proceEx = new ProceEx();
		sum = proceEx.sum(0, 10);
		System.out.println("1부터 10까지의 정수의 합은 " + sum + "입니다.");
		
//		result = sum % 2;
//		if(result == 0){
//			System.out.println("짝수 입니다.");
//		} else {
//			System.out.println("홀수 입니다.");
//		}
		
		System.out.println(proceEx.evenOdd(sum));
		
	}
	
	public int sum(int i, int j) {
		// TODO Auto-generated method stub
		
		int r = 0;
		
		for (int h = i; h <= j; h++) {
			r = r + h;
		}
		
		return r;
	}
	
	public String evenOdd(int sum) {
		// TODO Auto-generated method stub
		String r = new String();
		
		if((sum % 2) == 0){
			r = "짝수 입니다.";
		} else {
			r = "홀수 입니다.";
		}
		
		return r;
	}
	
}
