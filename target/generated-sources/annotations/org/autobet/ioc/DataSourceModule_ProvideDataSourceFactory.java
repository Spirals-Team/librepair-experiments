package org.autobet.ioc;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.sql.DataSource;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DataSourceModule_ProvideDataSourceFactory implements Factory<DataSource> {
  private final DataSourceModule module;

  public DataSourceModule_ProvideDataSourceFactory(DataSourceModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public DataSource get() {
    return Preconditions.checkNotNull(
        module.provideDataSource(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<DataSource> create(DataSourceModule module) {
    return new DataSourceModule_ProvideDataSourceFactory(module);
  }
}
