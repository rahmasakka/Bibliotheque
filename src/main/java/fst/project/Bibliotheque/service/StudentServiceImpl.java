package fst.project.Bibliotheque.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fst.project.Bibliotheque.exception.StudentCollectionException;
import fst.project.Bibliotheque.model.StudentDTO;
import fst.project.Bibliotheque.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepo;
	
	@Override
	public void createStudent(StudentDTO student)throws ConstraintViolationException, StudentCollectionException {
		Optional<StudentDTO> studentOptional = studentRepo.findByStudent(student.getStudent());
		if(studentOptional.isPresent()) {
			throw new StudentCollectionException(StudentCollectionException.StudentAlreadyExists());
		}else {
			student.setCreatedAt(new Date(System.currentTimeMillis()));
			studentRepo.save(student);
		}
	}
	
	public List<StudentDTO>getAllStudents(){
		List<StudentDTO> students = studentRepo.findAll();
		if(students.size()>0) {
			return students;
		}else {
			return new ArrayList<StudentDTO>();
		}
	}
	
	
	@Override
	public StudentDTO getSingleStudent(String id) throws StudentCollectionException{
		Optional<StudentDTO> optionalStudent = studentRepo.findById(id);
		if(!optionalStudent.isPresent()) {
			throw new StudentCollectionException(StudentCollectionException.NotFoundException(id));
		}else {
			return optionalStudent.get();
		}
	}

	
	@Override
	public void updateStudent(String id, StudentDTO student) throws StudentCollectionException{
		Optional<StudentDTO> studentWithId = studentRepo.findById(id);
		Optional<StudentDTO> studentWithSameName = studentRepo.findByStudent(student.getStudent());
		
		if(studentWithId.isPresent()) {
			
			if(studentWithSameName.isPresent() && !studentWithSameName.get().getId().equals(id)) {
				throw new StudentCollectionException(StudentCollectionException.StudentAlreadyExists());
			}
			
			StudentDTO studentToUpdate = studentWithId.get();
			studentToUpdate.setStudent(student.getStudent());
			studentToUpdate.setDescription(student.getDescription());
			studentToUpdate.setCompleted(student.getCompleted());
			studentToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
			studentRepo.save(studentToUpdate);
			
		}else {
			throw new StudentCollectionException(StudentCollectionException.NotFoundException(id));
		}
	}
	
	
	@Override
	public void deleteStudentById(String id) throws StudentCollectionException{
		Optional<StudentDTO> studentOptional = studentRepo.findById(id);
		if(!studentOptional.isPresent()) {
			throw new StudentCollectionException(StudentCollectionException.NotFoundException(id));
		}else{
			studentRepo.deleteById(id);
		}	
	}
}