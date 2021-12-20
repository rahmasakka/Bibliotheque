package fst.project.Bibliotheque.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import fst.project.Bibliotheque.exception.StudentCollectionException;
import fst.project.Bibliotheque.model.StudentDTO;

public interface StudentService {
	public void createStudent(StudentDTO student)throws ConstraintViolationException,StudentCollectionException;
	public List<StudentDTO> getAllStudents();
	public StudentDTO getSingleStudent(String id) throws StudentCollectionException;
	public void updateStudent(String id, StudentDTO student) throws StudentCollectionException;
	public void deleteStudentById(String id)throws StudentCollectionException;	
}