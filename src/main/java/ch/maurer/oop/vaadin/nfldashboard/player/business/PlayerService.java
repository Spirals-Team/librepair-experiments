package ch.maurer.oop.vaadin.nfldashboard.player.business;

import ch.maurer.oop.vaadin.nfldashboard.common.db.StaticData;
import ch.maurer.oop.vaadin.nfldashboard.player.db.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public final class PlayerService {

    private Map<Long, Player> players = new HashMap<>();
    private AtomicLong nextId = new AtomicLong(0);

    private PlayerService() {
    }

    public static PlayerService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Player> findPlayers(String filter) {
        String normalizedFilter = filter.toLowerCase();

        return players.values().stream()
                .filter(c -> c.getName().toLowerCase().contains(normalizedFilter))
                .map(Player::new)
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());
    }

    public Optional<Player> findPlayerByName(String name) {
        List<Player> playersMatching = findPlayers(name);

        if (playersMatching.isEmpty()) {
            return Optional.empty();
        }
        if (playersMatching.size() > 1) {
            throw new IllegalStateException("Player " + name + " is ambiguous");
        }
        return Optional.of(playersMatching.get(0));
    }

    public Player findPlayerOrThrow(String name) {
        return findPlayerByName(name).orElseThrow(() -> new IllegalStateException("Player " + name + " does not exist"));
    }

    public Optional<Player> findPlayerById(Long id) {
        Player player = players.get(id);
        return Optional.ofNullable(player);
    }

    public boolean deletePlayer(Player player) {
        return players.remove(player.getId()) != null;
    }

    public void savePlayer(Player dto) {
        Player entity = players.get(dto.getId());

        if (entity == null) {
            entity = new Player(dto);
            if (dto.getId() == null) {
                entity.setId(nextId.incrementAndGet());
            }
            players.put(entity.getId(), entity);
        } else {
            entity.setName(dto.getName());
        }
    }

    private static final class SingletonHolder {
        static final PlayerService INSTANCE = createDemoPlayerService();

        private SingletonHolder() {
        }

        private static PlayerService createDemoPlayerService() {
            PlayerService playerService = new PlayerService();
            Set<String> playerNames = new LinkedHashSet<>(StaticData.BEVERAGES.values());

            playerNames.forEach(name -> playerService.savePlayer(new Player(name)));

            return playerService;
        }
    }

}
