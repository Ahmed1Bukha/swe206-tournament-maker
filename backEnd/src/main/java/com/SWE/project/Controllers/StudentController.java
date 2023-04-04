package com.SWE.project.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SWE.project.Classes.Student;
import com.SWE.project.Exceptions.StudentNotFoundException;
import com.SWE.project.Repositories.StudentRepo;

@RestController
public class StudentController {

    @Autowired
    private final StudentRepo repo;

    public StudentController(StudentRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/students")
    List<Student> allStudents() {
        return repo.findAll();
    }

    @PostMapping("/students")
    Student newStudent(@RequestBody Student newStudent) {
        System.out.println(newStudent);
        return repo.save(newStudent);
    }

    @GetMapping("/students/{id}")
    Student oneStudent(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @PutMapping("/students/{id}")
    Student replaceStudent(@RequestBody Student newStudent, @PathVariable Long id) {
        /*
         * TODO:
         * Properly change all properties of the object after completing the class
         * structure.
         */

        return repo.findById(id).map(student -> {
            student.setName(newStudent.getName());
            // student.setGpa(newStudent.getGpa());
            return repo.save(student);
        }).orElseGet(() -> {
            newStudent.setId(id);
            return repo.save(newStudent);
        });
    }

    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
