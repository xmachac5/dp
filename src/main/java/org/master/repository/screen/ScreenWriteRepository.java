package org.master.repository.screen;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.command.screen.commands.CreateScreenCommand;
import org.master.command.screen.commands.DeleteScreenCommand;
import org.master.command.screen.commands.UpdateScreenCommand;
import org.master.domain.screen.ScreenAggregate;
import org.master.events.BaseEvent;
import org.master.events.EventDeserializer;
import org.master.model.Event;
import org.master.model.screen.ScreenWriteModel;
import org.master.repository.language.LanguageRepository;
import org.master.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class ScreenWriteRepository implements PanacheRepository<ScreenWriteModel> {

    @Inject
    EntityManager em;

    @Inject
    EventDeserializer eventDeserializer;

    @Inject
    LanguageRepository languageRepository;

    @Inject
    UserRepository userRepository;

    public void create(UUID id, CreateScreenCommand createScreenCommand) {
        ScreenWriteModel screenWriteModel = new ScreenWriteModel();
        screenWriteModel.setId(id);
        setScreenData(screenWriteModel, createScreenCommand.data(), createScreenCommand.name(),
                createScreenCommand.columns(), createScreenCommand.rowHeights(),
                createScreenCommand.primaryLanguageId(), createScreenCommand.url(), createScreenCommand.rowMaxHeights(),
                createScreenCommand.locals(), createScreenCommand.variableInit(),
                createScreenCommand.variableInitMapping(), createScreenCommand.background(), createScreenCommand.title());

        em.persist(screenWriteModel);
    }

    public void update(UpdateScreenCommand updateScreenCommand ) {
        ScreenWriteModel screenWriteModel = em.find(ScreenWriteModel.class, updateScreenCommand.id());
        screenWriteModel.setUpdatedAt(LocalDateTime.now());
        screenWriteModel.setUpdatedBy(userRepository.getCurrentUser());
        setScreenData(screenWriteModel, updateScreenCommand.data(), updateScreenCommand.name(),
                updateScreenCommand.columns(), updateScreenCommand.rowHeights(),
                updateScreenCommand.primaryLanguageId(), updateScreenCommand.url(),
                updateScreenCommand.rowMaxHeights(), updateScreenCommand.locals(), updateScreenCommand.variableInit(),
                updateScreenCommand.variableInitMapping(), updateScreenCommand.background(),
                updateScreenCommand.title());

        em.merge(screenWriteModel);
    }

    public void delete(DeleteScreenCommand deleteScreenCommand) {
        ScreenWriteModel screenWriteModel = em.find(ScreenWriteModel.class, deleteScreenCommand.uuid());
        screenWriteModel.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());
    }

    private void setScreenData(ScreenWriteModel screenWriteModel, JsonNode data, String name, Integer columns,
                               List<Integer> rowHeights, UUID languageUuid, String url, List<Integer> rowMaxHeights,
                               JsonNode locals, JsonNode variableInit, JsonNode variableInitMapping, JsonNode background,
                               String title) {
        screenWriteModel.setData(data);
        screenWriteModel.setName(name);
        screenWriteModel.setColumns(columns);
        screenWriteModel.setRowHeights(rowHeights);
        screenWriteModel.setPrimaryLanguage(languageRepository.findByUuid(languageUuid));
        screenWriteModel.setUrl(url);
        screenWriteModel.setRowMaxHeights(rowMaxHeights);
        screenWriteModel.setLocals(locals);
        screenWriteModel.setVariableInit(variableInit);
        screenWriteModel.setVariableInitMapping(variableInitMapping);
        screenWriteModel.setBackground(background);
        screenWriteModel.setTitle(title);
    }

    public ScreenWriteModel findByUuid(UUID uuid) {
        return em.find(ScreenWriteModel.class, uuid);
    }

    public ScreenAggregate load(UUID aggregateId) {
        // Fetch events from the database
        List<Event> storedEvents = em.createQuery("SELECT e FROM Event e WHERE e.aggregateId = :id ORDER BY e.timestamp ASC", Event.class)
                .setParameter("id", aggregateId)
                .getResultList();

        if (storedEvents.isEmpty()) {
            throw new IllegalStateException("No events found for aggregate ID: " + aggregateId);
        }

        // Rehydrate the ScreenAggregate
        ScreenAggregate aggregate = new ScreenAggregate(aggregateId);
        for (Event storedEvent : storedEvents) {
            // Deserialize payload into a BaseEvent or its subclass
            BaseEvent event = eventDeserializer.deserialize(storedEvent.getPayload());
            aggregate.apply(event); // Apply the event to rebuild state
        }
        return aggregate;
    }
}
