package com.MongoSpring.MongoSpring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;

import com.MongoSpring.MongoSpring.Model.ResponseObject;
import com.MongoSpring.MongoSpring.Model.Student;
import com.MongoSpring.MongoSpring.Service.StudentService;

@RestController
public class MainController implements WebMvcConfigurer {

    @Autowired
    private StudentService studentService;

    
    @PostMapping("/addStudent")
    ResponseEntity<ResponseObject> addStudent(@RequestBody Student student) {
        ResponseObject responseObject = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getStudentById(@PathVariable String id) {
        ResponseObject responseObject = studentService.getStudentById(id);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteStudentById(@PathVariable String id) {
        boolean exist = studentService.existById(id);
        if (exist) {
            ResponseObject responseObject = studentService.deleteStudentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "Can not find student with id = " + id + " to delete", ""));
    }

    
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateStudentById(@RequestBody Student newStudent, @PathVariable String id) {
        ResponseObject responseObject = studentService.updateStudentById(newStudent, id);
        if("Failed".equals(responseObject.getStatus())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);

    }

}
