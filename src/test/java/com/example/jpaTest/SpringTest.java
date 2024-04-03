package com.example.jpaTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringTest {

    @Autowired
    com.example.jpaTest.ArticleController articleController;
    @Test
    void t1() {
        articleController.test();
    }
}
