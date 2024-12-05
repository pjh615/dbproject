package com.example.dbproject;

import com.example.dbproject.domain.Member.Member;
import com.example.dbproject.domain.Posts.Posts;
import com.example.dbproject.domain.Posts.PostsRepository;
import com.example.dbproject.service.PostsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
