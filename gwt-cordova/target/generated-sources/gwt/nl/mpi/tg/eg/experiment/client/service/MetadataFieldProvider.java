package nl.mpi.tg.eg.experiment.client.service;

                import com.google.gwt.core.client.GWT;
                import nl.mpi.tg.eg.experiment.client.MetadataFields;
                import nl.mpi.tg.eg.experiment.client.model.MetadataField;
                
                public class MetadataFieldProvider {

                private final MetadataFields mateadataFields = GWT.create(MetadataFields.class);
            
                    public final MetadataField workerIdMetadataField = new MetadataField(mateadataFields.postName_workerId(), mateadataFields.registrationField_workerId(), mateadataFields.fieldValues_workerId(), mateadataFields.controlledRegex_workerId(), mateadataFields.controlledMessage_workerId());
                    public final MetadataField datOfBirthMetadataField = new MetadataField(mateadataFields.postName_datOfBirth(), mateadataFields.registrationField_datOfBirth(), mateadataFields.fieldValues_datOfBirth(), mateadataFields.controlledRegex_datOfBirth(), mateadataFields.controlledMessage_datOfBirth());
                    public final MetadataField genderMetadataField = new MetadataField(mateadataFields.postName_gender(), mateadataFields.registrationField_gender(), mateadataFields.fieldValues_gender(), mateadataFields.controlledRegex_gender(), mateadataFields.controlledMessage_gender());
                    public final MetadataField groupAllocation_Room_1MetadataField = new MetadataField(mateadataFields.postName_groupAllocation_Room_1(), mateadataFields.registrationField_groupAllocation_Room_1(), mateadataFields.fieldValues_groupAllocation_Room_1(), mateadataFields.controlledRegex_groupAllocation_Room_1(), mateadataFields.controlledMessage_groupAllocation_Room_1());
                    public final MetadataField groupAllocation_Room_2MetadataField = new MetadataField(mateadataFields.postName_groupAllocation_Room_2(), mateadataFields.registrationField_groupAllocation_Room_2(), mateadataFields.fieldValues_groupAllocation_Room_2(), mateadataFields.controlledRegex_groupAllocation_Room_2(), mateadataFields.controlledMessage_groupAllocation_Room_2());
                    public final MetadataField groupAllocation_Room_3MetadataField = new MetadataField(mateadataFields.postName_groupAllocation_Room_3(), mateadataFields.registrationField_groupAllocation_Room_3(), mateadataFields.fieldValues_groupAllocation_Room_3(), mateadataFields.controlledRegex_groupAllocation_Room_3(), mateadataFields.controlledMessage_groupAllocation_Room_3());
                    public final MetadataField groupAllocation_Room_4MetadataField = new MetadataField(mateadataFields.postName_groupAllocation_Room_4(), mateadataFields.registrationField_groupAllocation_Room_4(), mateadataFields.fieldValues_groupAllocation_Room_4(), mateadataFields.controlledRegex_groupAllocation_Room_4(), mateadataFields.controlledMessage_groupAllocation_Room_4());
                public final MetadataField[] metadataFieldArray = new MetadataField[]{
            workerIdMetadataField, datOfBirthMetadataField, genderMetadataField, groupAllocation_Room_1MetadataField, groupAllocation_Room_2MetadataField, groupAllocation_Room_3MetadataField, groupAllocation_Room_4MetadataField
                };
                }