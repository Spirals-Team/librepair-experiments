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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Provides the {@link AbstractSubscriber#consumeUpdate(Update)} method used by the runtime client
 * to consume updates dynamically.
 * As much as possible but without guarantee care has been taken to make this implementation as thread-safe
 * as possible.
 *
 * @author edwin.njeru
 */
public abstract class AbstractSubscriber implements Subscriber {

    private static final Logger log = LoggerFactory.getLogger(AbstractSubscriber.class);

    private Object mutex = new Object();

    // name of subscriber
    private String name;

    private List<SubscriptionService> subscriptions = new LinkedList<>();


    protected AbstractSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void update() {

        synchronized (mutex) {
            subscriptions.forEach(this::checkSubscriptions);
        }
    }

    protected abstract void consumeUpdate(Update update);


    @Override
    public void addSubscription(SubscriptionService subscription) {

        synchronized (mutex) {
            this.subscriptions.add(subscription);
        }
    }

    private void checkSubscriptions(SubscriptionService subscription) {

        Update update = subscription.getUpdate(this);

        if (update == null) {
            log.debug("{}:: No new update in subscription : {}", name, subscription);
        } else {

            log.debug("{}:: Consuming update::{}", name, update);

            consumeUpdate(update);

        }
    }

    @Override
    public void unSubscribe(SubscriptionService subscription) {

        synchronized (mutex) {
            this.subscriptions.remove(subscription);
        }
    }
}
