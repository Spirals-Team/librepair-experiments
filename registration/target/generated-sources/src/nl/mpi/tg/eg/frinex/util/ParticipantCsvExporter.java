package nl.mpi.tg.eg.frinex.util;
                
                import java.io.IOException;
                import java.text.SimpleDateFormat;
                import java.util.List;
                import nl.mpi.tg.eg.frinex.model.Participant;
                import nl.mpi.tg.eg.frinex.model.TagData;
                import org.apache.commons.csv.CSVPrinter;
                
                public class ParticipantCsvExporter {
            
                public void appendAggregateCsvHeader(CSVPrinter printer) throws IOException {
                printer.printRecord("UserId","WorkerId","DatOfBirth","Gender","GroupAllocation_Room_1","GroupAllocation_Room_2","GroupAllocation_Room_3","GroupAllocation_Room_4");
                }
                public void appendAggregateCsvRow(CSVPrinter printer, Participant participant, List<TagData> participantTagData) throws IOException, CsvExportException {
                SimpleDateFormat format = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss");
            
                TagData startData=null;
                for (TagData currentData : participantTagData) {
                if ("NextStimulus".equals(currentData.getEventTag())) {
                
                startData=currentData;
                switch(startData.getTagValue()){
            
                default:
                throw new CsvExportException("no case for: " + startData.getEventTag() + " " + startData.getTagValue() + " " + startData.getUserId());
                }}
                if ("RatingButton".equals(currentData.getEventTag()) || "volgende [ spatiebalk ]".equals(currentData.getTagValue())) {
                TagData endData=currentData;
                String msString = (startData==null)?"no start event ":Integer.toString(endData.getEventMs()-startData.getEventMs());   
                if(startData!=null) //throw new CsvExportException("no start for: " + endData.getEventTag() + " " + endData.getTagValue() + " " + endData.getUserId() + " " + endData.getTagDate());
                switch(startData.getTagValue()){
            
                default:
                throw new CsvExportException("no case for: " + endData.getEventTag() + " " + endData.getTagValue() + " " + endData.getUserId());
                }
                startData=null;
                }                
                }
                printer.printRecord(participant.getUserId(),
participant.getWorkerId(),
participant.getDatOfBirth(),
participant.getGender(),
participant.getGroupAllocation_Room_1(),
participant.getGroupAllocation_Room_2(),
participant.getGroupAllocation_Room_3(),
participant.getGroupAllocation_Room_4());
                }
            
                public void appendCsvHeader(CSVPrinter printer) throws IOException {
                printer.printRecord("UserId","WorkerId","DatOfBirth","Gender","GroupAllocation_Room_1","GroupAllocation_Room_2","GroupAllocation_Room_3","GroupAllocation_Room_4");
                }
                public void appendCsvRow(CSVPrinter printer, Participant participant) throws IOException {
                printer.printRecord(participant.getUserId(),participant.getWorkerId(),participant.getDatOfBirth(),participant.getGender(),participant.getGroupAllocation_Room_1(),participant.getGroupAllocation_Room_2(),participant.getGroupAllocation_Room_3(),participant.getGroupAllocation_Room_4());
                }
                }