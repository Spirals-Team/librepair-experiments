package recommender;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.model.events.completionevents.IProposal;
import recommender.evaluation.Evaluator;
import recommender.model.Recommendation;
import recommender.service.IExport;
import recommender.service.impl.Export;
import recommender.service.impl.ReadingArchiveEvents;
import recommender.visitor.CompletionExpressionVisitor;

public class Client {

	public static void main(String[] args) throws FileNotFoundException {
		run(args[0], args[1], args[2], args[3]);
	}

	private static String DIR_USERDATA;
	private static String DIR_METHODCOLLECTIONS;

	private static boolean doEvaluation = false;
	private static Evaluator evaluator;
	private static IExport export;

	public static void run(String userdata, String methodCollections, String outputDirectory, String flag)
			throws FileNotFoundException {

		if (userdata.isEmpty()) {
			DIR_USERDATA = System.getProperty("user.home") + File.separator + "Recommender" + File.separator + "Events";
		} else {
			DIR_USERDATA = userdata;
		}
		if (methodCollections.isEmpty()) {
			DIR_METHODCOLLECTIONS = System.getProperty("user.home") + File.separator + "Recommender" + File.separator
					+ "MethodCollections";
		} else {
			DIR_METHODCOLLECTIONS = methodCollections;
		}

		export = new Export(outputDirectory);
		checkForFolders(DIR_USERDATA);
		checkForFolders(DIR_METHODCOLLECTIONS);

		if (flag != null && flag.equals("-e")) {
			doEvaluation = true;
			evaluator = new Evaluator();
		}

		for (String user : findAllUsers()) {
			ReadingArchiveEvents ra = new ReadingArchiveEvents(new File(user));
			while (ra.hasNext()) {
				IIDEEvent event = ra.getNext(IIDEEvent.class);
				process(event);
			}
			ra.close();
		}
		if (doEvaluation) {
			evaluator.summarizeResults();
		}

	}

	private static void checkForFolders(String foldername) {
		File f = new File(foldername);
		if (!f.exists()) {
			f.mkdir();
		}
	}

	public static List<String> findAllUsers() {
		List<String> zips = Lists.newLinkedList();
		for (File f : FileUtils.listFiles(new File(DIR_USERDATA), new String[] { "zip" }, true)) {
			zips.add(f.getAbsolutePath());
		}
		return zips;
	}

	private static void process(IIDEEvent event) throws FileNotFoundException {

		if (event instanceof CompletionEvent) {
			ICompletionEvent ce = (CompletionEvent) event;

			HashMap<List<Recommendation>, IProposal> evaluationSet = new HashMap<List<Recommendation>, IProposal>();

			ce.getContext().getSST().accept(new CompletionExpressionVisitor(DIR_METHODCOLLECTIONS, evaluationSet, ce),null);

			Iterator<Entry<List<Recommendation>, IProposal>> it = evaluationSet.entrySet().iterator();
			while (it.hasNext()) {
				
				Map.Entry<List<Recommendation>, IProposal> pair = (Map.Entry<List<Recommendation>, IProposal>) it.next();
				
				if (!pair.getKey().isEmpty()) {
					export.printRecommendationsToCsv(pair.getKey());
				}
				
				if (doEvaluation) {	
					evaluator.evaluate(pair.getKey(), pair.getValue());
				}

			}

		}
	}

}
