#ifndef BLOCKING_RING_BUFFER_H
#define BLOCKING_RING_BUFFER_H

#include <thread>
#include <mutex>
#include <condition_variable>
#include "globals.hh"

class BlockingRingBuffer {
private:
    std::uint32_t read_idx, write_idx, capacity, available;
    std::uint8_t *buff;
    bool allow_writes;
    std::mutex m;
    std::condition_variable writable, readable;

    std::uint32_t write_noblock(const std::uint8_t *from, std::uint32_t& offset, std::uint32_t& sz);
    std::uint32_t read_noblock(std::uint8_t *to, std::uint32_t& offset, std::uint32_t& sz);

    metrics::Timer& s_t_write;
    metrics::Timer& s_t_write_wait;
    metrics::Hist& s_h_write_sz;

    metrics::Timer& s_t_read;
    metrics::Timer& s_t_read_wait;
    metrics::Hist& s_h_read_sz;

public:
    static constexpr std::uint32_t DEFAULT_RING_SZ = 1024 * 1024;
    
    BlockingRingBuffer(std::uint32_t _capacity = DEFAULT_RING_SZ);

    ~BlockingRingBuffer();

    std::uint32_t write(const std::uint8_t *from, std::uint32_t offset, std::uint32_t sz, bool do_block = true);

    std::uint32_t read(std::uint8_t *to, std::uint32_t offset, std::uint32_t sz, bool do_block = true);

    std::uint32_t reset();

    void readonly();
};

#endif
