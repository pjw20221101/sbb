package com.mysite.sbb;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {
	
	@Autowired    //객체 자동 주입 , JPA의 CRUD할수 있는 메소드가 적용되어 있음. 
	private QuestionRepository questionRepository; 

	@Test
	void contextLoads() {
		Question q1 = new Question(); 
		q1.setSubject("sbb가 무엇인가요");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());   // 현재 시간을 setter에 저장 
		this.questionRepository.save(q1);  	//첫번째 질문 저장 
		
		Question q2 = new Question(); 
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요. ");
		q2.setCreateDate(LocalDateTime.now());   // 현재 시간을 setter에 저장 
		this.questionRepository.save(q2); 
				
	}

}
