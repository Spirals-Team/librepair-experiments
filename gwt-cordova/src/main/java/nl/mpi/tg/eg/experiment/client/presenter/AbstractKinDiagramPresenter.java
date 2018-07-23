/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experiment.client.presenter;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import java.io.IOException;
import java.util.ArrayList;
import nl.mpi.kinnate.kindata.DataTypes;
import nl.mpi.kinnate.kindata.EntityData;
import nl.mpi.kinnate.kindata.EntityDate;
import nl.mpi.kinnate.kindata.GraphSorter;
import nl.mpi.kinnate.kindata.KinRectangle;
import nl.mpi.kinnate.kindata.UnsortablePointsException;
import nl.mpi.kinnate.kintypestrings.KinType;
import nl.mpi.kinnate.kintypestrings.KinTypeStringConverter;
import nl.mpi.kinnate.kintypestrings.ParserHighlight;
import nl.mpi.kinnate.svg.DiagramSettings;
import nl.mpi.kinnate.svg.EntitySvg;
import nl.mpi.kinnate.svg.KinElementException;
import nl.mpi.kinnate.svg.OldFormatException;
import nl.mpi.kinnate.svg.SvgDiagram;
import nl.mpi.kinnate.svg.SvgUpdateHandler;
import nl.mpi.kinnate.ui.KinTypeStringProvider;
import nl.mpi.kinnate.uniqueidentifiers.UniqueIdentifier;
import nl.mpi.kinoath.graph.DefaultSorter;
import nl.mpi.tg.eg.experiment.client.ServiceLocations;
import nl.mpi.tg.eg.experiment.client.listener.AppEventListner;
import nl.mpi.tg.eg.experiment.client.listener.PresenterEventListner;
import nl.mpi.tg.eg.experiment.client.listener.SingleShotEventListner;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import nl.mpi.tg.eg.frinex.common.model.Stimulus;
import nl.mpi.tg.eg.experiment.client.model.UserResults;
import nl.mpi.tg.eg.experiment.client.presenter.kin.DiagramSettingsGwt;
import nl.mpi.tg.eg.experiment.client.presenter.kin.KinDocumentGwt;
import nl.mpi.tg.eg.experiment.client.service.AudioPlayer;
import nl.mpi.tg.eg.experiment.client.service.DataSubmissionService;
import nl.mpi.tg.eg.experiment.client.service.LocalStorage;
import nl.mpi.tg.eg.experiment.client.view.KinTypeView;
import nl.mpi.tg.eg.experiment.client.view.TimedStimulusView;

/**
 * @since Aug 3, 2015 1:21:43 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public abstract class AbstractKinDiagramPresenter extends AbstractPresenter implements Presenter {

    protected final ServiceLocations serviceLocations = GWT.create(ServiceLocations.class);
    private final DataSubmissionService submissionService;
    private final LocalStorage localStorage;
    final UserResults userResults;
    Stimulus currentStimulus = null;
    private final Duration duration;
    final ArrayList<ButtonBase> buttonList = new ArrayList<>();
    private static final String RHOMBUS = "rhombus";

    public AbstractKinDiagramPresenter(RootLayoutPanel widgetTag, AudioPlayer audioPlayer, DataSubmissionService submissionService, UserResults userResults, LocalStorage localStorage) {
        super(widgetTag, new KinTypeView(audioPlayer));
        duration = new Duration();
        this.submissionService = submissionService;
        this.userResults = userResults;
        this.localStorage = localStorage;
    }

    public void kinTypeStringDiagram(final AppEventListner appEventListner, final int postLoadMs, final TimedStimulusListener timedStimulusListener, String kinTypeString) {
        final DiagramSettings diagramSettings = new DiagramSettingsGwt();
        final SvgDiagram svgDiagram = new SvgDiagram(diagramSettings, new EntitySvg());
        final SvgUpdateHandler svgUpdateHandler = new SvgUpdateHandler(svgDiagram);
        final KinDocumentGwt kinDocument = new KinDocumentGwt(svgUpdateHandler);
        final GraphSorter graphSorter = new DefaultSorter();

//        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, UriUtils.fromString("http://ems12.mpi.nl:80/kinoath-rest/kinoath/getkin/svg?kts=" + kinTypeString).asString());
        try {
            svgDiagram.generateDefaultSvg(kinDocument, graphSorter);
            if (kinTypeString != null) {
                KinTypeStringConverter kinTypeStringConverter = new KinTypeStringConverter(RHOMBUS, KinType.getReferenceKinTypes());
                final String[] kinTypeStringArray = kinTypeString.split("\\|");
//            kinTypeAllStrings.addAll(Arrays.asList(this.kinTypeString.split("\\|")));
                final DefaultSorter graphData = new DefaultSorter();
                final ArrayList<KinTypeStringProvider> arrayList = new ArrayList<>();
                arrayList.add(new KinTypeStringProvider() {

                    @Override
                    public String[] getCurrentStrings() {
                        return kinTypeStringArray;
                    }

                    @Override
                    public int getTotalLength() {
                        return kinTypeStringArray.length;
                    }

                    @Override
                    public void highlightKinTypeStrings(ParserHighlight[] parserHighlight, String[] kinTypeStrings) {
                    }
                });
                kinTypeStringConverter.readKinTypes(arrayList, graphData);
                svgDiagram.graphData.setEntitys(graphData.getDataNodes());
            } else {
                final EntityData entityData1 = new EntityData(new UniqueIdentifier(UniqueIdentifier.IdentifierType.tid, kinDocument.getUUID()), new String[]{"one"}, EntityData.SymbolType.square, true, null, null);
                final EntityData entityData2 = new EntityData(new UniqueIdentifier(UniqueIdentifier.IdentifierType.lid, kinDocument.getUUID()), new String[]{"two"}, EntityData.SymbolType.triangle, true, null, null);
                final EntityData entityData3 = new EntityData(new UniqueIdentifier(UniqueIdentifier.IdentifierType.lid, kinDocument.getUUID()));
                final EntityData entityData4 = new EntityData(new UniqueIdentifier(UniqueIdentifier.IdentifierType.lid, kinDocument.getUUID()), new String[]{"one"}, EntityData.SymbolType.circle, true, new EntityDate("1212/12/12"), new EntityDate("2121/21/12"));
                final EntityData entityData5 = new EntityData(new UniqueIdentifier(UniqueIdentifier.IdentifierType.tid, kinDocument.getUUID()), new String[]{"two"}, EntityData.SymbolType.triangle, true, null, new EntityDate("1250/10/10"));
                final EntityData entityData6 = new EntityData(new UniqueIdentifier(UniqueIdentifier.IdentifierType.lid, kinDocument.getUUID()), new String[]{"three"}, EntityData.SymbolType.square, true, new EntityDate("1300/12/12"), new EntityDate("1390/05/20"));
                entityData1.addRelatedNode(entityData6, DataTypes.RelationType.ancestor, "orange", null, null, null);
                entityData2.addRelatedNode(entityData6, DataTypes.RelationType.ancestor, "black", null, null, null);
                entityData3.addRelatedNode(entityData2, DataTypes.RelationType.ancestor, "black", null, null, null);
                entityData5.addRelatedNode(entityData4, DataTypes.RelationType.ancestor, "black", null, null, null);
                entityData6.addRelatedNode(entityData4, DataTypes.RelationType.ancestor, "black", null, null, null);
                svgDiagram.graphData.setEntitys(new EntityData[]{
                    entityData1, entityData2, entityData3, entityData4, entityData5, entityData6});
            }
            svgUpdateHandler.drawEntities(new KinRectangle(800, 600));
            ((TimedStimulusView) simpleView).addWidget(kinDocument.getHtmlDoc());
            Timer timer = new Timer() {
                public void run() {
                    timedStimulusListener.postLoadTimerFired();
                }
            };
            timer.schedule(postLoadMs);
//        ((TimedStimulusView) simpleView).addTimedImage(UriUtils.fromString("http://ems12.mpi.nl:80/kinoath-rest/kinoath/getkin/svg?kts=" + kinTypeString), 100, postLoadMs, timedStimulusListener);
//        ((TimedStimulusView) simpleView).addSvgImage(UriUtils.fromString("http://ems12.mpi.nl:80/kinoath-rest/kinoath/getkin/svg?kts=" + kinTypeString), 100, postLoadMs, timedStimulusListener);        
        } catch (IOException | KinElementException | OldFormatException | UnsortablePointsException exception) {
//            // todo: handle such errors in a more user friendly way
            ((TimedStimulusView) simpleView).addHtmlText(exception.getMessage(), null);
        }
    }

    public void loadKinTypeStringDiagram(final AppEventListner appEventListner, final int postLoadMs, final TimedStimulusListener timedStimulusListener, String diagramName) {
        kinTypeStringDiagram(appEventListner, postLoadMs, timedStimulusListener, loadKinTypeString(diagramName));
    }

    public void editableKinEntitesDiagram(final AppEventListner appEventListner, final int postLoadMs, final TimedStimulusListener timedStimulusListener, String diagramName) {
        kinTypeStringDiagram(appEventListner, postLoadMs, timedStimulusListener, null);
    }

    public void addKinTypeGui(final AppEventListner appEventListner, final String diagramName) {
        ((KinTypeView) simpleView).addHtmlText(loadKinTypeString(diagramName), null);
        ((KinTypeView) simpleView).addKinTypeGui();
        ((KinTypeView) simpleView).addOptionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "Add";
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                saveKinTypeString(diagramName, ((KinTypeView) simpleView).getKinTypeString());
                ((TimedStimulusView) simpleView).clearPageAndTimers(null);
                setContent(appEventListner);
                submissionService.submitTimeStamp(userResults.getUserData().getUserId(), "AddToDiagram", duration.elapsedMillis());
            }
        });
        ((KinTypeView) simpleView).addOptionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "Save Diagram To Server";
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                submissionService.submitTagValue(userResults.getUserData().getUserId(), getSelfTag(), "SaveDiagram", loadKinTypeString(diagramName), duration.elapsedMillis());
            }
        });
        ((KinTypeView) simpleView).addOptionButton(new PresenterEventListner() {

            @Override
            public String getLabel() {
                return "Clear Diagram";
            }

            @Override
            public int getHotKey() {
                return -1;
            }

            @Override
            public void eventFired(ButtonBase button, SingleShotEventListner singleShotEventListner) {
                clearKinTypeString(diagramName);
                ((TimedStimulusView) simpleView).clearPageAndTimers(null);
                setContent(appEventListner);
                submissionService.submitTimeStamp(userResults.getUserData().getUserId(), "ClearDiagram", duration.elapsedMillis());
            }
        });
    }

    public void clearKinTypeString(String diagramName) {
        localStorage.deleteStoredDataValue(userResults.getUserData().getUserId(), diagramName);
    }

    public void saveKinTypeString(String diagramName, String kinTypeString) {
        localStorage.appendStoredDataValue(userResults.getUserData().getUserId(), diagramName, kinTypeString);
    }

    public String loadKinTypeString(String diagramName) {
        return localStorage.getStoredDataValue(userResults.getUserData().getUserId(), diagramName);
    }
}
