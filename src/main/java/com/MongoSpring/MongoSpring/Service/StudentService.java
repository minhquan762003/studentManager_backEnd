package com.MongoSpring.MongoSpring.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.MongoSpring.MongoSpring.Model.ResponseObject;
import com.MongoSpring.MongoSpring.Model.Student;
import com.MongoSpring.MongoSpring.Repository.StudentRepo;

@Service
public class StudentService implements WebMvcConfigurer {

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

    public ResponseObject addStudent(Student student) {
        Student savedStudent = studentRepo.save(student);
        return new ResponseObject("ok", "Add Student successfully", savedStudent);
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public ResponseObject getStudentById(String id) {
        Optional<Student> foundStudent = studentRepo.findById(id);
        if (foundStudent.isPresent()) {
            return new ResponseObject("ok", "Query student successfully", foundStudent);
        }
        return new ResponseObject("Failed", "No student found", "");
    }

    public ResponseObject deleteStudentById(@PathVariable String id) {
        boolean exist = studentRepo.existsById(id);
        if (exist) {
            studentRepo.deleteById(id);
            return new ResponseObject("ok", "Delete student successfully", getStudentById(id));
        }

        return new ResponseObject("Failed", "Can not find student with id = " + id + " to delete", "");
    }

    public ResponseObject updateStudentById(Student newStudent, String id) {
        if (existById(id)) {
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

            return new ResponseObject("ok", "Updated Student", updatedStudent);
        }else{
            return new ResponseObject("Failed", "Update Student Failed", "");
        }

    }

    public boolean existById(String id) {
        return this.studentRepo.existsById(id);
    }

}
