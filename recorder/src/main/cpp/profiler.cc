#include "profiler.hh"
#include "stacktraces.hh"

ASGCTType Asgct::asgct_;
IsGCActiveType Asgct::is_gc_active_;

static void handle_profiling_signal(int signum, siginfo_t *info, void *context) {
    ReadsafePtr<Profiler> p(GlobalCtx::recording.cpu_profiler);
    if (p.available()) {
        p->handle(signum, info, context);
    }
}

void Profiler::handle(int signum, siginfo_t *info, void *context) {
    IMPLICITLY_USE(signum);
    IMPLICITLY_USE(info);//TODO: put a timer here after perf-tuning medida heavily, we'd dearly love a timer here, but the overhead makes it a no-go as of now.
    s_c_cpu_samp_total.inc();
    JNIEnv *jniEnv = getJNIEnv(jvm);
    ThreadBucket *thread_info = nullptr;
    PerfCtx::ThreadTracker* ctx_tracker = nullptr;
    auto current_sampling_attempt = sampling_attempts.fetch_add(1, std::memory_order_relaxed);
    bool default_ctx = false;
    bool do_record = true;

    if (jniEnv != nullptr) {
        thread_info = thread_map.get(jniEnv);
        if (thread_info != nullptr) {//TODO: increment a counter here to monitor freq of this, it could be GC thd or compiler-broker etc
            ctx_tracker = &(thread_info->data.ctx_tracker);
            if (ctx_tracker->in_ctx()) {
                do_record = ctx_tracker->should_record();
            } else {
                do_record = get_prob_pct().on(current_sampling_attempt, noctx_cov_pct);
                default_ctx = true;
            }
        } else {
            //This is most probably an internal JVM thread (GC/JIT compiler/etc) for which we do not have thread info
            do_record = capture_unknown_thd_bt ? get_prob_pct().on(current_sampling_attempt, noctx_cov_pct) : false;
        }
    } else {
        //Native thread
        do_record = capture_native_bt ? get_prob_pct().on(current_sampling_attempt, noctx_cov_pct) : false;
    }
    if (! do_record) {
        return;
    }

    BacktraceError err = BacktraceError::Fkp_no_error;

    if (jniEnv != NULL) {
        STATIC_ARRAY(frames, JVMPI_CallFrame, capture_stack_depth(), MAX_FRAMES_TO_CAPTURE);
        
        JVMPI_CallTrace trace;
        trace.env_id = jniEnv;
        trace.frames = frames;
        ASGCTType asgct = Asgct::GetAsgct();
        (*asgct)(&trace, capture_stack_depth(), context);
        if (trace.num_frames > 0) {
            cpu::InMsg m(trace, thread_info, err, default_ctx);
            buffer->push(m);
            return; // we got java trace, so bail-out
        }
        if (trace.num_frames <= 0) {
            err = static_cast<BacktraceError>(-1 * trace.num_frames);
            s_c_cpu_samp_err_unexpected.inc();
        }
    } else {
        err = BacktraceError::Fkp_no_jni_env;
        s_c_cpu_samp_err_no_jni.inc();
    }

    // Will definitely land here if jnienv == null
    // Can land here despite jnienv != null if asgct could not walk the stack of current thread.
    // We want to be absolutely sure if native bt capture has been enabled before proceeding
    if(!capture_native_bt) {
        return;
    }
    STATIC_ARRAY(native_trace, NativeFrame, capture_stack_depth(), MAX_FRAMES_TO_CAPTURE);

    auto bt_len = Stacktraces::fill_backtrace(native_trace, capture_stack_depth());
    cpu::InMsg m(native_trace, bt_len, thread_info, err, default_ctx);
    buffer->push(m);
}

bool Profiler::start(JNIEnv *jniEnv) {
    if (running) {
        logger->warn("Start called but sampling is already running");
        return true;
    }

    // reference back to Profiler::handle on the singleton
    // instance of Profiler
    handler->SetAction(&handle_profiling_signal);
    bool res = handler->updateSigprofInterval();
    running = true;
    return res;
}

void Profiler::stop() {
    if (!running) {
        return;
    }

    handler->stopSigprof();
    running = false;
}

void Profiler::set_sampling_freq(std::uint32_t sampling_freq) {
    auto mean_sampling_itvl = 1000000 / sampling_freq;
    std::uint32_t itvl_10_pct = 0.1 * mean_sampling_itvl;
    itvl_max = mean_sampling_itvl + itvl_10_pct;
    itvl_min = mean_sampling_itvl - itvl_10_pct;
    itvl_min = itvl_min > 0 ? itvl_min : DEFAULT_SAMPLING_INTERVAL;
    itvl_max = itvl_max > 0 ? itvl_max : DEFAULT_SAMPLING_INTERVAL;
    logger->warn("Chose CPU sampling interval range [{0:06d}, {1:06d}) for requested sampling freq {2:d} Hz", itvl_min, itvl_max, sampling_freq);
}

std::uint32_t Profiler::calculate_max_stack_depth(std::uint32_t _max_stack_depth) {
    return (_max_stack_depth > 0 && _max_stack_depth < (MAX_FRAMES_TO_CAPTURE - 1)) ? _max_stack_depth : DEFAULT_MAX_FRAMES_TO_CAPTURE;
}

void Profiler::configure() {
    buffer = new cpu::Queue(serializer, capture_stack_depth());

    handler = new SignalHandler(itvl_min, itvl_max);
    //int processor_interval = Size * itvl_min / 1000 / 2;
    //logger->debug("CpuSamplingProfiler is using processor-interval value: {}", processor_interval);
}

#define METRIC_TYPE "cpu_samples"

Profiler::Profiler(JavaVM *_jvm, jvmtiEnv *_jvmti, ThreadMap &_thread_map, ProfileSerializingWriter& _serializer, std::uint32_t _max_stack_depth, std::uint32_t _sampling_freq, ProbPct& _prob_pct, std::uint8_t _noctx_cov_pct, bool _capture_native_bt, bool _capture_unknown_thd_bt)
    : jvm(_jvm), jvmti(_jvmti), thread_map(_thread_map), max_stack_depth(_max_stack_depth), serializer(_serializer),
      prob_pct(_prob_pct), sampling_attempts(0), noctx_cov_pct(_noctx_cov_pct), capture_native_bt(_capture_native_bt), capture_unknown_thd_bt(_capture_unknown_thd_bt), running(false), samples_handled(0),

      s_c_cpu_samp_total(get_metrics_registry().new_counter({METRICS_DOMAIN, METRIC_TYPE, "opportunities"})),
      s_c_cpu_samp_err_no_jni(get_metrics_registry().new_counter({METRICS_DOMAIN, METRIC_TYPE, "err_no_jni"})),
      s_c_cpu_samp_err_unexpected(get_metrics_registry().new_counter({METRICS_DOMAIN, METRIC_TYPE, "err_unexpected"})),

      s_h_pop_spree_len(get_metrics_registry().new_histogram({METRICS_DOMAIN, METRIC_TYPE, "pop_spree", "length"})),
      s_t_pop_spree_tm(get_metrics_registry().new_timer({METRICS_DOMAIN, METRIC_TYPE, "pop_spree", "time"})) {

    set_sampling_freq(_sampling_freq);
    configure();
}

Profiler::~Profiler() {
    if (running) stop();
    delete handler;
    delete buffer;
}

void Profiler::run() {
    {
        auto _ = s_t_pop_spree_tm.time_scope();

        int popped_before = samples_handled;
        while (buffer->pop()) ++samples_handled;

        s_h_pop_spree_len.update(samples_handled - popped_before);
    }

    if (samples_handled > 200) {
        if (! handler->updateSigprofInterval()) {
            logger->warn("Couldn't switch sigprof interval to the next random value");
        }
        samples_handled = 0;
    }
}

Time::msec Profiler::run_itvl() {
    return Time::msec(Size * itvl_min / 1000 / 2);
}
