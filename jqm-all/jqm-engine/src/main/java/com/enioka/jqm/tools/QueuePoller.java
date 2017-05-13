/**
 * Copyright © 2013 enioka. All rights reserved
 * Authors: Marc-Antoine GOUILLART (marc-antoine.gouillart@enioka.com)
 *          Pierre COPPEE (pierre.coppee@enioka.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enioka.jqm.tools;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import com.enioka.jqm.jdbc.DbConn;
import com.enioka.jqm.jdbc.QueryResult;
import com.enioka.jqm.model.DeploymentParameter;
import com.enioka.jqm.model.JobInstance;
import com.enioka.jqm.model.Queue;

/**
 * A thread that polls a queue according to the parameters defined inside a {@link DeploymentParameter}.
 */
class QueuePoller implements Runnable, QueuePollerMBean
{
    private static Logger jqmlogger = Logger.getLogger(QueuePoller.class);

    private Queue queue = null;
    private JqmEngine engine;
    private int maxNbThread = 10;
    private int pollingInterval = 10000;

    private boolean run = true;
    private AtomicInteger actualNbThread = new AtomicInteger(0);
    private boolean hasStopped = true;
    private Calendar lastLoop = null;
    private Map<Integer, Date> peremption = new ConcurrentHashMap<Integer, Date>();

    private ObjectName name = null;

    private Thread localThread = null;
    private Semaphore loop;

    @Override
    public void stop()
    {
        jqmlogger.info("Poller " + queue.getName() + " has received a stop order");
        run = false;
        if (localThread != null)
        {
            localThread.interrupt();
        }
    }

    /**
     * Will make the thread ready to run once again after it has stopped.
     */
    void reset()
    {
        if (!hasStopped)
        {
            throw new IllegalStateException("cannot reset a non stopped queue poller");
        }
        hasStopped = false;
        run = true;
        lastLoop = null;
        loop = new Semaphore(0);
    }

    QueuePoller(JqmEngine engine, Queue q, int nbThreads, int interval)
    {
        jqmlogger.info("Engine " + engine.getNode().getName() + " will poll JobInstances on queue " + q.getName() + " every "
                + interval / 1000 + "s with " + nbThreads + " threads for concurrent instances");

        this.engine = engine;
        this.queue = q;
        this.pollingInterval = interval;
        this.maxNbThread = nbThreads;

        reset();
        registerMBean();
    }

    private void registerMBean()
    {
        try
        {
            if (this.engine.loadJmxBeans)
            {
                MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
                name = new ObjectName(
                        "com.enioka.jqm:type=Node.Queue,Node=" + this.engine.getNode().getName() + ",name=" + this.queue.getName());

                // Unregister MBean if it already exists. This may happen during frequent DP modifications.
                try
                {
                    mbs.getMBeanInfo(name);
                    mbs.unregisterMBean(name);
                }
                catch (InstanceNotFoundException e)
                {
                    // Nothing to do, this should be the normal case.
                }

                mbs.registerMBean(this, name);
            }
        }
        catch (Exception e)
        {
            throw new JqmInitError("Could not create JMX beans", e);
        }
    }

    protected List<JobInstance> dequeue(DbConn cnx)
    {
        // Free room?
        int usedSlots = actualNbThread.get();
        if (usedSlots >= maxNbThread)
        {
            return new ArrayList<JobInstance>();
        }

        // Get the list of all jobInstance within the defined queue, ordered by position
        QueryResult qr = cnx.runUpdate("ji_update_poll", this.engine.getNode().getId(), queue.getId(), maxNbThread - usedSlots);
        if (qr.nbUpdated > 0)
        {
            jqmlogger.debug("Poller has found " + qr.nbUpdated + " JI to run");
        }
        if (qr.nbUpdated > 0)
        {
            List<JobInstance> res = JobInstance.select(cnx, "ji_select_to_run", this.engine.getNode().getId(), queue.getId());
            if (res.size() == qr.nbUpdated)
            {
                cnx.commit();
                return res;
            }
            else
            {
                // Try again. This means the jobs marked for exec on previous loop have not already started.
                // So they were still in the SELECT WHERE STATE='ATTRIBUTED'.
                // Happens when loop interval is too low (ms and not s), so only happens during tests.
                jqmlogger.info("Polling interval seems too low");
                cnx.rollback();
                Thread.yield();
                return dequeue(cnx);
            }
        }
        else
        {
            return new ArrayList<JobInstance>();
        }
    }

    @Override
    synchronized public void run() // sync: avoid race condition on run when restarting after failure.
    {
        this.localThread = Thread.currentThread();
        this.localThread.setName("QUEUE_POLLER;polling;" + this.queue.getName());
        DbConn cnx = null;

        while (true)
        {
            lastLoop = Calendar.getInstance();
            jqmlogger.trace("poller loop");

            try
            {
                // Get a JI to run
                cnx = Helpers.getNewDbSession();
                for (JobInstance ji : dequeue(cnx))
                {
                    // We will run this JI!
                    jqmlogger.trace("JI number " + ji.getId() + " will be run by this poller this loop (already " + actualNbThread + "/"
                            + maxNbThread + " on " + this.queue.getName() + ")");
                    actualNbThread.incrementAndGet();
                    if (ji.getJD().getMaxTimeRunning() != null)
                    {
                        this.peremption.put(ji.getId(), new Date((new Date()).getTime() + ji.getJD().getMaxTimeRunning() * 60 * 1000));
                    }

                    // Run it
                    if (!ji.getJD().isExternal())
                    {
                        (new Thread(new Loader(ji, this.engine, this, this.engine.getClassloaderManager()))).start();
                    }
                    else
                    {
                        (new Thread(new LoaderExternal(cnx, ji, this))).start();
                    }
                }
            }
            catch (RuntimeException e)
            {
                if (Helpers.testDbFailure(e))
                {
                    jqmlogger.error("connection to database lost - stopping poller");
                    jqmlogger.trace("connection error was:", e.getCause());
                    this.hasStopped = true;
                    this.engine.pollerRestartNeeded(this);
                    break;
                }
                else
                {
                    jqmlogger.error("Queue poller has failed! It will stop.", e);
                    this.run = false;
                    this.hasStopped = true;
                    break;
                }
            }
            finally
            {
                // Reset the connection on each loop.
                Helpers.closeQuietly(cnx);
            }

            // Wait according to the deploymentParameter
            try
            {
                loop.tryAcquire(this.pollingInterval, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                run = false;
                break;
            }

            // Exit if asked to
            if (!run)
            {
                break;
            }
        }

        if (!run)
        {
            // Run is true only if the loop has exited abnormally, in which case the engine should try to restart the poller
            // So only do the graceful shutdown procedure if normal shutdown.

            jqmlogger
                    .info("Poller loop on queue " + this.queue.getName() + " is stopping [engine " + this.engine.getNode().getName() + "]");
            waitForAllThreads(60L * 1000);
            jqmlogger.info("Poller on queue " + this.queue.getName() + " has ended normally");

            // JMX
            if (this.engine.loadJmxBeans)
            {
                try
                {
                    ManagementFactory.getPlatformMBeanServer().unregisterMBean(name);
                }
                catch (Exception e)
                {
                    jqmlogger.error("Could not unregister JMX beans", e);
                }
            }

            // Let the engine decide if it should stop completely
            this.hasStopped = true; // BEFORE check
            this.engine.checkEngineEnd();
        }
        else
        {
            // else => Abnormal stop. Set booleans to reflect this.
            jqmlogger.error("Poller on queue " + this.queue.getName() + " has ended abnormally");
            this.run = false;
            this.hasStopped = true;
            this.engine.checkEngineEnd();
        }
    }

    @Override
    public Integer getCurrentActiveThreadCount()
    {
        return actualNbThread.get();
    }

    /**
     * Called when a payload thread has ended. This notifies the poller to free a slot and poll once again.
     */
    void decreaseNbThread(int jobId)
    {
        this.peremption.remove(jobId);
        this.actualNbThread.decrementAndGet();
        loop.release(1);
        this.engine.signalEndOfRun();
    }

    boolean isRunning()
    {
        return !this.hasStopped;
    }

    private void waitForAllThreads(long timeOutMs)
    {
        long timeWaitedMs = 0;
        long stepMs = 1000;
        while (timeWaitedMs <= timeOutMs)
        {
            jqmlogger.trace("Waiting the end of " + actualNbThread + " job(s)");

            if (actualNbThread.get() == 0)
            {
                break;
            }
            if (timeWaitedMs == 0)
            {
                jqmlogger.info("Waiting for the end of " + actualNbThread + " jobs on queue " + this.queue.getName() + " - timeout is "
                        + timeOutMs + "ms");
            }
            try
            {
                Thread.sleep(stepMs);
            }
            catch (InterruptedException e)
            {
                // Interruption => stop right now
                jqmlogger.warn("Some job instances did not finish in time - wait was interrupted");
                Thread.currentThread().interrupt();
                return;
            }
            timeWaitedMs += stepMs;
        }
        if (timeWaitedMs > timeOutMs)
        {
            jqmlogger.warn("Some job instances did not finish in time - they will be killed for the poller to be able to stop");
        }
    }

    Queue getQueue()
    {
        return this.queue;
    }

    JqmEngine getEngine()
    {
        return this.engine;
    }

    void setMaxThreads(int max)
    {
        if (this.maxNbThread > 0 && max == 0)
        {
            jqmlogger.info("Poller is being paused - it won't fetch any new job instances until it is resumed.");
        }
        else if (this.maxNbThread == 0 && max > 0)
        {
            jqmlogger.info("Poller is being resumed");
        }
        this.maxNbThread = max;
    }

    void setPollingInterval(int ms)
    {
        this.pollingInterval = ms;
    }

    // //////////////////////////////////////////////////////////
    // JMX
    // //////////////////////////////////////////////////////////

    @Override
    public long getCumulativeJobInstancesCount()
    {
        DbConn em2 = Helpers.getNewDbSession();
        try
        {
            return em2.runSelectSingle("history_select_count_for_poller", Long.class, this.queue.getId(), this.engine.getNode().getId());
        }
        finally
        {
            Helpers.closeQuietly(em2);
        }
    }

    @Override
    public float getJobsFinishedPerSecondLastMinute()
    {
        DbConn em2 = Helpers.getNewDbSession();
        try
        {
            return em2.runSelectSingle("history_select_count_last_mn_for_poller", Float.class, this.queue.getId(),
                    this.engine.getNode().getId());
        }
        finally
        {
            Helpers.closeQuietly(em2);
        }
    }

    @Override
    public long getCurrentlyRunningJobCount()
    {
        return this.actualNbThread.get();
    }

    @Override
    public Integer getPollingIntervalMilliseconds()
    {
        return this.pollingInterval;
    }

    @Override
    public Integer getMaxConcurrentJobInstanceCount()
    {
        return this.maxNbThread;
    }

    @Override
    public boolean isActuallyPolling()
    {
        // 100ms is a rough estimate of the time taken to do the actual poll. If it's more, there is a huge issue elsewhere.
        return (Calendar.getInstance().getTimeInMillis() - this.lastLoop.getTimeInMillis()) <= pollingInterval + 100;
    }

    @Override
    public boolean isFull()
    {
        return this.actualNbThread.get() >= maxNbThread;
    }

    @Override
    public int getLateJobs()
    {
        int i = 0;
        Date now = new Date();
        for (Date d : this.peremption.values())
        {
            if (now.after(d))
            {
                i++;
            }
        }
        return i;
    }
}
