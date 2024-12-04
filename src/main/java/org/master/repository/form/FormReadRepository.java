package org.master.repository.form;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.events.screen.ScreenCreatedEvent;
import org.master.events.screen.ScreenDeletedEvent;
import org.master.events.screen.ScreenUpdatedEvent;
import org.master.model.form.FormReadModel;
import org.master.model.screen.ScreenReadModel;
import org.master.repository.language.LanguageRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FormReadRepository implements PanacheRepository<FormReadModel> {
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

    public void update(ScreenUpdatedEvent screenUpdatedEvent ) {
        ScreenReadModel screenReadModel = em.find(ScreenReadModel.class, screenUpdatedEvent.getId());
        setScreenData(screenReadModel, screenUpdatedEvent.getData(),
                screenUpdatedEvent.getColumns(), screenUpdatedEvent.getRowHeights(), screenUpdatedEvent.getPrimaryLanguageId(),
                screenUpdatedEvent.getUrl(), screenUpdatedEvent.getRowMaxHeights(), screenUpdatedEvent.getLocals(),
                screenUpdatedEvent.getVariableInit(), screenUpdatedEvent.getVariableInitMapping(),
                screenUpdatedEvent.getBackground(), screenUpdatedEvent.getTitle());

        em.merge(screenReadModel);
    }

    public void delete(ScreenDeletedEvent screenDeletedEvent) {
        em.remove(em.find(ScreenReadModel.class, screenDeletedEvent.getId()));
    }

    private void setScreenData(ScreenReadModel screenReadModel, JsonNode data, Integer columns,
                               List<Integer> integers, UUID uuid, String url, List<Integer> integers2,
                               JsonNode locals, JsonNode jsonNode, JsonNode jsonNode2, JsonNode background,
                               String title) {
        screenReadModel.setData(data);
        screenReadModel.setColumns(columns);
        screenReadModel.setRowHeights(integers);
        screenReadModel.setPrimaryLanguage(languageRepository.findByUuid(uuid));
        screenReadModel.setUrl(url);
        screenReadModel.setRowMaxHeights(integers2);
        screenReadModel.setLocals(locals);
        screenReadModel.setVariableInit(jsonNode);
        screenReadModel.setVariableInitMapping(jsonNode2);
        screenReadModel.setBackground(background);
        screenReadModel.setTitle(title);
    }
}
