package org.master.repository.screen;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.events.screen.ScreenCreatedEvent;
import org.master.events.screen.ScreenDeletedEvent;
import org.master.events.screen.ScreenPublishedEvent;
import org.master.model.screen.ScreenReadModel;
import org.master.repository.language.LanguageRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ScreenReadRepository implements PanacheRepository<ScreenReadModel> {
    @Inject
    EntityManager em;

    @Inject
    LanguageRepository languageRepository;

    public void create(ScreenCreatedEvent screenCreatedEvent) {
        ScreenReadModel screenReadModel = new ScreenReadModel();
        screenReadModel.setId(screenCreatedEvent.getId());
        setScreenData(screenReadModel, screenCreatedEvent.getData(), screenCreatedEvent.getColumns(),
                screenCreatedEvent.getRowHeights(), screenCreatedEvent.getPrimaryLanguageId(), screenCreatedEvent.getUrl(),
                screenCreatedEvent.getRowMaxHeights(), screenCreatedEvent.getLocals(), screenCreatedEvent.getVariableInit(),
                screenCreatedEvent.getVariableInitMapping(), screenCreatedEvent.getBackground(), screenCreatedEvent.getTitle());

        em.persist(screenReadModel);
    }

    public void update(ScreenPublishedEvent screenPublishedEvent ) {
        ScreenReadModel screenReadModel = em.find(ScreenReadModel.class, screenPublishedEvent.getId());
        setScreenData(screenReadModel, screenPublishedEvent.getData(),
                screenPublishedEvent.getColumns(), screenPublishedEvent.getRowHeights(), screenPublishedEvent.getPrimaryLanguageId(),
                screenPublishedEvent.getUrl(), screenPublishedEvent.getRowMaxHeights(), screenPublishedEvent.getLocals(),
                screenPublishedEvent.getVariableInit(), screenPublishedEvent.getVariableInitMapping(),
                screenPublishedEvent.getBackground(), screenPublishedEvent.getTitle());

        em.merge(screenReadModel);
    }

    public void delete(ScreenDeletedEvent screenDeletedEvent) {
        em.remove(em.find(ScreenReadModel.class, screenDeletedEvent.getId()));
    }

    private void setScreenData(ScreenReadModel screenReadModel, JsonNode data, Integer columns,
                               List<Integer> rowHeights, UUID languageUuid, String url, List<Integer> rowMaxHeights,
                               JsonNode locals, JsonNode variableInit, JsonNode variableInitMapping, JsonNode background,
                               String title) {
        screenReadModel.setData(data);
        screenReadModel.setColumns(columns);
        screenReadModel.setRowHeights(rowHeights);
        screenReadModel.setPrimaryLanguage(languageRepository.findByUuid(languageUuid));
        screenReadModel.setUrl(url);
        screenReadModel.setRowMaxHeights(rowMaxHeights);
        screenReadModel.setLocals(locals);
        screenReadModel.setVariableInit(variableInit);
        screenReadModel.setVariableInitMapping(variableInitMapping);
        screenReadModel.setBackground(background);
        screenReadModel.setTitle(title);
    }
}
