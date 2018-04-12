package com.psl.integrador.config;

import com.psl.integrador.model.Collaborator;
import com.psl.integrador.model.Topic;
import com.psl.integrador.model.enums.Expertise;
import com.psl.integrador.repository.CollaboratorRepository;
import com.psl.integrador.repository.TopicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@EnableMongoRepositories(basePackageClasses = {TopicRepository.class, CollaboratorRepository.class})
@Configuration
public class MongoDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(TopicRepository topicRepository, CollaboratorRepository collaboratorRepository) {
        return strings -> {

            collaboratorRepository.deleteAll();
            topicRepository.deleteAll();

            List<Topic> listTopics = new ArrayList<>();
            Topic t1 = new Topic("Java", "Java test");
            t1.setTeachers(2);
            t1.setStudents(12);
            Topic t2 = new Topic("C#", "C# Test");
            t2.setTeachers(1);
            t2.setStudents(5);
            Topic t3 = new Topic("Python", "Python Test");
            t3.setTeachers(2);
            t3.setStudents(16);
            Topic t4 = new Topic("Ruby", "Ruby Test");
            t4.setTeachers(0);
            t4.setStudents(3);
            Topic t5 = new Topic("Scala", "Scala Test");
            t5.setTeachers(0);
            t5.setStudents(2);
            Topic t6 = new Topic("React.js", "React Test");
            t6.setTeachers(1);
            t6.setStudents(16);
            Topic t7 = new Topic("Angular 4", "Angular 4 Test");
            t7.setTeachers(2);
            t7.setStudents(8);
            Topic t8 = new Topic("MongoDB", "MongoDB Test");
            t8.setTeachers(1);
            t8.setStudents(5);
            Topic t9 = new Topic("Vue.js", "Vue Test");
            t9.setTeachers(1);
            t9.setStudents(4);
            Topic t10 = new Topic("SQL Server", "Scala Test");
            t10.setTeachers(0);
            t10.setStudents(2);
            Topic t11 = new Topic("Node.js", "Node Test");
            t11.setTeachers(3);
            t11.setStudents(11);
            Topic t12 = new Topic("Postgresql", "Postgresql Test");
            t12.setTeachers(0);
            t12.setStudents(1);
            Topic t13 = new Topic("Firebase", "Firebase Test");
            t13.setTeachers(0);
            t13.setStudents(4);
            Topic t14 = new Topic("Haskell", "Haskell Test");
            t14.setTeachers(0);
            t14.setStudents(15);
            Topic t15 = new Topic("Xamarin", "Xamarin Test");
            t15.setTeachers(1);
            t15.setStudents(3);


            listTopics.add(t1);
            listTopics.add(t2);
            listTopics.add(t3);
            listTopics.add(t4);
            listTopics.add(t6);
            listTopics.add(t7);
            listTopics.add(t8);
            listTopics.add(t9);
            listTopics.add(t10);
            listTopics.add(t11);
            listTopics.add(t12);
            listTopics.add(t13);
            listTopics.add(t14);
            listTopics.add(t15);

            topicRepository.save(listTopics);

            Collaborator c1 = new Collaborator();
            c1.setName("Jose");
            c1.setEmail("josedavidrestrepoduque@gmail.com");
            c1.addTopicToTeach(t1, Expertise.BEGINNER);
            c1.addTopicToLearn(t2, Expertise.EXPERT);

            Collaborator c2 = new Collaborator();
            c2.setName("Juan");
            c2.setEmail("josedavidrestrepoduque@gmail.com");
            c2.addTopicToLearn(t1, Expertise.BEGINNER);
            c2.addTopicToTeach(t3, Expertise.INTERMEDIATE);
            c2.addTopicToLearn(t4, Expertise.EXPERT);

            collaboratorRepository.save(c1);
            collaboratorRepository.save(c2);
        };
    }

}
