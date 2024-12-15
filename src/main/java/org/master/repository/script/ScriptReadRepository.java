package org.master.repository.script;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.events.script.ScriptCreatedEvent;
import org.master.events.script.ScriptDeletedEvent;
import org.master.events.script.ScriptPublishedEvent;
import org.master.model.screen.ScreenReadModel;
import org.master.model.script.ScriptReadModel;

@ApplicationScoped
public class ScriptReadRepository implements PanacheRepository<ScreenReadModel> {
    @Inject
    EntityManager em;

    public void create(ScriptCreatedEvent scriptCreatedEvent) {
        ScriptReadModel scriptReadModel = new ScriptReadModel();
        scriptReadModel.setId(scriptCreatedEvent.getId());
        scriptReadModel.setStructure(((ObjectNode)scriptCreatedEvent.getVariables()).put("code", scriptCreatedEvent.getCode()));
        em.persist(scriptReadModel);
    }

    public void update(ScriptPublishedEvent scriptPublishedEvent ) {
        ScriptReadModel scriptReadModel = em.find(ScriptReadModel.class, scriptPublishedEvent.getId());
        scriptReadModel.setStructure(((ObjectNode)scriptPublishedEvent.getVariables()).put("code", scriptPublishedEvent.getCode()));

        em.merge(scriptReadModel);
    }

    public void delete(ScriptDeletedEvent scriptDeletedEvent) {
        em.remove(em.find(ScriptReadModel.class, scriptDeletedEvent.getId()));
    }
}
