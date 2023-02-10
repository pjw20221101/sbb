package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mysite.sbb.question.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	//생성자를 통한 객체 주입 : DI
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository; 

	//답변글을 저장하는 메소드 , Controller 에서 Question 생성해서 아규먼트로 인풋 
	public void create(Question question, String content) {
		
		//Answer 객체를 생성후 아규면트로 넘어오는 값을 setter 주입 
		Answer answer = new Answer(); 
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		
		this.answerRepository.save(answer); 
		
	}
	
	
	
}
