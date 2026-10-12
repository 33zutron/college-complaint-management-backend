package com.anurag.cs.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.anurag.cs.entity.*;
import com.anurag.cs.enums.Role;
import com.anurag.cs.repository.*;

/** Creates a default admin plus starter departments/categories on first run. */
@Component
public class DataSeeder implements CommandLineRunner {
      @Autowired StudentRepository students;
      @Autowired DepartmentRepository depts;
      @Autowired ComplaintCategoryRepository cats;
      @Autowired PasswordEncoder pe;

      public void run(String... args) {
            if (students.findByEmail("admin@college.edu") == null) {
                  Student a = new Student(); a.setName("Administrator"); a.setEmail("admin@college.edu");
                  a.setRole(Role.ADMIN); a.setPassword(pe.encode("Admin@123")); students.save(a);
            }
            if (depts.count() == 0) for (String n : List.of("Academic Affairs", "Hostel & Mess", "Transport", "Facilities & Maintenance", "Examination Cell", "IT Support")) { Department d = new Department(); d.setName(n); depts.save(d); }
            if (cats.count() == 0) for (String n : List.of("Academics", "Hostel", "Transport", "Infrastructure", "Examination", "Technical Support")) { ComplaintCategory c = new ComplaintCategory(); c.setName(n); cats.save(c); }
      }
}
