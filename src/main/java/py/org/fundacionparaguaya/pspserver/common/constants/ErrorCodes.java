package py.org.fundacionparaguaya.pspserver.common.constants;

public enum ErrorCodes implements ErrorResource {

    VALIDATION_FAILURE("error.validationFailure") {
        public String getMessage() {
            return "Validation Failure";
        }

        @Override
        public String getUrl() {
            return null;
        }
    },
    INTERNAL_SERVER_ERROR("error.internalServerError") {
        public String getMessage() {
            return "Internal Server Error";
        }

        @Override
        public String getUrl() {
            return null;
        }
    };

    private final String code;

    ErrorCodes(String code) {
        this.code = code;
    }


}