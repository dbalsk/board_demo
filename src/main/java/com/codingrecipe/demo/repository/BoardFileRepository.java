package com.codingrecipe.demo.repository;

import com.codingrecipe.demo.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {

}
