package fst.project.Bibliotheque.exception;

public class StudentCollectionException extends Exception {

	
	private static final long serialVersionUID = 1L;
	public StudentCollectionException(String message) {
		super(message);	
	}
	
	public static String NotFoundException(String id) {
		return "Student with "+ id + " not Found!";
	}
	
	public static String StudentAlreadyExists() {
		return "Student with given name already exists!";
	}
}