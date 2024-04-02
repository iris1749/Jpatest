package com.example.jpaTest;

import org.hibernate.jpa.HibernateHints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class JpaTestApplicationTests {

	@Autowired //ArticleRepository를 스프링 부트가 알아서 찾아서 해줌.
	ArticleRepository articleRepository;

	@Test
	void contextLoads() {
		Article article = new Article();
		article.setTitle("hihi");
		article.setContent("hihihi");

		articleRepository.save(article); //DB반영

		Article article2 = new Article();
		article.setTitle("제목2");
		article.setContent("내용2");

		articleRepository.save(article);

	}

	@Test
	void test2() {
		List<Article> articleList = articleRepository.findAll();

		for (Article article : articleList) {
			System.out.println(article.getTitle());
		}
	}

	@Test
	void test3() {
		Article article	= articleRepository.findById(1).get();
		System.out.println(article.getTitle());
	}

	@Test
	void  test4() {
		Article article = articleRepository.findById(1).get();

		article.setTitle("제목1");
		articleRepository.save(article);
	}

	@Test
	void test5() {
		articleRepository.deleteById(1);
	}

	@Test
	void test6() {
		Article article = articleRepository.findById(2).get();
		articleRepository.delete(article);
	}

}
