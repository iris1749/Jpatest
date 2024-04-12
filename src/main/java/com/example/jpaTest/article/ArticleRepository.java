package com.example.jpaTest.article;

import com.example.jpaTest.article.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//JpaRepository 상송 받아 interface 생성
// <대상엔디디, id타입>
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    void deleteByArticle(Article article);

    // find => 하고자 액션
    // delete, findAll
    // by => 조건
    // Title => 컬럼
    List<Article> findByTitleAndContent(String title, String content);

    // 게시물 제목에 특정 단어가 포함된 게시물 검색
    @Query(value = """
            select a
            from Article a
            where a.title LIKE concat('%', :title, '%')
    """)
    List<Article> findByTitleLike(@Param("title") String title);

    @Query("""
        select new com.example.jpaExam.article.MyResultDto(a.id, a.title, a.content)
          from Article a
         where a.id = 1
    """)
    List<MyResultDto> findTitleAndContent();
}
