package recommender.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import recommender.model.Recommendation;
import recommender.service.IExport;

public class Export implements IExport{

	private static String DIR_OUTPUT;
	private String timeOfRun;
	private File directory;
	public Export(String outputDirectory) {
		
		DIR_OUTPUT =outputDirectory;
		
		File f = new File(DIR_OUTPUT);
		if(!f.exists()) {
			f.mkdir();
		}
		directory = f;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		timeOfRun = dateFormat.format(date);
	}
	
	public void printRecommendationsToCsv(List<Recommendation> recommendations) throws FileNotFoundException {

		try(FileWriter fw = new FileWriter(directory+File.separator+"Recommendations_RunOf_"+timeOfRun+".txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)){
			
			out.println("===================================================================\r\n");
		    for(Recommendation reco : recommendations) {
		    	 out.println(reco.toString()+"\r\n");
		    }
		    out.println("===================================================================\r\n");
		} catch (IOException e) {
			System.out.println("Could not write to Output file. Exception was:"+e.getStackTrace());    
		}

	}
	
	public void printEvent(ICompletionEvent event) throws FileNotFoundException {

		try(FileWriter fw = new FileWriter(directory+File.separator+"Event_RunOf_"+timeOfRun+".txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)){
			
			out.println(event.toString());
			out.println("===================================================================\r\n");
		} catch (IOException e) {
			System.out.println("Could not write to Event file. Exception was:"+e.getStackTrace());     
		}
	}
}
