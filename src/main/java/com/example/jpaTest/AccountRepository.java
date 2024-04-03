package com.example.jpaTest;

import com.example.jpaTest.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
