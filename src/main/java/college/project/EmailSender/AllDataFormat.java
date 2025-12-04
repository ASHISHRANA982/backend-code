package college.project.EmailSender;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AllDataFormat {

    public String OneTimePasswordSend(String password,String username,String name){

        String message="<body style=\"font-family: Arial, sans-serif; background-color: #f5f7fa; padding: 30px;\">\n" +
                "    <div style=\"max-width: 500px; background-color: #ffffff; margin: auto; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); padding: 25px;\">\n" +
                "        <h2 style=\"color: #2E86C1; text-align: center;\">Verification Code</h2>\n" +
                "\n" +
                "        <p>Dear <strong>"+name+"</strong>,</p>\n" +
                "\n" +
                "        <p>We received a request to verify your identity. Please use the following Auto Generated Password to complete your Login Process:</p>\n" +
                "        <p>Username: "+username+"</p>\n" +

                "\n" +
                "        <div style=\"text-align: center; margin: 10px 0;\">\n" +
                "            <h1 style=\"background-color: #2E86C1; color: white; display: inline-block; padding: 10px 20px; border-radius: 5px;\">\n" +
                "                "+password+"\n" +
                "            </h1>\n" +
                "        </div>\n" +
                "\n" +
                "        <p>Please change username and password <strong>(Forget Password)</strong> after One Time Login. Do not share it with anyone.</p>\n" +
                "\n" +
                "        <p>If you did not request this, please ignore this email or contact our support team immediately.</p>\n" +
                "\n" +
                "        <br>\n" +
                "        <p>Best regards,</p>\n" +
                "        <p><strong>Kartavya Team</strong></p>\n" +
                "    </div>\n" +
                "</body>";

        return message;
    }

    public String sendOTP(String otp,String name){
        String html = """
            <html><body style='font-family:Arial;'>
                <h3>Hello, %s</h3>
                <p>Your verification code to update your username or password is:</p>
                <h2 style='color:#2E86C1;'>%s</h2>
                <p>This code will expire in 10 minutes.</p>
                <p>Thanks,<br><strong>BloodBank Connect Team</strong></p>
            </body></html>
            """.formatted(name, otp);
        return html;
    }

    public String UpdateLoginSend(String name){

        String html="<body style=\"font-family: Arial, sans-serif; background-color: #f5f7fa; padding: 30px;\">\n" +
                "  <div style=\"max-width: 550px; background-color: #ffffff; margin: auto; border-radius: 10px; \n" +
                "              box-shadow: 0 4px 8px rgba(0,0,0,0.1); padding: 25px;\">\n" +
                "              \n" +
                "    <h2 style=\"color: #2E86C1; text-align: center;\">Your Account Information Has Been Updated</h2>\n" +
                "\n" +
                "    <p>Dear <strong>"+name+"</strong>,</p>\n" +
                "\n" +
                "    <p>This is to inform you that your login credentials for your <strong> Kartavya </strong> account have been successfully updated.</p>\n" +
                "\n" +
                "\n" +
                "    <p>If you made this change, no further action is required.</p>\n" +
                "    <p>If you <strong>did not</strong> request this update, please contact our support team immediately to secure your account.</p>\n" +
                "\n" +
                "    <p style=\"margin-top: 25px;\">Stay safe,<br>\n" +
                "    <strong>Kartavya Team</strong></p>\n" +
                "  </div>\n" +
                "</body>";

        return html;
    }

    public String RequestForBlood(String uName, String hName, String bloodGroup, int units, LocalDateTime currentDate){
        String html="\n" +
                "<body style=\"font-family: Arial, sans-serif; background-color: #f4f6f8; padding: 24px;\">\n" +
                "  <div style=\"max-width:600px; margin: auto; background: #ffffff; border-radius: 10px; padding: 22px; box-shadow: 0 4px 12px rgba(0,0,0,0.06);\">\n" +
                "    <h2 style=\"color:#c62828; text-align:center; margin-top:0;\">Urgent Blood Request</h2>\n" +
                "\n" +
                "    <p style=\"margin:8px 0;\">\n" +
                "      <strong>Requester: "+uName+"</strong><br>\n" +
                "      <strong>Hospital / Location: "+hName+"</strong><br>\n" +
                "      \n" +
                "    </p>\n" +
                "\n" +
                "    <p style=\"margin:12px 0; font-size:16px;\">\n" +
                "      <strong>Blood Group: </strong> <span style=\"background:#e8f0ff;padding:6px 10px;border-radius:6px;\">"+bloodGroup+"</span>\n" +
                "      &nbsp;&nbsp;\n" +
                "      <strong>Required Units: "+units+"</strong>\n" +
                "    </p>\n" +
                "    \n" +
                "    <p style=\"text-align:center; font-style:italic; color:#c62828; margin-top:20px;\">\n" +
                "  \"A few minutes of your time can give someone a lifetime of memories.\"\n" +
                "</p>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <table style=\"width:100%; font-size:14px; margin-top:10px; border-collapse:collapse;\">\n" +
                "    \n" +
                "      <tr>\n" +
                "        <td style=\"vertical-align:top; padding:6px 0;\"><strong>Needed By</strong></td>\n" +
                "        <td style=\"padding:6px 0;\"> "+currentDate+" </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "\n" +
                "    <p style=\"color:#555; font-size:13px; margin-top:18px;\">\n" +
                "      Please do not reply to this automated message. If you have concerns, visit to our <a href=\"#\">{{YourApp URL link}}</a>\n" +
                "    </p>\n" +
                "\n" +
                "    <hr style=\"border:none; border-top:1px solid #eee; margin:18px 0;\" />\n" +
                "\n" +
                "    <p style=\"font-size:12px; color:#888; margin:0;\">\n" +
                "      {{YourAppName}} — supporting safe blood donations. <br>\n" +
                "      <span style=\"color:#aaa;\">If this request was created in error, ignore this email or contact support at <a href=\"mailto:support@yourappname.com\">support@yourappname.com</a>.</span>\n" +
                "    </p>\n" +
                "  </div>\n" +
                "</body>\n";

        return html;
    }

}
