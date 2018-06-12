/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.kernel.subscriptions;

import com.google.common.collect.ImmutableList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p>
 * Implements the {@link SubscriptionService} interface which is being observed by the registered {@link Subscriber}.
 * This is made possible through the {@code registerSubscriber} and the {@code deregisterSubscriber} methods.
 * The boolean {@code updated} keeps track of whether or not there's an update in the {@link SubscriptionService} and is
 * used to notify subscribers. Therefore if there is no update and someone calls {@link SubscriptionService#notifyObservers()}
 * it doesn't send false notifications
 * Synchronization is also used to make sure that the notification is only sent to the subscribers
 * registered before the {@link Update} is published to the {@link SubscriptionService}</p>
 * <p>
 * adapted from https://www.journaldev.com/1739/observer-design-pattern-in-java
 * posted on AUGUST 2, 2016</p>
 * <p>
 *
 * @author Pankaj
 * </p>
 * <p>
 * modified by</p>
 * @author edwin.njeru
 */
public class SimpleSubscription implements SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SimpleSubscription.class);
    // Mutant expression object to prevent inconsistent states in the subscriber list
    private final Object mutex = new Object();
    // track all current subscribers
    private List<Subscriber> subscribers = FastList.newList();
    // Queue for updates
    private Queue<Update> updateQueue = new ConcurrentLinkedQueue<>();
    // the state being tracked by the {@link Subscriber}
    private boolean updated;

    @Override
    public void registerSubscriber(Subscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException("Null observer");
        }

        synchronized (mutex) {
            if (!subscribers.contains(subscriber)) {
                subscribers.add(subscriber);
            }
        }

    }

    @Override
    public void deregisterSubscriber(Subscriber observer) {

        synchronized (mutex) {
            subscribers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {

        List<Subscriber> observersLocal;

        // this sync block ensures that observer registered after file
        // has been updated are not notified
        synchronized (mutex) {
            if (!updated) {
                return;
            }
            observersLocal = ImmutableList.copyOf(subscribers);

            this.updated = false;
        }

        observersLocal.forEach(Subscriber::update);

        // Now to remove the first item after everyone has peeked
        this.updateQueue.poll();
    }

    @Override
    public Update getUpdate(Subscriber subscriber) {

        return this.updateQueue.peek();
    }

    // Method to post file to the topic
    @Override
    public void postUpdate(Update update) {

        log.debug("Update : {} has been updated to the topic {}", update, this);

        this.updateQueue.offer(update);
        this.updated = true;

        notifyObservers();

    }
}
