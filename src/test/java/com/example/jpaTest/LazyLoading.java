package com.example.jpaTest;

import com.example.jpaTest.article.Article;
import com.example.jpaTest.member.Member;
import com.example.jpaTest.member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
public class LazyLoading {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    void t1(){
        Member member = memberRepository.findById(1).get();
        System.out.println("회원이름" + member.getName());

        List<Article> articles = member.getArticles();
        System.out.println("게시글 개수 :" + articles.size());
    }
//  sql이 필요한 시점까지 실행을 지연. LAZY Loading
}
