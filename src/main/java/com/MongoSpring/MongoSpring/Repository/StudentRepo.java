package com.MongoSpring.MongoSpring.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.MongoSpring.MongoSpring.Model.Student;
import java.util.*;
public interface StudentRepo extends MongoRepository<Student,String>{
    List<Student> findByname(String name);
}
