package com.anurag.cs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anurag.cs.entity.ComplaintCategory;

public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategory, Long> {
}
