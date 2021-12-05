package com.analytical.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.analytical.news.entities.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

}
