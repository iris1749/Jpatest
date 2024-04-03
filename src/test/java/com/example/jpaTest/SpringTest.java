package com.example.jpaTest;


import com.example.jpaTest.article.ArticleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringTest {

    @Autowired
    ArticleController articleController;
    @Test
    void t1() {
        articleController.test();
    }
}
