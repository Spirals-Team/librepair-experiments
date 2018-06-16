package academia;

import academia.business.account.AccountRepository;
import academia.business.account.AccountService;
import academia.business.activity.ActivityRepository;
import academia.business.activity.ActivityService;
import academia.business.appointment.AppointmentService;
import academia.business.restriction.RestrictionRepository;
import academia.business.restriction.RestrictionService;
import academia.domain.AccountType;
import academia.model.Account;
import academia.model.Activity;
import academia.model.Restriction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

            AppointmentService appointmentService) {
        return (args) -> {
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

            String customerName = "customer";
            Account customer = accountRepository.findByUsername(customerName);
            if (customer == null) {
                List<Restriction> restrictions = Arrays.asList(restrictionCardiaco, restrictionJoelho);
                customer = new Account("Cliente da Silva", customerName, customerName, AccountType.CUSTOMER, restrictions);
                customer = accountService.create(customer);
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
                        Arrays.asList(trainerBodyBuilder), Arrays.asList(restrictionColuna));
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

//            Appointment appointment = new Appointment();
//            appointment.setActivity(activity);
//            appointment.setDate(new Date(2018, 1, 1));
//            appointment.setVagas(10);
//            appointmentService.create(appointment);
        };
    }
}