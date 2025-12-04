package college.project.exception;

public class DonorNotFound extends RuntimeException{
    public DonorNotFound(String message){
        super(message);
    }
}
