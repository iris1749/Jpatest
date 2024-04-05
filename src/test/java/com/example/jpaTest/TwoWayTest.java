package com.example.jpaTest;

import com.example.jpaTest.article.Article;
import com.example.jpaTest.article.ArticleRepository;
import com.example.jpaTest.member.Member;
import com.example.jpaTest.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class TwoWayTest {


    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    void t1() {
        Member member = new Member();
        member.setName("회원1");

        Article article1 = new Article();
        article1.setTitle("제목1");
        article1.setContent("내용1");

        Article article2 = new Article();
        article2.setTitle("제목2");
        article2.setContent("내용2");

//        관계의 주인이 아닌 member기준으로 객체 셋팅 => DB에 재대로 반영이 되지 않음
//        member.getArticles().add(article1);
//        member.getArticles().add(article2);

//        관계의 주인인 article기준으로 객체 세팅 => DB에 재대로 반영됨.
//        article1.setMember(member);
//        article2.setMember(member);

//        member에 article를 추가하는 메서드를 따로 만들어서 정리
        member.addArticle(article1);
        member.addArticle(article2);

        memberRepository.save(member);
        articleRepository.save(article1);
        articleRepository.save(article2);


    }

}
