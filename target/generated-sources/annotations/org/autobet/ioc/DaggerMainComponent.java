package org.autobet.ioc;

import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.SetFactory;
import java.util.Set;
import javax.annotation.Generated;
import javax.inject.Provider;
import javax.sql.DataSource;
import org.autobet.ai.Player;
import org.autobet.ai.PlayerEvaluator;
import org.autobet.ai.TeamRater;
import org.autobet.ai.TeamRaterStatsCollector;
import org.autobet.util.GamesProcessorDriver;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerMainComponent implements MainComponent {
  private Provider<DataSource> provideDataSourceProvider;

  private Provider<DatabaseConnectionModule.DatabaseConnection> provideConnectionProvider;

  private Provider<Player> provideRandomPlayerProvider;

  private Provider<Player> provideLowBetPlayerProvider;

  private Provider<GamesProcessorDriver> provideDriverProvider;

  private Provider<TeamRaterStatsCollector> provideTeamRaterStatsCollectorProvider;

  private Provider<TeamRater> provideGoalBasedRaterProvider;

  private Provider<Set<TeamRater>> setOfTeamRaterProvider;

  private Provider<Set<Player>> provideChancesBasedPlayersProvider;

  private Provider<Set<Player>> setOfPlayerProvider;

  private Provider<PlayerEvaluator> providePlayerEvaluatorProvider;

  private DaggerMainComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static MainComponent create() {
    return builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.provideDataSourceProvider =
        DoubleCheck.provider(
            DataSourceModule_ProvideDataSourceFactory.create(builder.dataSourceModule));

    this.provideConnectionProvider =
        DoubleCheck.provider(
            DatabaseConnectionModule_ProvideConnectionFactory.create(
                builder.databaseConnectionModule, provideDataSourceProvider));

    this.provideRandomPlayerProvider = AIModule_ProvideRandomPlayerFactory.create(builder.aIModule);

    this.provideLowBetPlayerProvider = AIModule_ProvideLowBetPlayerFactory.create(builder.aIModule);

    this.provideDriverProvider =
        AIModule_ProvideDriverFactory.create(builder.aIModule, provideDataSourceProvider);

    this.provideTeamRaterStatsCollectorProvider =
        AIModule_ProvideTeamRaterStatsCollectorFactory.create(
            builder.aIModule, provideDriverProvider);

    this.provideGoalBasedRaterProvider =
        AIModule_ProvideGoalBasedRaterFactory.create(builder.aIModule);

    this.setOfTeamRaterProvider =
        SetFactory.<TeamRater>builder(1, 0).addProvider(provideGoalBasedRaterProvider).build();

    this.provideChancesBasedPlayersProvider =
        AIModule_ProvideChancesBasedPlayersFactory.create(
            builder.aIModule, provideTeamRaterStatsCollectorProvider, setOfTeamRaterProvider);

    this.setOfPlayerProvider =
        SetFactory.<Player>builder(2, 1)
            .addProvider(provideRandomPlayerProvider)
            .addProvider(provideLowBetPlayerProvider)
            .addCollectionProvider(provideChancesBasedPlayersProvider)
            .build();

    this.providePlayerEvaluatorProvider =
        AIModule_ProvidePlayerEvaluatorFactory.create(builder.aIModule, provideDriverProvider);
  }

  @Override
  public DatabaseConnectionModule.DatabaseConnection connectToDatabase() {
    return provideConnectionProvider.get();
  }

  @Override
  public DataSource getDataSource() {
    return provideDataSourceProvider.get();
  }

  @Override
  public Set<Player> getPlayers() {
    return setOfPlayerProvider.get();
  }

  @Override
  public Set<TeamRater> getTeamRaters() {
    return setOfTeamRaterProvider.get();
  }

  @Override
  public PlayerEvaluator getPlayerEvaluator() {
    return providePlayerEvaluatorProvider.get();
  }

  @Override
  public TeamRaterStatsCollector getStatsCollector() {
    return provideTeamRaterStatsCollectorProvider.get();
  }

  public static final class Builder {
    private DataSourceModule dataSourceModule;

    private DatabaseConnectionModule databaseConnectionModule;

    private AIModule aIModule;

    private Builder() {}

    public MainComponent build() {
      if (dataSourceModule == null) {
        this.dataSourceModule = new DataSourceModule();
      }
      if (databaseConnectionModule == null) {
        this.databaseConnectionModule = new DatabaseConnectionModule();
      }
      if (aIModule == null) {
        this.aIModule = new AIModule();
      }
      return new DaggerMainComponent(this);
    }

    public Builder dataSourceModule(DataSourceModule dataSourceModule) {
      this.dataSourceModule = Preconditions.checkNotNull(dataSourceModule);
      return this;
    }

    public Builder databaseConnectionModule(DatabaseConnectionModule databaseConnectionModule) {
      this.databaseConnectionModule = Preconditions.checkNotNull(databaseConnectionModule);
      return this;
    }

    public Builder aIModule(AIModule aIModule) {
      this.aIModule = Preconditions.checkNotNull(aIModule);
      return this;
    }
  }
}
