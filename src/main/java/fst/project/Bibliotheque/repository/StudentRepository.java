package fst.project.Bibliotheque.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import fst.project.Bibliotheque.model.StudentDTO;

@Repository
public interface StudentRepository extends MongoRepository<StudentDTO, String> {

	@Query("{'student': ?0}")
	Optional<StudentDTO> findByStudent(String student);
}