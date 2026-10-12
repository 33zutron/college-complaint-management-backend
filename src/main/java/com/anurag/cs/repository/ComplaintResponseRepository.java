package com.anurag.cs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anurag.cs.entity.ComplaintResponse;

public interface ComplaintResponseRepository extends JpaRepository<ComplaintResponse, Long> {
    List<ComplaintResponse> findByComplaintId(Long complaintId);
    void deleteByComplaintId(Long complaintId);
}
