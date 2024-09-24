package com.MongoSpring.MongoSpring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;

import com.MongoSpring.MongoSpring.Model.ResponseObject;
import com.MongoSpring.MongoSpring.Model.Student;
import com.MongoSpring.MongoSpring.Repository.StudentRepo;

@RestController
public class MainController implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Autowired
    StudentRepo studentRepo;

    
    @PostMapping("/addStudent")
    ResponseEntity<ResponseObject> addStudent(@RequestBody Student student) {
        // List<Student> foundStudents =
        // studentRepo.findByname(student.getName().trim());
        // if(foundStudents.size() > 0){
        // return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
        // new ResponseObject("failed", "Student name already taken", "")
        // );
        // }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Add Student successfully", studentRepo.save(student)));

    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getStudentById(@PathVariable String id) {
        Optional<Student> foundStudent = studentRepo.findById(id);
        if (foundStudent.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query student successfully", foundStudent));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "No student found", ""));
    }

    
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteStudentById(@PathVariable String id) {
        boolean exist = studentRepo.existsById(id);
        if (exist) {
            studentRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete student successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "Can not find student with id = " + id + " to delete", ""));
    }

    
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateStudentById(@RequestBody Student newStudent, @PathVariable String id) {
        Student updatedStudent = studentRepo.findById(id).map(
                student -> {
                    student.setName(newStudent.getName());
                    student.setAddress(newStudent.getAddress());
                    student.setClassName(newStudent.getClassName());
                    student.setBirthDay(newStudent.getBirthDay());
                    return studentRepo.save(student);
                }).orElseGet(() -> {
                    return studentRepo.save(newStudent);
                });

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Updated Student", updatedStudent));

    }

}
