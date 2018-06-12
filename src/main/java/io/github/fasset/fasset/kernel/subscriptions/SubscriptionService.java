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

/**
 * This interface allows a client to be able to publish an {@link Update} to each {@link Subscriber}
 * currently subscribed.
 * The method {@code postUpdate(Update)} once triggered will change the state of the client
 * and then call {@code notifyObservers()}, effectively publishing the {@link Update} to
 * each {@link Subscriber}
 *
 * @author edwin.njeru
 */
public interface SubscriptionService {

    // methods to registerSubscriber and deregisterSubscriber observers
    void registerSubscriber(Subscriber subscriber);

    void deregisterSubscriber(Subscriber observer);

    // method to notify observers of change
    void notifyObservers();

    // method to get updates from Subject
    Update getUpdate(Subscriber subscriber);

    void postUpdate(Update update);
}
