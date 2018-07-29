/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.autobet.ioc;

import dagger.Component;
import org.autobet.ai.Player;
import org.autobet.ai.PlayerEvaluator;
import org.autobet.ai.TeamRater;
import org.autobet.ai.TeamRaterStatsCollector;

import javax.inject.Singleton;
import javax.sql.DataSource;

import java.util.Map;
import java.util.Set;

import static org.autobet.ImmutableCollectors.toImmutableMap;

@Singleton
@Component(modules = {DataSourceModule.class, DatabaseConnectionModule.class, AIModule.class})
public interface MainComponent
{
    DatabaseConnectionModule.DatabaseConnection connectToDatabase();

    DataSource getDataSource();

    Set<Player> getPlayers();

    default Map<String, Player> getPlayersMap()
    {
        return getPlayers().stream()
                .collect(toImmutableMap(Player::getName));
    }

    Set<TeamRater> getTeamRaters();

    default Map<String, TeamRater> getTeamRaterMap()
    {
        return getTeamRaters().stream()
                .collect(toImmutableMap(TeamRater::getName));
    }

    PlayerEvaluator getPlayerEvaluator();

    TeamRaterStatsCollector getStatsCollector();
}
