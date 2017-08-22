/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.application;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.function.Supplier;
import org.apache.commons.io.FilenameUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;
import org.sonar.process.ProcessProperties;
import org.sonar.process.Props;
import org.sonar.process.monitor.JavaCommand;
import org.sonar.process.monitor.Monitor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class AppTest {

  @Rule
  public TemporaryFolder temp = new TemporaryFolder();
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private final JavaCommand esCommand = mock(JavaCommand.class);
  private final JavaCommand webCommand = mock(JavaCommand.class);
  private final JavaCommand ceCommand = mock(JavaCommand.class);
  private final JavaCommandFactory javaCommandFactory = new JavaCommandFactory() {

    @Override
    public JavaCommand createESCommand(Props props, File homeDir) {
      return AppTest.this.esCommand;
    }

    @Override
    public JavaCommand createWebCommand(Props props, File homeDir) {
      return AppTest.this.webCommand;
    }

    @Override
    public JavaCommand createCeCommand(Props props, File homeDir) {
      return AppTest.this.ceCommand;
    }
  };
  private App.CheckFSConfigOnReload checkFsConfigOnReload = mock(App.CheckFSConfigOnReload.class);

  @Test
  public void starPath() throws IOException {
    File homeDir = temp.newFolder();
    String startPath = App.starPath(homeDir, "lib/search");
    assertThat(FilenameUtils.normalize(startPath, true))
      .endsWith("*")
      .startsWith(FilenameUtils.normalize(homeDir.getAbsolutePath(), true));
  }

  @Test
  public void start_all_processes_if_cluster_mode_is_disabled() throws Exception {
    Props props = initDefaultProps();
    Monitor monitor = mock(Monitor.class);
    Cluster cluster = mock(Cluster.class);
    App app = new App(props.rawProperties(), properties -> props, monitor, checkFsConfigOnReload, javaCommandFactory, cluster);
    app.start();

    ArgumentCaptor<Supplier<List<JavaCommand>>> argument = newJavaCommandArgumentCaptor();
    verify(monitor).start(argument.capture());
    assertThat(argument.getValue().get())
      .containsExactly(esCommand, webCommand, ceCommand);

    app.stopAsync();
    verify(monitor).stop();
  }

  @Test
  public void start_only_web_server_node_in_cluster() throws Exception {
    Props props = initDefaultProps();
    props.set(ProcessProperties.CLUSTER_ENABLED, "true");
    props.set(ProcessProperties.CLUSTER_CE_DISABLED, "true");
    props.set(ProcessProperties.CLUSTER_SEARCH_DISABLED, "true");

    List<JavaCommand> commands = start(props);

    assertThat(commands).containsOnly(webCommand);
  }

  @Test
  public void start_only_compute_engine_node_in_cluster() throws Exception {
    Props props = initDefaultProps();
    props.set(ProcessProperties.CLUSTER_ENABLED, "true");
    props.set(ProcessProperties.CLUSTER_WEB_DISABLED, "true");
    props.set(ProcessProperties.CLUSTER_SEARCH_DISABLED, "true");

    List<JavaCommand> commands = start(props);

    assertThat(commands).contains(ceCommand);
  }

  @Test
  public void start_only_elasticsearch_node_in_cluster() throws Exception {
    Props props = initDefaultProps();
    props.set(ProcessProperties.CLUSTER_ENABLED, "true");
    props.set(ProcessProperties.CLUSTER_WEB_DISABLED, "true");
    props.set(ProcessProperties.CLUSTER_CE_DISABLED, "true");

    List<JavaCommand> commands = start(props);

    assertThat(commands).containsOnly(esCommand);
  }

  @Test
  public void javaCommands_supplier_reloads_properties_and_ensure_filesystem_props_have_not_changed() throws Exception {
    // initial props is not cluster => all three processes must be created
    Props initialProps = initDefaultProps();
    // second props is cluster with only ES and CE enabled => only two processes must be created
    Props props = initDefaultProps();
    props.set(ProcessProperties.CLUSTER_ENABLED, "true");
    props.set(ProcessProperties.CLUSTER_WEB_DISABLED, "true");
    // setup an App that emulate reloading of props returning different configuration
    Iterator<Props> propsIterator = Arrays.asList(initialProps, props).iterator();
    Monitor monitor = mock(Monitor.class);
    Cluster cluster = mock(Cluster.class);
    App app = new App(initialProps.rawProperties(), properties -> propsIterator.next(), monitor, checkFsConfigOnReload, javaCommandFactory, cluster);
    // start App and capture the JavaCommand supplier it provides to the Monitor
    app.start();
    Supplier<List<JavaCommand>> supplier = captureJavaCommandsSupplier(monitor);

    // first call, consumes initial props
    assertThat(supplier.get()).containsExactly(esCommand, webCommand, ceCommand);
    verifyZeroInteractions(checkFsConfigOnReload);

    // second call, consumes second props
    assertThat(supplier.get()).containsExactly(esCommand, ceCommand);
    verify(checkFsConfigOnReload).accept(props);

    // third call will trigger error from iterator
    expectedException.expect(NoSuchElementException.class);
    supplier.get();
  }

  @Test
  public void javaCommands_supplier_propagate_errors_from_CheckFsConfigOnReload_instance() throws Exception {
    // initial props is not cluster => all three processes must be created
    Props initialProps = initDefaultProps();
    // second props is cluster with only ES and CE enabled => only two processes must be created
    Props props = initDefaultProps();
    props.set(ProcessProperties.CLUSTER_ENABLED, "true");
    props.set(ProcessProperties.CLUSTER_WEB_DISABLED, "true");
    // setup an App that emulate reloading of props returning different configuration
    Iterator<Props> propsIterator = Arrays.asList(initialProps, props).iterator();
    Monitor monitor = mock(Monitor.class);
    Cluster cluster = mock(Cluster.class);
    App app = new App(initialProps.rawProperties(), properties -> propsIterator.next(), monitor, checkFsConfigOnReload, javaCommandFactory, cluster);
    // start App and capture the JavaCommand supplier it provides to the Monitor
    app.start();
    Supplier<List<JavaCommand>> supplier = captureJavaCommandsSupplier(monitor);

    // first call, consumes initial props
    assertThat(supplier.get()).containsExactly(esCommand, webCommand, ceCommand);
    verifyZeroInteractions(checkFsConfigOnReload);

    // second call, consumes second props and propagates exception thrown by checkFsConfigOnReload
    IllegalStateException expected = new IllegalStateException("emulating change of FS properties is not supported exception");
    doThrow(expected).when(checkFsConfigOnReload).accept(props);
    try {
      supplier.get();
      fail("Supplier should have propagated exception from checkFsConfigOnReload");
    } catch (IllegalStateException e) {
      assertThat(e).isSameAs(expected);
    }
  }

  private Supplier<List<JavaCommand>> captureJavaCommandsSupplier(Monitor monitor) throws InterruptedException {
    ArgumentCaptor<Supplier<List<JavaCommand>>> argument = newJavaCommandArgumentCaptor();
    verify(monitor).start(argument.capture());

    return argument.getValue();
  }

  private Props initDefaultProps() throws IOException {
    Props props = new Props(new Properties());
    ProcessProperties.completeDefaults(props);
    props.set(ProcessProperties.PATH_HOME, temp.newFolder().getAbsolutePath());
    props.set(ProcessProperties.PATH_TEMP, temp.newFolder().getAbsolutePath());
    props.set(ProcessProperties.PATH_LOGS, temp.newFolder().getAbsolutePath());
    return props;
  }

  private List<JavaCommand> start(Props props) throws Exception {
    Monitor monitor = mock(Monitor.class);
    Cluster cluster = mock(Cluster.class);
    App app = new App(props.rawProperties(), properties -> props, monitor, checkFsConfigOnReload, javaCommandFactory, cluster);
    app.start();
    ArgumentCaptor<Supplier<List<JavaCommand>>> argument = newJavaCommandArgumentCaptor();
    verify(monitor).start(argument.capture());
    return argument.getValue().get();
  }

  private ArgumentCaptor<Supplier<List<JavaCommand>>> newJavaCommandArgumentCaptor() {
    Class<Supplier<List<JavaCommand>>> listClass = (Class<Supplier<List<JavaCommand>>>) (Class) List.class;
    return ArgumentCaptor.forClass(listClass);
  }

}
