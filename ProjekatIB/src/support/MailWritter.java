package support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

public class MailWritter {
	public static void sendMessage(Gmail service, String userId, MimeMessage email)
		      throws MessagingException, IOException {
		    
			Message message = createMessageWithEmail(email);
		    message = service.users().messages().send(userId, message).execute();

		    System.out.println("Message id: " + message.getId());
		    System.out.println(message.toPrettyString());
		  }

		  /**
		   * Create a Message from an email
		   *
		   * @param email Email to be set to raw of message
		   * @return Message containing base64url encoded email.
		   * @throws IOException
		   * @throws MessagingException
		   */
		  private static Message createMessageWithEmail(MimeMessage email)
		      throws MessagingException, IOException {
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    email.writeTo(baos);
		    String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
		    Message message = new Message();
		    message.setRaw(encodedEmail);
		    return message;
		  }

}