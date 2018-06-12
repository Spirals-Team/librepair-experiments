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
package io.github.fasset.fasset.kernel.subscriber;

import io.github.fasset.fasset.kernel.notifications.FileUploadNotification;
import io.github.fasset.fasset.kernel.subscriptions.SimpleSubscription;
import io.github.fasset.fasset.kernel.subscriptions.SubscriptionService;

import java.time.LocalDateTime;

/**
 * Provides storage service for file uploaded to the backend
 */
class FileSystemStorageService extends SimpleSubscription implements SubscriptionService {

    private FileStorageSubscriber subscriber;

    private FileStoragePersistenceSubscriber persistenceSubscriber;


    FileSystemStorageService() {
        super();
        this.subscriber = new FileStorageSubscriber("FileStorageSubscriber1");
        persistenceSubscriber = new FileStoragePersistenceSubscriber("PersistenceSubscriber1");
    }

    @SuppressWarnings("all")
    void store(String fileName) {

        subscriber.addSubscription(this);
        persistenceSubscriber.addSubscription(this);
        registerSubscriber(subscriber);
        registerSubscriber(persistenceSubscriber);

        postUpdate(() -> new FileUploadNotification(fileName, "Mar 2018", LocalDateTime.now().toString()));
    }
}
