package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor   // DI (생성자에 객체 주입) 
@Service
public class QuestionService {
	//JPA 메소드를 사용하기 위해  ( 생성자를 이용한 객체 자동 주입 ) 
	private final QuestionRepository questionRepository; 
	
	//메소드 : question테이블의 List 정보를 가지고오는 메소드 
	public List<Question> getList() {
		return this.questionRepository.findAll(); 
		
	}
	
	//상세 페이지를 처리 하는 메소드 : id를 받아서 Question 테이블을 select (findById(1)
		//해서 select 한 레코드를 Question 객체에 담아서 리턴 
	public Question getQuestion(Integer id) {
		
		//select * from question where id = ?
		Optional<Question> op = this.questionRepository.findById(id) ; 
		if ( op.isPresent()) {	//op 에 값이 존재하는 경우 
			return op.get();  // Question 객체를 리턴 
		}else {
			//사용자 정의 예외 : 
			//throw : 예외를 강제로 발생 
			//throws : 예외를 요청한 곳에서 처리하도록 미루는 것 
			throw new DataNotFoundException("요청한 파일을 찾지 못했습니다. "); 
		}		
	}
	
	
	public void create(String subject, String content) {
		//Question 객체를 생성후 Setter주입 
		Question q = new Question(); 
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		
		//Repository의 save () 메소드에 Question 객체 저장 
		this.questionRepository.save(q);      //db에 insert 
		
	}
	

}
