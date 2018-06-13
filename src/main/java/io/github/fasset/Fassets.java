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
package io.github.fasset;

import io.github.fasset.fasset.config.DateProperties;
import io.github.fasset.fasset.config.MoneyProperties;
import io.github.fasset.fasset.config.StorageProperties;
import io.github.fasset.fasset.kernel.batch.depreciation.model.DepreciationProperties;
import io.github.fasset.fasset.kernel.storage.StorageService;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executor;

/**
 * This is the entry point for the fassets program
 */
@EnableAsync
@EnableCaching
@EnableTransactionManagement
@EnableBatchProcessing
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"io.github.fasset.fasset.repository", "io.github.fasset.fasset.kernel.batch.depreciation.model", "io.github.fasset.fasset.book.keeper.repo"})
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EntityScan(basePackages = {"io.github.fasset.fasset.model", "io.github.fasset.fasset.kernel.batch.depreciation.model", "io.github.fasset.fasset.book.keeper"})
@ComponentScan("io.github.fasset.fasset")
@PropertySource("classpath:batch.properties")
@EnableConfigurationProperties(value = {StorageProperties.class, MoneyProperties.class, DepreciationProperties.class, DateProperties.class})
public class Fassets {

    public static void main(String[] args) {
        SpringApplication.run(Fassets.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {

        return (args) -> {

            storageService.deleteAll();

            storageService.init();
        };
    }

    @Bean
    public Executor asynchExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(500);
        executor.initialize();
        return executor;
    }

    @Bean
    public CacheManagerCustomizer<CaffeineCacheManager> cacheManagerCustomizer() {

        return cacheManager -> cacheManager.setAllowNullValues(false);
    }
}
