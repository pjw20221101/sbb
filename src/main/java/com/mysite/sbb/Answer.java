package com.mysite.sbb;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
}
