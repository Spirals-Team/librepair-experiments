package fk.prof.backend.exception;

public class AggregationFailure extends RuntimeException implements ProfException {
  private boolean serverFailure = false;

  public AggregationFailure() {
    super();
  }

  public AggregationFailure(String message) {
    super(message);
  }

  public AggregationFailure(Throwable throwable) {
    super(throwable);
  }

  public AggregationFailure(boolean serverFailure) {
    this.serverFailure = serverFailure;
    initCause(new RuntimeException());
  }

  public AggregationFailure(String message, Throwable throwable) {
    super(message, throwable);
  }

  public AggregationFailure(String message, boolean serverFailure) {
    super(message);
    this.serverFailure = serverFailure;
  }

  public AggregationFailure(Throwable throwable, boolean serverFailure) {
    super(throwable);
    this.serverFailure = serverFailure;
  }

  public AggregationFailure(String message, Throwable throwable, boolean serverFailure) {
    super(message, throwable);
    this.serverFailure = serverFailure;
  }

  @Override
  public boolean isServerFailure() {
    return serverFailure;
  }
}
