/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.operator.project;

import com.facebook.presto.spi.Page;
import com.facebook.presto.spi.PageBuilder;
import com.facebook.presto.spi.type.Type;
import com.google.common.collect.ImmutableList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.facebook.presto.spi.block.PageBuilderStatus.DEFAULT_MAX_PAGE_SIZE_IN_BYTES;
import static java.lang.Math.toIntExact;
import static java.util.Objects.requireNonNull;

/**
 * This class is intended to be used right after the PageProcessor to ensure
 * that the size of the pages returned by FilterAndProject and ScanFilterAndProject
 * is big enough to do not introduce considerable synchronization overhead
 * <p>
 * As long as the input page contains more than {@link MergingPageOutput#DEFAULT_MIN_ROW_COUNT} rows
 * or bigger than {@link MergingPageOutput#DEFAULT_MIN_PAGE_SIZE_IN_BYTES} it is returned as is without
 * additional memory copy.
 * <p>
 * The page data that has been buffered so far before receiving a "big" page is being flushed
 * before transferring a "big" page.
 * <p>
 * Although it is still possible that the {@link MergingPageOutput} may return a tiny page,
 * this situation is considered to be rare due to the assumption that filter selectivity may not
 * vary a lot based on the particular input page.
 * <p>
 * Considering the CPU time required to process(filter, project) a full (~1MB) page returned by a
 * connector, the CPU cost of memory copying (< 50kb, < 1024 rows) is supposed to be negligible
 */
public class MergingPageOutput
        implements Iterator<Page>
{
    private static final long DEFAULT_MIN_PAGE_SIZE_IN_BYTES = 50 * 1024;
    private static final long DEFAULT_MIN_ROW_COUNT = 1024;

    private final List<Type> types;
    private final PageBuilder pageBuilder;
    private final Queue<Page> outputQueue = new LinkedList<>();

    private final long minPageSizeInBytes;
    private final long minRowCount;

    public MergingPageOutput(Iterable<? extends Type> types)
    {
        this(types, DEFAULT_MIN_PAGE_SIZE_IN_BYTES, DEFAULT_MIN_ROW_COUNT, DEFAULT_MAX_PAGE_SIZE_IN_BYTES);
    }

    public MergingPageOutput(Iterable<? extends Type> types, long minPageSizeInBytes, long minRowCount, long maxPageSizeInBytes)
    {
        this.types = ImmutableList.copyOf(requireNonNull(types, "types is null"));
        this.minPageSizeInBytes = minPageSizeInBytes;
        this.minRowCount = minRowCount;
        pageBuilder = PageBuilder.withMaxPageSize(toIntExact(maxPageSizeInBytes), this.types);
    }

    public void accept(Iterator<Page> pages)
    {
        requireNonNull(pages, "pages is null");
        pages.forEachRemaining(this::accept);
    }

    public void accept(Page page)
    {
        requireNonNull(page, "page is null");

        // avoid memory copying for pages that are big enough
        if (page.getSizeInBytes() >= minPageSizeInBytes || page.getPositionCount() >= minRowCount) {
            flush();
            outputQueue.add(page);
            return;
        }

        append(page);
    }

    public void flush()
    {
        if (!pageBuilder.isEmpty()) {
            Page output = pageBuilder.build();
            pageBuilder.reset();
            outputQueue.add(output);
        }
    }

    private void append(Page page)
    {
        for (int position = 0; position < page.getPositionCount(); position++) {
            if (pageBuilder.isFull()) {
                flush();
            }
            pageBuilder.declarePosition();
            for (int channel = 0; channel < types.size(); channel++) {
                Type type = types.get(channel);
                type.appendTo(page.getBlock(channel), position, pageBuilder.getBlockBuilder(channel));
            }
        }
        if (pageBuilder.isFull()) {
            flush();
        }
    }

    @Override
    public boolean hasNext()
    {
        return !outputQueue.isEmpty();
    }

    @Override
    public Page next()
    {
        return outputQueue.poll();
    }

    public long getRetainedSizeInBytes()
    {
        return pageBuilder.getRetainedSizeInBytes() + outputQueue.stream().mapToLong(Page::getRetainedSizeInBytes).sum();
    }
}
