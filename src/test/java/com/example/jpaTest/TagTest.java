package com.example.jpaTest;

import com.example.jpaTest.article.Article;
import com.example.jpaTest.article.ArticleService;
import com.example.jpaTest.article.tag.ArticleTag;
import com.example.jpaTest.article.tag.ArticleTagRepository;
import com.example.jpaTest.article.tag.Tag;
import com.example.jpaTest.article.tag.TagService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
public class TagTest {

    @Autowired
    ArticleService articleService;
    @Autowired
    TagService tagService;

    @Autowired
    ArticleTagRepository articleTagRepository;

    @Test
    @Transactional
    @Rollback(false)
    void t1() {
        Article article1 = articleService.save("제목1", "내용1");
        Article article2 = articleService.save("제목2", "내용2");

        Tag tag1 = tagService.save("놀라움");
        Tag tag2 = tagService.save("공포");
        Tag tag3 = tagService.save("행복");

        // 1번 게시물에 1번 태그 추가
        ArticleTag articleTag1 = new ArticleTag();
        articleTag1.setArticle(article1);
        articleTag1.setTag(tag1);

        // 1번 게시물에 2번 태그 추가
        ArticleTag articleTag2 = new ArticleTag();
        articleTag2.setArticle(article1);
        articleTag2.setTag(tag2);

        // 2번 게시물에 1번 태그 추가
        ArticleTag articleTag3 = new ArticleTag();
        articleTag3.setArticle(article2);
        articleTag3.setTag(tag1);

        // 2번 게시물에 3번 태그 추가
        ArticleTag articleTag4 = new ArticleTag();
        articleTag4.setArticle(article2);
        articleTag4.setTag(tag3);

        articleTagRepository.save(articleTag1);
        articleTagRepository.save(articleTag2);
        articleTagRepository.save(articleTag3);
        articleTagRepository.save(articleTag4);
    }

    @Test
    @Transactional
    @Rollback(false)
    void t2() {
        // 추가
        Article article1 = articleService.findById(1);
        Tag tag1 = tagService.findById(3);

        ArticleTag articleTag = new ArticleTag();
        articleTag.setArticle(article1);
        articleTag.setTag(tag1);

        article1.getArticleTags().add(articleTag);

    }


    @Test
    @Transactional
    @Rollback(false)
    void t3() {
        // 삭제

        // 외래키로 참조되고 있는 객체는 먼저 지울 수 없다.
        // 참조하고 있는 객체를 먼저 지우고 참조되는 객체는 나중에 지운다.
        // 부모(1) - 자식(N) 관계에서는 자식이 먼저 삭제되어야 한다.

        Article article1 = articleService.findById(1);
        articleService.delete(article1); // 부모 삭제

    }

    @Test
    @Transactional
    @Rollback(false)
    void t4() {
        Article article = articleService.findById(1);

        List<ArticleTag> children = article.getArticleTags();

        for (ArticleTag child : children) {
            System.out.println("제목 : " + child.getArticle().getTitle());
            System.out.println("태그 : " + child.getTag().getName());
        }

        children.remove(0);
        System.out.println("=====================================");
        for (ArticleTag child : children) {
            System.out.println("제목 : " + child.getArticle().getTitle());
            System.out.println("태그 : " + child.getTag().getName());
        }


        // 잘쓰면 좋다.
        // 부모 객체 중심으로 코드를 짤 수가 있음.
        // 다른 서비스를 호출하지 않아도 됨.

        // 모르고 쓰면 큰일난다.
        // 연관관계가 복잡한 경우에 사용하면 안된다.
        // 한 부모가 완벽히 한 자식을 소유할 때만 사용하길 권장.

        // cascade.remove => 카테고리 : 게시물
        // cascade.remove, orphanRemoval => 신중하게 사용.

    }
}
