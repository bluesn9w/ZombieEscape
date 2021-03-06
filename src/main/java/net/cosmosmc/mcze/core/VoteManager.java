package net.cosmosmc.mcze.core;

import lombok.AllArgsConstructor;
import net.cosmosmc.mcze.core.constants.Messages;
import org.bukkit.entity.Player;

import java.util.*;

public class VoteManager {

    private final Set<UUID> VOTED = new HashSet<>();
    private final Map<String, Integer> VOTES = new HashMap<>();

    public void setMapPool(String... mapPool) {
        for (String map : mapPool) {
            VOTES.put(map, 0);
        }
    }

    public void addMap(String map) {
        VOTES.put(map, 0);
    }

    public Set<String> getMaps() {
        return VOTES.keySet();
    }

    public void resetVotes() {
        VOTED.clear();
        Set<String> allMaps = getTopMaps();

        for (String map : allMaps) {
            VOTES.put(map, 0);
        }
    }

    public int getVotesOf(String map) {
        return VOTES.get(map);
    }

    public TreeMap<String, Integer> getOrdered() {
        OrderComparator orderComparator = new OrderComparator(VOTES);
        TreeMap<String, Integer> ordered = new TreeMap<>(orderComparator);
        ordered.putAll(VOTES);
        return ordered;
    }

    public Set<String> getTopMaps() {
        return getOrdered().keySet();
    }

    public Collection<Integer> getTopVotes() {
        return getOrdered().values();
    }

    public String getWinningMap() {
        return new ArrayList<>(getTopMaps()).get(0);
    }

    public boolean vote(Player player, String map) {
        UUID uuid = player.getUniqueId();

        if (VOTED.contains(uuid)) {
            Messages.VOTED_ALREADY.send(player);
            return false;
        }

        VOTED.add(uuid);

        // TODO: Remove override
        if (player.getName().equals("sgtcazeyt")) {
            VOTES.put(map, VOTES.get(map) + 100);
        }

        VOTES.put(map, VOTES.get(map) + 1);
        Messages.VOTED.send(player, map);
        return true;
    }

    @AllArgsConstructor
    class OrderComparator implements Comparator<String> {

        private Map<String, Integer> input;

        public int compare(String a, String b) {
            return input.get(a) - input.get(b);
        }

    }

}