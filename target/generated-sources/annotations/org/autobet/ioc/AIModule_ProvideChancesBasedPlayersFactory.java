package org.autobet.ioc;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Set;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.autobet.ai.Player;
import org.autobet.ai.TeamRater;
import org.autobet.ai.TeamRaterStatsCollector;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AIModule_ProvideChancesBasedPlayersFactory implements Factory<Set<Player>> {
  private final AIModule module;

  private final Provider<TeamRaterStatsCollector> statsCollectorProvider;

  private final Provider<Set<TeamRater>> teamRatersProvider;

  public AIModule_ProvideChancesBasedPlayersFactory(
      AIModule module,
      Provider<TeamRaterStatsCollector> statsCollectorProvider,
      Provider<Set<TeamRater>> teamRatersProvider) {
    assert module != null;
    this.module = module;
    assert statsCollectorProvider != null;
    this.statsCollectorProvider = statsCollectorProvider;
    assert teamRatersProvider != null;
    this.teamRatersProvider = teamRatersProvider;
  }

  @Override
  public Set<Player> get() {
    return Preconditions.checkNotNull(
        module.provideChancesBasedPlayers(statsCollectorProvider.get(), teamRatersProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Set<Player>> create(
      AIModule module,
      Provider<TeamRaterStatsCollector> statsCollectorProvider,
      Provider<Set<TeamRater>> teamRatersProvider) {
    return new AIModule_ProvideChancesBasedPlayersFactory(
        module, statsCollectorProvider, teamRatersProvider);
  }
}
