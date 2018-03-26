/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.unifiedpush.service.impl;

import java.util.concurrent.Future;

import javax.inject.Inject;

import org.jboss.aerogear.unifiedpush.jpa.dao.impl.JPAHealthDao;
import org.jboss.aerogear.unifiedpush.service.HealthDBService;
import org.jboss.aerogear.unifiedpush.service.impl.health.HealthDetails;
import org.jboss.aerogear.unifiedpush.service.impl.health.Status;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class HealthServiceImpl implements HealthDBService {
    @Inject
    private JPAHealthDao healthDao;

    @Async
    @Override
    public Future<HealthDetails> dbStatus() {
        HealthDetails details = new HealthDetails();
        details.setDescription("Database connection");
        details.start();
        try {
            healthDao.dbCheck();
            details.setTestStatus(Status.OK);
            details.setResult("connected");
        } catch (Exception e) {
            details.setTestStatus(Status.CRIT);
            details.setResult(e.getMessage());
        }
        details.stop();
        return new AsyncResult<>(details);
    }
}
