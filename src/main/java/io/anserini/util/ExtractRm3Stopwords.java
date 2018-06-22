package io.anserini.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.kohsuke.args4j.ParserProperties;

public class ExtractRm3Stopwords {
  private static final Logger LOG = LogManager.getLogger(ExtractRm3Stopwords.class);

  public static class Args {
    @Option(name = "-index", metaVar = "[path]", required = true, usage = "Lucene index")
    String index;

    @Option(name = "-output", metaVar = "[file]", required = true, usage = "output file")
    String output;

    @Option(name = "-field", metaVar = "[name]", required = true, usage = "field")
    String field;

    @Option(name = "-topK", metaVar = "[num]", required = false, usage = "number of terms to keep")
    int topK = 100;
  }

  public static class Pair {
    public String key;
    public int value;

    public Pair(String k, int v) {
      this.key = k;
      this.value = v;
    }
  }
  public static void main(String[] args) throws Exception {
    Args myArgs = new Args();
    CmdLineParser parser = new CmdLineParser(myArgs, ParserProperties.defaults().withUsageWidth(90));

    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      System.err.println(e.getMessage());
      parser.printUsage(System.err);
      System.err.println("Example: ExtractRm3Stopwords" + parser.printExample(OptionHandlerFilter.REQUIRED));
      return;
    }

    Directory dir = FSDirectory.open(Paths.get(myArgs.index));
    IndexReader reader = DirectoryReader.open(dir);

    Comparator<Pair> comp = new Comparator<Pair>(){
      @Override
      public int compare(Pair p1, Pair p2) {
        if (p1.value == p2.value) {
          return p1.key.compareTo(p2.key);
        } else return (p1.value < p2.value) ? -1 : 1;
      }
    };

    PriorityQueue<Pair> queue = new PriorityQueue<Pair>(myArgs.topK, comp);

    LOG.info("Starting to iterate through all terms...");
    Terms terms = MultiFields.getFields(reader).terms(myArgs.field);
    TermsEnum termsEnum = terms.iterator();
    BytesRef text = null;
    int cnt = 0;
    while ((text = termsEnum.next()) != null) {
      String term = text.utf8ToString();
      if (term.length() == 0) continue;

      Pair p = new Pair(term, reader.docFreq(new Term(myArgs.field, term)));
      if (queue.size() < myArgs.topK) {
        queue.add(p);
      } else {
        if (comp.compare(p, queue.peek()) > 0 ) {
          queue.poll();
          queue.add(p);
        }
      }

      cnt++;
      if (cnt % 1000000 == 0) {
        LOG.info("At term " + term);
      }
    }

    PrintStream out = new PrintStream(new FileOutputStream(new File(myArgs.output)));
    Pair pair;
    while ((pair = queue.poll()) != null) {
      out.println(pair.key);
    }
    out.close();

    LOG.info("Done!");
  }
}
