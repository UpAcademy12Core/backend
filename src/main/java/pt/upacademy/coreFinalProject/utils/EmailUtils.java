package pt.upacademy.coreFinalProject.utils;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;



@RequestScoped
public class EmailUtils {
	
	private static boolean active = false;

	public static void sendNewUser(UserDTO userDTO) throws IOException {
		if (!active) {
			return;
		}
		Mail mail = new Mail();
		Email fromEmail = new Email();
	    fromEmail.setName(System.getProperty("SGFromName"));
	    fromEmail.setEmail(System.getProperty("SGFromEmail"));
	    mail.setFrom(fromEmail);

	    mail.setTemplateId(System.getProperty("SGTemplateId"));

	    Personalization personalization = new Personalization();
	    personalization.addDynamicTemplateData("user" , userDTO);
	    personalization.addTo(new Email(userDTO.getEmail()));
	    mail.addPersonalization(personalization);
		
		try {
			SendGrid sg = new SendGrid(System.getProperty("SGKey"));
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}

public boolean validEmailAdress(String email) {
    Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    if (pattern.matcher(email).matches() == false) {
        throw new IllegalArgumentException("Invalid Email Adress");
    }
    else  {
    	return true;
    }
}
}


