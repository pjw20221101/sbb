package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity		// JPA에서 자바객체를 DB의 테이블에 매핑 
public class Answer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 	//Primary Key, 자동 증가 (1,1)
	
	@Column(columnDefinition ="TEXT")
	private String content; 
	
	private LocalDateTime createDate; 	//create_date
	
	@ManyToOne			//Foreign Key : 부모테이블의 PK, UK컬럼의 값을 참조해서 값을 할당. 
	private Question question;    //부모테이블이 Question 테이블의 Primary Key 를 참조 (id)
						//question_id
	
	
	//2월 16일 Entity 컬럼 추가 , 글작성자 , 
	//여러개의 질문이 한 명의 사용자에게 작성될 수 있으므로 @ManyToOne 관계가 성립한다.
	@ManyToOne				//foreign key , SiteUser를 참조해서 값을 넣는다. 
    private SiteUser author;
	
	private LocalDateTime modifyDate;
	
	
}
