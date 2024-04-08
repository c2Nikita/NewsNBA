package com.nvelich.newsnba.repositories;

import com.nvelich.newsnba.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    // Additional custom queries for News entity
}