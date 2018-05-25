package org.autobet.ioc;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import javax.sql.DataSource;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DatabaseConnectionModule_ProvideConnectionFactory
    implements Factory<DatabaseConnectionModule.DatabaseConnection> {
  private final DatabaseConnectionModule module;

  private final Provider<DataSource> dataSourceProvider;

  public DatabaseConnectionModule_ProvideConnectionFactory(
      DatabaseConnectionModule module, Provider<DataSource> dataSourceProvider) {
    assert module != null;
    this.module = module;
    assert dataSourceProvider != null;
    this.dataSourceProvider = dataSourceProvider;
  }

  @Override
  public DatabaseConnectionModule.DatabaseConnection get() {
    return Preconditions.checkNotNull(
        module.provideConnection(dataSourceProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<DatabaseConnectionModule.DatabaseConnection> create(
      DatabaseConnectionModule module, Provider<DataSource> dataSourceProvider) {
    return new DatabaseConnectionModule_ProvideConnectionFactory(module, dataSourceProvider);
  }
}
