package com.anurag.cs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anurag.cs.entity.ComplaintCategory;
import com.anurag.cs.repository.ComplaintCategoryRepository;

@Service
public class ComplaintCategoryService {
    @Autowired
    private ComplaintCategoryRepository repo;

    public ComplaintCategory create(ComplaintCategory category) {
        return repo.save(category);
    }

    public List<ComplaintCategory> getAll() {
        return repo.findAll();
    }
}
