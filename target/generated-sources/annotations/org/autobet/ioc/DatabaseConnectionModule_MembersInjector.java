package org.autobet.ioc;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import javax.sql.DataSource;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DatabaseConnectionModule_MembersInjector
    implements MembersInjector<DatabaseConnectionModule> {
  private final Provider<DataSource> dataSourceProvider;

  public DatabaseConnectionModule_MembersInjector(Provider<DataSource> dataSourceProvider) {
    assert dataSourceProvider != null;
    this.dataSourceProvider = dataSourceProvider;
  }

  public static MembersInjector<DatabaseConnectionModule> create(
      Provider<DataSource> dataSourceProvider) {
    return new DatabaseConnectionModule_MembersInjector(dataSourceProvider);
  }

  @Override
  public void injectMembers(DatabaseConnectionModule instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.provideConnection(dataSourceProvider.get());
  }
}
