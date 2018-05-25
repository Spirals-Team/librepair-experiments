package org.autobet.ioc;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import org.autobet.ai.TeamRater;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AIModule_ProvideGoalBasedRaterFactory implements Factory<TeamRater> {
  private final AIModule module;

  public AIModule_ProvideGoalBasedRaterFactory(AIModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public TeamRater get() {
    return Preconditions.checkNotNull(
        module.provideGoalBasedRater(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<TeamRater> create(AIModule module) {
    return new AIModule_ProvideGoalBasedRaterFactory(module);
  }
}
