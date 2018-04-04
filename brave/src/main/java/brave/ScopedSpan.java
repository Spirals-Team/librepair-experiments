package brave;

import brave.propagation.CurrentTraceContext;
import brave.propagation.TraceContext;

/**
 * Used to model the latency of an operation.
 *
 * Here's a typical example of synchronous tracing from perspective of the scoped span:
 * <pre>{@code
 * // Note span methods chain. Explicitly start the span when ready.
 * ScopedSpan span = tracer.startScopedSpan("encode");
 * try {
 *   return encoder.encode();
 * } catch (RuntimeException | Error e) {
 *   span.error(e); // Unless you handle exceptions, you might not know the operation failed!
 *   throw e;
 * } finally {
 *   span.finish(); // finish - start = the duration of the operation in microseconds
 * }
 * }</pre>
 *
 * <p>Note: this type is only useful for local operations. For remote operations, use {@link Span}
 */
public abstract class ScopedSpan {

  /** Same as {@link Span#isNoop()} */
  public abstract boolean isNoop();

  public abstract TraceContext context();

  /** Same as {@link SpanCustomizer#annotate(String)} */
  public abstract ScopedSpan annotate(String value);

  /** Same as {@link SpanCustomizer#tag(String, String)} */
  public abstract ScopedSpan tag(String key, String value);

  /** Same as {@link Span#error(Throwable)} */
  public abstract ScopedSpan error(Throwable throwable);

  /**
   * Closes the {@link CurrentTraceContext#newScope(TraceContext) scope} associated with this span,
   * then reports the span complete, assigning the most precise duration possible.
   */
  public abstract void finish();

  ScopedSpan() {
  }
}
