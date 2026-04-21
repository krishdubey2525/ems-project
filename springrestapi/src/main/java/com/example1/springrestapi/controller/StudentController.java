package com.example1.springrestapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {
    @GetMapping("/ss")
    public ResponseEntity<Student> getinfo(){

        Student student=new Student(1,"krish","dubey");
        return new ResponseEntity<>(student ,HttpStatus.OK);
    }
@GetMapping("/sss")
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"anshul","raj"));
        students.add(new Student(2,"neeraj","yadav"));
        return ResponseEntity.ok(students);
    }
    //by path variable,
    //
    @GetMapping("/s{id}/{fn}/{ln}")
    public ResponseEntity<Student> bypath(@PathVariable("id") int studentid, @PathVariable("fn") String fname, @PathVariable("ln") String lname ){
        Student student=new Student(studentid,fname,lname);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    //requestparam
    //by this we can change id fname and lname of any entry by ?
    @GetMapping("s/sa")
    public ResponseEntity<Student> byreqyest(@RequestParam int id,@RequestParam String fname,@RequestParam String lname){
        Student student=new Student(id,fname,lname);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Student> create(@RequestBody Student student){
        System.out.println(student.getId());
        System.out.println(student.getFname());
        System.out.println(student.getLname());
        return new ResponseEntity<>(student,HttpStatus.CREATED);
    }
    @PutMapping("/{id}/put")
    public ResponseEntity<Student> update(@RequestBody Student student,@PathVariable("id") int studentid){
        System.out.println(student.getFname());
        System.out.println(student.getLname());
        return new ResponseEntity<>(student,HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable("id") int studentid){
        System.out.println(studentid);
        return ResponseEntity.ok("id deleted successfully");
    }
}
