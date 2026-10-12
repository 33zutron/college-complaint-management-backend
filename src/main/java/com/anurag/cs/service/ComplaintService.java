package com.anurag.cs.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.anurag.cs.dto.*;
import com.anurag.cs.entity.*;
import com.anurag.cs.enums.*;
import com.anurag.cs.repository.*;

@Service
public class ComplaintService {
    @Autowired private ComplaintRepository repo;
    @Autowired private ComplaintResponseRepository responseRepo;
    @Autowired private DepartmentRepository departmentRepo;
    @Autowired private ComplaintCategoryRepository categoryRepo;

    private ResponseStatusException err(HttpStatus s, String m) { return new ResponseStatusException(s, m); }

    private Complaint find(Long id) { return repo.findById(id).orElseThrow(() -> err(HttpStatus.NOT_FOUND, "Complaint not found")); }

    private void apply(Complaint c, ComplaintDTO dto) {
        c.setSubject(dto.getSubject());
        c.setDescription(dto.getDescription());
        c.setDepartment(dto.getDepartmentId() == null ? null : departmentRepo.findById(dto.getDepartmentId()).orElse(null));
        c.setCategory(dto.getCategoryId() == null ? null : categoryRepo.findById(dto.getCategoryId()).orElse(null));
        c.setPriority(dto.getPriority() != null ? Priority.valueOf(dto.getPriority()) : Priority.MEDIUM);
        if (dto.getAttachmentUrl() != null) {
            c.setAttachmentUrl(dto.getAttachmentUrl());
            c.setAttachmentName(dto.getAttachmentName());
            c.setAttachmentType(dto.getAttachmentType());
        }
    }

    public Complaint createComplaint(ComplaintDTO dto, Student student) {
        Complaint c = new Complaint();
        apply(c, dto);
        c.setStudent(student); // always the logged-in student, never trusted from the request
        c.setStatus(ComplaintStatus.OPEN);
        return repo.save(c);
    }

    public List<Complaint> getAllComplaints() { return repo.findAll(); }

    public List<Complaint> getComplaintsByStudent(Long id) { return repo.findByStudentId(id); }

    /** Students may only see their own complaints; admins see all. */
    public Complaint getForUser(Long id, Student user) {
        Complaint c = find(id);
        if (user.getRole() != Role.ADMIN && (c.getStudent() == null || !c.getStudent().getId().equals(user.getId())))
            throw err(HttpStatus.FORBIDDEN, "This is not your complaint");
        return c;
    }

    public Complaint update(Long id, ComplaintDTO dto) { Complaint c = find(id); apply(c, dto); return repo.save(c); }

    public Complaint updateStatus(Long id, ComplaintStatusDTO dto) {
        Complaint c = find(id);
        if (dto.getStatus() != null) {
            ComplaintStatus s = ComplaintStatus.valueOf(dto.getStatus());
            if (s == ComplaintStatus.WITHDRAWN) throw err(HttpStatus.BAD_REQUEST, "Only the student can withdraw a complaint");
            c.setStatus(s);
        }
        if (dto.getDepartmentId() != null) c.setDepartment(departmentRepo.findById(dto.getDepartmentId()).orElse(null));
        return repo.save(c);
    }

    public Complaint withdraw(Long id, String reason, Student user) {
        Complaint c = getForUser(id, user);
        if (reason == null || reason.trim().length() < 10) throw err(HttpStatus.BAD_REQUEST, "Please give a reason (at least 10 characters)");
        if (c.getStatus() != ComplaintStatus.OPEN && c.getStatus() != ComplaintStatus.IN_PROGRESS)
            throw err(HttpStatus.BAD_REQUEST, "A " + c.getStatus() + " complaint can no longer be withdrawn");
        c.setStatus(ComplaintStatus.WITHDRAWN);
        c.setWithdrawReason(reason.trim());
        c.setWithdrawnAt(LocalDateTime.now());
        return repo.save(c);
    }

    @Transactional
    public String deleteComplaint(Long id) {
        find(id);
        responseRepo.deleteByComplaintId(id);
        repo.deleteById(id);
        return "Complaint deleted successfully";
    }
}
