package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {
	
	@Autowired    //객체 자동 주입 , JPA의 CRUD할수 있는 메소드가 적용되어 있음. 
	private QuestionRepository questionRepository; 
	
	@Test
	public void testjpa3() {
		List<Question> or = 
		this.questionRepository.findBySubjectLikeOrderByCreateDateAsc("%sbb%");
		
		List<Question> or2 = 
				this.questionRepository.findBySubjectLikeOrderByCreateDateDesc("%sbb%");
				
		
		
		System.out.println("검색된 레코드수 :  " + or.size());
		
		System.out.println(" ASC : 오름 차순 정렬 ");
		for ( int i = 0 ; i < or.size(); i++) {
		Question q = or.get(i); 
		
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getCreateDate());
		System.out.println("============================");
		}
		
		System.out.println("=======DESC : 내림차순 정렬======================");
		
		for ( int i = 0 ; i < or.size(); i++) {
		Question q = or2.get(i); 
		
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getCreateDate());
		System.out.println("============================");
		}
		
		
		
	
		/*
		for (Question q : or) {
			System.out.println(q);
		}
		*/
	}
	
	
	/* 두컬럼을 or 연산으로 검색 : subject , content 
	@Test
	public void testjpa2() {
		List<Question> sq = 
		this.questionRepository.findBySubjectLikeOrContentLike("%sbb%", "%생성%"); 
		
		Question q4 = sq.get(0); 
		System.out.println("총 검색된 갯수 : " + sq.size());
		System.out.println(q4.getId());
		System.out.println(q4.getSubject());
		System.out.println(q4.getContent());
	}
	
	*/ 
	
	/* 사용자 정의 select 문 : subject 컬럼, content 컬럼 검색 , Like 
	@Test
	public void testjpa () {
		
		List<Question> all = this.questionRepository.findBySubjectLike("%sbb%"); 
		
		Question q = all.get(0); 
		System.out.println("리스트에 검색된 레코드수 : " + all.size());
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getContent()); 
		
		System.out.println("=======================");
		
		List<Question> all2 = this.questionRepository.findByContentLike("%생성%");
		
		Question q3 = all2.get(0); 
		
		System.out.println("리스트에 검색된 레코드수 : " + all.size());
		System.out.println(q3.getId());
		System.out.println(q3.getSubject());
		System.out.println(q3.getContent()); 	
	}
		*/
	

	/* 조건에 맞는 레코드 하나만 가져 오기  : PK 컬럼은 findById(1) 
	 * Question 테이블의 Primary Key 컬럼은 기본적으로 제공됨 : findById (1)
	 * 
	@Test
	public void jpaTestget () {
		Optional<Question> oq = this.questionRepository.findById(2); 
		if (oq.isPresent()) {
			Question q = oq.get(); 
			System.out.println(q.getId());
			System.out.println(q.getSubject());
			System.out.println(q.getContent());
			System.out.println(q.getCreateDate());
		}
	}
	*/
	
	
	
	
	/* Select List JUnit Test 
	@Test
	public void jpaTest() {
		
		List<Question> all = this.questionRepository.findAll(); 
		//assertEquals(4, all.size());   // assertEquals( 기대값, 실제값)  , 성공 (두값 이일치)	
		
		Question q = all.get(0); 	// List all 변수에 담긴 0번방의 Question 객체를 끄집어낸다. 
		//assertEquals("sbb가 무엇인가요",q.getSubject() );  //성공 
		
		System.out.println(q.getId());
		System.out.println(q.getSubject());
		System.out.println(q.getContent());
		
	}
	*/ 
	
	
	
	/* Insert JUnit Test  , JPA 인터페이스에 정의 된 save() 
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
	*/ 
}
