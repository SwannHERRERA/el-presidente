package org.esgi.el_presidente.core.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.esgi.el_presidente.App;
import org.esgi.el_presidente.core.season.Season;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The Event manager.
 */
public class EventManager {
    private final List<Event> events;
    private int step;
    private final Random random;

    /**
     * @param events the event list
     */
    public EventManager(@JsonProperty("events") List<Event> events) {
        this.events = Objects.requireNonNullElseGet(events, ArrayList::new);
        step = 0;
        random = new Random();
    }

    /**
     * @param event the event
     * @return the current event manager
     */
    public EventManager addEvent(Event event) {
        events.add(event);
        return this;
    }

    public Event getRandomEvent() {
        if (events.size() == 0) {
            return null;
        }
        return events.get(getRandomIndex(events.size()));
    }

    public Event getRandomEventBySeason(Season season) {
        List<Event> events = this.events.stream().filter(e -> e.getSeason() == season || e.getSeason() == null)
                .collect(Collectors.toList());
        return events.get(getRandomIndex(events.size()));
    }

    public int getRandomIndex(int maxExcludedIndex) {
        return random.nextInt(maxExcludedIndex);
    }

    public Event getNextEvent() {
        if (events.size() == 0) {
            return null;
        }
        Event event = events.get(step);
        step = (step + 1) % events.size();
        return event;
    }

    public void resetStep() {
        step = 0;
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getStep() {
        return step;
    }

    /**
     * Gets event manager from json. TODO put this mapper to scenario in the future
     *
     * @param ressourceJsonPath the ressource json path
     * @return the event manager from json
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IOException              the io exception
     */
    public static EventManager getEventManagerFromJson(String ressourceJsonPath)
            throws IllegalArgumentException, IOException {
        String inputString = App.readFileFromRessource(ressourceJsonPath);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputString, EventManager.class);
    }

}
