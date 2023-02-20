package com.mysite.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	//final 필드의 생성자를 자동으로 만들어서 생성자를 통한 의존성 주입
@Controller			//
public class QuestionController {
		/*  클래스를 객체로 생성 어노테이션 ( 빈(객체) 등록 , Spring Framework)
		    @Component : 일반적인 클래스를 객체화 
		    @Controller : 클라이언트 요청을 받아서 처리 , Controller 
		    	1. 클라이언트 요청을 받는다. @GetMapping, @PostMapping 
		    	2. 비즈니스 로직 처리, Service의 메소드 호출, 
		    	3. View 페이지로 응답  
		    	
		    @Service : DAO의 메소드를 인터페이스로 생성후 인터페이스의 메소드를 구현한 클래스
		    	- 유지보수를 쉽게 하기 위해서 (약결합) 
		    @Repository : DAO 클래스를 빈등록 
		    	   		 
		 */
		/*  DI ( 의존성 주입 ) 
		  1. @Autowired : Spring Framework 의 생성된 빈(객체)을 주입, 타입을 찾아서 주입
		  		같은 타입의 객체가 존재할 경우 문제가 발생될 수 있다. 
		  2. 생성자를 통한 의존성 주입 (권장방식) 
		  3. Setter 를 사용한 의존성 주입 
		 
		 */
	
	//생성자를 통한 의존성 주입 <== 권장하는 방식 
		//Controller 에서 직접 Repository 를 접근하지 않고 Service 를 접근 하도록 함. 
	//private final QuestionRepository questionrepository; 
	private final QuestionService questionService; 
	private final UserService userService;		//2월 16일 수정됨
	
//	@GetMapping("/question/list")	//http://localhost:9292/question/list
//	@PostMapping("/question/list")  // Form 태그의 method=post action ="/question/list"
	//@ResponseBody			//요청을 요청한 브라우저에 출력 
//	public String list(Model model) {
		//1. 클라이언트 요청 정보 :   http://localhost:9292/question/list
		
		//2. 비즈니스 로직을 처리 
//		List<Question> questionList = 
				//this.questionrepository.findAll(); 
//				this.questionService.getList(); 
		//3. 뷰(view) 페이지로 전송
			//Model : 뷰페이지로 서버의 데이터를 담아서 전송 객체 ( Session, Application ) 
//		model.addAttribute("questionList", questionList);
		
//		return "question_list"; 
//	}
	
	// 2월 14일 페이징 처리 를 위해 수정됨 
	// http://localhost:9292/question/list/?page=0
	
	//2월 17일 : 검색 기능 추가 적용 : 파라미터로 검색어 인풋을 받음. 
	@GetMapping("/question/list")
//	public String list(Model model, @RequestParam (value="page", defaultValue="0") int page ) {
	
	public String list(Model model, @RequestParam (value="page", defaultValue="0") int page, 
			@RequestParam(value = "kw", defaultValue = "") String kw) {
	
		// 비즈니스 로직 처리 : 
//		Page<Question> paging = this.questionService.getList(page); 
		Page<Question> paging = this.questionService.getList(page, kw); 
		
		//model 객체에 결과로 받은 paging 객체를 client 로 전송 
		model.addAttribute("paging", paging); 
		//2월 17일 : model 에 넘겨 받은 검색어 추가 적용해서 View 페이지로 전송
		model.addAttribute("kw", kw);
				
		return "question_list"; 
	}
	
	
	// 상세 페이지를 처리하는 메소드 : /question/detail/1
	@GetMapping(value = "/question/detail/{id}")
	public String detail (Model model , @PathVariable("id") Integer id , AnswerForm answerForm) {
		// 서비스 클래스의 메소드 호출 : 상세페이지 보여 달라 
		Question q = 
				this.questionService.getQuestion(id); 
		
		// Model 객체에 담아서 클라이언트에게 전송 
		model.addAttribute("question", q); 
		
		return "question_detail";   //template  : question_detail.html
	}
	
	@PreAuthorize("isAuthenticated()")		//로그인 시에만 접근 가능하도록 설정
	@GetMapping("/question/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form"; 
	}
	
	
	//2월 16일 : Principal principal 추가됨 
	@PreAuthorize("isAuthenticated()")	//로그인 시에만 접근 가능하도록 설정
	@PostMapping("/question/create")
	public String questionCreate(			
			//@RequestParam String subject,@RequestParam String content
			@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal)
			 {
				
				if (bindingResult.hasErrors()) {   //subject,content 가 비어있을때 
					return "question_form"; 
				}
	
		//현재 로그온한 사용자정보를 확인해 보기 
		//System.out.println("현재 로그온한 사용자 정보 : " + principal.getName() );
				
				
		//로직 작성부분 ( Service 에서 로직을 만들어서 작동 ) 
		//this.questionService.create(subject, content); 
	
	//2월 16일 추가 항목 : 			
	SiteUser siteUser = this.userService.getUser(principal.getName());
	
	//2월 16일 추가 항목 : 
	this.questionService.create(questionForm.getSubject(), questionForm.getContent(),siteUser); 			
		//값을 DB에 저장후 List페이지로 리다이렉트 (질문 목록으로 이동)
		return "redirect:/question/list";      
	}
	
	
	
	//2월 16일 수정 항목 추가
	
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
 
    	Question question = this.questionService.getQuestion(id);
    	
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
        	
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            
        }
        
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        
        
        return "question_form";
    }
    
    /*
     위와 같이 questionModify 메서드를 추가했다. 만약 현재 로그인한 사용자와 질문의 작성자가 동일하지 않을 경우에는 
     "수정권한이 없습니다." 오류가 발생하도록 했다. 그리고 수정할 질문의 제목과 내용을 화면에 보여주기 위해 questionForm 객체에 
     값을 담아서 템플릿으로 전달했다. (이 과정이 없다면 화면에 "제목", "내용"의 값이 채워지지 않아 비워져 보인다.)

	그리고 여기서 눈여겨 보아야 할 부분은 질문 등록시 사용했던 "question_form" 템플릿을 질문 수정에서도 사용한다는 점이다. 
	질문 등록 템플릿을 그대로 사용할 경우 질문을 수정하고 "저장하기" 버튼을 누르면 질문이 수정되는 것이 아니라 새로운 질문이 등록된다. 
	이 문제는 템플릿 폼 태그의 action을 잘 활용하면 유연하게 대처할수 있다. 어떻게 대처할 수 있는지 템플릿을 수정하면서 살펴보자.
	
     */
    
    
    //2월 16일 : 질문 수정 postMapping 추가 
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("id") Integer id) {
    	
    	
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        
        Question question = this.questionService.getQuestion(id);
        
        
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        
        return String.format("redirect:/question/detail/%s", id);
    }
	
    
    //2월 16일 질문 삭제 기능 추가됨 
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
    	
        Question question = this.questionService.getQuestion(id);
        
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
        	
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
            
        }
        
        this.questionService.delete(question);
        
        return "redirect:/";
    }
    
    
    //id : Question 객체 
    //principal : 현재 투표하는 객체를 가지고 옮 
    
    //2월 17일 : 질문에서 추천 기능 추가 
    @PreAuthorize("isAuthenticated()")
    @GetMapping("question/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
    
    //2월 17일 : 답변에서 추천 기능 추가 
    
    
	
}
