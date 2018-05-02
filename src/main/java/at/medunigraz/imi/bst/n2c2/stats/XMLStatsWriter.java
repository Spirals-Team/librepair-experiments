package at.medunigraz.imi.bst.n2c2.stats;

import at.medunigraz.imi.bst.n2c2.model.Criterion;
import at.medunigraz.imi.bst.n2c2.model.metrics.Metrics;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Map;

public class XMLStatsWriter extends AbstractStatsWriter {

    private Document doc;
    private Element rootElement;

    public XMLStatsWriter(OutputStream output) {
        super(output);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        doc = documentBuilder.newDocument();

        writeHeader();
    }

    public XMLStatsWriter(File outputFile) throws FileNotFoundException {
        this(new FileOutputStream(outputFile));
    }

    protected void writeHeader() {
        rootElement = doc.createElement("report");
        doc.appendChild(rootElement);
        flush();
    }

    public void write(Metrics metrics) {
        for (Criterion c : Criterion.values()) {
            Element topicElement = doc.createElement(GROUPED_BY.toLowerCase());
            topicElement.setAttribute("name", c.name());
            rootElement.appendChild(topicElement);

            Map<String, Double> metricsByCriterion = metrics.getMetrics(c);
            for (Map.Entry<String, Double> entry : metricsByCriterion.entrySet()) {
                Element metricElement = doc.createElement(entry.getKey().toLowerCase());
                metricElement.appendChild(doc.createTextNode(String.valueOf(entry.getValue())));
                topicElement.appendChild(metricElement);
            }
        }
    }

    @Override
    public void close() throws IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void flush() {

    }
}
