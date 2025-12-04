package college.project.EmailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private Logger logger= LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        simpleMailMessage.setFrom("ashishrana98277@gmail.com");
        mailSender.send(simpleMailMessage);

        logger.info("Email has been sent..");
    }

    @Override
    public void sendEmail(String[] to, String subject, String message) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("ashishrana98277@gmail.com");


        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlContent) {

        MimeMessage mimeMessage=mailSender.createMimeMessage();


        try{

            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,
                    true,"UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("Ashish Rana<ashishrana98277@gmail.com>");
            helper.setText(htmlContent,true);
            mimeMessage.setHeader("X-Priority", "1");
            mimeMessage.setHeader("Importance", "High");
            mailSender.send(mimeMessage);

            logger.info("Email has been sent...");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithHtml(String[] to, String subject, String htmlContent) {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        try{

            MimeMessageHelper mimeMessageHelper=new
                    MimeMessageHelper(mimeMessage,true,"UTF-8");

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom("Ashish Rana<ashishrana98277@gmail.com>");
            mimeMessageHelper.setText(htmlContent,true);
            mimeMessage.setHeader("X-Priority", "1");
            mimeMessage.setHeader("Importance", "High");

            mailSender.send(mimeMessage);


        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, File file) {
        MimeMessage mimeMessage=mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
            helper.setFrom("ashishrana98277@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message);

            FileSystemResource resource=new FileSystemResource(file);
            helper.addAttachment(resource.getFilename(),file);
            mailSender.send(mimeMessage);
            logger.info("Email send successful");
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, InputStream inputStream) {
        MimeMessage mimeMessage=mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
            helper.setFrom("ashishrana98277@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message);

            File file=new File("test.png");
            Files.copy(inputStream,file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileSystemResource fileSystemResource=new FileSystemResource(file);

            helper.addAttachment(fileSystemResource.getFilename(),file);

            mailSender.send(mimeMessage);
            logger.info("Email send successful.");
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
