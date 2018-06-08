package cn.productai.apiv2;

import cn.productai.api.core.DefaultProfile;
import cn.productai.api.core.IProfile;
import cn.productai.apiv2.exceptions.PAIException;
import cn.productai.apiv2.impl.CustomTrainingImpl;
import cn.productai.apiv2.impl.TrainingSetImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;


public class Test {
    private static final Logger logger = LogManager.getLogger(Test.class);

    public static void main(String[] args) {
        IProfile profile = new DefaultProfile();
        profile.setAccessKeyId(System.getenv("X_CA_ACCESS_KEY_ID"));

        try {
            trainingSetExamples(profile);
            customTrainingExamples(profile);
        } catch (PAIException e) {
            e.printStackTrace();
        }
    }

    static void customTrainingExamples(IProfile profile) throws PAIException {
        CustomTraining customTraining = new CustomTrainingImpl();
        customTraining.setProfile(profile);

        String result;
        String serviceId = "avhiclgf";

        result = customTraining.createService(serviceId,
                "Foo Service", "Foo Description", "classifier");
        System.out.println(">>> createService result: " + result);

        result = customTraining.listAllService();
        System.out.println(">>> listAllService result: " + result);

        result = customTraining.getServiceById(serviceId);
        System.out.println(">>> getServiceById result: " + result);

        result = customTraining.updateServiceName(serviceId, "Bar name");
        System.out.println(">>> updateServiceName result: " + result);

        result = customTraining.predict(serviceId, "https://styleai-shopping.oss-cn-beijing.aliyuncs.com/14d1b1e8a38ae767cc67834799be5ef9ed4595b9.jpg", null);
        System.out.println(">>> predict result: " + result);

        result = customTraining.deleteServiceById(serviceId);
        System.out.println(">>> deleteServiceById result: " + result);
    }

    static void trainingSetExamples(IProfile profile) throws PAIException {
        TrainingSet trainingSet = new TrainingSetImpl();
        trainingSet.setProfile(profile);

        String trainingSetId = "cx42ut7z";

        String result;

        result = trainingSet.create("foo name", "bar of description");
        System.out.println(">>> Create result: " + result);

        result = trainingSet.listAll();
        System.out.println(">>> List all result: " + result);

        result = trainingSet.getById(trainingSetId);
        System.out.println(">>> Get by id result: " + result);

        result = trainingSet.update(trainingSetId, "new name", "new desc");
        System.out.println(">>> Update by id result: " + result);

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            File file = new File(classloader.getResource("raw_data.csv").getFile());
            result = trainingSet.bulkAddTrainingData(trainingSetId, file);
            System.out.println(">>> bulkAddTrainingData result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            File file = new File(classloader.getResource("raw_data_delete.csv").getFile());
            result = trainingSet.bulkDeleteTrainingData(trainingSetId, file);
            System.out.println(">>> bulkDeleteTrainingData result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        result = trainingSet.clearTrainingSet(trainingSetId, "Foo Name");
        System.out.println(">>> clearTrainingSet by id result: " + result);

        result = trainingSet.delete(trainingSetId);
        System.out.println(">>> Delete by id result: " + result);
    }
}
