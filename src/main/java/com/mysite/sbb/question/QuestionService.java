package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor   // DI (생성자에 객체 주입) 
@Service
public class QuestionService {
	//JPA 메소드를 사용하기 위해  ( 생성자를 이용한 객체 자동 주입 ) 
	private final QuestionRepository questionRepository; 
	
	//메소드 : question테이블의 List 정보를 가지고오는 메소드  <2월 14일 수정됨 : Paging 처리를 위해>  
//	public List<Question> getList() {
//		return this.questionRepository.findAll(); 
//		
//	}
	
	
	//Controller 에서 getList메소드 호출시 출력할 page 번호를 매개변수로 받음. : 0, 1, 2, 3
	
	//2월 17일 : 검색 기능 추가됨 
//	public Page<Question> getList(int page) {
	public Page<Question> getList(int page, String kw) {	
		//최신글을 먼저 출력 하기, 날짜 컬럼 (createDate) 을 desc 해서 출력 
		List<Sort.Order> sorts = new ArrayList(); 
		sorts.add(Sort.Order.desc("createDate")); 
	
		//Pageable 객체에 2개의 값을 담아서 메개변수로 던짐  , 10 <== 출력할 레코드 수 
		Pageable pageable = PageRequest.of(page, 10 , Sort.by(sorts)); 
		
		//2월 17일 : 검색 기능이 추가됨 
		Specification<Question> spec = search(kw);
		
//		return this.questionRepository.findAll(pageable); 
		return this.questionRepository.findAll(spec, pageable); 
		
//		return this.questionRepository.findAllByKeyword(kw, pageable);
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
	
	//2월 16일 SiteUser user 추가함. 
	public void create(String subject, String content, SiteUser user) {
		//Question 객체를 생성후 Setter주입 
		Question q = new Question(); 
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		
		//Repository의 save () 메소드에 Question 객체 저장 
		this.questionRepository.save(q);      //db에 insert 
		
	}
	
	
	//2월 16일 글 수정 항목 추가됨
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }
    
    //2월 16일 : 질문 삭제 기능 추가 
    public void delete(Question question) {
        this.questionRepository.delete(question);
    }
    
    //2월 17일 : 추천 기능 추가 
    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
    
    
    
    //2월 17일 : JPA 검색 기능 추가 
    
	/*  실제 SQL 쿼리 
	 
select
    distinct q.id,
    q.author_id,
    q.content,
    q.create_date,
    q.modify_date,
    q.subject 
    
from question q 
	left outer join site_user u1 
	on q.author_id=u1.id 
	
left outer join answer a 
	on q.id=a.question_id 
	
left outer join site_user u2 
	on a.author_id=u2.id 
	
where
    q.subject like '%스프링%' 
    or q.content like '%스프링%' 
    or u1.username like '%스프링%' 
    or a.content like '%스프링%' 
    or u2.username like '%스프링%'  
	 
	 
	 */
	
	
	/* JPA 메소드를 사용한 쿼리   */ 
	
    private Specification<Question> search(String kw) {
    	
        return new Specification<>() {
        	
            private static final long serialVersionUID = 1L;
            
            
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	
            	
                query.distinct(true);  // 중복을 제거 
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자 
            }
        };
    }
	

}
