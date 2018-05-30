package org;

import org.ormlayer.HibernateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.webTemplate.config.web",  // MVC @Configuration
        "com.webTemplate.config.security", // Security @Configuration
//        "com.webTemplate.config.jpa", // Database @Configuration -> does Entity Scan and Repository scan
        "com.webTemplate.service", // Service scan @Service
        "com.webTemplate.controler", // Controller scan @Controller
        "org.springboot"
})
public class Application {
    public static void main(String[] args) {
        testSpringBoot(args);
        testHibernate();
    }

    private static void testHibernate() {
        //Init Hibernate
        HibernateUtils.ormInit();
//        TestServiceDAOImpl serviceDAO = new TestServiceDAOImpl();
//        TestEntity entity = serviceDAO.create("TEST");
//        serviceDAO.deleteTestEntity(entity);
        HibernateUtils.destroy();
    }

    private static void testSpringBoot(String[] args) {
        //Init spring boot
        ApplicationContext context = SpringApplication.run(Application.class, args);

        for (String bean : context.getBeanDefinitionNames())
            System.out.println("[bean name]=" + bean);
    }
}
