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
import io.github.fasset.fasset.kernel.subscriptions.Subscriber;
import io.github.fasset.fasset.kernel.subscriptions.SubscriptionService;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * We are not asserting anything. We are just checking how the observer pattern will behave
 */
public class FileUploadTopicTest {

    @Test
    public void fileUploadTopicWorks() throws Exception {

        // creating the subject
        SimpleSubscription topic = new SimpleSubscription();

        // create observers
        Subscriber obj1 = new FileUploadsListener("FileUploadListener 1");
        Subscriber obj2 = new FileUploadsListener("FileUploadListener 2");
        Subscriber obj3 = new FileUploadsListener("FileUploadListener 3");

        // registerSubscriber observers to the subject
        topic.registerSubscriber(obj1);
        topic.registerSubscriber(obj2);
        topic.registerSubscriber(obj3);

        // attach observer to subject
        obj1.addSubscription(topic);
        obj2.addSubscription(topic);
        obj3.addSubscription(topic);

        // check if any update is available
        obj1.update();

        // today send the file to the topic
        //topic.postUpdate("Data listing 15646");
        topic.postUpdate(() -> "Data listing 15646");

        // today send a new file to the topic. Expecting observers to not read the former topic
        // only the current one
        //topic.postUpdate("New Data listing 15647");
        topic.postUpdate(() -> "New Data listing 15647");

        // today send a new file to the topic. Expecting observers to not read the former topic
        // only the current one
        //topic.postUpdate("Even Newer Data listing 15648");
        topic.postUpdate(() -> "Even Newer Data listing 15648");

    }

    @Test
    public void subscriptionForFileUploadsShouldWork() throws Exception {

        SubscriptionService fileUploadsSubscriptionService = new SimpleSubscription();

        Subscriber fileUploadsSubscriber = new CompositeFileUploadsSubscriber("FileUploadsSubscriber1");
        Subscriber fileUploadsRecorder = new CompositeFileUploadsSubscriber("FileUploadsRecorder 1");

        fileUploadsSubscriptionService.registerSubscriber(fileUploadsSubscriber);
        fileUploadsSubscriptionService.registerSubscriber(fileUploadsRecorder);

        fileUploadsSubscriber.addSubscription(fileUploadsSubscriptionService);
        fileUploadsRecorder.addSubscription(fileUploadsSubscriptionService);


        fileUploadsSubscriptionService.postUpdate(() -> new FileUploadNotification("Data listing 15482", "Jan 2018", LocalDateTime.now().toString()));

        fileUploadsSubscriber.unSubscribe(fileUploadsSubscriptionService);

        fileUploadsSubscriptionService.postUpdate(() -> new FileUploadNotification("Data listing 10001", "Mar 2018", LocalDateTime.now().toString()));
    }

    @Test
    public void fileSystemStorageAsSubscriptionServiceCouldWork() throws Exception {

        FileSystemStorageService storageService = new FileSystemStorageService();

        storageService.store("File Containing Assets");
    }
}