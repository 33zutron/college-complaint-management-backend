package com.anurag.cs.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.anurag.cs.entity.Department;
import com.anurag.cs.repository.DepartmentRepository;

@RestController
@RequestMapping("/departments")
@CrossOrigin(origins = "${app.frontend-url:http://localhost:5173}")
public class DepartmentController {
    @Autowired private DepartmentRepository repo;

    @GetMapping public List<Department> getAll() { return repo.findAll(); }

    @PostMapping @PreAuthorize("hasRole('ADMIN')")
    public Department create(@RequestBody Department body) { body.setId(null); return repo.save(body); }

    @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public Department update(@PathVariable Long id, @RequestBody Department body) {
        Department x = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        x.setName(body.getName());
        return repo.save(x);
    }

    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> delete(@PathVariable Long id) {
        try { repo.deleteById(id); }
        catch (DataIntegrityViolationException e) { throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete: it is still used by complaints"); }
        return Map.of("deleted", id);
    }
}
