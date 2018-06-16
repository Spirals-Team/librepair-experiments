package academia.business.activity;

import academia.domain.AccountType;
import academia.model.Account;
import academia.model.Activity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ValidationException;
import java.util.Arrays;

public class ActivityUnitTests {

    protected ActivityServiceImpl activityServiceImpl;

    protected ActivityRepository activityRepository;

    @Before
    public void setup() {
        activityServiceImpl = new ActivityServiceImpl( );
        activityRepository  = Mockito.mock(ActivityRepository.class);

        // injeta dependências
        ReflectionTestUtils.setField(activityServiceImpl, "activityRepository", activityRepository);
    }

    @Test
    public void create_activity_all_attributes_valid() {

        Activity activity = new Activity( );
        Account trainer = new Account( );
        activity.setName( "PF" );
        activity.setDescription( "Preparador Físico" );
        trainer.setType( AccountType.TRAINER );
        activity.setTrainers(Arrays.asList(trainer));

        try {
            activityServiceImpl.create(activity);
        } catch ( Exception e ){
            Assert.fail("Não deve falhar pois tem todos atributos");
        }
    }

    @Test
    public void name_is_mandatory( ){
        Activity activity = new Activity( );
        Account trainer = new Account( );
        activity.setDescription( "Preparador Físico" );
        trainer.setType( AccountType.TRAINER );
        activity.setTrainers(Arrays.asList(trainer));

        try {
            activityServiceImpl.create(activity);
            Assert.fail("Deve cair no catch, pois falhou");
        } catch ( ValidationException e ){
            Assert.assertEquals(e.getMessage(), "Atividade deve ter um nome");
        }
    }

    @Test
    public void description_is_mandatory( ){
        Activity activity = new Activity( );
        Account trainer = new Account( );
        activity.setName( "PF" );
        trainer.setType( AccountType.TRAINER );
        activity.setTrainers(Arrays.asList(trainer));

        try {
            activityServiceImpl.create(activity);
            Assert.fail("Deve cair no catch, pois falhou");
        } catch ( ValidationException e ){
            Assert.assertEquals(e.getMessage(), "Atividade deve ter uma descrição");
        }
    }

    @Test
    public void activity_is_mandatory( ){
        Activity activity = new Activity( );
        Account trainer = new Account( );
        activity.setName( "PF" );
        activity.setDescription( "Preparador Físico" );
        trainer.setType( AccountType.TRAINER );

        try {
            activityServiceImpl.create(activity);
            Assert.fail("Deve cair no catch, pois falhou");
        } catch ( ValidationException e ){
            Assert.assertEquals(e.getMessage(), "Atividade deve ter um treinador");
        }
    }

    @Test
    public void trainer_is_mandatory( ){
        Activity activity = new Activity( );
        Account trainer = new Account( );
        activity.setName( "PF" );
        activity.setDescription( "Preparador Físico" );
        trainer.setName( "Jonas" );
        activity.setTrainers(Arrays.asList(trainer));
        trainer.setType( AccountType.CUSTOMER);

        try {
            activityServiceImpl.create(activity);
            Assert.fail("Deve cair no catch, pois falhou");
        } catch ( ValidationException e ){
            Assert.assertEquals(e.getMessage(), "Usuário atrelado a atividade deve ser um treinador");
        }
    }


}
