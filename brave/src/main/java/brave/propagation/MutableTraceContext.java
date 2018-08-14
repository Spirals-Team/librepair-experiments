package brave.propagation;

import brave.internal.Nullable;
import java.util.logging.Logger;

import static brave.propagation.SamplingFlags.FLAG_DEBUG;
import static brave.propagation.TraceContext.FLAG_SHARED;

// parseXXX methods package protected until we figure out if this is reusable enough to expose
public final class MutableTraceContext extends InternalMutableTraceContext {
  static final Logger LOG = Logger.getLogger(MutableTraceContext.class.getName());

  /**
   * Used to join an incoming trace. For example, by reading http headers.
   *
   * @see brave.Tracer#nextSpan(TraceContextOrSamplingFlags)
   */
  public interface Extractor<C> {

    /**
     * Returns either a trace context or sampling flags parsed from the carrier. If nothing was
     * parsable, sampling flags will be set to {@link SamplingFlags#EMPTY}.
     *
     * @param carrier holds propagation fields. For example, an incoming message or http request.
     */
    void extract(C carrier, MutableTraceContext state);
  }

  public static MutableTraceContext create(TraceContextOrSamplingFlags context) {
    if (context == null) throw new NullPointerException("context == null");
    MutableTraceContext result = new MutableTraceContext();
    result.flags = context.value.flags;
    if (context.type == 1) {
      TraceContext traceContext = ((TraceContext) context.value);
      result.traceIdHigh = traceContext.traceIdHigh;
      result.traceId = traceContext.traceId;
      result.parentId = traceContext.parentId;
      result.spanId = traceContext.spanId;
      result.extra = traceContext.extra;
    } else if (context.type == 2) {
      TraceIdContext traceIdContext = ((TraceIdContext) context.value);
      result.traceIdHigh = traceIdContext.traceIdHigh;
      result.traceId = traceIdContext.traceId;
      result.extra = context.extra;
    } else if (context.type == 3) {
      result.extra = context.extra;
    }
    return result;
  }

  public static MutableTraceContext create(TraceContext context) {
    if (context == null) throw new NullPointerException("context == null");
    MutableTraceContext result = new MutableTraceContext();
    result.traceIdHigh = context.traceIdHigh;
    result.traceId = context.traceId;
    result.parentId = context.parentId;
    result.spanId = context.spanId;
    result.flags = context.flags;
    result.extra = context.extra;
    return result;
  }

  public TraceContext toTraceContext() {
    String missing = "";
    if (traceId == 0L) missing += " traceId";
    if (spanId == 0L) missing += " spanId";
    if (!"".equals(missing)) throw new IllegalStateException("Missing: " + missing);
    return new TraceContext(this);
  }

  /** @see TraceContext#traceIdHigh() */
  public long traceIdHigh() {
    return traceIdHigh;
  }

  /** @see TraceContext#traceIdHigh() */
  public void traceIdHigh(long traceIdHigh) {
    this.traceIdHigh = traceIdHigh;
  }

  /** @see TraceContext#traceId() */
  public long traceId() {
    return traceId;
  }

  /** @see TraceContext#traceId() */
  public void traceId(long traceId) {
    this.traceId = traceId;
  }

  /** @see TraceContext#parentIdAsLong() */
  public long parentId() {
    return parentId;
  }

  /** @see TraceContext#parentIdAsLong() */
  public void parentId(long parentId) {
    this.parentId = parentId;
  }

  /** @see TraceContext#spanId() */
  public long spanId() {
    return spanId;
  }

  /** @see TraceContext#spanId() */
  public void spanId(long spanId) {
    this.spanId = spanId;
  }

  /** @see TraceContext#sampled() */
  @Nullable public Boolean sampled() {
    return SamplingFlags.sampled(flags);
  }

  /** @see TraceContext#sampled() */
  public void sampled(boolean sampled) {
    _sampled(sampled);
  }

  /** @see TraceContext#debug() */
  public boolean debug() {
    return (flags & FLAG_DEBUG) == FLAG_DEBUG;
  }

  /** @see TraceContext#debug() */
  public void debug(boolean debug) {
    _debug(debug);
  }

  /** @see TraceContext#shared() */
  public boolean shared() {
    return (flags & FLAG_SHARED) == FLAG_SHARED;
  }

  /** @see TraceContext#shared() */
  public void shared(boolean shared) {
    _shared(shared);
  }

  /** Returns an {@linkplain #addExtra(Object) extra} of the given type if present or null if not. */
  public <T> T findExtra(Class<T> type) {
    return findExtra(extra, type);
  }

  /**
   * Adds iff there is not already an instance of the given type.
   *
   * @see TraceContext#extra()
   */
  public void addExtra(Object extra) {
    _addExtra(extra);
  }

  @Override Logger logger() {
    return LOG;
  }
} 


