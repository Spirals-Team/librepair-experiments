package psidev.psi.mi.jami.bridges.picr.io;

import psidev.psi.mi.jami.bridges.picr.GetUPIForAccessionResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Picr REST xml result parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25-Mar-2010</pre>
 */
public class PicrRESTParser {

   /**
     * Sets up a logger for this class.
     */
    public static final Logger log = Logger.getLogger(PicrRESTParser.class.getName());

    // //////////////////////
    // Private methods

    private Unmarshaller getUnmarshaller() throws JAXBException {

        // create a JAXBContext capable of handling classes generated into the
        // jaxb package
        JAXBContext jc = JAXBContext.newInstance( "psidev.psi.mi.jami.bridges.picr" );

        // create and return Unmarshaller
        return jc.createUnmarshaller();
    }

    private GetUPIForAccessionResponse unmarshall( URL url ) throws JAXBException, FileNotFoundException {

        if ( url == null ) {
            throw new IllegalArgumentException( "You must give a non null URL." );
        }

        // create an Unmarshaller
        Unmarshaller u = getUnmarshaller();

        // unmarshal an entrySet instance document into a tree of Java content
        // objects composed of classes from the jaxb package.
        return ( GetUPIForAccessionResponse ) u.unmarshal( url );
    }

    private GetUPIForAccessionResponse unmarshall( File file ) throws JAXBException, FileNotFoundException {

        if ( file == null ) {
            throw new IllegalArgumentException( "You must give a non null file." );
        }

        if ( !file.exists() ) {
            throw new IllegalArgumentException( "You must give an existing file. : " + file.getPath() );
        }

        if ( !file.canRead() ) {
            throw new IllegalArgumentException( "You must give a readable file." );
        }

        // create an Unmarshaller
        Unmarshaller u = getUnmarshaller();

        // unmarshal an entrySet instance document into a tree of Java content
        // objects composed of classes from the jaxb package.

        if (log.isLoggable(Level.FINE)){
            log.fine("unmarshal : " + file.getPath());
        }

        FileInputStream inputStream = new FileInputStream( file );
        try {
            return ( GetUPIForAccessionResponse ) u.unmarshal( inputStream );
        } catch (ClassCastException e){
            if (log.isLoggable(Level.WARNING)){
                log.warning("ClassCastException: file: " + file.getPath());
            }
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.severe("Impossible to close the resulting file input stream: " + e);
            }
        }
      //  return (EBIApplicationResult) ((JAXBElement)u.unmarshal(new FileInputStream(file))).getValue();
        //( CVMappingType ) ( ( JAXBElement ) u.unmarshal( new FileInputStream( file ) ) ).getValue();
        return null;
    }

    private GetUPIForAccessionResponse unmarshall( InputStream is ) throws JAXBException {

        if ( is == null ) {
            throw new IllegalArgumentException( "You must give a non null input stream." );
        }

        // create an Unmarshaller
        Unmarshaller u = getUnmarshaller();

        // unmarshal an entrySet instance document into a tree of Java content
        // objects composed of classes from the jaxb package.
        return ( GetUPIForAccessionResponse ) u.unmarshal( is );
    }

    private GetUPIForAccessionResponse unmarshall( String s ) throws JAXBException {

        if ( s == null ) {
            throw new IllegalArgumentException( "You must give a non null String." );
        }

        // create an Unmarshaller
        Unmarshaller u = getUnmarshaller();

        // unmarshal an entrySet instance document into a tree of Java content
        // objects composed of classes from the jaxb package.
        return ( GetUPIForAccessionResponse ) u.unmarshal( new StringReader( s ) );
    }

    // ////////////////////////
    // Public methods

    /**
     * <p>read.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @return a {@link psidev.psi.mi.jami.bridges.picr.GetUPIForAccessionResponse} object.
     * @throws psidev.psi.mi.jami.bridges.picr.io.PicrParsingException if any.
     */
    public GetUPIForAccessionResponse read( String s ) throws PicrParsingException {
        try {
            return unmarshall( s );
        } catch ( JAXBException e ) {
            throw new PicrParsingException( "Problem during the parsing of the Picr REST xml results", e );
        }
    }

    /**
     * <p>read.</p>
     *
     * @param file a {@link java.io.File} object.
     * @return a {@link psidev.psi.mi.jami.bridges.picr.GetUPIForAccessionResponse} object.
     * @throws psidev.psi.mi.jami.bridges.picr.io.PicrParsingException if any.
     */
    public GetUPIForAccessionResponse read( File file ) throws PicrParsingException {
        try {
            return unmarshall( file );
        } catch ( JAXBException e ) {
            throw new PicrParsingException( "Problem during the parsing of the Picr REST xml results", e );
        } catch ( FileNotFoundException e ) {
            throw new PicrParsingException( "We couldn't find the file " + file.getAbsolutePath(), e );
        }
    }

    /**
     * <p>read.</p>
     *
     * @param is a {@link java.io.InputStream} object.
     * @return a {@link psidev.psi.mi.jami.bridges.picr.GetUPIForAccessionResponse} object.
     * @throws psidev.psi.mi.jami.bridges.picr.io.PicrParsingException if any.
     */
    public GetUPIForAccessionResponse read( InputStream is ) throws PicrParsingException {
        try {
            return unmarshall( is );
        } catch ( JAXBException e ) {
            throw new PicrParsingException( "Problem during the parsing of the Picr REST xml results", e );
        }
    }

    /**
     * <p>read.</p>
     *
     * @param url a {@link java.net.URL} object.
     * @return a {@link psidev.psi.mi.jami.bridges.picr.GetUPIForAccessionResponse} object.
     * @throws psidev.psi.mi.jami.bridges.picr.io.PicrParsingException if any.
     */
    public GetUPIForAccessionResponse read( URL url ) throws PicrParsingException {
        try {
            return unmarshall( url );
        } catch ( JAXBException e ) {
            throw new PicrParsingException( "Problem during the parsing of the Picr REST xml results", e );
        } catch ( FileNotFoundException e ) {
            throw new PicrParsingException( "We couldn't open the URL " + url.toString(), e );
        }
    }
}
