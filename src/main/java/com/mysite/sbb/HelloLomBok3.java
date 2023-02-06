package com.mysite.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor    //기본 생성자 : 
@ToString
@AllArgsConstructor   // 객체 생성시 모든 필드의 값을 입력 받아 필드의 초기값을 할당 
public class HelloLomBok3 {
	
	private String hello;		//필드 : private
	private int lombok; 		//필드 
	/*
	public HelloLomBok3() {}      <== 기본 생성자 
	public HelloLomBok3(String hello, int lombok) {   <== @AllArgsConstructor
		this.hello = hello; 
		this.lombok = lombok; 
	}
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//@AllArgsContructor 테스트: 객체 생성시에 필드의 값 할당함.  
		HelloLomBok3 lombok3 = new HelloLomBok3("안녕", 55);
		
		// 필드의 내용을 출력 
		System.out.println(lombok3.getHello());
		System.out.println(lombok3.getLombok());
		
		// toString () 메소드 호출 
		System.out.println(lombok3);

	}

}
