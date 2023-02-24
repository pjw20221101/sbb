package com.mysite.sbb.question;

import java.time.LocalDateTime;   //자신의 시스템의 로케일의 시간설정 
import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
//persistence : JPA에서 사용된 어노테이션 
import jakarta.persistence.Entity;   //JPA 에서 적용된 어노테이션 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	
	// @Column(columnDefinition = "TEXT")
	@Column(length = 4000)
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
	@ManyToOne     // Foreign Key : site_user 테이블의 Primary key 참조 
    private SiteUser author;
	
	private LocalDateTime modifyDate;
	
	@ManyToMany
    Set<SiteUser> voter;    // 한명의 사용자가  여러 질문에 투표 할 수 있다.
							// 하나의 질문에 여러명의 사용자가 투표 할 수 있다. 1번만 투표 가능 하도록 set
	
	//List : 방의 번호(Index)를 가지고 중복된 값을 저장 할 수 있다.
	//Set : 자료형은 중복된 값을 넣을수 없는 자료형 
		// Set은 방번호를 가지지 않는다. 
	
}
