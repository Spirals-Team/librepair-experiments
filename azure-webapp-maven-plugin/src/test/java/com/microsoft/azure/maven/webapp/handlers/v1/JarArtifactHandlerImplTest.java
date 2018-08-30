/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.maven.webapp.handlers.v1;

import com.microsoft.azure.management.appservice.DeploymentSlot;
import com.microsoft.azure.maven.deploytarget.DeployTarget;
import com.microsoft.azure.maven.webapp.AbstractWebAppMojo;
import com.microsoft.azure.maven.webapp.deploytarget.DeploymentSlotDeployTarget;
import com.microsoft.azure.maven.webapp.deploytarget.WebAppDeployTarget;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JarArtifactHandlerImplTest {
    @Mock
    private AbstractWebAppMojo mojo;

    private JarArtifactHandlerImpl handler = null;

    private JarArtifactHandlerImpl handlerSpy = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        handler = new JarArtifactHandlerImpl(mojo);
        handlerSpy = spy(handler);
    }

    @Test
    public void publish() throws Exception {
        final DeployTarget deployTarget = new WebAppDeployTarget(this.mojo.getWebApp());
        doNothing().when(handlerSpy).publish(deployTarget);

        handlerSpy.publish(deployTarget);
        verify(handlerSpy).publish(deployTarget);
    }

    @Test
    public void publishToDeploymentSlot() throws Exception {
        final DeploymentSlot slot = mock(DeploymentSlot.class);
        final DeployTarget deployTarget = new DeploymentSlotDeployTarget(slot);

        doNothing().when(handlerSpy).publish(deployTarget);

        handlerSpy.publish(deployTarget);
        verify(handlerSpy).publish(deployTarget);
    }

    @Test
    public void prepareDeploymentFiles() throws IOException {

    }

    @Test
    public void generateWebConfigFile() {

    }

    @Test
    public void getJarFile() {
        doReturn("test.jar").when(mojo).getJarFile();
        assertEquals("test.jar", handlerSpy.getJarFile().getName());

        doReturn("").when(mojo).getJarFile();
        doReturn("").when(mojo).getBuildDirectoryAbsolutePath();
        final MavenProject project = mock(MavenProject.class);
        doReturn(project).when(mojo).getProject();
        final Build build = mock(Build.class);
        doReturn(build).when(project).getBuild();
        doReturn("test").when(build).getFinalName();
        assertEquals("test.jar", handlerSpy.getJarFile().getName());
    }

    @Test(expected = MojoExecutionException.class)
    public void assureJarFileExistedWhenFileExtWrong() throws MojoExecutionException {
        handlerSpy.assureJarFileExisted(new File("test.jar"));
    }

    @Test(expected = MojoExecutionException.class)
    public void assureJarFileExistedWhenFileNotExist() throws MojoExecutionException {
        final File fileMock = mock(File.class);

        doReturn("test.jar").when(fileMock).getName();
        doReturn(false).when(fileMock).exists();
        handlerSpy.assureJarFileExisted(fileMock);
    }

    @Test(expected = MojoExecutionException.class)
    public void assureJarFileExistedWhenIsNotAFile() throws MojoExecutionException {
        final File fileMock = mock(File.class);

        doReturn("test.jar").when(fileMock).getName();
        doReturn(false).when(fileMock).exists();
        doReturn(false).when(fileMock).isFile();
        handlerSpy.assureJarFileExisted(fileMock);
    }

    @Test
    public void assureJarFileExisted() throws MojoExecutionException {
        final File file = mock(File.class);
        doReturn("test.jar").when(file).getName();
        doReturn(true).when(file).exists();
        doReturn(true).when(file).isFile();

        handlerSpy.assureJarFileExisted(file);

        verify(file).getName();
        verify(file).exists();
        verify(file).isFile();
    }
}
