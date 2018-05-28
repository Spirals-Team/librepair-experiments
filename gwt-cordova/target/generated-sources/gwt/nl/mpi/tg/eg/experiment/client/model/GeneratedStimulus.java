package nl.mpi.tg.eg.experiment.client.model;
            
            import com.google.gwt.core.client.GWT;
            import java.util.Arrays;
            import java.util.List;
            import nl.mpi.tg.eg.experiment.client.ServiceLocations;
            import nl.mpi.tg.eg.experiment.client.util.GeneratedStimulusProvider;
            import nl.mpi.tg.eg.frinex.common.model.AbstractStimulus;
            import nl.mpi.tg.eg.frinex.common.model.Stimulus;

            public class GeneratedStimulus extends AbstractStimulus {
            protected final ServiceLocations serviceLocations = GWT.create(ServiceLocations.class);
        
     
            public enum Tag implements nl.mpi.tg.eg.frinex.common.model.Stimulus.Tag {

        tag_Training, tag_Room_1, tag_Set_1, tag_allRooms, tag_Room_2, tag_Set_2, tag_Room_3, tag_Set_3, tag_Room_4, tag_Set_4
            }

        
            
            public static final void fillStimulusList(List<Stimulus> stimulusArray) {
            stimulusArray.addAll(Arrays.asList(GeneratedStimulusProvider.values));
            }
            
            public GeneratedStimulus(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String audioPath, String videoPath, String imagePath, String ratingLabels, String correctResponses, String ... parameters) {
            super(uniqueId, tags, label, code, pauseMs, audioPath, videoPath, imagePath, ratingLabels, correctResponses);
        
            }

            /*public GeneratedStimulus(String uniqueId, Tag[] tags, String label, String code, int pauseMs, String ratingLabels, String correctResponses) {
            super(uniqueId, tags, label, code, pauseMs, ratingLabels, correctResponses);
            }*/

            @Override
            public String getAudio() {
            return serviceLocations.staticFilesUrl() + super.getAudio();
            }

            @Override
            public String getImage() {
            return serviceLocations.staticFilesUrl() + super.getImage();
            }

            @Override
            public String getVideo() {
            return serviceLocations.staticFilesUrl() + super.getVideo();
            }            
                   
            }   
        