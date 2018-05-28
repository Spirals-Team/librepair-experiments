package org.EDM.Transformations.formats.topx;

import com.neovisionaries.i18n.LanguageCode;
import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.Begin;
import eu.europeana.corelib.definitions.jibx.Contributor;
import eu.europeana.corelib.definitions.jibx.Coverage;
import eu.europeana.corelib.definitions.jibx.Created;
import eu.europeana.corelib.definitions.jibx.CurrentLocation;
import eu.europeana.corelib.definitions.jibx.Description;
import eu.europeana.corelib.definitions.jibx.EdmType;
import eu.europeana.corelib.definitions.jibx.End;
import eu.europeana.corelib.definitions.jibx.HasMet;
import eu.europeana.corelib.definitions.jibx.Language;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.jibx.Title;
import eu.europeana.corelib.definitions.jibx.Type2;
import org.EDM.Transformations.formats.EDM;
import org.EDM.Transformations.formats.xslt.XSLTTransformations;
import org.csuc.deserialize.JibxUnMarshall;

import java.io.InputStream;
import java.util.UUID;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import nl.nationaalarchief.topx.v2.ActorType;
import nl.nationaalarchief.topx.v2.AggregatieType;
import nl.nationaalarchief.topx.v2.BestandType;
import nl.nationaalarchief.topx.v2.NonEmptyStringTypeAttribuut;
import nl.nationaalarchief.topx.v2.PeriodeType;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import nl.nationaalarchief.topx.v2.TopxType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;
import org.csuc.serialize.JibxMarshall;

public class TOPX2EDM extends RDF implements EDM {

    private static final Logger LOGGER = LogManager.getLogger(TOPX2EDM.class);
    private final TopxType topx;
    private final String identifier;
    private final Map<String, String> properties;
    private final List<AgentType> agents;
    private final List<PlaceType> places;
    private final List<TimeSpanType> timeSpans;

    public TOPX2EDM(String identifier, TopxType type, Map<String, String> properties) {
        this.agents = new ArrayList<>();
        this.places = new ArrayList<>();
        this.timeSpans = new ArrayList<>();
        this.identifier = identifier;
        this.topx = type;
        this.properties = properties;
        
        edmProvidedCHO();
        //edmEntities();
    }

    private void edmEntities(){
        agents.forEach((agent) -> {
            RDF.Choice choice = new RDF.Choice();
            choice.setAgent(agent);
            this.getChoiceList().add(choice);
        });
        places.forEach((place) -> {
            RDF.Choice choice = new RDF.Choice();
            choice.setPlace(place);
            this.getChoiceList().add(choice);
        });
        timeSpans.forEach((timeSpan) -> {
            RDF.Choice choice = new RDF.Choice();
            choice.setTimeSpan(timeSpan);
            this.getChoiceList().add(choice);
        });
    }
    
    private AgentType edmAgent(ActorType actor) {
        AgentType agent = new AgentType();
        String agentId = String.format("Agent:%s", UUID.randomUUID().toString());
        agent.setAbout(agentId);
        List<PrefLabel> prefLabelList = new ArrayList<>();
        PrefLabel prefLabel = new PrefLabel();
        prefLabel.setString(actor.getIdentificatiekenmerk().getValue());
        agent.setPrefLabelList(prefLabelList);
        agents.add(agent);
        return agent;
    }

    private AgentType edmAgent(String prefLabelText) {
        ActorType actor = new ActorType();
        NonEmptyStringTypeAttribuut prefLabelAttr = new NonEmptyStringTypeAttribuut();
        prefLabelAttr.setValue(prefLabelText);
        actor.setIdentificatiekenmerk(prefLabelAttr);
        return edmAgent(actor);
    }

    private Contributor agentToContributor(AgentType agent){
        Contributor contributor = new Contributor();
        Resource contribRes = new Resource();
        contribRes.setResource(agent.getAbout());
        contributor.setResource(contribRes);
        return contributor;
    }
    
    private PlaceType edmPlace(String prefLabelText) {
        PlaceType place = new PlaceType();
        place.setAbout(String.format("Place:%s", UUID.randomUUID().toString()));
        List<PrefLabel> prefLabelList = new ArrayList<>();
        PrefLabel prefLabel = new PrefLabel();
        prefLabel.setString(prefLabelText);
        place.setPrefLabelList(prefLabelList);
        places.add(place);
        return place;
    }

    private Coverage placeToCoverage(PlaceType place){
        Coverage coverage = new Coverage();
        Resource coverageRes = new Resource();
        coverageRes.setResource(place.getAbout());
        coverage.setResource(coverageRes);
        return coverage;
    }
    
     private CurrentLocation placeToCurrentLocation(PlaceType place){
        CurrentLocation curLocation = new CurrentLocation();
        Resource curLocationRes = new Resource();
        curLocationRes.setResource(place.getAbout());
        curLocation.setResource(curLocationRes);
        return curLocation;
    }
    
    private TimeSpanType edmTimeSpan(PeriodeType period) {
        TimeSpanType timeSpan = new TimeSpanType();
        timeSpan.setAbout(String.format("TimeSpan:%s", UUID.randomUUID().toString()));
        String prefLabelText = String.valueOf(period.getBegin().getJaar().getValue().getYear());
        List<PrefLabel> prefLabelList = new ArrayList<>();
        PrefLabel prefLabel = new PrefLabel();
        prefLabel.setString(prefLabelText);
        timeSpan.setPrefLabelList(prefLabelList);
        Begin begin = new Begin();
        begin.setString(period.getBegin().getDatum().getValue().toString());
        timeSpan.setBegin(begin);
        End end = new End();
        end.setString(period.getEind().getDatum().getValue().toString());
        timeSpan.setEnd(end);
        this.timeSpans.add(timeSpan);
        return timeSpan;
    }
    
    private Created timeSpanToCreated(TimeSpanType timespan) {
        Created coverage = new Created();
        Resource createdRes = new Resource();
        createdRes.setResource(timespan.getAbout());
        coverage.setResource(createdRes);
        return coverage;
    }

    private void edmProvidedCHO() {
        try {
            Choice choice = new Choice();
            ProvidedCHOType provided = new ProvidedCHOType();

            String choId = null;
            List<Contributor> contributors = new ArrayList<>();
            List<Coverage> coverages = new ArrayList<>();
            List<Description> descriptions = new ArrayList<>();
            List<Language> languages = new ArrayList<>();
            CurrentLocation currentLocation = null;
            List<Title> titles = new ArrayList<>();
            List<HasMet> hasMetList = new ArrayList<>();
            List<Created> createdList = new ArrayList<>();
            Type2 type = new Type2();
            type.setType(EdmType.TEXT);

            if (topx.getAggregatie() != null) {
                AggregatieType aggregatie = topx.getAggregatie();
                choId = aggregatie.getIdentificatiekenmerk().getValue();
                aggregatie.getContext().getActor().forEach((actor) -> {
                    contributors.add(this.agentToContributor(this.edmAgent(actor)));
                });
                aggregatie.getEventGeschiedenis().forEach((event) -> {
                    contributors.add(this.agentToContributor(this.edmAgent(event.getVerantwoordelijkeFunctionaris().getValue())));
                });
                aggregatie.getDekking().forEach((coverage) -> {
                    coverage.getGeografischGebied().forEach((geographicArea) -> {
                        coverages.add(this.placeToCoverage(this.edmPlace(geographicArea.getValue())));
                    });
                    if (coverage.getInTijd() != null){                        
                        createdList.add(this.timeSpanToCreated(edmTimeSpan(coverage.getInTijd())));
                    }                    
                });
                aggregatie.getOmschrijving().forEach((desc) -> {
                    Description descEdm = new Description();
                    descEdm.setString(desc.getValue());
                    descriptions.add(descEdm);
                });
                aggregatie.getTaal().forEach((lang) -> {
                    String langText = lang.getValue().name();
                    Locale locale = new Locale(langText);
                    LanguageCode langCode = LanguageCode.getByLocale(locale);
                    Language langEdm = new Language();
                    langEdm.setString(langCode.getName());
                    languages.add(langEdm);
                });
                currentLocation = placeToCurrentLocation(edmPlace(aggregatie.getPlaats().getValue()));
                aggregatie.getNaam().forEach((name) -> {
                    Title titleEdm = new Title();
                    titleEdm.setString(name.getValue());
                    titles.add(titleEdm);
                });
                aggregatie.getExternIdentificatiekenmerk().forEach((externIdMark) -> {
                    String charSystem = externIdMark.getKenmerkSysteem().getValue();
                    HasMet hasMet = new HasMet();
                    hasMet.setResource(charSystem);
                });
            } else if (topx.getBestand() != null) {
                BestandType bestand = topx.getBestand();
                choId = bestand.getIdentificatiekenmerk().getValue();
                bestand.getContext().getActor().forEach((actor) -> {
                    contributors.add(this.agentToContributor(this.edmAgent(actor)));
                });
                bestand.getEventGeschiedenis().forEach((event) -> {
                    contributors.add(this.agentToContributor(this.edmAgent(event.getVerantwoordelijkeFunctionaris().getValue())));
                });
                bestand.getDekking().forEach((coverage) -> {
                    coverage.getGeografischGebied().forEach((geographicArea) -> {
                        coverages.add(this.placeToCoverage(this.edmPlace(geographicArea.getValue())));
                    });
                    if (coverage.getInTijd() != null){                        
                        createdList.add(this.timeSpanToCreated(edmTimeSpan(coverage.getInTijd())));
                    }                    
                });
                bestand.getOmschrijving().forEach((desc) -> {
                    Description descEdm = new Description();
                    descEdm.setString(desc.getValue());
                    descriptions.add(descEdm);
                });
                bestand.getTaal().forEach((lang) -> {
                    String langText = lang.getValue().name();
                    Locale locale = new Locale(langText);
                    LanguageCode langCode = LanguageCode.getByLocale(locale);
                    Language langEdm = new Language();
                    langEdm.setString(langCode.getName());
                    languages.add(langEdm);
                });
                currentLocation = placeToCurrentLocation(edmPlace(bestand.getPlaats().getValue()));
                bestand.getNaam().forEach((name) -> {
                    Title titleEdm = new Title();
                    titleEdm.setString(name.getValue());
                    titles.add(titleEdm);
                });
                bestand.getExternIdentificatiekenmerk().forEach((externIdMark) -> {
                    String charSystem = externIdMark.getKenmerkSysteem().getValue();
                    HasMet hasMet = new HasMet();
                    hasMet.setResource(charSystem);
                });
            }

            String typeStr = properties.get("edmType");
            if (typeStr != null) {
                type.setType(EdmType.convert(typeStr));
            }
            provided.setAbout(choId);
            provided.setHasMetList(hasMetList);
            provided.setCurrentLocation(currentLocation);
            provided.setType(type);

            contributors.forEach((contributor) -> {
                eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice cContributor = new eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice();
                cContributor.setContributor(contributor);
                provided.getChoiceList().add(cContributor);
            });
            
            coverages.forEach((coverage) -> {
                eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice cCoverage = new eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice();
                cCoverage.setCoverage(coverage);
                provided.getChoiceList().add(cCoverage);
            });
            
            descriptions.forEach((description) -> {
                eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice cDescription = new eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice();
                cDescription.setDescription(description);
                provided.getChoiceList().add(cDescription);
            });
            
            languages.forEach((language) -> {
                eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice cLanguage = new eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice();
                cLanguage.setLanguage(language);
                provided.getChoiceList().add(cLanguage);
            });
            
            titles.forEach((title) -> {
                eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice cTitle = new eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice();
                cTitle.setTitle(title);
                provided.getChoiceList().add(cTitle);
            });
            
            createdList.forEach((created) -> {
                eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice cCreated = new eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice();
                cCreated.setCreated(created);
                provided.getChoiceList().add(cCreated);
            });

            choice.setProvidedCHO(provided);
            this.getChoiceList().add(choice);
        } catch (Exception exception) {
            LOGGER.error(String.format("[%s] error generate edmProvidedCHO \n%s", identifier, exception));
        }
    }

     @Override
    public XSLTTransformations transformation(OutputStream out, Map<String, String> xsltProperties) throws Exception {
        throw new IllegalArgumentException("transformation is not valid for TOPX2EDM!");
    }

    @Override
    public XSLTTransformations transformation(String xslt, OutputStream out, Map<String, String> xsltProperties) throws Exception {
        throw new IllegalArgumentException("transformation is not valid for TOPX2EDM!");
    }

    @Override
    public XSLTTransformations transformation(String xslt) throws Exception {
        throw new IllegalArgumentException("transformation is not valid for TOPX2EDM!");
    }

    @Override
    public void creation() {
        if (!Objects.equals(this, new RDF()))
            JibxMarshall.marshall(this, StandardCharsets.UTF_8.toString(),
                    false, IoBuilder.forLogger(TOPX2EDM.class).setLevel(Level.INFO).buildOutputStream(), RDF.class, -1);
    }

    @Override
    public void creation(Charset encoding, boolean alone, OutputStream outs) {
        if (!Objects.equals(this, new RDF()))
            JibxMarshall.marshall(this, encoding.toString(), alone, outs, RDF.class, -1);
    }

    @Override
    public void creation(Charset encoding, boolean alone, Writer writer) {
        if (!Objects.equals(this, new RDF()))
            JibxMarshall.marshall(this, encoding.toString(), alone, writer, RDF.class, -1);
    }

    @Override
    public JibxUnMarshall validateSchema(InputStream ins, Charset enc, Class<?> classType) {
        return new JibxUnMarshall(ins, enc, classType);
    }

    @Override
    public JibxUnMarshall validateSchema(InputStream ins, String name, Charset enc, Class<?> classType) {
        return new JibxUnMarshall(ins, name, enc, classType);
    }

    @Override
    public JibxUnMarshall validateSchema(Reader rdr, Class<?> classType) {
        return new JibxUnMarshall(rdr, classType);
    }

    @Override
    public JibxUnMarshall validateSchema(Reader rdr, String name, Class<?> classType) {
        return new JibxUnMarshall(rdr, name, classType);
    }

    @Override
    public void modify(RDF rdf) {

    }
}
