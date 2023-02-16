package com.mysite.sbb.question;

import java.time.LocalDateTime;   //자신의 시스템의 로케일의 시간설정 
import java.util.List;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
//persistence : JPA에서 사용된 어노테이션 
import jakarta.persistence.Entity;   //JPA 에서 적용된 어노테이션 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity    // 자바 클래스를 DataBase의 테이블과 매핑된 클래스  : 테이명 : question 
public class Question {
	
	@Id	//primary key 
	@GeneratedValue (strategy = GenerationType.IDENTITY)   //시퀀스 할당 
	private Integer id; //Primary Key , 시퀀스 (1, 1) 
	
	@Column(length =200)		//200자까지 
	private String subject; 
	
	@Column(columnDefinition = "TEXT")
	private String content; 
	
	private LocalDateTime createDate; 	//create_date : 
	
	/*
	@Column(length = 300)
	private String addr ; 
	*/
	
	//Question 테이블에서 Answer 테이블을 참조하는 컬럼을 생성 @OnetoMany 
	@OneToMany (mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList; 
	
		//question.getAnswerList() ; 
	
	//2월 16일 Entity 컬럼 추가 , 글작성자 , 
	//여러개의 질문이 한 명의 사용자에게 작성될 수 있으므로 @ManyToOne 관계가 성립한다.
	@ManyToOne
    private SiteUser author;
	
	private LocalDateTime modifyDate;
		
}
