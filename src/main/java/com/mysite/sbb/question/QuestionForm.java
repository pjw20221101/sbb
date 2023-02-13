package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
	
	//  subject 폼의 값과 content 폼의 값의 유효성 체크 
	
	@NotEmpty(message="제목은 필수 사항입니다.")  //subject 값이 비어있을때 작동 
	@Size (max=200)							//subject 값을 최대 200자까지 
	private String subject; 
	
	
	@NotEmpty(message="내용은 필수 항목 입니다. ")
	private String content; 

}
