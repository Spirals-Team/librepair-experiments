package innovimax.mixthem.operation;

import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.ConstructorContext;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.exception.AbnormalExecutionError;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import innovimax.mixthem.MixException;
import innovimax.mixthem.arguments.ParamValue;
import innovimax.mixthem.arguments.RuleParam;
import innovimax.mixthem.io.InputResource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class AbstractCopyOperation extends AbstractOperation implements ICopyOperation {
    protected static final int BUFFER_SIZE = 1024;

    private final CopyMode copyMode;

    public AbstractCopyOperation(final CopyMode copyMode, final Map<RuleParam, ParamValue> params) {
        super(params);
        ConstructorContext _bcornu_methode_context1 = new ConstructorContext(AbstractCopyOperation.class, 31, 619, 978);
        try {
            this.copyMode = copyMode;
            CallChecker.varAssign(this.copyMode, "this.copyMode", 33, 951, 975);
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    @Override
    public void processFiles(final List<InputResource> inputs, final OutputStream output) throws MixException, IOException {
        MethodContext _bcornu_methode_context1 = new MethodContext(void.class, 37, 982, 1590);
        try {
            CallChecker.varInit(this, "this", 37, 982, 1590);
            CallChecker.varInit(output, "output", 37, 982, 1590);
            CallChecker.varInit(inputs, "inputs", 37, 982, 1590);
            CallChecker.varInit(this.copyMode, "copyMode", 37, 982, 1590);
            CallChecker.varInit(BUFFER_SIZE, "innovimax.mixthem.operation.AbstractCopyOperation.BUFFER_SIZE", 37, 982, 1590);
            switch (copyMode) {
                case FIRST :
                    if (CallChecker.beforeDeref(inputs, List.class, 40, 1164, 1169)) {
                        process(CallChecker.isCalled(inputs, List.class, 40, 1164, 1169).get(0), output);
                    }else
                        throw new AbnormalExecutionError();
                    
                    break;
                case SECOND :
                    if (CallChecker.beforeDeref(inputs, List.class, 43, 1227, 1232)) {
                        process(CallChecker.isCalled(inputs, List.class, 43, 1227, 1232).get(1), output);
                    }else
                        throw new AbnormalExecutionError();
                    
                    break;
                case UMPTEENTH :
                    int index = CallChecker.init(int.class);
                    if (CallChecker.beforeDeref(params, Map.class, 46, 1297, 1302)) {
                        if (CallChecker.beforeDeref(CallChecker.isCalled(params, Map.class, 46, 1297, 1302).get(RuleParam.FILE_INDEX), ParamValue.class, 46, 1297, 1328)) {
                            index = CallChecker.isCalled(CallChecker.isCalled(params, Map.class, 46, 1297, 1302).get(RuleParam.FILE_INDEX), ParamValue.class, 46, 1297, 1328).asInt();
                            CallChecker.varAssign(index, "index", 46, 1297, 1302);
                        }
                    }else
                        throw new AbnormalExecutionError();
                    
                    if (CallChecker.beforeDeref(inputs, List.class, 47, 1351, 1356)) {
                        process(CallChecker.isCalled(inputs, List.class, 47, 1351, 1356).get(index), output);
                    }else
                        throw new AbnormalExecutionError();
                    
                    break;
                case ALL :
                default :
                    if (CallChecker.beforeDeref(inputs, List.class, 51, 1425, 1430)) {
                        if (CallChecker.beforeDeref(CallChecker.isCalled(inputs, List.class, 51, 1425, 1430).stream(), Stream.class, 51, 1425, 1439)) {
                            CallChecker.isCalled(CallChecker.isCalled(inputs, List.class, 51, 1425, 1430).stream(), Stream.class, 51, 1425, 1439).forEach(( input) -> {
                                try {
                                    process(input, output);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                    }else
                        throw new AbnormalExecutionError();
                    
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }
}

