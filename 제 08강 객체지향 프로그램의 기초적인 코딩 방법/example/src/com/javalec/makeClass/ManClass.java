package com.javalec.makeClass;

public class ManClass {
	
	private int age;
	private int height;
	private int weight;
	private String phoneNum;
	
	public ManClass() {
		// TODO Auto-generated constructor stub
	}
	
	public ManClass(int age, int height, int weight, String phoneNum) {
		// TODO Auto-generated constructor stub
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.phoneNum = phoneNum;
		
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	
}
