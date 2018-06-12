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
 * Interface allows a client to subscribe and unSubscribe to a service as well as to receive
 * updates from the {@link SubscriptionService} through the update method.
 *
 * @author edwin.njeru
 */
public interface Subscriber {

    // method to update the observer, used by the subject
    void update();

    // attach with subject to observe
    void addSubscription(SubscriptionService subscription);

    void unSubscribe(SubscriptionService subscription);
}
