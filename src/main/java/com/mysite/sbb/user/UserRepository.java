package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	
	
	// JPA는 SQL 쿼리를 사용하지 않고 JPA 메소드가 SQL 쿼리로 변환해서 처리 
	
	//findAll() 
	//findById()
	//save()   <== Insert, Update
	//delete() <== delete
	
	 Optional<SiteUser> findByusername(String username);
	
}