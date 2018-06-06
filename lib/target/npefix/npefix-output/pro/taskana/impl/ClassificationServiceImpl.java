package pro.taskana.impl;

import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.ConstructorContext;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.context.TryContext;
import fr.inria.spirals.npefix.resi.exception.AbnormalExecutionError;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.taskana.Classification;
import pro.taskana.ClassificationQuery;
import pro.taskana.ClassificationService;
import pro.taskana.ClassificationSummary;
import pro.taskana.TaskQuery;
import pro.taskana.TaskService;
import pro.taskana.TaskSummary;
import pro.taskana.TaskanaEngine;
import pro.taskana.TaskanaRole;
import pro.taskana.configuration.TaskanaEngineConfiguration;
import pro.taskana.exceptions.ClassificationAlreadyExistException;
import pro.taskana.exceptions.ClassificationInUseException;
import pro.taskana.exceptions.ClassificationNotFoundException;
import pro.taskana.exceptions.ConcurrencyException;
import pro.taskana.exceptions.DomainNotFoundException;
import pro.taskana.exceptions.InvalidArgumentException;
import pro.taskana.exceptions.NotAuthorizedException;
import pro.taskana.impl.util.IdGenerator;
import pro.taskana.mappings.ClassificationMapper;
import pro.taskana.mappings.JobMapper;
import pro.taskana.mappings.TaskMapper;
import static pro.taskana.impl.Job.State.READY;

public class ClassificationServiceImpl implements ClassificationService {
    private static final String ID_PREFIX_CLASSIFICATION = "CLI";

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassificationServiceImpl.class);

    private ClassificationMapper classificationMapper;

    private TaskMapper taskMapper;

    private TaskanaEngineImpl taskanaEngine;

    ClassificationServiceImpl(TaskanaEngine taskanaEngine, ClassificationMapper classificationMapper, TaskMapper taskMapper) {
        super();
        ConstructorContext _bcornu_methode_context1 = new ConstructorContext(ClassificationServiceImpl.class, 46, 1664, 1976);
        try {
            this.taskanaEngine = ((TaskanaEngineImpl) (taskanaEngine));
            CallChecker.varAssign(this.taskanaEngine, "this.taskanaEngine", 49, 1820, 1874);
            this.classificationMapper = classificationMapper;
            CallChecker.varAssign(this.classificationMapper, "this.classificationMapper", 50, 1884, 1932);
            this.taskMapper = taskMapper;
            CallChecker.varAssign(this.taskMapper, "this.taskMapper", 51, 1942, 1970);
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    @Override
    public Classification createClassification(Classification classification) throws ClassificationAlreadyExistException, ClassificationNotFoundException, DomainNotFoundException, InvalidArgumentException, NotAuthorizedException {
        MethodContext _bcornu_methode_context1 = new MethodContext(Classification.class, 55, 1983, 4137);
        try {
            CallChecker.varInit(this, "this", 55, 1983, 4137);
            CallChecker.varInit(classification, "classification", 55, 1983, 4137);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 55, 1983, 4137);
            CallChecker.varInit(this.taskMapper, "taskMapper", 55, 1983, 4137);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 55, 1983, 4137);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 55, 1983, 4137);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 55, 1983, 4137);
            if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 58, 2248, 2253)) {
                CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 58, 2248, 2253).debug("entry to createClassification(classification = {})", classification);
            }
            if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 59, 2340, 2352)) {
                taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 59, 2340, 2352);
                CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 59, 2340, 2352).checkRoleMembership(TaskanaRole.BUSINESS_ADMIN, TaskanaRole.ADMIN);
            }
            if (CallChecker.beforeDeref(classification, Classification.class, 60, 2462, 2475)) {
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 60, 2435, 2447)) {
                    if (CallChecker.beforeDeref(classification, Classification.class, 60, 2504, 2517)) {
                        if (CallChecker.beforeDeref("", String.class, 60, 2494, 2495)) {
                            classification = CallChecker.beforeCalled(classification, Classification.class, 60, 2462, 2475);
                            taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 60, 2435, 2447);
                            classification = CallChecker.beforeCalled(classification, Classification.class, 60, 2504, 2517);
                            if ((!(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 60, 2435, 2447).domainExists(CallChecker.isCalled(classification, Classification.class, 60, 2462, 2475).getDomain()))) && (!(CallChecker.isCalled("", String.class, 60, 2494, 2495).equals(CallChecker.isCalled(classification, Classification.class, 60, 2504, 2517).getDomain())))) {
                                if (CallChecker.beforeDeref(classification, Classification.class, 61, 2581, 2594)) {
                                    if (CallChecker.beforeDeref(classification, Classification.class, 62, 2637, 2650)) {
                                        classification = CallChecker.beforeCalled(classification, Classification.class, 61, 2581, 2594);
                                        classification = CallChecker.beforeCalled(classification, Classification.class, 62, 2637, 2650);
                                        throw new DomainNotFoundException(CallChecker.isCalled(classification, Classification.class, 61, 2581, 2594).getDomain(), (("Domain " + (CallChecker.isCalled(classification, Classification.class, 62, 2637, 2650).getDomain())) + " does not exist in the configuration."));
                                    }else
                                        throw new AbnormalExecutionError();
                                    
                                }else
                                    throw new AbnormalExecutionError();
                                
                            }
                        }else
                            throw new AbnormalExecutionError();
                        
                    }else
                        throw new AbnormalExecutionError();
                    
                }else
                    throw new AbnormalExecutionError();
                
            }else
                throw new AbnormalExecutionError();
            
            ClassificationImpl classificationImpl = CallChecker.init(ClassificationImpl.class);
            boolean isClassificationExisting = CallChecker.init(boolean.class);
            TryContext _bcornu_try_context_1 = new TryContext(1, ClassificationServiceImpl.class);
            try {
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 67, 2839, 2851)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 67, 2839, 2851);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 67, 2839, 2851).openConnection();
                }
                if (CallChecker.beforeDeref(classification, Classification.class, 68, 2934, 2947)) {
                    if (CallChecker.beforeDeref(classification, Classification.class, 68, 2959, 2972)) {
                        classification = CallChecker.beforeCalled(classification, Classification.class, 68, 2934, 2947);
                        classification = CallChecker.beforeCalled(classification, Classification.class, 68, 2959, 2972);
                        isClassificationExisting = doesClassificationExist(CallChecker.isCalled(classification, Classification.class, 68, 2934, 2947).getKey(), CallChecker.isCalled(classification, Classification.class, 68, 2959, 2972).getDomain());
                        CallChecker.varAssign(isClassificationExisting, "isClassificationExisting", 68, 2883, 2986);
                    }
                }
                if (isClassificationExisting) {
                    throw new ClassificationAlreadyExistException(classification);
                }
                classificationImpl = ((ClassificationImpl) (classification));
                CallChecker.varAssign(classificationImpl, "classificationImpl", 72, 3137, 3193);
                this.checkClassificationId(classificationImpl);
                if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 74, 3267, 3284)) {
                    classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 74, 3267, 3284);
                    CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 74, 3267, 3284).setCreated(Instant.now());
                }
                if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 75, 3356, 3373)) {
                    if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 75, 3325, 3342)) {
                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 75, 3356, 3373);
                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 75, 3325, 3342);
                        CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 75, 3325, 3342).setModified(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 75, 3356, 3373).getCreated());
                    }
                }
                this.initDefaultClassificationValues(classificationImpl);
                if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 78, 3477, 3494)) {
                    if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 78, 3522, 3539)) {
                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 78, 3477, 3494);
                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 78, 3522, 3539);
                        if (((CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 78, 3477, 3494).getParentId()) != null) && (!(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 78, 3522, 3539).getParentId().isEmpty()))) {
                            if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 79, 3607, 3624)) {
                                classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 79, 3607, 3624);
                                this.getClassification(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 79, 3607, 3624).getParentId());
                            }
                        }
                    }
                }
                if (CallChecker.beforeDeref(classificationMapper, ClassificationMapper.class, 81, 3668, 3687)) {
                    classificationMapper = CallChecker.beforeCalled(classificationMapper, ClassificationMapper.class, 81, 3668, 3687);
                    CallChecker.isCalled(classificationMapper, ClassificationMapper.class, 81, 3668, 3687).insert(classificationImpl);
                }
                if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 82, 3729, 3734)) {
                    CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 82, 3729, 3734).debug("Method createClassification created classification {}.", classificationImpl);
                }
                if (CallChecker.beforeDeref(classification, Classification.class, 84, 3839, 3852)) {
                    classification = CallChecker.beforeCalled(classification, Classification.class, 84, 3839, 3852);
                    if (CallChecker.beforeDeref(CallChecker.isCalled(classification, Classification.class, 84, 3839, 3852).getDomain(), String.class, 84, 3839, 3864)) {
                        classification = CallChecker.beforeCalled(classification, Classification.class, 84, 3839, 3852);
                        if (!(CallChecker.isCalled(CallChecker.isCalled(classification, Classification.class, 84, 3839, 3852).getDomain(), String.class, 84, 3839, 3864).isEmpty())) {
                            addClassificationToRootDomain(classificationImpl);
                        }
                    }
                }
            } finally {
                _bcornu_try_context_1.finallyStart(1);
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 88, 3992, 4004)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 88, 3992, 4004);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 88, 3992, 4004).returnConnection();
                }
                if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 89, 4038, 4043)) {
                    CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 89, 4038, 4043).debug("exit from createClassification()");
                }
            }
            return classificationImpl;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Classification) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    private void checkClassificationId(ClassificationImpl classificationImpl) throws InvalidArgumentException {
        MethodContext _bcornu_methode_context2 = new MethodContext(void.class, 94, 4144, 4453);
        try {
            CallChecker.varInit(this, "this", 94, 4144, 4453);
            CallChecker.varInit(classificationImpl, "classificationImpl", 94, 4144, 4453);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 94, 4144, 4453);
            CallChecker.varInit(this.taskMapper, "taskMapper", 94, 4144, 4453);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 94, 4144, 4453);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 94, 4144, 4453);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 94, 4144, 4453);
            if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 95, 4264, 4281)) {
                if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 95, 4313, 4330)) {
                    if (CallChecker.beforeDeref("", String.class, 95, 4303, 4304)) {
                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 95, 4264, 4281);
                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 95, 4313, 4330);
                        if (((CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 95, 4264, 4281).getId()) != null) && (!(CallChecker.isCalled("", String.class, 95, 4303, 4304).equals(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 95, 4313, 4330).getId())))) {
                            throw new InvalidArgumentException("ClassificationId should be null on creation");
                        }
                    }
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    private void addClassificationToRootDomain(ClassificationImpl classificationImpl) {
        MethodContext _bcornu_methode_context3 = new MethodContext(void.class, 100, 4460, 6347);
        try {
            CallChecker.varInit(this, "this", 100, 4460, 6347);
            CallChecker.varInit(classificationImpl, "classificationImpl", 100, 4460, 6347);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 100, 4460, 6347);
            CallChecker.varInit(this.taskMapper, "taskMapper", 100, 4460, 6347);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 100, 4460, 6347);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 100, 4460, 6347);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 100, 4460, 6347);
            if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 101, 4557, 4574)) {
                classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 101, 4557, 4574);
                if (CallChecker.beforeDeref(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 101, 4557, 4574).getDomain(), String.class, 101, 4557, 4586)) {
                    classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 101, 4557, 4574);
                    if (!(CallChecker.isCalled(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 101, 4557, 4574).getDomain(), String.class, 101, 4557, 4586).equals(""))) {
                        boolean doesExist = CallChecker.varInit(((boolean) (true)), "doesExist", 102, 4614, 4638);
                        String idBackup = CallChecker.init(String.class);
                        if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 103, 4670, 4687)) {
                            classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 103, 4670, 4687);
                            idBackup = CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 103, 4670, 4687).getId();
                            CallChecker.varAssign(idBackup, "idBackup", 103, 4670, 4687);
                        }
                        String domainBackup = CallChecker.init(String.class);
                        if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 104, 4732, 4749)) {
                            classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 104, 4732, 4749);
                            domainBackup = CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 104, 4732, 4749).getDomain();
                            CallChecker.varAssign(domainBackup, "domainBackup", 104, 4732, 4749);
                        }
                        boolean isValidInDomainBackup = CallChecker.init(boolean.class);
                        if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 105, 4808, 4825)) {
                            classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 105, 4808, 4825);
                            if (CallChecker.beforeDeref(((Boolean) (CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 105, 4808, 4825).getIsValidInDomain())), boolean.class, 105, 4808, 4846)) {
                                if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 105, 4808, 4825)) {
                                    classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 105, 4808, 4825);
                                    isValidInDomainBackup = CallChecker.isCalled(((Boolean) (CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 105, 4808, 4825).getIsValidInDomain())), boolean.class, 105, 4808, 4846);
                                    CallChecker.varAssign(isValidInDomainBackup, "isValidInDomainBackup", 105, 4808, 4846);
                                }
                            }
                        }
                        if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 106, 4861, 4878)) {
                            classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 106, 4861, 4878);
                            CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 106, 4861, 4878).setId(IdGenerator.generateWithPrefix(ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION));
                        }
                        if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 107, 4957, 4974)) {
                            classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 107, 4957, 4974);
                            CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 107, 4957, 4974).setDomain("");
                        }
                        if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 108, 5003, 5020)) {
                            classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 108, 5003, 5020);
                            CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 108, 5003, 5020).setIsValidInDomain(false);
                        }
                        TryContext _bcornu_try_context_2 = new TryContext(2, ClassificationServiceImpl.class, "pro.taskana.exceptions.ClassificationNotFoundException", "pro.taskana.exceptions.ClassificationAlreadyExistException");
                        try {
                            if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 110, 5106, 5123)) {
                                if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 110, 5135, 5152)) {
                                    classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 110, 5106, 5123);
                                    classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 110, 5135, 5152);
                                    this.getClassification(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 110, 5106, 5123).getKey(), CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 110, 5135, 5152).getDomain());
                                }
                            }
                            throw new ClassificationAlreadyExistException(classificationImpl);
                        } catch (ClassificationNotFoundException e) {
                            _bcornu_try_context_2.catchStart(2);
                            doesExist = false;
                            CallChecker.varAssign(doesExist, "doesExist", 113, 5325, 5342);
                            if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 114, 5360, 5365)) {
                                CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 114, 5360, 5365).debug("Method createClassification: Classification does not exist in root domain. Classification {}.", classificationImpl);
                            }
                        } catch (ClassificationAlreadyExistException ex) {
                            _bcornu_try_context_2.catchStart(2);
                            if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 118, 5611, 5616)) {
                                CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 118, 5611, 5616).warn("Method createClassification: Classification does already exist in root domain. Classification {}.", classificationImpl);
                            }
                        } finally {
                            _bcornu_try_context_2.finallyStart(2);
                            if (!doesExist) {
                                if (CallChecker.beforeDeref(classificationMapper, ClassificationMapper.class, 123, 5864, 5883)) {
                                    classificationMapper = CallChecker.beforeCalled(classificationMapper, ClassificationMapper.class, 123, 5864, 5883);
                                    CallChecker.isCalled(classificationMapper, ClassificationMapper.class, 123, 5864, 5883).insert(classificationImpl);
                                }
                                if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 124, 5933, 5938)) {
                                    CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 124, 5933, 5938).debug("Method createClassification: Classification created in root-domain, too. Classification {}.", classificationImpl);
                                }
                            }
                            if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 128, 6145, 6162)) {
                                classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 128, 6145, 6162);
                                CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 128, 6145, 6162).setId(idBackup);
                            }
                            if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 129, 6197, 6214)) {
                                classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 129, 6197, 6214);
                                CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 129, 6197, 6214).setDomain(domainBackup);
                            }
                            if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 130, 6257, 6274)) {
                                classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 130, 6257, 6274);
                                CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 130, 6257, 6274).setIsValidInDomain(isValidInDomainBackup);
                            }
                        }
                    }
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    @Override
    public Classification updateClassification(Classification classification) throws ClassificationNotFoundException, ConcurrencyException, InvalidArgumentException, NotAuthorizedException {
        MethodContext _bcornu_methode_context4 = new MethodContext(Classification.class, 136, 6354, 10413);
        try {
            CallChecker.varInit(this, "this", 136, 6354, 10413);
            CallChecker.varInit(classification, "classification", 136, 6354, 10413);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 136, 6354, 10413);
            CallChecker.varInit(this.taskMapper, "taskMapper", 136, 6354, 10413);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 136, 6354, 10413);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 136, 6354, 10413);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 136, 6354, 10413);
            if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 138, 6571, 6576)) {
                CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 138, 6571, 6576).debug("entry to updateClassification(Classification = {})", classification);
            }
            if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 139, 6663, 6675)) {
                taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 139, 6663, 6675);
                CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 139, 6663, 6675).checkRoleMembership(TaskanaRole.BUSINESS_ADMIN, TaskanaRole.ADMIN);
            }
            ClassificationImpl classificationImpl = CallChecker.varInit(null, "classificationImpl", 140, 6753, 6797);
            TryContext _bcornu_try_context_3 = new TryContext(3, ClassificationServiceImpl.class);
            try {
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 142, 6825, 6837)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 142, 6825, 6837);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 142, 6825, 6837).openConnection();
                }
                classificationImpl = ((ClassificationImpl) (classification));
                CallChecker.varAssign(classificationImpl, "classificationImpl", 143, 6869, 6925);
                Classification oldClassification = CallChecker.init(Classification.class);
                if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 146, 7074, 7091)) {
                    if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 147, 7119, 7136)) {
                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 146, 7074, 7091);
                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 147, 7119, 7136);
                        oldClassification = this.getClassification(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 146, 7074, 7091).getKey(), CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 147, 7119, 7136).getDomain());
                        CallChecker.varAssign(oldClassification, "oldClassification", 146, 7074, 7091);
                    }
                }
                if (CallChecker.beforeDeref(oldClassification, Classification.class, 148, 7169, 7185)) {
                    if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 148, 7208, 7225)) {
                        oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 148, 7169, 7185);
                        if (CallChecker.beforeDeref(CallChecker.isCalled(oldClassification, Classification.class, 148, 7169, 7185).getModified(), Instant.class, 148, 7169, 7199)) {
                            oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 148, 7169, 7185);
                            classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 148, 7208, 7225);
                            if (!(CallChecker.isCalled(CallChecker.isCalled(oldClassification, Classification.class, 148, 7169, 7185).getModified(), Instant.class, 148, 7169, 7199).equals(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 148, 7208, 7225).getModified()))) {
                                if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 151, 7448, 7465)) {
                                    classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 151, 7448, 7465);
                                    throw new ConcurrencyException(("The current Classification has been modified while editing. The values can not be updated. Classification=" + (CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 151, 7448, 7465).toString())));
                                }else
                                    throw new AbnormalExecutionError();
                                
                            }
                        }else
                            throw new AbnormalExecutionError();
                        
                    }else
                        throw new AbnormalExecutionError();
                    
                }else
                    throw new AbnormalExecutionError();
                
                if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 153, 7506, 7523)) {
                    classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 153, 7506, 7523);
                    CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 153, 7506, 7523).setModified(Instant.now());
                }
                this.initDefaultClassificationValues(classificationImpl);
                if (CallChecker.beforeDeref(oldClassification, Classification.class, 157, 7698, 7714)) {
                    if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 157, 7733, 7750)) {
                        oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 157, 7698, 7714);
                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 157, 7733, 7750);
                        if ((CallChecker.isCalled(oldClassification, Classification.class, 157, 7698, 7714).getCategory()) != (CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 157, 7733, 7750).getCategory())) {
                            List<TaskSummary> taskSumamries = CallChecker.init(List.class);
                            if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 158, 7819, 7831)) {
                                taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 158, 7819, 7831);
                                if (CallChecker.beforeDeref(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 158, 7819, 7831).getTaskService(), TaskService.class, 158, 7819, 7848)) {
                                    if (CallChecker.beforeDeref(oldClassification, Classification.class, 160, 7929, 7945)) {
                                        taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 158, 7819, 7831);
                                        if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 158, 7819, 7831).getTaskService(), TaskService.class, 158, 7819, 7848).createTaskQuery(), TaskQuery.class, 158, 7819, 7887)) {
                                            taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 158, 7819, 7831);
                                            oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 160, 7929, 7945);
                                            if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 158, 7819, 7831).getTaskService(), TaskService.class, 158, 7819, 7848).createTaskQuery(), TaskQuery.class, 158, 7819, 7887).classificationIdIn(CallChecker.isCalled(oldClassification, Classification.class, 160, 7929, 7945).getId()), TaskQuery.class, 158, 7819, 7954)) {
                                                taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 158, 7819, 7831);
                                                oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 160, 7929, 7945);
                                                taskSumamries = CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 158, 7819, 7831).getTaskService(), TaskService.class, 158, 7819, 7848).createTaskQuery(), TaskQuery.class, 158, 7819, 7887).classificationIdIn(CallChecker.isCalled(oldClassification, Classification.class, 160, 7929, 7945).getId()), TaskQuery.class, 158, 7819, 7954).list();
                                                CallChecker.varAssign(taskSumamries, "taskSumamries", 158, 7819, 7831);
                                            }
                                        }
                                    }
                                }
                            }
                            boolean categoryChanged = CallChecker.init(boolean.class);
                            if (CallChecker.beforeDeref(oldClassification, Classification.class, 163, 8030, 8046)) {
                                if (CallChecker.beforeDeref(classification, Classification.class, 164, 8092, 8105)) {
                                    if (CallChecker.beforeDeref(oldClassification, Classification.class, 165, 8151, 8167)) {
                                        if (CallChecker.beforeDeref(classification, Classification.class, 165, 8190, 8203)) {
                                            oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 163, 8030, 8046);
                                            oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 165, 8151, 8167);
                                            if (((CallChecker.isCalled(CallChecker.isCalled(oldClassification, Classification.class, 163, 8030, 8046), Classification.class, 163, 8030, 8060).getCategory()) == null) || (CallChecker.beforeDeref(CallChecker.isCalled(oldClassification, Classification.class, 165, 8151, 8167).getCategory(), String.class, 165, 8151, 8181))) {
                                                classification = CallChecker.beforeCalled(classification, Classification.class, 164, 8092, 8105);
                                                oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 165, 8151, 8167);
                                                classification = CallChecker.beforeCalled(classification, Classification.class, 165, 8190, 8203);
                                                categoryChanged = !(((CallChecker.isCalled(CallChecker.isCalled(oldClassification, Classification.class, 163, 8030, 8046), Classification.class, 163, 8030, 8060).getCategory()) == null) ? (CallChecker.isCalled(classification, Classification.class, 164, 8092, 8105).getCategory()) == null : CallChecker.isCalled(CallChecker.isCalled(oldClassification, Classification.class, 165, 8151, 8167).getCategory(), String.class, 165, 8151, 8181).equals(CallChecker.isCalled(classification, Classification.class, 165, 8190, 8203).getCategory()));
                                                CallChecker.varAssign(categoryChanged, "categoryChanged", 163, 8030, 8046);
                                            }
                                        }
                                    }
                                }
                            }
                            if (CallChecker.beforeDeref(taskSumamries, List.class, 166, 8243, 8255)) {
                                taskSumamries = CallChecker.beforeCalled(taskSumamries, List.class, 166, 8243, 8255);
                                if ((!(CallChecker.isCalled(taskSumamries, List.class, 166, 8243, 8255).isEmpty())) && categoryChanged) {
                                    List<String> taskIds = CallChecker.varInit(new ArrayList<>(), "taskIds", 167, 8309, 8349);
                                    if (CallChecker.beforeDeref(taskSumamries, List.class, 168, 8371, 8383)) {
                                        taskSumamries = CallChecker.beforeCalled(taskSumamries, List.class, 168, 8371, 8383);
                                        if (CallChecker.beforeDeref(CallChecker.isCalled(taskSumamries, List.class, 168, 8371, 8383).stream(), Stream.class, 168, 8371, 8392)) {
                                            taskSumamries = CallChecker.beforeCalled(taskSumamries, List.class, 168, 8371, 8383);
                                            CallChecker.isCalled(CallChecker.isCalled(taskSumamries, List.class, 168, 8371, 8383).stream(), Stream.class, 168, 8371, 8392).forEach(( ts) -> taskIds.add(ts.getTaskId()));
                                        }
                                    }
                                    if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 169, 8515, 8532)) {
                                        if (CallChecker.beforeDeref(taskMapper, TaskMapper.class, 169, 8458, 8467)) {
                                            classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 169, 8515, 8532);
                                            taskMapper = CallChecker.beforeCalled(taskMapper, TaskMapper.class, 169, 8458, 8467);
                                            CallChecker.isCalled(taskMapper, TaskMapper.class, 169, 8458, 8467).updateClassificationCategoryOnChange(taskIds, CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 169, 8515, 8532).getCategory());
                                        }
                                    }
                                }
                            }else
                                throw new AbnormalExecutionError();
                            
                        }
                    }else
                        throw new AbnormalExecutionError();
                    
                }else
                    throw new AbnormalExecutionError();
                
                if (CallChecker.beforeDeref(oldClassification, Classification.class, 174, 8663, 8679)) {
                    if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 174, 8702, 8719)) {
                        oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 174, 8663, 8679);
                        if (CallChecker.beforeDeref(CallChecker.isCalled(oldClassification, Classification.class, 174, 8663, 8679).getParentId(), String.class, 174, 8663, 8693)) {
                            oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 174, 8663, 8679);
                            classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 174, 8702, 8719);
                            if (!(CallChecker.isCalled(CallChecker.isCalled(oldClassification, Classification.class, 174, 8663, 8679).getParentId(), String.class, 174, 8663, 8693).equals(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 174, 8702, 8719).getParentId()))) {
                                if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 175, 8759, 8776)) {
                                    if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 175, 8804, 8821)) {
                                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 175, 8759, 8776);
                                        classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 175, 8804, 8821);
                                        if (((CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 175, 8759, 8776).getParentId()) != null) && (!(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 175, 8804, 8821).getParentId().isEmpty()))) {
                                            if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 176, 8893, 8910)) {
                                                classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 176, 8893, 8910);
                                                this.getClassification(CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 176, 8893, 8910).getParentId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (CallChecker.beforeDeref(classificationMapper, ClassificationMapper.class, 179, 8972, 8991)) {
                    classificationMapper = CallChecker.beforeCalled(classificationMapper, ClassificationMapper.class, 179, 8972, 8991);
                    CallChecker.isCalled(classificationMapper, ClassificationMapper.class, 179, 8972, 8991).update(classificationImpl);
                }
                boolean priorityChanged = CallChecker.init(boolean.class);
                if (CallChecker.beforeDeref(oldClassification, Classification.class, 180, 9059, 9075)) {
                    if (CallChecker.beforeDeref(classification, Classification.class, 180, 9094, 9107)) {
                        oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 180, 9059, 9075);
                        classification = CallChecker.beforeCalled(classification, Classification.class, 180, 9094, 9107);
                        priorityChanged = (CallChecker.isCalled(oldClassification, Classification.class, 180, 9059, 9075).getPriority()) != (CallChecker.isCalled(classification, Classification.class, 180, 9094, 9107).getPriority());
                        CallChecker.varAssign(priorityChanged, "priorityChanged", 180, 9059, 9075);
                    }
                }
                boolean serviceLevelChanged = CallChecker.init(boolean.class);
                if (CallChecker.beforeDeref(oldClassification, Classification.class, 181, 9167, 9183)) {
                    if (CallChecker.beforeDeref(classification, Classification.class, 181, 9210, 9223)) {
                        oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 181, 9167, 9183);
                        if (CallChecker.beforeDeref(CallChecker.isCalled(oldClassification, Classification.class, 181, 9167, 9183).getServiceLevel(), String.class, 181, 9167, 9201)) {
                            oldClassification = CallChecker.beforeCalled(oldClassification, Classification.class, 181, 9167, 9183);
                            classification = CallChecker.beforeCalled(classification, Classification.class, 181, 9210, 9223);
                            serviceLevelChanged = !(CallChecker.isCalled(CallChecker.isCalled(oldClassification, Classification.class, 181, 9167, 9183).getServiceLevel(), String.class, 181, 9167, 9201).equals(CallChecker.isCalled(classification, Classification.class, 181, 9210, 9223).getServiceLevel()));
                            CallChecker.varAssign(serviceLevelChanged, "serviceLevelChanged", 181, 9167, 9183);
                        }
                    }
                }
                if (priorityChanged || serviceLevelChanged) {
                    Map<String, String> args = CallChecker.varInit(new HashMap<>(), "args", 184, 9320, 9362);
                    if (CallChecker.beforeDeref(classificationImpl, ClassificationImpl.class, 185, 9449, 9466)) {
                        if (CallChecker.beforeDeref(args, Map.class, 185, 9380, 9383)) {
                            classificationImpl = CallChecker.beforeCalled(classificationImpl, ClassificationImpl.class, 185, 9449, 9466);
                            args = CallChecker.beforeCalled(args, Map.class, 185, 9380, 9383);
                            CallChecker.isCalled(args, Map.class, 185, 9380, 9383).put(TaskUpdateOnClassificationChangeExecutor.CLASSIFICATION_ID, CallChecker.isCalled(classificationImpl, ClassificationImpl.class, 185, 9449, 9466).getId());
                        }
                    }
                    if (CallChecker.beforeDeref(args, Map.class, 186, 9494, 9497)) {
                        args = CallChecker.beforeCalled(args, Map.class, 186, 9494, 9497);
                        CallChecker.isCalled(args, Map.class, 186, 9494, 9497).put(TaskUpdateOnClassificationChangeExecutor.PRIORITY_CHANGED, String.valueOf(priorityChanged));
                    }
                    if (CallChecker.beforeDeref(args, Map.class, 187, 9612, 9615)) {
                        args = CallChecker.beforeCalled(args, Map.class, 187, 9612, 9615);
                        CallChecker.isCalled(args, Map.class, 187, 9612, 9615).put(TaskUpdateOnClassificationChangeExecutor.SERVICE_LEVEL_CHANGED, String.valueOf(serviceLevelChanged));
                    }
                    Job job = CallChecker.varInit(new Job(), "job", 189, 9759, 9778);
                    if (CallChecker.beforeDeref(job, Job.class, 190, 9796, 9798)) {
                        job = CallChecker.beforeCalled(job, Job.class, 190, 9796, 9798);
                        CallChecker.isCalled(job, Job.class, 190, 9796, 9798).setCreated(Instant.now());
                    }
                    if (CallChecker.beforeDeref(job, Job.class, 191, 9843, 9845)) {
                        job = CallChecker.beforeCalled(job, Job.class, 191, 9843, 9845);
                        CallChecker.isCalled(job, Job.class, 191, 9843, 9845).setState(READY);
                    }
                    if (CallChecker.beforeDeref(job, Job.class, 192, 9890, 9892)) {
                        job = CallChecker.beforeCalled(job, Job.class, 192, 9890, 9892);
                        CallChecker.isCalled(job, Job.class, 192, 9890, 9892).setExecutor(TaskUpdateOnClassificationChangeExecutor.class.getName());
                    }
                    if (CallChecker.beforeDeref(job, Job.class, 193, 9981, 9983)) {
                        job = CallChecker.beforeCalled(job, Job.class, 193, 9981, 9983);
                        CallChecker.isCalled(job, Job.class, 193, 9981, 9983).setArguments(args);
                    }
                    if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 194, 10021, 10033)) {
                        taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 194, 10021, 10033);
                        if (CallChecker.beforeDeref(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 194, 10021, 10033).getSqlSession(), SqlSession.class, 194, 10021, 10049)) {
                            taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 194, 10021, 10033);
                            if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 194, 10021, 10033).getSqlSession(), SqlSession.class, 194, 10021, 10049).getMapper(JobMapper.class), JobMapper.class, 194, 10021, 10076)) {
                                taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 194, 10021, 10033);
                                CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 194, 10021, 10033).getSqlSession(), SqlSession.class, 194, 10021, 10049).getMapper(JobMapper.class), JobMapper.class, 194, 10021, 10076).insertJob(job);
                            }
                        }
                    }
                }
                if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 197, 10121, 10126)) {
                    CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 197, 10121, 10126).debug("Method updateClassification() updated the classification {}.", classificationImpl);
                }
                return classification;
            } finally {
                _bcornu_try_context_3.finallyStart(3);
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 201, 10302, 10314)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 201, 10302, 10314);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 201, 10302, 10314).returnConnection();
                }
                if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 202, 10348, 10353)) {
                    CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 202, 10348, 10353).debug("exit from updateClassification().");
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            return ((Classification) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context4.methodEnd();
        }
    }

    private void initDefaultClassificationValues(ClassificationImpl classification) throws InvalidArgumentException {
        MethodContext _bcornu_methode_context5 = new MethodContext(void.class, 211, 10420, 12625);
        try {
            CallChecker.varInit(this, "this", 211, 10420, 12625);
            CallChecker.varInit(classification, "classification", 211, 10420, 12625);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 211, 10420, 12625);
            CallChecker.varInit(this.taskMapper, "taskMapper", 211, 10420, 12625);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 211, 10420, 12625);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 211, 10420, 12625);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 211, 10420, 12625);
            Instant now = CallChecker.varInit(Instant.now(), "now", 212, 10683, 10710);
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 213, 10724, 10737)) {
                if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 213, 10768, 10781)) {
                    if (CallChecker.beforeDeref("", String.class, 213, 10758, 10759)) {
                        classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 213, 10724, 10737);
                        classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 213, 10768, 10781);
                        if (((CallChecker.isCalled(classification, ClassificationImpl.class, 213, 10724, 10737).getId()) == null) || (CallChecker.isCalled("", String.class, 213, 10758, 10759).equals(CallChecker.isCalled(classification, ClassificationImpl.class, 213, 10768, 10781).getId()))) {
                            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 214, 10807, 10820)) {
                                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 214, 10807, 10820);
                                CallChecker.isCalled(classification, ClassificationImpl.class, 214, 10807, 10820).setId(IdGenerator.generateWithPrefix(ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION));
                            }
                        }
                    }
                }
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 217, 10910, 10923)) {
                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 217, 10910, 10923);
                if ((CallChecker.isCalled(classification, ClassificationImpl.class, 217, 10910, 10923).getCreated()) == null) {
                    if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 218, 10961, 10974)) {
                        classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 218, 10961, 10974);
                        CallChecker.isCalled(classification, ClassificationImpl.class, 218, 10961, 10974).setCreated(now);
                    }
                }
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 221, 11016, 11029)) {
                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 221, 11016, 11029);
                if ((CallChecker.isCalled(classification, ClassificationImpl.class, 221, 11016, 11029).getModified()) == null) {
                    if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 222, 11068, 11081)) {
                        classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 222, 11068, 11081);
                        CallChecker.isCalled(classification, ClassificationImpl.class, 222, 11068, 11081).setModified(now);
                    }
                }
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 225, 11124, 11137)) {
                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 225, 11124, 11137);
                if ((CallChecker.isCalled(classification, ClassificationImpl.class, 225, 11124, 11137).getIsValidInDomain()) == null) {
                    if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 226, 11183, 11196)) {
                        classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 226, 11183, 11196);
                        CallChecker.isCalled(classification, ClassificationImpl.class, 226, 11183, 11196).setIsValidInDomain(true);
                    }
                }
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 229, 11247, 11260)) {
                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 229, 11247, 11260);
                if ((CallChecker.isCalled(classification, ClassificationImpl.class, 229, 11247, 11260).getServiceLevel()) != null) {
                    TryContext _bcornu_try_context_4 = new TryContext(4, ClassificationServiceImpl.class, "java.lang.Exception");
                    try {
                        if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 231, 11340, 11353)) {
                            classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 231, 11340, 11353);
                            Duration.parse(CallChecker.isCalled(classification, ClassificationImpl.class, 231, 11340, 11353).getServiceLevel());
                        }
                    } catch (Exception e) {
                        _bcornu_try_context_4.catchStart(4);
                        throw new InvalidArgumentException("Invalid service level. Please use the format defined by ISO 8601");
                    } finally {
                        _bcornu_try_context_4.finallyStart(4);
                    }
                }
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 237, 11568, 11581)) {
                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 237, 11568, 11581);
                if ((CallChecker.isCalled(classification, ClassificationImpl.class, 237, 11568, 11581).getKey()) == null) {
                    throw new InvalidArgumentException("Classification must contain a key");
                }
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 241, 11711, 11724)) {
                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 241, 11711, 11724);
                if ((CallChecker.isCalled(classification, ClassificationImpl.class, 241, 11711, 11724).getParentId()) == null) {
                    if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 242, 11763, 11776)) {
                        classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 242, 11763, 11776);
                        CallChecker.isCalled(classification, ClassificationImpl.class, 242, 11763, 11776).setParentId("");
                    }
                }
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 245, 11818, 11831)) {
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 246, 11867, 11879)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 246, 11867, 11879);
                    if (CallChecker.beforeDeref(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 246, 11867, 11879).getConfiguration(), TaskanaEngineConfiguration.class, 246, 11867, 11898)) {
                        if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 246, 11934, 11947)) {
                            taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 246, 11867, 11879);
                            if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 246, 11867, 11879).getConfiguration(), TaskanaEngineConfiguration.class, 246, 11867, 11898).getClassificationTypes(), List.class, 246, 11867, 11923)) {
                                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 245, 11818, 11831);
                                taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 246, 11867, 11879);
                                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 246, 11934, 11947);
                                if (((CallChecker.isCalled(classification, ClassificationImpl.class, 245, 11818, 11831).getType()) != null) && (!(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 246, 11867, 11879).getConfiguration(), TaskanaEngineConfiguration.class, 246, 11867, 11898).getClassificationTypes(), List.class, 246, 11867, 11923).contains(CallChecker.isCalled(classification, ClassificationImpl.class, 246, 11934, 11947).getType())))) {
                                    if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 247, 12041, 12054)) {
                                        classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 247, 12041, 12054);
                                        throw new InvalidArgumentException((("Given classification type " + (CallChecker.isCalled(classification, ClassificationImpl.class, 247, 12041, 12054).getType())) + " is not valid according to the configuration."));
                                    }else
                                        throw new AbnormalExecutionError();
                                    
                                }
                            }
                        }
                    }
                }
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 251, 12157, 12170)) {
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 252, 12210, 12222)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 252, 12210, 12222);
                    if (CallChecker.beforeDeref(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 252, 12210, 12222).getConfiguration(), TaskanaEngineConfiguration.class, 252, 12210, 12241)) {
                        if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 252, 12282, 12295)) {
                            taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 252, 12210, 12222);
                            if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 252, 12210, 12222).getConfiguration(), TaskanaEngineConfiguration.class, 252, 12210, 12241).getClassificationCategories(), List.class, 252, 12210, 12271)) {
                                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 251, 12157, 12170);
                                taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 252, 12210, 12222);
                                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 252, 12282, 12295);
                                if (((CallChecker.isCalled(classification, ClassificationImpl.class, 251, 12157, 12170).getCategory()) != null) && (!(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 252, 12210, 12222).getConfiguration(), TaskanaEngineConfiguration.class, 252, 12210, 12241).getClassificationCategories(), List.class, 252, 12210, 12271).contains(CallChecker.isCalled(classification, ClassificationImpl.class, 252, 12282, 12295).getCategory())))) {
                                    if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 253, 12397, 12410)) {
                                        classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 253, 12397, 12410);
                                        throw new InvalidArgumentException((("Given classification category " + (CallChecker.isCalled(classification, ClassificationImpl.class, 253, 12397, 12410).getCategory())) + " is not valid according to the configuration."));
                                    }else
                                        throw new AbnormalExecutionError();
                                    
                                }
                            }
                        }
                    }
                }
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 257, 12517, 12530)) {
                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 257, 12517, 12530);
                if (CallChecker.beforeDeref(CallChecker.isCalled(classification, ClassificationImpl.class, 257, 12517, 12530).getDomain(), String.class, 257, 12517, 12542)) {
                    classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 257, 12517, 12530);
                    if (CallChecker.isCalled(CallChecker.isCalled(classification, ClassificationImpl.class, 257, 12517, 12530).getDomain(), String.class, 257, 12517, 12542).isEmpty()) {
                        if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 258, 12569, 12582)) {
                            classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 258, 12569, 12582);
                            CallChecker.isCalled(classification, ClassificationImpl.class, 258, 12569, 12582).setIsValidInDomain(false);
                        }
                    }
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context5.methodEnd();
        }
    }

    @Override
    public Classification getClassification(String id) throws ClassificationNotFoundException {
        MethodContext _bcornu_methode_context6 = new MethodContext(Classification.class, 263, 12632, 13592);
        try {
            CallChecker.varInit(this, "this", 263, 12632, 13592);
            CallChecker.varInit(id, "id", 263, 12632, 13592);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 263, 12632, 13592);
            CallChecker.varInit(this.taskMapper, "taskMapper", 263, 12632, 13592);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 263, 12632, 13592);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 263, 12632, 13592);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 263, 12632, 13592);
            if (id == null) {
                throw new ClassificationNotFoundException(id, (("Classification for id " + id) + " was not found."));
            }
            if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 268, 12908, 12913)) {
                CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 268, 12908, 12913).debug("entry to getClassification(id = {})", id);
            }
            Classification result = CallChecker.varInit(null, "result", 269, 12973, 13001);
            TryContext _bcornu_try_context_5 = new TryContext(5, ClassificationServiceImpl.class);
            try {
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 271, 13029, 13041)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 271, 13029, 13041);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 271, 13029, 13041).openConnection();
                }
                if (CallChecker.beforeDeref(classificationMapper, ClassificationMapper.class, 272, 13082, 13101)) {
                    classificationMapper = CallChecker.beforeCalled(classificationMapper, ClassificationMapper.class, 272, 13082, 13101);
                    result = CallChecker.isCalled(classificationMapper, ClassificationMapper.class, 272, 13082, 13101).findById(id);
                    CallChecker.varAssign(result, "result", 272, 13073, 13115);
                }
                if (result == null) {
                    if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 274, 13167, 13172)) {
                        CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 274, 13167, 13172).error("Classification for id {} was not found. Throwing ClassificationNotFoundException", id);
                    }
                    throw new ClassificationNotFoundException(id, (("Classification for id " + id) + " was not found"));
                }
                return result;
            } finally {
                _bcornu_try_context_5.finallyStart(5);
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 279, 13455, 13467)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 279, 13455, 13467);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 279, 13455, 13467).returnConnection();
                }
                if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 280, 13501, 13506)) {
                    CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 280, 13501, 13506).debug("exit from getClassification(). Returning result {} ", result);
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            return ((Classification) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context6.methodEnd();
        }
    }

    @Override
    public Classification getClassification(String key, String domain) throws ClassificationNotFoundException {
        MethodContext _bcornu_methode_context7 = new MethodContext(Classification.class, 285, 13599, 14983);
        try {
            CallChecker.varInit(this, "this", 285, 13599, 14983);
            CallChecker.varInit(domain, "domain", 285, 13599, 14983);
            CallChecker.varInit(key, "key", 285, 13599, 14983);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 285, 13599, 14983);
            CallChecker.varInit(this.taskMapper, "taskMapper", 285, 13599, 14983);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 285, 13599, 14983);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 285, 13599, 14983);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 285, 13599, 14983);
            if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 286, 13729, 13734)) {
                CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 286, 13729, 13734).debug("entry to getClassification(key = {}, domain = {})", key, domain);
            }
            if (key == null) {
                throw new ClassificationNotFoundException(key, domain, (("Classification for null key and domain " + domain) + " was not found."));
            }
            if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 291, 14010, 14015)) {
                CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 291, 14010, 14015).debug("entry to getClassification(key = {}, domain = {})", key, domain);
            }
            Classification result = CallChecker.varInit(null, "result", 292, 14098, 14126);
            TryContext _bcornu_try_context_6 = new TryContext(6, ClassificationServiceImpl.class);
            try {
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 294, 14154, 14166)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 294, 14154, 14166);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 294, 14154, 14166).openConnection();
                }
                if (CallChecker.beforeDeref(classificationMapper, ClassificationMapper.class, 295, 14207, 14226)) {
                    classificationMapper = CallChecker.beforeCalled(classificationMapper, ClassificationMapper.class, 295, 14207, 14226);
                    result = CallChecker.isCalled(classificationMapper, ClassificationMapper.class, 295, 14207, 14226).findByKeyAndDomain(key, domain);
                    CallChecker.varAssign(result, "result", 295, 14198, 14259);
                }
                if (result == null) {
                    if (CallChecker.beforeDeref(classificationMapper, ClassificationMapper.class, 297, 14320, 14339)) {
                        classificationMapper = CallChecker.beforeCalled(classificationMapper, ClassificationMapper.class, 297, 14320, 14339);
                        result = CallChecker.isCalled(classificationMapper, ClassificationMapper.class, 297, 14320, 14339).findByKeyAndDomain(key, "");
                        CallChecker.varAssign(result, "result", 297, 14311, 14368);
                    }
                    if (result == null) {
                        if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 299, 14428, 14433)) {
                            CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 299, 14428, 14433).error("Classification for key {} and domain {} was not found. Throwing ClassificationNotFoundException", key, domain);
                        }
                        throw new ClassificationNotFoundException(key, domain, (("Classification for key " + key) + " was not found"));
                    }
                }
                return result;
            } finally {
                _bcornu_try_context_6.finallyStart(6);
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 308, 14846, 14858)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 308, 14846, 14858);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 308, 14846, 14858).returnConnection();
                }
                if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 309, 14892, 14897)) {
                    CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 309, 14892, 14897).debug("exit from getClassification(). Returning result {} ", result);
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            return ((Classification) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context7.methodEnd();
        }
    }

    @Override
    public ClassificationQuery createClassificationQuery() {
        MethodContext _bcornu_methode_context8 = new MethodContext(ClassificationQuery.class, 314, 14990, 15124);
        try {
            CallChecker.varInit(this, "this", 314, 14990, 15124);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 314, 14990, 15124);
            CallChecker.varInit(this.taskMapper, "taskMapper", 314, 14990, 15124);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 314, 14990, 15124);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 314, 14990, 15124);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 314, 14990, 15124);
            return new ClassificationQueryImpl(taskanaEngine);
        } catch (ForceReturn _bcornu_return_t) {
            return ((ClassificationQuery) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context8.methodEnd();
        }
    }

    @Override
    public Classification newClassification(String key, String domain, String type) {
        MethodContext _bcornu_methode_context9 = new MethodContext(Classification.class, 319, 15131, 15448);
        try {
            CallChecker.varInit(this, "this", 319, 15131, 15448);
            CallChecker.varInit(type, "type", 319, 15131, 15448);
            CallChecker.varInit(domain, "domain", 319, 15131, 15448);
            CallChecker.varInit(key, "key", 319, 15131, 15448);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 319, 15131, 15448);
            CallChecker.varInit(this.taskMapper, "taskMapper", 319, 15131, 15448);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 319, 15131, 15448);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 319, 15131, 15448);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 319, 15131, 15448);
            ClassificationImpl classification = CallChecker.varInit(new ClassificationImpl(), "classification", 320, 15235, 15295);
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 321, 15305, 15318)) {
                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 321, 15305, 15318);
                CallChecker.isCalled(classification, ClassificationImpl.class, 321, 15305, 15318).setKey(key);
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 322, 15341, 15354)) {
                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 322, 15341, 15354);
                CallChecker.isCalled(classification, ClassificationImpl.class, 322, 15341, 15354).setDomain(domain);
            }
            if (CallChecker.beforeDeref(classification, ClassificationImpl.class, 323, 15383, 15396)) {
                classification = CallChecker.beforeCalled(classification, ClassificationImpl.class, 323, 15383, 15396);
                CallChecker.isCalled(classification, ClassificationImpl.class, 323, 15383, 15396).setType(type);
            }
            return classification;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Classification) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context9.methodEnd();
        }
    }

    private boolean doesClassificationExist(String key, String domain) {
        MethodContext _bcornu_methode_context10 = new MethodContext(boolean.class, 327, 15455, 15944);
        try {
            CallChecker.varInit(this, "this", 327, 15455, 15944);
            CallChecker.varInit(domain, "domain", 327, 15455, 15944);
            CallChecker.varInit(key, "key", 327, 15455, 15944);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 327, 15455, 15944);
            CallChecker.varInit(this.taskMapper, "taskMapper", 327, 15455, 15944);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 327, 15455, 15944);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 327, 15455, 15944);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 327, 15455, 15944);
            boolean isExisting = CallChecker.varInit(((boolean) (false)), "isExisting", 328, 15532, 15558);
            TryContext _bcornu_try_context_7 = new TryContext(7, ClassificationServiceImpl.class, "java.lang.Exception");
            try {
                if (CallChecker.beforeDeref(classificationMapper, ClassificationMapper.class, 330, 15590, 15609)) {
                    classificationMapper = CallChecker.beforeCalled(classificationMapper, ClassificationMapper.class, 330, 15590, 15609);
                    if ((CallChecker.isCalled(classificationMapper, ClassificationMapper.class, 330, 15590, 15609).findByKeyAndDomain(key, domain)) != null) {
                        isExisting = true;
                        CallChecker.varAssign(isExisting, "isExisting", 331, 15670, 15687);
                    }
                }
            } catch (Exception ex) {
                _bcornu_try_context_7.catchStart(7);
                if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 334, 15748, 15753)) {
                    CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 334, 15748, 15753).warn("Classification-Service throwed Exception while calling mapper and searching for classification. EX={}", ex);
                }
            } finally {
                _bcornu_try_context_7.finallyStart(7);
            }
            return isExisting;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context10.methodEnd();
        }
    }

    @Override
    public void deleteClassification(String classificationKey, String domain) throws ClassificationInUseException, ClassificationNotFoundException, NotAuthorizedException {
        MethodContext _bcornu_methode_context11 = new MethodContext(void.class, 342, 15951, 16968);
        try {
            CallChecker.varInit(this, "this", 342, 15951, 16968);
            CallChecker.varInit(domain, "domain", 342, 15951, 16968);
            CallChecker.varInit(classificationKey, "classificationKey", 342, 15951, 16968);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 342, 15951, 16968);
            CallChecker.varInit(this.taskMapper, "taskMapper", 342, 15951, 16968);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 342, 15951, 16968);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 342, 15951, 16968);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 342, 15951, 16968);
            if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 344, 16150, 16155)) {
                CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 344, 16150, 16155).debug("entry to deleteClassification(key = {}, domain = {})", classificationKey, domain);
            }
            if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 345, 16255, 16267)) {
                taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 345, 16255, 16267);
                CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 345, 16255, 16267).checkRoleMembership(TaskanaRole.BUSINESS_ADMIN, TaskanaRole.ADMIN);
            }
            TryContext _bcornu_try_context_8 = new TryContext(8, ClassificationServiceImpl.class);
            try {
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 347, 16363, 16375)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 347, 16363, 16375);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 347, 16363, 16375).openConnection();
                }
                Classification classification = CallChecker.init(Classification.class);
                if (CallChecker.beforeDeref(this.classificationMapper, ClassificationMapper.class, 348, 16439, 16463)) {
                    this.classificationMapper = CallChecker.beforeCalled(this.classificationMapper, ClassificationMapper.class, 348, 16439, 16463);
                    classification = CallChecker.isCalled(this.classificationMapper, ClassificationMapper.class, 348, 16439, 16463).findByKeyAndDomain(classificationKey, domain);
                    CallChecker.varAssign(classification, "classification", 348, 16439, 16463);
                }
                if (classification == null) {
                    throw new ClassificationNotFoundException(classificationKey, domain, ((("The classification " + classificationKey) + "wasn't found in the domain ") + domain));
                }
                deleteClassification(classification.getId());
            } finally {
                _bcornu_try_context_8.finallyStart(8);
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 355, 16848, 16860)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 355, 16848, 16860);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 355, 16848, 16860).returnConnection();
                }
                if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 356, 16894, 16899)) {
                    CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 356, 16894, 16899).debug("exit from deleteClassification(key,domain)");
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context11.methodEnd();
        }
    }

    @Override
    public void deleteClassification(String classificationId) throws ClassificationInUseException, ClassificationNotFoundException, NotAuthorizedException {
        MethodContext _bcornu_methode_context12 = new MethodContext(void.class, 361, 16975, 19233);
        try {
            CallChecker.varInit(this, "this", 361, 16975, 19233);
            CallChecker.varInit(classificationId, "classificationId", 361, 16975, 19233);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 361, 16975, 19233);
            CallChecker.varInit(this.taskMapper, "taskMapper", 361, 16975, 19233);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 361, 16975, 19233);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 361, 16975, 19233);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 361, 16975, 19233);
            if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 363, 17158, 17163)) {
                CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 363, 17158, 17163).debug("entry to deleteClassification(id = {})", classificationId);
            }
            if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 364, 17240, 17252)) {
                taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 364, 17240, 17252);
                CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 364, 17240, 17252).checkRoleMembership(TaskanaRole.BUSINESS_ADMIN, TaskanaRole.ADMIN);
            }
            TryContext _bcornu_try_context_10 = new TryContext(10, ClassificationServiceImpl.class);
            try {
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 366, 17348, 17360)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 366, 17348, 17360);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 366, 17348, 17360).openConnection();
                }
                Classification classification = CallChecker.init(Classification.class);
                if (CallChecker.beforeDeref(this.classificationMapper, ClassificationMapper.class, 367, 17424, 17448)) {
                    this.classificationMapper = CallChecker.beforeCalled(this.classificationMapper, ClassificationMapper.class, 367, 17424, 17448);
                    classification = CallChecker.isCalled(this.classificationMapper, ClassificationMapper.class, 367, 17424, 17448).findById(classificationId);
                    CallChecker.varAssign(classification, "classification", 367, 17424, 17448);
                }
                if (classification == null) {
                    throw new ClassificationNotFoundException(classificationId, (("The classification " + classificationId) + "wasn't found"));
                }
                classification = CallChecker.beforeCalled(classification, Classification.class, 373, 17707, 17720);
                if (CallChecker.beforeDeref(CallChecker.isCalled(classification, Classification.class, 373, 17707, 17720).getDomain(), String.class, 373, 17707, 17732)) {
                    if (CallChecker.isCalled(classification.getDomain(), String.class, 373, 17707, 17732).equals("")) {
                        final ClassificationQuery npe_invocation_var0 = createClassificationQuery();
                        List<ClassificationSummary> classificationsInDomain = CallChecker.init(List.class);
                        if (CallChecker.beforeDeref(npe_invocation_var0, ClassificationQuery.class, 375, 17906, 17932)) {
                            classification = CallChecker.beforeCalled(classification, Classification.class, 376, 17961, 17974);
                            if (CallChecker.beforeDeref(CallChecker.isCalled(npe_invocation_var0, ClassificationQuery.class, 375, 17906, 17932).keyIn(CallChecker.isCalled(classification, Classification.class, 376, 17961, 17974).getKey()), ClassificationQuery.class, 375, 17906, 17984)) {
                                classificationsInDomain = CallChecker.isCalled(CallChecker.isCalled(npe_invocation_var0, ClassificationQuery.class, 375, 17906, 17932).keyIn(classification.getKey()), ClassificationQuery.class, 375, 17906, 17984).list();
                                CallChecker.varAssign(classificationsInDomain, "classificationsInDomain", 375, 17906, 17932);
                            }
                        }
                        if (CallChecker.beforeDeref(classificationsInDomain, void.class, 378, 18083, 18105)) {
                            for (ClassificationSummary classificationInDomain : classificationsInDomain) {
                                if (CallChecker.beforeDeref(classificationInDomain, ClassificationSummary.class, 379, 18145, 18166)) {
                                    if (CallChecker.beforeDeref("", String.class, 379, 18135, 18136)) {
                                        if (!(CallChecker.isCalled("", String.class, 379, 18135, 18136).equals(CallChecker.isCalled(classificationInDomain, ClassificationSummary.class, 379, 18145, 18166).getDomain()))) {
                                            if (CallChecker.beforeDeref(classificationInDomain, ClassificationSummary.class, 380, 18229, 18250)) {
                                                deleteClassification(CallChecker.isCalled(classificationInDomain, ClassificationSummary.class, 380, 18229, 18250).getId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                final ClassificationQuery npe_invocation_var1 = createClassificationQuery();
                List<ClassificationSummary> childClassifications = CallChecker.init(List.class);
                if (CallChecker.beforeDeref(npe_invocation_var1, ClassificationQuery.class, 385, 18380, 18406)) {
                    if (CallChecker.beforeDeref(CallChecker.isCalled(npe_invocation_var1, ClassificationQuery.class, 385, 18380, 18406).parentIdIn(classificationId), ClassificationQuery.class, 385, 18380, 18435)) {
                        childClassifications = CallChecker.isCalled(CallChecker.isCalled(npe_invocation_var1, ClassificationQuery.class, 385, 18380, 18406).parentIdIn(classificationId), ClassificationQuery.class, 385, 18380, 18435).list();
                        CallChecker.varAssign(childClassifications, "childClassifications", 385, 18380, 18406);
                    }
                }
                if (CallChecker.beforeDeref(childClassifications, void.class, 387, 18509, 18528)) {
                    for (ClassificationSummary child : childClassifications) {
                        if (CallChecker.beforeDeref(child, ClassificationSummary.class, 388, 18575, 18579)) {
                            this.deleteClassification(CallChecker.isCalled(child, ClassificationSummary.class, 388, 18575, 18579).getId());
                        }
                    }
                }
                TryContext _bcornu_try_context_9 = new TryContext(9, ClassificationServiceImpl.class, "org.apache.ibatis.exceptions.PersistenceException");
                try {
                    if (CallChecker.beforeDeref(this.classificationMapper, ClassificationMapper.class, 392, 18640, 18664)) {
                        this.classificationMapper = CallChecker.beforeCalled(this.classificationMapper, ClassificationMapper.class, 392, 18640, 18664);
                        CallChecker.isCalled(this.classificationMapper, ClassificationMapper.class, 392, 18640, 18664).deleteClassification(classificationId);
                    }
                } catch (PersistenceException e) {
                    _bcornu_try_context_9.catchStart(9);
                    if (isReferentialIntegrityConstraintViolation(e)) {
                        throw new ClassificationInUseException((("The classification " + classificationId) + " is in use and cannot be deleted. There are either tasks or attachments associated with the classification."));
                    }
                } finally {
                    _bcornu_try_context_9.finallyStart(9);
                }
            } finally {
                _bcornu_try_context_10.finallyStart(10);
                if (CallChecker.beforeDeref(taskanaEngine, TaskanaEngineImpl.class, 400, 19123, 19135)) {
                    taskanaEngine = CallChecker.beforeCalled(taskanaEngine, TaskanaEngineImpl.class, 400, 19123, 19135);
                    CallChecker.isCalled(taskanaEngine, TaskanaEngineImpl.class, 400, 19123, 19135).returnConnection();
                }
                if (CallChecker.beforeDeref(ClassificationServiceImpl.LOGGER, Logger.class, 401, 19169, 19174)) {
                    CallChecker.isCalled(ClassificationServiceImpl.LOGGER, Logger.class, 401, 19169, 19174).debug("exit from deleteClassification()");
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context12.methodEnd();
        }
    }

    private boolean isReferentialIntegrityConstraintViolation(PersistenceException e) {
        MethodContext _bcornu_methode_context13 = new MethodContext(boolean.class, 405, 19240, 19430);
        try {
            CallChecker.varInit(this, "this", 405, 19240, 19430);
            CallChecker.varInit(e, "e", 405, 19240, 19430);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 405, 19240, 19430);
            CallChecker.varInit(this.taskMapper, "taskMapper", 405, 19240, 19430);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 405, 19240, 19430);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 405, 19240, 19430);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 405, 19240, 19430);
            return (isH2OrPostgresIntegrityConstraintViolation(e)) || (isDb2IntegrityConstraintViolation(e));
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context13.methodEnd();
        }
    }

    private boolean isDb2IntegrityConstraintViolation(PersistenceException e) {
        MethodContext _bcornu_methode_context14 = new MethodContext(boolean.class, 409, 19437, 19633);
        try {
            CallChecker.varInit(this, "this", 409, 19437, 19633);
            CallChecker.varInit(e, "e", 409, 19437, 19633);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 409, 19437, 19633);
            CallChecker.varInit(this.taskMapper, "taskMapper", 409, 19437, 19633);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 409, 19437, 19633);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 409, 19437, 19633);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 409, 19437, 19633);
            if (CallChecker.beforeDeref(e, PersistenceException.class, 410, 19596, 19596)) {
                e = CallChecker.beforeCalled(e, PersistenceException.class, 410, 19528, 19528);
                e = CallChecker.beforeCalled(e, PersistenceException.class, 410, 19596, 19596);
                return ((CallChecker.isCalled(e, PersistenceException.class, 410, 19528, 19528).getCause()) instanceof SQLIntegrityConstraintViolationException) && (CallChecker.isCalled(CallChecker.isCalled(e, PersistenceException.class, 410, 19596, 19596).getMessage(), String.class, 410, 19596, 19609).contains("-532"));
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context14.methodEnd();
        }
    }

    private boolean isH2OrPostgresIntegrityConstraintViolation(PersistenceException e) {
        MethodContext _bcornu_methode_context15 = new MethodContext(boolean.class, 413, 19640, 19845);
        try {
            CallChecker.varInit(this, "this", 413, 19640, 19845);
            CallChecker.varInit(e, "e", 413, 19640, 19845);
            CallChecker.varInit(this.taskanaEngine, "taskanaEngine", 413, 19640, 19845);
            CallChecker.varInit(this.taskMapper, "taskMapper", 413, 19640, 19845);
            CallChecker.varInit(this.classificationMapper, "classificationMapper", 413, 19640, 19845);
            CallChecker.varInit(LOGGER, "pro.taskana.impl.ClassificationServiceImpl.LOGGER", 413, 19640, 19845);
            CallChecker.varInit(ID_PREFIX_CLASSIFICATION, "pro.taskana.impl.ClassificationServiceImpl.ID_PREFIX_CLASSIFICATION", 413, 19640, 19845);
            if (CallChecker.beforeDeref(e, PersistenceException.class, 414, 19796, 19796)) {
                e = CallChecker.beforeCalled(e, PersistenceException.class, 414, 19796, 19796);
                if (CallChecker.beforeDeref(CallChecker.isCalled(e, PersistenceException.class, 414, 19796, 19796).getCause(), Throwable.class, 414, 19796, 19807)) {
                    e = CallChecker.beforeCalled(e, PersistenceException.class, 414, 19740, 19740);
                    e = CallChecker.beforeCalled(e, PersistenceException.class, 414, 19796, 19796);
                    return ((CallChecker.isCalled(e, PersistenceException.class, 414, 19740, 19740).getCause()) instanceof SQLException) && (CallChecker.isCalled(CallChecker.isCalled(((SQLException) (CallChecker.isCalled(e, PersistenceException.class, 414, 19796, 19796).getCause())), SQLException.class, 414, 19796, 19807).getSQLState(), String.class, 414, 19780, 19822).equals("23503"));
                }else
                    throw new AbnormalExecutionError();
                
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context15.methodEnd();
        }
    }
}

