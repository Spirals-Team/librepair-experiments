package com.mailosaur;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mailosaur.models.Attachment;
import com.mailosaur.models.Message;
import com.mailosaur.models.SearchCriteria;
import com.mailosaur.models.SpamAssassinRule;
import com.mailosaur.models.SpamCheckResult;

public class FilesTest {
	private static MailosaurClient client;
	private static String server;
	private static Message email;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private String isoDateString = dateFormat.format(Calendar.getInstance().getTime());
	
	@BeforeClass
    public static void setUpBeforeClass() throws IOException, InterruptedException, MessagingException {
		server = System.getenv("MAILOSAUR_SERVER");
		String apiKey = System.getenv("MAILOSAUR_API_KEY");
		String baseUrl = System.getenv("MAILOSAUR_BASE_URL");
		String host = System.getenv("MAILOSAUR_SMTP_HOST");
		host = (host == null) ? "mailosaur.io" : host;
		
		if (apiKey == null || server == null) {
			throw new IOException("Missing necessary environment variables - refer to README.md");
		}
        
        client = new MailosaurClient(apiKey, baseUrl);
        
        client.messages().deleteAll(server);
		
		String testEmailAddress = String.format("files_test.%s@%s", server, host);
		Mailer.sendEmail(client,  server, testEmailAddress);
		
		SearchCriteria criteria = new SearchCriteria();
    	criteria.withSentTo(testEmailAddress);
		email = client.messages().waitFor(server, criteria);
	}

    @Test
	public void testGetEmail() throws IOException {
    	InputStream inputStream = client.files().getEmail(email.id());
    	
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();				
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
			baos.write(buffer, 0, read);
		}		
		baos.flush();		
		byte[] bytes = baos.toByteArray();
    	
    	assertNotNull(bytes);
        assertTrue(bytes.length > 1);
        assertTrue(new String(bytes).contains(email.subject()));
    }
    
//    @Test
//	public void testGetAttachment() throws IOException {
//		Email email = emails.get(0);
//		Attachment attachment = email.attachments().get(0);
//    	
//    	InputStream stream = client.files().getAttachment(attachment.id());
//    	byte[] bytes = toByteArray(stream);
//    	
//    	assertNotNull(bytes);
//        assertEquals(attachment.length(), new Long(bytes.length));
//    }
}