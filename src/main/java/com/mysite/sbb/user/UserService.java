package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
    
    
    //username 을 인풋 받아 사용자 정보 얻어오기 , 2월 16일 추가
    
    public SiteUser getUser(String username) {
    	
    	
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        
        if (siteUser.isPresent()) {  //Optional 에 SiteUser가 검색되면 (존재하면) 
        	
            return siteUser.get();
            
        } else {
            throw new DataNotFoundException("siteuser not found - 해당 사용자는 존재하지 안습니다. ");
        }
    }
    
    
}