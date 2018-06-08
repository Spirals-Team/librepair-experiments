package main.java.liasd.asadera.model.task.process.caracteristicBuilder.vector.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.java.liasd.asadera.model.task.process.caracteristicBuilder.QueryBasedIn;
import main.java.liasd.asadera.model.task.process.caracteristicBuilder.QueryBasedOut;
import main.java.liasd.asadera.model.task.process.caracteristicBuilder.vector.BiGramVectorSentence;
import main.java.liasd.asadera.model.task.process.processCompatibility.ParameterizedMethod;
import main.java.liasd.asadera.model.task.process.processCompatibility.ParameterizedType;
import main.java.liasd.asadera.optimize.SupportADNException;
import main.java.liasd.asadera.optimize.parameter.Parameter;
import main.java.liasd.asadera.textModeling.Corpus;
import main.java.liasd.asadera.textModeling.Query;
import main.java.liasd.asadera.textModeling.wordIndex.InvertedIndex;
import main.java.liasd.asadera.textModeling.wordIndex.NGram;
import main.java.liasd.asadera.tools.jgibblda.Pair;

@Deprecated
public class BiGramCentroid extends BiGramVectorSentence implements QueryBasedOut {

	public static enum Centroid_Parameter {
		NbMaxWordInCentroid("NbMaxWordInCentroid");

		private String name;

		private Centroid_Parameter(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private Query query;
	protected int nbMaxWordInCentroid;

	protected InvertedIndex<NGram> invertIndex;

	public BiGramCentroid(int id) throws SupportADNException {
		super(id);
		query = new Query();
		supportADN.put("NbMaxWordInCentroid", Integer.class);

		listParameterOut.add(new ParameterizedType(null, double[].class, QueryBasedOut.class));
	}

	@Override
	public BiGramCentroid makeCopy() throws Exception {
		BiGramCentroid p = new BiGramCentroid(id);
		p.setNbMaxWordInCentroid(nbMaxWordInCentroid);
		initCopy(p);
		return p;
	}

	@Override
	public void initADN() throws Exception {
		int n = Integer.parseInt(getCurrentProcess().getModel().getProcessOption(id, "NbMaxWordInCentroid"));
		getCurrentProcess().getADN()
				.putParameter(new Parameter<Integer>(Centroid_Parameter.NbMaxWordInCentroid.getName(), n));
		getCurrentProcess().getADN().getParameter(Integer.class, Centroid_Parameter.NbMaxWordInCentroid.getName())
				.setMaxValue(2 * n + 1);
		getCurrentProcess().getADN().getParameter(Integer.class, Centroid_Parameter.NbMaxWordInCentroid.getName())
				.setMinValue(1);
	}

	private void init() {
		nbMaxWordInCentroid = getCurrentProcess().getADN().getParameterValue(Integer.class,
				Centroid_Parameter.NbMaxWordInCentroid.getName());
		invertIndex = new InvertedIndex<NGram>(index);
	}

	@Override
	public void processCaracteristics(List<Corpus> listCorpus) {
		super.processCaracteristics(listCorpus);

		init();
		double[] centroid = new double[index.size()];
		List<Pair> listBestWord = new ArrayList<Pair>();
		double minTfIdf = 0;

		for (Corpus corpus : listCorpus) {
			int corpusId = corpus.getiD();

			for (NGram w : invertIndex.getCorpusWordIndex().get(corpusId)) {
				double tfidf = w.getTfCorpus(corpusId) * w.getIdf(index.getNbDocument());
				if (listBestWord.size() < nbMaxWordInCentroid) {
					listBestWord.add(new Pair(w.getiD(), tfidf));
					Collections.sort(listBestWord);
					minTfIdf = (double) listBestWord.get(listBestWord.size() - 1).second;
				} else if (tfidf > minTfIdf) {
					listBestWord.remove(nbMaxWordInCentroid - 1);
					listBestWord.add(new Pair(w.getiD(), tfidf));
					Collections.sort(listBestWord);
					minTfIdf = (double) listBestWord.get(listBestWord.size() - 1).second;
				}
			}
		}

		for (Pair p : listBestWord)
			centroid[(int) p.first] = (double) p.second;

		query.setQuery(centroid);
	}

	@Override
	public void finish() {
		super.finish();
		query.clear();
	}

	public void setNbMaxWordInCentroid(int nbMaxWordInCentroid) {
		this.nbMaxWordInCentroid = nbMaxWordInCentroid;
	}

	@Override
	public Query getQuery() {
		return query;
	}

	@Override
	public boolean isOutCompatible(ParameterizedMethod compatibleMethod) {
		return super.isOutCompatible(compatibleMethod) || super.isOutCompatible(compatibleMethod) && compatibleMethod
				.getParameterTypeIn().contains(new ParameterizedType(null, double[].class, QueryBasedIn.class));
	}

	@Override
	public void setCompatibility(ParameterizedMethod compatibleMethod) {
		super.setCompatibility(compatibleMethod);
		if (compatibleMethod.getParameterTypeIn()
				.contains(new ParameterizedType(null, double[].class, QueryBasedIn.class)))
			((QueryBasedIn) compatibleMethod).setQuery(query);
	}
}
