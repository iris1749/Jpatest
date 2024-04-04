package com.example.jpaTest;

import com.example.jpaTest.article.Article;
import com.example.jpaTest.article.ArticleRepository;
import jakarta.transaction.Transactional;
import org.hibernate.dialect.function.array.ArrayToStringFunction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

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

	@Autowired
	AccountRepository accountRepository;

	// 트랜잭션
	@Test
	void t7() {
		Account account1 = new Account();
		account1.setOwner("hong");
		account1.setAmount(10000);

		Account account2 = new Account();
		account2.setOwner("kim");
		account2.setAmount(10000);

		accountRepository.save(account1);
		accountRepository.save(account2);

	}

	// 트랜잭션을 제대로 처리하지 않으면 데이터가 꼬일 수 있음
	@Test
	void t8() {
		// hong -> kim 1000원 보내기
		Account hong = accountRepository.findById(1).get();
		Account kim = accountRepository.findById(2).get();

		hong.setAmount(hong.getAmount() - 1000); // 1000 감소
		accountRepository.save(hong);

		kim.setAmount(kim.getAmount() + 1000); // 1000 증가
		accountRepository.save(kim);

	}

	@Test
	@Transactional
	// 트랜잭션을 사용한 경우 처리과정중에 오류가 발생 할 경우 이전에 처리된 내역이 저장 되지않고
	// 작업을 하기 전 상태로 데이터를 되돌림

	void t9() {
		Account hong = accountRepository.findById(1).get();
		Account kim = accountRepository.findById(2).get();

		hong.setAmount(hong.getAmount() - 1000); // 1000 감소
		accountRepository.save(hong);

		if(true) {
			throw new RuntimeException("강제로 예외 발생");
		}

		kim.setAmount(kim.getAmount() + 1000); // 1000 증가
		accountRepository.save(kim);

	}

	@Test
	void t10(){
		Article article1 = new Article();
		article1.setTitle("제목3");
		article1.setContent("내용3");
		articleRepository.save(article1);

		Article article2 = new Article();
		article2.setTitle("제목4");
		article2.setContent("내용4");
		articleRepository.save(article2);

		//DB에서 바로 꺼내오는것이 아님
		// 1차 캐시에서 저장된후 꺼내오는거임.
		Article target = articleRepository.findById(article1.getId()).get();
		System.out.println(target.getTitle());
		System.out.println(target.getContent());
	}

	@Test
	@Transactional
	@Rollback(false)
	void t11() {
		Article article = articleRepository.findById(3).get();
		System.out.println(article.getTitle());
		System.out.println(article.getContent());
	}

	@Test
	@Transactional
	@Rollback(false)
	void t12(){
		Article article1 = new Article();
		article1.setTitle("제목1");
		article1.setContent("내용1");

		System.out.println("==========article1 저장==========");
		articleRepository.save(article1);
		System.out.println("=================================");

		Article article2 = new Article();
		article2.setTitle("제목2");
		article2.setContent("내용2");

		System.out.println("==========article2 저장==========");
		articleRepository.save(article2);
		System.out.println("=================================");

	}

	// jpa 쓰기 지연됨
	@Test
	@Transactional
	@Rollback(false)
	void t13(){
		Article article1 = articleRepository.findById(1).get();
		System.out.println(article1.getId());
		System.out.println(article1.getTitle());

		Article article2 = articleRepository.findById(2).get();
		System.out.println(article2.getId());
		System.out.println(article2.getTitle());

		System.out.println("==========article1 삭제==========");
		articleRepository.delete(article1);
		System.out.println("=================================");

		System.out.println("==========article2 삭제==========");
		articleRepository.delete(article2);
		System.out.println("=================================");

	}

	@Test
	@Transactional
	@Rollback(false)
	void t14(){
		Article article = articleRepository.findById(3).get();

		article.setTitle("제목44");
		article.setContent("내용44");

//		articleRepository.save(article);

//		업데이트를 하지 않아도 변경된 쿼리가 있을경우 저절로 update하여 갱신한다.

	}

	// 영속성 컨텍스트

	// 연관 관계

	// 지연로딩

	// 영속성 전이

}
