package spoon.test.template.testclasses;


public class LoggerModel {
    private java.lang.String _classname_;

    private java.lang.String _methodName_;

    private spoon.reflect.code.CtBlock<?> _block_;

    public void block() throws java.lang.Throwable {
        try {
            spoon.test.template.testclasses.logger.Logger.enter(_classname_, _methodName_);
            _block_.S();
        } finally {
            spoon.test.template.testclasses.logger.Logger.exit(_methodName_);
        }
    }
}

