package college.project.exception;

public class BloodBankAlreadyExistsException extends RuntimeException{
    public BloodBankAlreadyExistsException(String message){
        super(message);
    }
}
