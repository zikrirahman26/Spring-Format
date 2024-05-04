package com.zikrirahman.id.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zikrirahman.id.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    
    List<Book> findByNameContainingOrderByIdDesc(String substring);

    List<Book> findByTotalLessThanEqual(Long total);

    List<Book> findByNameOrTotal(String name, Long total);
}
