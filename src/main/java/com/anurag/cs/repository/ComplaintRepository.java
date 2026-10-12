package com.anurag.cs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anurag.cs.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStudentId(Long studentId);
}
