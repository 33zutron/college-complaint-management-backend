package com.anurag.cs.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.anurag.cs.dto.*;
import com.anurag.cs.entity.*;
import com.anurag.cs.service.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/complaints")
@CrossOrigin(origins = "${app.frontend-url:http://localhost:5173}")
public class ComplaintController {
    @Autowired private ComplaintService service;
    @Autowired private ComplaintResponseService responseService;

    private Student me(Authentication a) { return (Student) a.getPrincipal(); }

    // ---- student ----
    @PostMapping @PreAuthorize("hasRole('STUDENT')")
    public Complaint create(@Valid @RequestBody ComplaintDTO dto, Authentication a) { return service.createComplaint(dto, me(a)); }

    @GetMapping("/mine") @PreAuthorize("hasRole('STUDENT')")
    public List<Complaint> mine(Authentication a) { return service.getComplaintsByStudent(me(a).getId()); }

    @PutMapping("/{id}/withdraw") @PreAuthorize("hasRole('STUDENT')")
    public Complaint withdraw(@PathVariable Long id, @RequestBody Map<String, String> body, Authentication a) {
        return service.withdraw(id, body.get("reason"), me(a));
    }

    // ---- shared (ownership enforced in service) ----
    @GetMapping("/{id}")
    public Complaint get(@PathVariable Long id, Authentication a) { return service.getForUser(id, me(a)); }

    @GetMapping("/{id}/responses")
    public List<ComplaintResponse> responses(@PathVariable Long id, Authentication a) {
        service.getForUser(id, me(a));
        return responseService.getResponses(id);
    }

    // ---- admin ----
    @GetMapping @PreAuthorize("hasRole('ADMIN')")
    public List<Complaint> all() { return service.getAllComplaints(); }

    @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public Complaint update(@PathVariable Long id, @Valid @RequestBody ComplaintDTO dto) { return service.update(id, dto); }

    @PutMapping("/{id}/status") @PreAuthorize("hasRole('ADMIN')")
    public Complaint status(@PathVariable Long id, @RequestBody ComplaintStatusDTO dto) { return service.updateStatus(id, dto); }

    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> delete(@PathVariable Long id) { return Map.of("message", service.deleteComplaint(id)); }

    @PostMapping("/{id}/responses") @PreAuthorize("hasRole('ADMIN')")
    public ComplaintResponse respond(@PathVariable Long id, @Valid @RequestBody ComplaintResponseDTO dto) { return responseService.addResponse(id, dto); }
}
