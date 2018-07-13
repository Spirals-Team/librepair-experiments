/*
 * Copyright (C) 2017-2018 Kaloyan Raev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.goobox.sync.storj;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorjExecutorService extends ThreadPoolExecutor {

    private static final Logger logger = LoggerFactory.getLogger(StorjExecutorService.class);

    public StorjExecutorService(int processors, LinkedBlockingQueue<Runnable> linkedBlockingQueue) {
        super(processors, processors, 60, TimeUnit.SECONDS, linkedBlockingQueue);
        logger.info("started StorjExecutorService with {} threads", processors);
    }

}