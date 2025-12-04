package college.project.EmailSender;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface   EmailService {

        //send email to single person
        void sendEmail(String to,String subject,String message);


        //send email to multiple person
        void sendEmail(String[]to,String subject,String message);


        //send email with html
        void sendEmailWithHtml(String to,String subject,String htmlContent);

        //send email with html
        void sendEmailWithHtml(String[] to,String subject,String htmlContent);



        //send email with file
        void sendEmailWithFile(String to, String subject, String message, File file);

        void sendEmailWithFile(String to, String subject, String message, InputStream inputStream );





}
