package college.project.exception;

public class DonorAlreadyExistsException extends RuntimeException{
    public DonorAlreadyExistsException(String message) {
        super(message);
    }
}
