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
 * This is a thin wrapper for data to be passed around the {@link Subscriber} implementations
 * The object being transported can be loaded through lambda semantics, then unloaded
 * by the {@link Subscriber} using {@code getPayload()} method.
 * Rather than use generics which would lead to unnecessary complications, the "unloaded" value
 * will need to be casted into their respective types. This approach of "dumb update - smart subscriber"
 * assumes the {@link Subscriber} will take care while unloading the update, and also enables the
 * {@link SubscriptionService} to quickly upload any type of data through.
 * {@link Subscriber} be aware...
 *
 * @author edwin.njeru
 */
@FunctionalInterface
public interface Update {

    /**
     * @return Object wrapped in this update
     */
    Object getPayload();

}
