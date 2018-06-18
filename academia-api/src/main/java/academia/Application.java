package academia;

import academia.business.account.AccountRepository;
import academia.business.account.AccountService;
import academia.business.activity.ActivityRepository;
import academia.business.activity.ActivityService;
import academia.business.plan.PlanService;
import academia.business.restriction.RestrictionRepository;
import academia.business.restriction.RestrictionService;
import academia.domain.AccountType;
import academia.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(
            AccountService accountService,
            AccountRepository accountRepository,

            RestrictionService restrictionService,
            RestrictionRepository restrictionRepository,

            ActivityService activityService,
            ActivityRepository activityRepository,

            PlanService planService
    ) {
        return (args) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            /*
                restrictions
             */
            String restrictionName1 = "Esforço cardíaco";
            Restriction restrictionCardiaco = restrictionRepository.findByName(restrictionName1);
            if (restrictionCardiaco == null) {
                restrictionCardiaco = restrictionService.create(new Restriction(restrictionName1));
            }

            String restrictionName2 = "Impacto no joelho";
            Restriction restrictionJoelho = restrictionRepository.findByName(restrictionName2);
            if (restrictionJoelho == null) {
                restrictionJoelho = restrictionService.create(new Restriction(restrictionName2));
            }

            String restrictionName3 = "Esforço na coluna";
            Restriction restrictionColuna = restrictionRepository.findByName(restrictionName3);
            if (restrictionColuna == null) {
                restrictionColuna = restrictionService.create(new Restriction(restrictionName3));
            }

            String restrictionNameRespiratorio = "Esforço respiratório";
            Restriction restrictionRespiratorio = restrictionRepository.findByName(restrictionNameRespiratorio);
            if (restrictionRespiratorio == null) {
                restrictionRespiratorio = restrictionService.create(new Restriction(restrictionNameRespiratorio));
            }

            /*
                accounts
             */
            String adminName = "admin";
            Account admin = accountRepository.findByUsername(adminName);
            if (admin == null) {
                admin = accountService.create(new Account("Administrador da Academia", adminName, adminName, AccountType.ADMIN, null));
            }

            String customerName1 = "customer1";
            Account customer1 = accountRepository.findByUsername(customerName1);
            if (customer1 == null) {
                List<Restriction> restrictions = Arrays.asList(restrictionCardiaco, restrictionJoelho);
                customer1 = new Account("Rafael da Silva", customerName1, customerName1, AccountType.CUSTOMER, restrictions);
                customer1 = accountService.create(customer1);
            }

            String customerName2 = "customer2";
            Account customer2 = accountRepository.findByUsername(customerName2);
            if (customer2 == null) {
                List<Restriction> restrictions = Arrays.asList(restrictionRespiratorio);
                customer2 = new Account("Luís dos Santos", customerName2, customerName2, AccountType.CUSTOMER, restrictions);
                customer2 = accountService.create(customer2);
            }

            String customerName3 = "customer3";
            Account customer3 = accountRepository.findByUsername(customerName3);
            if (customer3 == null) {
                List<Restriction> restrictions = Arrays.asList();
                customer3 = new Account("Sr Saudável", customerName3, customerName3, AccountType.CUSTOMER, restrictions);
                customer3 = accountService.create(customer3);
            }

            String trainerBodyBuilderName = "trainer1";
            Account trainerBodyBuilder = accountRepository.findByUsername(trainerBodyBuilderName);
            if (trainerBodyBuilder == null) {
                trainerBodyBuilder = accountService.create(new Account("João Marombeiro", trainerBodyBuilderName, trainerBodyBuilderName, AccountType.TRAINER, null));
            }

            String trainerAthleticName = "trainer2";
            Account trainerAthletic = accountRepository.findByUsername(trainerAthleticName);
            if (trainerAthletic == null) {
                trainerAthletic = accountService.create(new Account("José Corredor", trainerAthleticName, trainerAthleticName, AccountType.TRAINER, null));
            }

            String trainerYogaName = "trainer3";
            Account trainerYogaGuy = accountRepository.findByUsername(trainerYogaName);
            if (trainerYogaGuy == null) {
                trainerYogaGuy = accountService.create(new Account("Yoga Guy", trainerYogaName, trainerYogaName, AccountType.TRAINER, null));
            }

            /*
                activities
             */
            String activityNameRunning = "Corrida";
            Activity activityRunning = activityRepository.findByName(activityNameRunning);
            if (activityRunning == null) {
                activityRunning = new Activity(activityNameRunning, "Corrida na esteira para treinamento cardiovascular",
                        Arrays.asList(trainerAthletic), Arrays.asList(restrictionJoelho, restrictionColuna, restrictionRespiratorio));
                activityRunning = activityService.create(activityRunning);
            }

            String activityNameWalking = "Caminhada";
            Activity activityWalking = activityRepository.findByName(activityNameWalking);
            if (activityWalking == null) {
                activityWalking = new Activity(activityNameWalking, "Caminhada na esteira para treinamento cardiovascular",
                        Arrays.asList(trainerAthletic, trainerYogaGuy), new ArrayList<>());
                activityWalking = activityService.create(activityWalking);
            }

            String activityNameWeightTraining = "Musculação - reforço";
            Activity activityWeightTraining = activityRepository.findByName(activityNameWeightTraining);
            if (activityWeightTraining == null) {
                activityWeightTraining = new Activity(activityNameWeightTraining, "Musculação para reforço muscular",
                        Arrays.asList(trainerBodyBuilder, trainerYogaGuy), Arrays.asList(restrictionColuna));
                activityWeightTraining = activityService.create(activityWeightTraining);
            }

            String activityNameBodyBuilding = "Musculação - hipertrofia";
            Activity activityBodyBuilding = activityRepository.findByName(activityNameBodyBuilding);
            if (activityBodyBuilding == null) {
                activityBodyBuilding = new Activity(activityNameBodyBuilding, "Musculação para ganho de massa muscular",
                        Arrays.asList(trainerBodyBuilder), Arrays.asList(restrictionColuna, restrictionCardiaco));
                activityBodyBuilding = activityService.create(activityBodyBuilding);
            }

            String activityNameYoga = "Yoga";
            Activity activityYoga = activityRepository.findByName(activityNameYoga);
            if (activityYoga == null) {
                activityYoga = new Activity(activityNameYoga, "Aulas de Yoga",
                        Arrays.asList(trainerYogaGuy), new ArrayList<>());
                activityYoga = activityService.create(activityYoga);
            }

            String activityNamePilates = "Pilates";
            Activity activityPilates = activityRepository.findByName(activityNamePilates);
            if (activityPilates == null) {
                activityPilates = new Activity(activityNamePilates, "Aulas de Pilates",
                        Arrays.asList(trainerYogaGuy, trainerBodyBuilder), new ArrayList<>());
                activityPilates = activityService.create(activityPilates);
            }

            /*
                plan
             */
            Appointment appointment1 = new Appointment(activityWalking, trainerAthletic, LocalDateTime.parse("20/06/2018 19:00", formatter));
            Appointment appointment2 = new Appointment(activityWalking, trainerAthletic, LocalDateTime.parse("21/06/2018 19:00", formatter));
            Appointment appointment3 = new Appointment(activityYoga, trainerYogaGuy, LocalDateTime.parse("22/06/2018 19:00", formatter));
            Plan plan1 = new Plan(customer1, Arrays.asList(appointment1, appointment2, appointment3));
            planService.create(plan1);

            Appointment appointment4 = new Appointment(activityBodyBuilding, trainerBodyBuilder, LocalDateTime.parse("20/06/2018 20:00", formatter));
            Appointment appointment5 = new Appointment(activityRunning, trainerAthletic, LocalDateTime.parse("21/06/2018 20:00", formatter));
            Appointment appointment6 = new Appointment(activityPilates, trainerYogaGuy, LocalDateTime.parse("22/06/2018 20:00", formatter));
            Plan plan2 = new Plan(customer3, Arrays.asList(appointment4, appointment5, appointment6));
            planService.create(plan2);
        };
    }
}