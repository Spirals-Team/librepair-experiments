package spoon.test.template.testclasses.logger;


public class LoggerTemplate extends spoon.template.BlockTemplate {
    @spoon.template.Parameter
    private java.lang.String _classname_;

    @spoon.template.Parameter
    private java.lang.String _methodName_;

    @spoon.template.Parameter
    private spoon.reflect.code.CtBlock<?> _block_;

    @spoon.template.Local
    public LoggerTemplate(java.lang.String _classname_, java.lang.String _methodName_, spoon.reflect.code.CtBlock<?> _block_) {
        this._classname_ = _classname_;
        this._methodName_ = _methodName_;
        this._block_ = _block_;
    }

    @java.lang.Override
    public void block() throws java.lang.Throwable {
        try {
            spoon.test.template.testclasses.logger.Logger.enter("_classname_", "_methodName_");
            _block_.S();
        } finally {
            spoon.test.template.testclasses.logger.Logger.exit("_methodName_");
        }
    }
}

