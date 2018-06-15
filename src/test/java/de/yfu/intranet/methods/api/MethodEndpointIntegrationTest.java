package de.yfu.intranet.methods.api;


import de.yfu.intranet.methods.api.resources.MethodResource;
import de.yfu.intranet.methods.data.domain.*;
import de.yfu.intranet.methods.exceptions.MethodException;
import de.yfu.intranet.methods.exceptions.UserException;
import de.yfu.intranet.methods.api.resources.mapper.MethodMapper;
import de.yfu.intranet.methods.service.MethodService;
import de.yfu.intranet.methods.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static de.yfu.intranet.methods.util.MethodObjectFactory.anyMethod;
import static de.yfu.intranet.methods.util.MethodObjectFactory.anyMethodLevel;
import static de.yfu.intranet.methods.util.MethodObjectFactory.anyMethodType;

import static de.yfu.intranet.methods.util.UserObjectFactory.anyEditor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MethodEndpointIntegrationTest {

    private final MethodLevel METHOD_LEVEL = anyMethodLevel();
    private final MethodType METHOD_TYPE = anyMethodType();
    private final User USER = anyEditor();

    @Mock
    private MethodService methodService;
    @Mock
    private UserService userService;
    @Autowired
    private MethodController methodController;
    @Autowired
    private MethodMapper methodMapper;


    @Before
    public void setUp() {
        methodController = new MethodController(methodService,methodMapper,userService);
    }

    @Test
    public void createMethod_returnsCreatedMethod_ifServiceReturnsCreatedMethod() throws UserException, MethodException {
        Method anyMethod = anyMethod(USER);
        MethodResource anyMethodResource = methodMapper.mapFromDataObject(anyMethod);
        anyMethodResource.setId(null);
        anyMethodResource.getAttachments().iterator().next().setId(null);

        when(methodService.createMethod(any(Method.class))).thenReturn(anyMethod);

        ResponseEntity<MethodResource> response = methodController.createMethod(USER.getId(), anyMethodResource);
        Method createdMethod = methodMapper.mapToDataObject(response.getBody());

        verify(methodService).createMethod(any(Method.class));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createdMethod.getId()).isNotNull();
        assertEqualMethod(anyMethod, createdMethod);
    }

    @Test
    public void getMethod_returnsMethod_ifMethodWithGivenIdExists() throws MethodException {
        Method anyMethod = anyMethod(USER);
        when(methodService.getMethod(USER.getId(), anyMethod.getId())).thenReturn(anyMethod);

        MethodResource response = methodController.getMethod(anyMethod.getId(), USER.getId());
        Method method = methodMapper.mapToDataObject(response);
        verify(methodService).getMethod(USER.getId(), anyMethod.getId());
        assertThat(anyMethod).isEqualToComparingFieldByField(method);
    }

    private void assertEqualMethod(Method actual, Method other) {
        assertThat(other.getContent()).isEqualTo(actual.getContent());
        assertThat(other.getCreatedBy()).isEqualTo(USER);
        assertEqualAttachment(
                other.getAttachments().iterator().next(),
                actual.getAttachments().iterator().next());
        assertEqualTypeLevelGoal(other, actual);
    }

    private void assertEqualAttachment(Attachment actual, Attachment other) {
        assertThat(actual.getCreatedAt()).isEqualTo(other.getCreatedAt());
        assertThat(actual.getContent()).isEqualTo(other.getContent());
        assertThat(actual.getCreatedBy()).isEqualTo(other.getCreatedBy());
    }

    private void assertEqualTypeLevelGoal(Method actual, Method other) {
        assertThat(actual.getMethodTypes()).isEqualTo(other.getMethodTypes());
        assertThat(actual.getMethodLevels()).isEqualTo(other.getMethodLevels());
        assertThat(actual.getSeminarGoals()).isEqualTo(other.getSeminarGoals());
    }
}
