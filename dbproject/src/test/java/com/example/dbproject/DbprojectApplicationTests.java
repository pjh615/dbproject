package com.example.dbproject;

import com.example.dbproject.model.Member.Member;
import com.example.dbproject.service.PostsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DbprojectApplicationTests {

	@Autowired
	private PostsService pService;

	@Test
	void testJpa(){
		for(int i=101; i<=300; i++){
			Member member = new Member();
			String title = String.format("테스트 데이터: [%03d]", i);
			String content = "내용없음";
			pService.create(title, content, member);
		}
	}

}
