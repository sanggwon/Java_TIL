package com.javalec.proceEx;

public class ProceEx {
	public static void main(String[] args) {
		
		int sum = 0;
		int result = 0;
		
		for (int i = 0; i <= 10; i++) {
			sum = sum + i;
		}
		System.out.println("1부터 10까지의 정수의 합은 " + sum + "입니다.");
		
		result = sum % 2;
		if(result == 0){
			System.out.println("짝수 입니다.");
		} else {
			System.out.println("홀수 입니다.");
		}
		
	}
}
