package com.economizate.nubeManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.economizate.servicios.INube;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

public class ConnectorDrive implements INube{
	
	private String pathFile;
	
	public ConnectorDrive(String pathFile) {
		this.pathFile = pathFile;
	}
	
	private static final String APPLICATION_NAME =  NubePropiedades.getInstance().getPropiedad("APPLICATION_NAME");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    // Directory to store user credentials.
    private static final String CREDENTIALS_FOLDER =  NubePropiedades.getInstance().getPropiedad("CREDENTIALS_FOLDER"); 

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
    private static final String CLIENT_SECRET_DIR = NubePropiedades.getInstance().getPropiedad("CLIENT_SECRET_DIR");
    

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If there is no client_secret.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = ConnectorDrive.class.getResourceAsStream(CLIENT_SECRET_DIR);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
    

    @Override
	public boolean conectar() {
		boolean result = false;
		if(this.authorize() != null)
			result = true;
		return result;
	}
    
    @Override
    public boolean upload(){
    	boolean result = false;
    	
    	Drive service = authorize();
    	
    	File fileMetadata = new File();
    	fileMetadata.setName(NubePropiedades.getInstance().getPropiedad("FILE_TO_UPLOAD"));//+ new Date().getTime() );
    	fileMetadata.setMimeType("application/vnd.google-apps.spreadsheet");

    	java.io.File filePath = new java.io.File(pathFile);
    	FileContent mediaContent = new FileContent("text/csv", filePath);
    	File file;
		try {
			file = service.files().create(fileMetadata, mediaContent)
			.setFields("id")
			.execute();
			
	    	System.out.println("File ID: " + file.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	result = true;
    	return result;
    }
    
    @Override
	public Enum<?> getTipo() {
		return NubeEnum.DRIVE;
	}
    
    public Drive authorize(){
    	 NetHttpTransport HTTP_TRANSPORT;
    	 Drive service = null;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			
			service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
			         .setApplicationName(APPLICATION_NAME)
			         .build();
			
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return service;
    }
    
    /**
     * 
     * @return id del archivo subido
     */
    public String uploadId(){
    	String id ="";
    	Drive service = authorize();
    	
    	File fileMetadata = new File();
    	fileMetadata.setName("historial-movimientos");//+ new Date().getTime() );
    	fileMetadata.setMimeType("application/vnd.google-apps.spreadsheet");

    	java.io.File filePath = new java.io.File(pathFile);
    	FileContent mediaContent = new FileContent("text/csv", filePath);
    	File file;
		try {
			file = service.files().create(fileMetadata, mediaContent)
			.setFields("id")
			.execute();
			
	    	id = file.getId();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return id;
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
    	
        new ConnectorDrive("src/main/java/com/economizate/nubeManager/reporte/reporte-test.csv").upload();
        
        // Print the names and IDs for up to 10 files.
        /*FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }*/
    }

}
