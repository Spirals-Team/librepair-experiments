package org.linkedgeodesy.gazetteerjson.run;

import org.linkedgeodesy.gazetteerjson.config.POM_gazetteerjson;
import org.linkedgeodesy.gazetteerjson.log.Logging;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.linkedgeodesy.gazetteerjson.gazetteer.ChronOntology;
import org.linkedgeodesy.gazetteerjson.gazetteer.GeoNames;
import org.linkedgeodesy.gazetteerjson.gazetteer.GettyTGN;
import org.linkedgeodesy.gazetteerjson.gazetteer.IDAIGazetteer;
import org.linkedgeodesy.gazetteerjson.gazetteer.Pleiades;
import org.linkedgeodesy.org.gazetteerjson.json.JSONLD;

/**
 * main class for running
 */
public class Main {

    /**
     * main method
     *
     * @param args
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) throws IOException, SQLException {
        List<String> o = new ArrayList();
        try {
            o.add("# POM_gazetteerjson.getInfo" + "\r\n");
            o.add(POM_gazetteerjson.getInfo().toJSONString() + "\r\n");
            o.add("# IDAIGazetteer.getPlaceById" + "\r\n");
            o.add(IDAIGazetteer.getPlaceById("2181124").toJSONString() + "\r\n");
            o.add("# IDAIGazetteer.getPlaceByBBox" + "\r\n");
            o.add(IDAIGazetteer.getPlacesByBBox("50.082665", "8.161050", "49.903887", "8.161050", "49.903887", "8.371850", "50.082665", "8.371850").toJSONString() + "\r\n");
            o.add("# IDAIGazetteer.getPlaceByString" + "\r\n");
            o.add(IDAIGazetteer.getPlacesByString("Mainz").toJSONString() + "\r\n");
            o.add("# ChronOntology.getPlacesById" + "\r\n");
            o.add(ChronOntology.getPlacesById("EfFq8qCFODK8") + "\r\n");
            o.add("# GeoNames.getPlaceById" + "\r\n");
            o.add(GeoNames.getPlaceById("2874225").toJSONString() + "\r\n");
            o.add("# GeoNames.getPlacesByBBox" + "\r\n");
            o.add(GeoNames.getPlacesByBBox("50.082665", "8.161050", "49.903887", "8.161050", "49.903887", "8.371850", "50.082665", "8.371850").toJSONString() + "\r\n");
            o.add("# GeoNames.getPlacesByString" + "\r\n");
            o.add(GeoNames.getPlacesByString("Mainz").toJSONString() + "\r\n");
            o.add("# GettyTGN.getPlaceById" + "\r\n");
            o.add(GettyTGN.getPlaceById("7004449").toJSONString() + "\r\n"); // Mainz
            o.add(GettyTGN.getPlaceById("7008038").toJSONString() + "\r\n"); // Paris
            o.add("# GettyTGN.getPlacesByBBox" + "\r\n");
            o.add(GettyTGN.getPlacesByBBox("50.082665", "8.161050", "49.903887", "8.161050", "49.903887", "8.371850", "50.082665", "8.371850").toJSONString() + "\r\n");
            o.add(GettyTGN.getPlacesByBBox("48.866667", "2.333333", "48.866667", "2.333333", "48.866667", "2.333333", "48.866667", "2.333333").toJSONString() + "\r\n");
            o.add("# GettyTGN.getPlacesByString" + "\r\n");
            o.add(GettyTGN.getPlacesByString("Mainz").toJSONString() + "\r\n");
            o.add("# Pleiades.getPlaceById" + "\r\n");
            o.add(Pleiades.getPlaceById("109169").toJSONString() + "\r\n"); // Mogontiacum
            o.add("# Pleiades.getPlacesByBBox" + "\r\n");
            o.add(Pleiades.getPlacesByBBox("50.082665", "8.161050", "49.903887", "8.161050", "49.903887", "8.371850", "50.082665", "8.371850").toJSONString() + "\r\n");
            o.add("# Pleiades.getPlacesByString" + "\r\n");
            o.add(Pleiades.getPlacesByString("Mainz").toJSONString() + "\r\n");
            o.add("# JSONLD.getJSONLDGazetteerResource" + "\r\n");
            o.add(JSONLD.getJSONLDGazetteerResource(GeoNames.getPlaceById("2874225")).toJSONString() + "\r\n");
            o.add("# JSONLD.getJSONLDGazetteerResource" + "\r\n");
            o.add(JSONLD.getJSONLDGazetteerResource(IDAIGazetteer.getPlaceById("2181124")).toJSONString() + "\r\n");
            o.add("# JSONLD.getJSONLDGazetteerSearch" + "\r\n");
            o.add(JSONLD.getJSONLDGazetteerSearch(GettyTGN.getPlacesByBBox("48.866667", "2.333333", "48.866667", "2.333333", "48.866667", "2.333333", "48.866667", "2.333333")).toJSONString() + "\r\n");
            o.add("# JSONLD.getJSONLDChronOntologyJSON" + "\r\n");
            
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(IDAIGazetteer.getPlaceById("2181124")).toString(), "TURTLE"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(IDAIGazetteer.getPlaceById("2181124")).toString(), "N-TRIPLES"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(IDAIGazetteer.getPlaceById("2181124")).toString(), "JSON-LD"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(IDAIGazetteer.getPlaceById("2181124")).toString(), "RDF/XML"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(IDAIGazetteer.getPlaceById("2181124")).toString(), "N3"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(IDAIGazetteer.getPlaceById("2181124")).toString(), "RDF/JSON"));
            
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GeoNames.getPlaceById("2874225")).toString(), "TURTLE"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GeoNames.getPlaceById("2874225")).toString(), "N-TRIPLES"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GeoNames.getPlaceById("2874225")).toString(), "JSON-LD"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GeoNames.getPlaceById("2874225")).toString(), "RDF/XML"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GeoNames.getPlaceById("2874225")).toString(), "N3"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GeoNames.getPlaceById("2874225")).toString(), "RDF/JSON"));
            
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GettyTGN.getPlaceById("7008038")).toString(), "TURTLE"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GettyTGN.getPlaceById("7008038")).toString(), "N-TRIPLES"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GettyTGN.getPlaceById("7008038")).toString(), "JSON-LD"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GettyTGN.getPlaceById("7008038")).toString(), "RDF/XML"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GettyTGN.getPlaceById("7008038")).toString(), "N3"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerResource(GettyTGN.getPlaceById("7008038")).toString(), "RDF/JSON"));
            
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDGazetteerSearch(Pleiades.getPlacesByString("Mainz")).toJSONString(), "Turtle"));
            //System.out.println(JSONLD.getRDF(JSONLD.getJSONLDChronOntologyJSON(ChronOntology.getPlacesById("EfFq8qCFODK8")).toString(), "Turtle"));
            FileOutput.writeFile(o);
        } catch (Exception e) {
            System.out.println(Logging.getMessageJSON(e, "org.linkedgeodesy.gazetteerjson.run.Main").toJSONString());
            o.add(Logging.getMessageJSON(e, "org.linkedgeodesy.gazetteerjson.run.Main").toJSONString());
            FileOutput.writeFile(o);
        }
    }

}
