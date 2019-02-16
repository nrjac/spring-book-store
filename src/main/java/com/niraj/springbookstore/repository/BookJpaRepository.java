package com.niraj.springbookstore.repository;

import com.niraj.springbookstore.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookJpaRepository extends JpaRepository<Books, Long>{
    Books findByBookName(String name);
    Books findOneById(Long id);
    @Query("SELECT b FROM Books b where b.publishDate like :date%")
    List<Books> findByPublishDate(@Param("date") String date);
}

