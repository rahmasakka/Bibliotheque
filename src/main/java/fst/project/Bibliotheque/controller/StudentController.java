package fst.project.Bibliotheque.controller;

import java.util.List;
import javax.validation.ConstraintViolationException;
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
import fst.project.Bibliotheque.exception.StudentCollectionException;
import fst.project.Bibliotheque.model.StudentDTO;
import fst.project.Bibliotheque.service.StudentService;

@RestController
public class StudentController {
	/*
	@Autowired
	private StudentRepository studentRepo;
	*/
	@Autowired
	private StudentService studentService;

	@GetMapping("/students")
	public ResponseEntity<?> getAllStudents(){
		List<StudentDTO>students = studentService.getAllStudents();
		return new ResponseEntity<>(students, students.size() > 0? HttpStatus.OK: HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/students")
	public ResponseEntity<?> createStudent(@RequestBody StudentDTO student){
		try {
			studentService.createStudent(student);
			return new ResponseEntity<StudentDTO>(student, HttpStatus.OK);
		}catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch (StudentCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	/*
	@GetMapping("/students/{id}")
	public ResponseEntity<?>getSingleStudent(@PathVariable("id") String id){
		Optional<StudentDTO> studentOptional =  studentRepo.findById(id);
		if(studentOptional.isPresent()) {
			return new ResponseEntity<>(studentOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Student not found with id "+ id, HttpStatus.NOT_FOUND);
		}
	}
	*/
	
	
	@GetMapping("/students/{id}")
	public ResponseEntity<?>getSingleStudent(@PathVariable("id") String id){
		try {
			return new ResponseEntity<>(studentService.getSingleStudent(id), HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	@PutMapping("/students/{id}")
	public ResponseEntity<?>updateById(@PathVariable("id") String id, @RequestBody StudentDTO student){
		Optional<StudentDTO> studentOptional =  studentRepo.findById(id);
		if(studentOptional.isPresent()) {
			StudentDTO studentToSave = studentOptional.get();
			studentToSave.setCompleted(student.getCompleted() != null? student.getCompleted():studentToSave.getCompleted());
			studentToSave.setStudent(student.getStudent() != null ? student.getStudent(): studentToSave.getStudent());
			studentToSave.setDescription(student.getDescription() != null ? student.getDescription() : studentToSave.getDescription());
			studentToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
			studentToSave.setCreatedAt(new Date());
			studentRepo.save(studentToSave);
			return new ResponseEntity<>(studentToSave, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Student not found with id "+ id, HttpStatus.NOT_FOUND);
		}
	}*/
	
	
	@PutMapping("/students/{id}")
	public ResponseEntity<?>updateById(@PathVariable("id") String id, @RequestBody StudentDTO student){
		try {
			studentService.updateStudent(id, student);
			return new ResponseEntity<>("Update student with id " + id, HttpStatus.OK);
		}catch(ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(StudentCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);	
		}
	}
	
	/*
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id){
		try {
			studentRepo.deleteById(id);
			return new ResponseEntity<>("Successfully deleted with id "+ id, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}	
	
	*/
	
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id){
		try {
			studentService.deleteStudentById(id);
			return new ResponseEntity<>("Successfully deleted with id "+ id, HttpStatus.OK);
			
		}catch(StudentCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}	
}