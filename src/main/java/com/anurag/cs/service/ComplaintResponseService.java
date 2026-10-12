package com.anurag.cs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anurag.cs.dto.ComplaintResponseDTO;
import com.anurag.cs.entity.Complaint;
import com.anurag.cs.entity.ComplaintResponse;
import com.anurag.cs.repository.ComplaintRepository;
import com.anurag.cs.repository.ComplaintResponseRepository;

@Service
public class ComplaintResponseService {
    @Autowired
    private ComplaintResponseRepository repo;

    @Autowired
    private ComplaintRepository complaintRepo;

    public ComplaintResponse addResponse(Long complaintId, ComplaintResponseDTO dto) {
        Complaint complaint = complaintRepo.findById(complaintId).orElse(null);
        if (complaint == null) {
            return null;
        }
        ComplaintResponse response = new ComplaintResponse();
        response.setComplaint(complaint);
        response.setMessage(dto.getMessage());
        response.setRespondedBy(dto.getRespondedBy());
        if (dto.getAttachmentUrl() != null) {
            response.setAttachmentUrl(dto.getAttachmentUrl());
            response.setAttachmentName(dto.getAttachmentName());
            response.setAttachmentType(dto.getAttachmentType());
        }
        return repo.save(response);
    }

    public List<ComplaintResponse> getResponses(Long complaintId) {
        return repo.findByComplaintId(complaintId);
    }
}
