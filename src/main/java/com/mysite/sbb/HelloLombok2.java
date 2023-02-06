package com.mysite.sbb;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HelloLombok2 {
	
	private int seq; 
	private String title; 
	private String writer; 
	private String content; 
	private Date regdate; 
	private int cnt; 
	

	public static void main(String[] args) {
		//객체 생성후 테스트 
		HelloLombok2 lombok2 = new HelloLombok2();
		
		//setter 메소드가 자동생성되었는지 확인 
		lombok2.setSeq(20);
		lombok2.setTitle("제목입니다");
		lombok2.setWriter("이순신");
		lombok2.setContent("내용입니다");
		lombok2.setCnt(0);
		
		//getter 를 사용해서 lombok2객체에 저장된 메모리 필드의 값을 출력 
		System.out.println(lombok2.getSeq());
		System.out.println(lombok2.getTitle());
		System.out.println(lombok2.getWriter());
		System.out.println(lombok2.getContent());
		System.out.println(lombok2.getCnt());
		
		//toString () 메소드 호출 : 객체 자체를 print 
		System.out.println(lombok2);
		

	}

}
