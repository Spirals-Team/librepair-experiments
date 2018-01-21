package com.mailosaur;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mailosaur.models.Attachment;
import com.mailosaur.models.Email;
import com.mailosaur.models.SearchCriteria;
import com.mailosaur.models.SpamAssassinRule;
import com.mailosaur.models.SpamCheckResult;

public class EmailsTest {
	private static MailosaurClient client;
	private static String server;
	private static List<Email> emails;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private String isoDateString = dateFormat.format(Calendar.getInstance().getTime());
	
	@BeforeClass
    public static void setUpBeforeClass() throws IOException, InterruptedException, MessagingException {
		server = System.getenv("MAILOSAUR_SERVER");
		String apiKey = System.getenv("MAILOSAUR_API_KEY");
		String baseUrl = System.getenv("MAILOSAUR_BASE_URL");
		
		if (apiKey == null || server == null) {
			throw new IOException("Missing necessary environment variables - refer to README.md");
		}
        
        client = new MailosaurClient(apiKey, baseUrl);
        
        client.emails().deleteAll(server);
        
        Mailer.sendEmails(client, server, 5);
        
        emails = client.emails().list(server);
	}

    @Test
	public void testList() throws IOException {
    	assertEquals(5, emails.size());
    	
    	for (Email email : emails) {
    		validateEmailSummary(email);
    	}
    }
    
    @Test
    public void testGet() throws IOException {
    	Email emailToRetrieve = emails.get(0);
    	Email email = client.emails().get(emailToRetrieve.id());
    	validateEmail(emailToRetrieve, email);
    }
    
    @Test
    public void testGetNotFound() throws IOException {
    	Email email = client.emails().get(UUID.randomUUID());
    	assertNull(email);
    }
    
    @Test
    public void testSearchNoCriteria() throws IOException {
    	try {
			client.emails().search(server, new SearchCriteria());
    		throw new IOException("Should have thrown MailosaurException");
    	} catch (MailosaurException e) { }
    }
    
    @Test
    public void testSearchBySentTo() throws IOException {
    	Email targetEmail = emails.get(1);
    	SearchCriteria criteria = new SearchCriteria();
    	criteria.withSentTo(targetEmail.to().get(0).address());
    	List<Email> results = client.emails().search(server, criteria);
    	assertEquals(1, results.size());
    	assertEquals(targetEmail.to().get(0).address(), results.get(0).to().get(0).address());
    	assertEquals(targetEmail.subject(), results.get(0).subject());
    }
    
    @Test
    public void testSearchBySentToInvalidEmail() throws IOException {
    	try {
	    	SearchCriteria criteria = new SearchCriteria();
	    	criteria.withSentTo(".not_an_email_address");
	    	client.emails().search(server, criteria);
	    	throw new IOException("Should have thrown MailosaurException");
    	} catch (MailosaurException e) { }
    }
    
    @Test
    public void testSearchByBody() throws IOException {
    	Email targetEmail = emails.get(1);
    	String uniqueString = targetEmail.subject().substring(0, targetEmail.subject().indexOf(" subject"));
    	SearchCriteria criteria = new SearchCriteria();
    	criteria.withBody(uniqueString += " html");
    	List<Email> results = client.emails().search(server, criteria);
    	assertEquals(1, results.size());
    	assertEquals(targetEmail.to().get(0).address(), results.get(0).to().get(0).address());
    	assertEquals(targetEmail.subject(), results.get(0).subject());
    }
    
    @Test
    public void testSearchBySubject() throws IOException {
    	Email targetEmail = emails.get(1);
    	String uniqueString = targetEmail.subject().substring(0, targetEmail.subject().indexOf(" subject"));
    	SearchCriteria criteria = new SearchCriteria();
    	criteria.withSubject(uniqueString);
    	List<Email> results = client.emails().search(server, criteria);
    	assertEquals(1, results.size());
    	assertEquals(targetEmail.to().get(0).address(), results.get(0).to().get(0).address());
    	assertEquals(targetEmail.subject(), results.get(0).subject());
    }
    
    @Test
    public void testSpamCheck() throws IOException {
    	UUID targetId = emails.get(0).id();
    	SpamCheckResult result = client.emails().spamCheck(targetId);
    	assertEquals(targetId, result.emailId());
    	
    	for (SpamAssassinRule rule : result.spamAssassin()) {
    		assertNotNull(rule.rule());
    		assertNotNull(rule.description());
    	}
    }
    
    @Test
	public void testDelete() throws IOException {
		UUID targetEmailId = emails.get(4).id();
		
		client.emails().delete(targetEmailId);
		
		// Attempting to delete again should fail
		try {
			client.emails().delete(targetEmailId);
    		throw new IOException("Should have thrown MailosaurException");
    	} catch (MailosaurException e) { }
	}
    
    private void validateEmail(Email expected, Email actual) {
    	validateMetadata(actual);
    	validateAttachmentMetadata(actual);
    	validateHtml(actual);
    	validateText(actual);
    	validateHeaders(expected, actual);
    }
    
    private void validateEmailSummary(Email email) {
    	validateMetadata(email);
    	validateAttachmentMetadata(email);
    }
    
    private void validateHtml(Email email) {
    	// HTML.Body
    	assertTrue(email.html().body().startsWith("<div dir=\"ltr\">"));

    	// HTML.Links
    	assertEquals(3, email.html().links().size());
		assertEquals("https://mailosaur.com/", email.html().links().get(0).href());
		assertEquals("mailosaur", email.html().links().get(0).text());
		assertEquals("https://mailosaur.com/", email.html().links().get(1).href());
		assertNull(email.html().links().get(1).text());
		assertEquals("http://invalid/", email.html().links().get(2).href());
		assertEquals("invalid", email.html().links().get(2).text());

		// HTML.Images
		assertTrue(email.html().images().get(1).src().startsWith("cid"));
		assertEquals("Inline image 1", email.html().images().get(1).alt());
    }
    
    private void validateText(Email email) {
    	// Text.Body
    	assertTrue(email.text().body().startsWith("this is a test"));
    	
    	// Text.Links
    	assertEquals(2, email.text().links().size());
		assertEquals("https://mailosaur.com/", email.text().links().get(0).href());
		assertEquals(email.text().links().get(0).href(), email.text().links().get(0).text());
		assertEquals("https://mailosaur.com/", email.text().links().get(1).href());
		assertEquals(email.text().links().get(1).href(), email.text().links().get(1).text());
    }
    
    private void validateHeaders(Email expected, Email actual) {
    	String expectedFromHeader = String.format("%s <%s>", expected.from().get(0).name(), expected.from().get(0).address());
    	String expectedToHeader = String.format("%s <%s>", expected.to().get(0).name(), expected.to().get(0).address());
    	
    	// Fallback casing for headers is used, as header casing is determined by sending server
    	assertEquals(expectedFromHeader, (actual.headers().get("From") == null) ? actual.headers().get("from") : actual.headers().get("From"));
    	assertEquals(expectedToHeader, (actual.headers().get("To") == null) ? actual.headers().get("to") : actual.headers().get("To"));
		assertEquals(expected.subject(), (actual.headers().get("Subject") == null) ? actual.headers().get("subject") : actual.headers().get("Subject"));
    }
    
    private void validateMetadata(Email email) {
    	assertEquals(1, email.from().size());
        assertEquals(1, email.to().size());
        assertNotNull(email.from().get(0).address());
        assertNotNull(email.from().get(0).name());
        assertNotNull(email.to().get(0).address());
        assertNotNull(email.to().get(0).name());
        assertNotNull(email.subject());
        assertNotNull(email.senderhost());
        assertNotNull(email.server());
        
    	assertEquals(isoDateString, dateFormat.format(email.received().toDate()));
    }
    
    private void validateAttachmentMetadata(Email email) {
		assertEquals(2, email.attachments().size());
		
		Attachment file1 = email.attachments().get(0);
		assertNotNull(file1.id());
		assertEquals((Long) 82138L, file1.length());
		assertEquals("cat.png", file1.fileName());
		assertEquals(isoDateString, dateFormat.format(file1.creationDate().toDate()));
		assertEquals("image/png", file1.contentType());
		
		Attachment file2 = email.attachments().get(1);
		assertNotNull(file2.id());
		assertEquals((Long) 212080L, file2.length());
		assertEquals("dog.png", file2.fileName());
		assertEquals(isoDateString, dateFormat.format(file2.creationDate().toDate()));
		assertEquals("image/png", file2.contentType());
	}
}