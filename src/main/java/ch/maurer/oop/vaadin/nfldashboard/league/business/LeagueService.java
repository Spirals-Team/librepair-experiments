package ch.maurer.oop.vaadin.nfldashboard.league.business;

import ch.maurer.oop.vaadin.nfldashboard.common.business.LocalDateToStringConverter;
import ch.maurer.oop.vaadin.nfldashboard.common.db.StaticData;
import ch.maurer.oop.vaadin.nfldashboard.league.db.League;
import ch.maurer.oop.vaadin.nfldashboard.player.business.PlayerService;
import ch.maurer.oop.vaadin.nfldashboard.player.db.Player;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class LeagueService {

    private Map<Long, League> leagues = new HashMap<>();
    private AtomicLong nextId = new AtomicLong(0);

    private LeagueService() {
    }

    public static LeagueService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<League> findLeague(String filter) {
        String normalizedFilter = filter.toLowerCase();

        return leagues.values().stream().filter(league -> filterTextOf(league).contains(normalizedFilter))
                .sorted((r1, r2) -> r2.getId().compareTo(r1.getId()))
                .collect(Collectors.toList());
    }

    private String filterTextOf(League league) {
        LocalDateToStringConverter dateConverter = new LocalDateToStringConverter();
        String filterableText = Stream
                .of(league.getName(),
                        league.getPlayer() == null ? StaticData.UNDEFINED
                                : league.getPlayer().getName(),
                        String.valueOf(league.getScore()),
                        String.valueOf(league.getCount()),
                        dateConverter.toPresentation(league.getDate()))
                .collect(Collectors.joining("\t"));
        return filterableText.toLowerCase();
    }

    public boolean deleteLeague(League league) {
        return leagues.remove(league.getId()) != null;
    }

    public void saveLeague(League dto) {
        League entity = leagues.get(dto.getId());
        Player player = dto.getPlayer();

        if (player != null) {
            player = PlayerService.getInstance().findPlayerById(player.getId()).orElse(null);
        }
        if (entity == null) {
            entity = new League(dto);
            if (dto.getId() == null) {
                entity.setId(nextId.incrementAndGet());
            }
            leagues.put(entity.getId(), entity);
        } else {
            entity.setScore(dto.getScore());
            entity.setName(dto.getName());
            entity.setDate(dto.getDate());
            entity.setCount(dto.getCount());
        }
        entity.setPlayer(player);
    }

    private static final class SingletonHolder {
        static final LeagueService INSTANCE = createDemoLeagueService();

        private SingletonHolder() {
        }

        private static LeagueService createDemoLeagueService() {
            final LeagueService leagueService = new LeagueService();
            Random r = new Random();
            int leagueCount = 20 + r.nextInt(30);
            List<Map.Entry<String, String>> beverages = new ArrayList<>(StaticData.BEVERAGES.entrySet());

            for (int i = 0; i < leagueCount; i++) {
                League league = new League();
                Map.Entry<String, String> beverage = beverages.get(r.nextInt(StaticData.BEVERAGES.size()));
                Player player = PlayerService.getInstance().findPlayerOrThrow(beverage.getValue());

                league.setName(beverage.getKey());
                LocalDate testDay = getRandomDate();
                league.setDate(testDay);
                league.setScore(1 + r.nextInt(5));
                league.setPlayer(player);
                league.setCount(1 + r.nextInt(15));
                leagueService.saveLeague(league);
            }

            return leagueService;
        }

        private static LocalDate getRandomDate() {
            long minDay = LocalDate.of(1930, 1, 1).toEpochDay();
            long maxDay = LocalDate.now().toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            return LocalDate.ofEpochDay(randomDay);
        }
    }
}
