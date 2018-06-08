package cn.productai.apiv2;

import cn.productai.api.core.DefaultProfile;
import cn.productai.api.core.IProfile;
import cn.productai.apiv2.exceptions.PAIException;
import cn.productai.apiv2.impl.TrainingSetImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TrainingSetTest {

    private static IProfile profile = new DefaultProfile();
    private static TrainingSet trainingSet = new TrainingSetImpl();

    @BeforeAll
    static void initAll() {
        profile.setAccessKeyId(System.getenv("X_CA_ACCESS_KEY_ID"));
        trainingSet.setProfile(profile);
    }

    @Test
    @DisplayName("Create")
    void testCreate() throws PAIException {
        String result = trainingSet.create("Foo Name", "Bar Description");
        System.out.println("create");
        System.out.println(result);
    }

    @Test
    @DisplayName("List all")
    void testListAll() throws PAIException {
        String result = trainingSet.listAll();
        System.out.println("List all");
        System.out.println(result);
    }

    @Test
    @DisplayName("Get service by Id")
    void testGetById() throws PAIException {
        String trainingSetId = "0ingz90g";
        String result = trainingSet.getById(trainingSetId);
        System.out.println("Get by Id");
        System.out.println(result);
    }

    @Test
    @DisplayName("Update")
    void testUpdate() throws PAIException {
        String trainingSetId = "0ingz90g";
        String result = trainingSet.update(trainingSetId,
                "new name", "new description");
        System.out.println("Update");
        System.out.println(result);
    }

    @Test
    @DisplayName("Delete")
    void testDelete() throws PAIException {
        String trainingSetId = "0ingz90g";
        String result = trainingSet.delete(trainingSetId);
        System.out.println("Delete");
        System.out.println(result);
    }

    @Test
    @DisplayName("Delete")
    void testBulkAddTrainingData() {
//        String trainingSetId = "0ingz90g";
//        String file = "";
//        String result = trainingSet.bulkAddTrainingData(trainingSetId, file);
//        System.out.println("bulkAddTrainingData");
//        System.out.println(result);
    }

    @Test
    @DisplayName("Delete")
    void testBulkDeleteTrainingData() {
//        String trainingSetId = "0ingz90g";
//        String file = "";
//        String result = trainingSet.bulkDeleteTrainingData(trainingSetId, file);
//        System.out.println("bulkDeleteTrainingData");
//        System.out.println(result);
    }
}
