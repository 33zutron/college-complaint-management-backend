package com.anurag.cs.entity;

import java.time.LocalDateTime;

import com.anurag.cs.enums.ComplaintStatus;
import com.anurag.cs.enums.Priority;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    @jakarta.persistence.Column(length = 2000)
    private String description;
    @jakarta.persistence.Column(length = 1000)
    private String withdrawReason;
    private LocalDateTime withdrawnAt;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Department department;

    @ManyToOne
    private ComplaintCategory category;

    @Enumerated(EnumType.STRING)
    @jakarta.persistence.Column(length = 32)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @jakarta.persistence.Column(length = 32)
    private ComplaintStatus status;

    private LocalDateTime createdAt;

    @jakarta.persistence.Column(length = 1000)
    private String attachmentUrl;
    @jakarta.persistence.Column(length = 255)
    private String attachmentName;
    @jakarta.persistence.Column(length = 100)
    private String attachmentType;

    public Complaint() {
        this.createdAt = LocalDateTime.now();
        this.status = ComplaintStatus.OPEN;
    }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }
    public String getAttachmentName() { return attachmentName; }
    public void setAttachmentName(String attachmentName) { this.attachmentName = attachmentName; }
    public String getAttachmentType() { return attachmentType; }
    public void setAttachmentType(String attachmentType) { this.attachmentType = attachmentType; }

    public String getWithdrawReason() { return withdrawReason; }
    public void setWithdrawReason(String r) { this.withdrawReason = r; }
    public LocalDateTime getWithdrawnAt() { return withdrawnAt; }
    public void setWithdrawnAt(LocalDateTime t) { this.withdrawnAt = t; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ComplaintCategory getCategory() {
        return category;
    }

    public void setCategory(ComplaintCategory category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
