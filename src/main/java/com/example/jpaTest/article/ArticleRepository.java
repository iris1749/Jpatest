package com.example.jpaTest.article;

import com.example.jpaTest.article.Article;

import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository 상송 받아 interface 생성
// <대상엔디디, id타입>
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    void deleteByArticle(Article article);
}
