package acceptance.classification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import acceptance.AbstractAccTest;
import pro.taskana.ClassificationService;
import pro.taskana.ClassificationSummary;
import pro.taskana.TimeInterval;
import pro.taskana.BaseQuery.SortDirection;
import pro.taskana.exceptions.ClassificationNotFoundException;
import pro.taskana.exceptions.ConcurrencyException;
import pro.taskana.exceptions.InvalidArgumentException;
import pro.taskana.exceptions.NotAuthorizedException;
import pro.taskana.security.JAASRunner;
import pro.taskana.security.WithAccessId;

/**
 * Acceptance test for all "get classification" scenarios.
 */
@RunWith(JAASRunner.class)
public class QueryClassificationAccTest extends AbstractAccTest {

    private static SortDirection asc = SortDirection.ASCENDING;
    private static SortDirection desc = SortDirection.DESCENDING;

    public QueryClassificationAccTest() {
        super();
    }

    @Test
    public void testQueryClassificationValuesForColumnName() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<String> columnValueList = classificationService.createClassificationQuery()
            .listValues("NAME", null);
        assertNotNull(columnValueList);
        assertEquals(15, columnValueList.size());

        columnValueList = classificationService.createClassificationQuery()
            .listValues("TYPE", null);
        assertNotNull(columnValueList);
        assertEquals(2, columnValueList.size());

        columnValueList = classificationService.createClassificationQuery()
            .domainIn("")
            .listValues("TYPE", null);
        assertNotNull(columnValueList);
        assertEquals(2, columnValueList.size());

        columnValueList = classificationService.createClassificationQuery()
            .domainIn("")
            .listValues("CREATED", null);
        assertNotNull(columnValueList);

        columnValueList = classificationService.createClassificationQuery()
            .domainIn("")
            .validInDomainEquals(false)
            .listValues("VALID_IN_DOMAIN", null);
        assertNotNull(columnValueList);
        assertEquals(1, columnValueList.size());    // all are false in ""
    }

    @Test
    public void testFindClassificationsByCategoryAndDomain() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> classificationSummaryList = classificationService.createClassificationQuery()
            .categoryIn("MANUAL")
            .domainIn("DOMAIN_A")
            .list();

        assertNotNull(classificationSummaryList);
        assertEquals(2, classificationSummaryList.size());
    }

    @Test
    public void testGetOneClassificationForMultipleDomains() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> classifications = classificationService.createClassificationQuery()
            .keyIn("L10000")
            .domainIn("DOMAIN_A", "DOMAIN_B", "")
            .list();

        assertNotNull(classifications);
        assertEquals(2, classifications.size());
    }

    @Test
    public void testGetClassificationsForTypeAndParent() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> classifications = classificationService.createClassificationQuery()
            .typeIn("TASK", "DOCUMENT")
            .parentIdIn("")
            .list();

        assertNotNull(classifications);
        assertEquals(25, classifications.size());

        List<ClassificationSummary> documentTypes = classifications.stream()
            .filter(c -> c.getType().equals("DOCUMENT"))
            .collect(
                Collectors.toList());
        assertEquals(2, documentTypes.size());

        List<ClassificationSummary> taskTypes = classifications.stream()
            .filter(c -> c.getType().equals("TASK"))
            .collect(
                Collectors.toList());
        assertEquals(23, taskTypes.size());
    }

    @Test
    public void testGetClassificationsForKeyAndCategories() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> classifications = classificationService.createClassificationQuery()
            .keyIn("T2100", "L10000")
            .categoryIn("EXTERNAL", "MANUAL")
            .list();

        assertNotNull(classifications);
        assertEquals(5, classifications.size());

        List<ClassificationSummary> externCategory = classifications.stream()
            .filter(c -> c.getCategory().equals("EXTERNAL"))
            .collect(
                Collectors.toList());
        assertEquals(2, externCategory.size());

        List<ClassificationSummary> manualCategory = classifications.stream()
            .filter(c -> c.getCategory().equals("MANUAL"))
            .collect(
                Collectors.toList());
        assertEquals(3, manualCategory.size());
    }

    @Test
    public void testGetClassificationsWithParentId() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> classifications = classificationService.createClassificationQuery()
            .keyIn("A12", "A13")
            .categoryIn("EXTERNAL", "MANUAL")
            .parentIdIn("CLI:100000000000000000000000000000000014")
            .list();

        assertNotNull(classifications);
        assertEquals(1, classifications.size());

        classifications = classificationService.createClassificationQuery()
            .keyIn("A12", "A13")
            .categoryIn("EXTERNAL", "MANUAL", "AUTOMATIC")
            .parentIdIn("CLI:100000000000000000000000000000000014", "CLI:100000000000000000000000000000000010",
                "CLI:100000000000000000000000000000000011")
            .domainIn("DOMAIN_A")
            .list();
        assertNotNull(classifications);
        assertEquals(2, classifications.size());
    }

    @Test
    public void testGetClassificationsWithParentKey() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> classifications = classificationService.createClassificationQuery()
            .keyIn("A12", "A13")
            .categoryIn("EXTERNAL", "MANUAL")
            .parentKeyIn("L10000")
            .list();

        assertNotNull(classifications);
        assertEquals(1, classifications.size());

        classifications = classificationService.createClassificationQuery()
            .keyIn("A12", "A13")
            .categoryIn("EXTERNAL", "MANUAL", "AUTOMATIC")
            .parentKeyIn("L10000", "T2100", "T6310")
            .domainIn("DOMAIN_A")
            .list();
        assertNotNull(classifications);
        assertEquals(2, classifications.size());
    }

    @Test
    public void testGetClassificationsWithCustom1() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> classifications = classificationService.createClassificationQuery()
            .custom1Like("VNR,RVNR,KOLVNR", "VNR")
            .domainIn("DOMAIN_A")
            .list();
        assertNotNull(classifications);
        assertEquals(14, classifications.size());
    }

    @Test
    public void testGetClassificationsWithCustom1Like() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> classifications = classificationService.createClassificationQuery()
            .custom1Like("%RVNR%")
            .domainIn("DOMAIN_A")
            .typeIn("TASK")
            .list();
        assertNotNull(classifications);
        assertEquals(13, classifications.size());
    }

    @Test
    public void testGetClassificationsWithParentAndCustom2() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> classifications = classificationService.createClassificationQuery()
            .parentIdIn("CLI:100000000000000000000000000000000004")
            .custom2Like("TEXT_1", "TEXT_2")
            .list();
        // zwei tests
        assertNotNull(classifications);
        assertEquals(3, classifications.size());
    }

    @Test
    public void testFindClassificationsByCreatedTimestamp() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> classificationSummaryList = classificationService.createClassificationQuery()
            .domainIn("DOMAIN_A")
            .createdWithin(todaysInterval())
            .list();

        assertNotNull(classificationSummaryList);
        assertEquals(17, classificationSummaryList.size());
    }

    @Test
    public void testFindClassificationsByPriorityAndValidInDomain() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> list = classificationService.createClassificationQuery()
            .validInDomainEquals(Boolean.TRUE)
            .priorityIn(1, 2, 3)
            .list();
        assertEquals(14, list.size());

    }

    @WithAccessId(
        userName = "businessadmin")
    @Test
    public void testFindClassificationByModifiedWithin()
        throws ClassificationNotFoundException, NotAuthorizedException, ConcurrencyException, InvalidArgumentException {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        String clId = "CLI:200000000000000000000000000000000015";
        classificationService.updateClassification(classificationService.getClassification(clId));
        List<ClassificationSummary> list = classificationService.createClassificationQuery()
                .modifiedWithin(new TimeInterval(
                        classificationService.getClassification(clId).getModified(),
                        Instant.now()))
                .list();
        assertEquals(1, list.size());
        assertEquals(clId, list.get(0).getId());
    }

    @Test
    public void testGetClassificationByNameLike() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .nameLike("Dynamik%")
                .list();
        assertEquals(8, results.size());
    }

    @Test
    public void testGetClassificationByNameIn() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .nameIn("Widerruf", "OLD-Leistungsfall")
                .list();
        assertEquals(5, results.size());
    }

    @Test
    public void testGetClassificationByDescriptionLike() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .descriptionLike("Widerruf%")
                .list();
        assertEquals(8, results.size());
    }

    @Test
    public void testClassificationByGetServiceLevelIn() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .serviceLevelIn("P2D")
                .list();
        assertEquals(5, results.size());
    }

    @Test
    public void testGetClassificationByServiceLevelLike() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .serviceLevelLike("PT%")
                .list();
        assertEquals(2, results.size());
    }

    @Test
    public void testGetClassificationByApplicationEntryPointIn() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .applicationEntryPointIn("specialPoint", "point0815")
                .list();
        assertEquals(3, results.size());
    }

    @Test
    public void testGetClassificationByApplicationEntryPointLike() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .applicationEntryPointLike("point%")
                .list();
        assertEquals(3, results.size());
    }

    @Test
    public void testGetClassificationByCustom1In() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom1In("VNR,RVNR,KOLVNR, ANR", "VNR")
                .list();
        assertEquals(13, results.size());
    }

    @Test
    public void getClassificationByCustom2In() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom2In("CUSTOM_2", "custom2")
                .list();
        assertEquals(2, results.size());
    }

    @Test
    public void getClassificationByCustom3In() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom3In("Custom_3", "custom3")
                .list();
        assertEquals(2, results.size());
    }

    @Test
    public void getClassificationByCustom4In() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom4In("custom_4", "custom4")
                .list();
        assertEquals(2, results.size());
    }

    @Test
    public void getClassificationByCustom5In() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom5In("cuStom_5", "custom5")
                .list();
        assertEquals(2, results.size());
    }

    @Test
    public void getClassificationByCustom6In() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom6In("cusTom_6", "custom6")
                .list();
        assertEquals(2, results.size());
    }

    @Test
    public void getClassificationByCustom7In() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom7In("custOm_7", "custom7")
                .list();
        assertEquals(2, results.size());
    }

    @Test
    public void getClassificationByCustom8In() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom8In("custoM_8", "custom8")
                .list();
        assertEquals(2, results.size());
    }

    @Test
    public void testGetClassificationByCustom3Like() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom3Like("cus%")
                .list();
        assertEquals(2, results.size());
    }

    @Test
    public void testGetClassificationByCustom4Like() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom4Like("cus%")
                .list();
        assertEquals(3, results.size());
    }

    @Test
    public void testGetClassificationByCustom5Like() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom5Like("cus%")
                .list();
        assertEquals(2, results.size());
    }

    @Test
    public void testGetClassificationByCustom6Like() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom6Like("cus%")
                .list();
        assertEquals(3, results.size());
    }

    @Test
    public void testGetClassificationByCustom7Like() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom7Like("cus%")
                .list();
        assertEquals(3, results.size());
    }

    @Test
    public void testGetClassificationByCustom8Like() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .custom8Like("cus%")
                .list();
        assertEquals(3, results.size());
    }

    @Test
    public void testGetClassificationOrderByKeyAsc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByKey(asc)
                .list();
        assertEquals("A12", results.get(0).getKey());
    }

    @Test
    public void testGetClassificationOrderByParentIdDesc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByParentId(desc)
                .list();
        assertEquals("CLI:100000000000000000000000000000000015", results.get(0).getParentId());
    }

    @Test
    public void testGetClassificationOrderByParentKeyDesc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByParentId(desc)
                .list();
        assertEquals("CLI:100000000000000000000000000000000015", results.get(0).getParentId());
    }

    @Test
    public void testGetClassificationOrderByCategoryDesc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByCategory(desc)
                .list();
        assertEquals("MANUAL", results.get(0).getCategory());
    }

    @Test
    public void testGetClassificationOrderByDomainAsc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByDomain(asc)
                .list();
        assertEquals("", results.get(0).getDomain());
    }

    @Test
    public void testGetClassificationOrderByPriorityDesc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByPriority(desc)
                .list();
        assertEquals(999, results.get(0).getPriority());
    }

    @Test
    public void testGetClassificationOrderByNameAsc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByName(asc)
                .list();
        assertEquals("Beratungsprotokoll", results.get(0).getName());
    }

    @Test
    public void testGetClassificationOrderByServiceLevelDesc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByServiceLevel(desc)
                .list();
        assertEquals("PT7H", results.get(0).getServiceLevel());
    }

    @Test
    public void testGetClassificationOrderByApplicationEntryPointAsc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByApplicationEntryPoint(asc)
                .list();
        assertEquals("CLI:000000000000000000000000000000000001", results.get(0).getId());
    }

    @Test
    public void testGetClassificationOrderByCustom1Desc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByCustom1(desc)
                .list();
        assertEquals("CLI:000000000000000000000000000000000002", results.get(0).getId());
    }

    @Test
    public void testGetClassificationOrderByCustom2Asc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByCustom2(asc)
                .list();
        assertEquals("CLI:000000000000000000000000000000000001", results.get(0).getId());
    }

    @Test
    public void testGetClassificationOrderByCustom3Desc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByCustom3(desc)
                .list();
        assertEquals("CLI:100000000000000000000000000000000014", results.get(0).getId());
    }

    @Test
    public void testGetClassificationOrderByCustom4Asc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByCustom4(asc)
                .list();
        assertEquals("CLI:000000000000000000000000000000000001", results.get(0).getId());
    }

    @Test
    public void testGetClassificationOrderByCustom5Desc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByCustom5(desc)
                .list();
        assertEquals("CLI:100000000000000000000000000000000011", results.get(0).getId());
    }

    @Test
    public void testGetClassificationOrderByCustom6Asc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByCustom6(asc)
                .list();
        assertEquals("CLI:000000000000000000000000000000000001", results.get(0).getId());
    }

    @Test
    public void testGetClassificationOrderByCustom7Desc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByCustom7(desc)
                .list();
        assertEquals("CLI:100000000000000000000000000000000011", results.get(0).getId());
    }

    @Test
    public void testGetClassificationOrderByCustom8Asc() {
        ClassificationService classificationService = taskanaEngine.getClassificationService();
        List<ClassificationSummary> results = classificationService.createClassificationQuery()
                .orderByCustom8(asc)
                .list();
        assertEquals("CLI:000000000000000000000000000000000001", results.get(0).getId());
    }
}
