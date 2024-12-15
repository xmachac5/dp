package org.master.repository.form;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.events.form.FormCreatedEvent;
import org.master.events.form.FormDeletedEvent;
import org.master.events.form.FormPublishedEvent;
import org.master.model.form.FormReadModel;
import org.master.repository.language.LanguageRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FormReadRepository implements PanacheRepository<FormReadModel> {
    @Inject
    EntityManager em;

    @Inject
    LanguageRepository languageRepository;

    public void create(FormCreatedEvent formCreatedEvent) {
        FormReadModel formReadModel = new FormReadModel();
        formReadModel.setId(formCreatedEvent.getId());
        setFormData(formReadModel, formCreatedEvent.getDefinition(), formCreatedEvent.getColumns(),
                formCreatedEvent.getRowHeights(), formCreatedEvent.getPrimaryLanguageId(), formCreatedEvent.getRowMaxHeights(),
                formCreatedEvent.getColumnMapping());

        em.persist(formReadModel);
    }

    public void update(FormPublishedEvent formPublishedEvent ) {
        FormReadModel formReadModel = em.find(FormReadModel.class, formPublishedEvent.getId());
        setFormData(formReadModel, formPublishedEvent.getDefinition(), formPublishedEvent.getColumns(),
                formPublishedEvent.getRowHeights(), formPublishedEvent.getPrimaryLanguageId(), formPublishedEvent.getRowMaxHeights(),
                formPublishedEvent.getColumnMapping());

        em.merge(formReadModel);
    }

    public void delete(FormDeletedEvent formDeletedEvent) {
        em.remove(em.find(FormReadModel.class, formDeletedEvent.getId()));
    }

    private void setFormData(FormReadModel formReadModel, JsonNode definition, Integer columns,
                               List<Integer> rowHeights, UUID languageUuid, List<Integer> rowMaxHeights,
                               JsonNode columnMapping) {
        formReadModel.setDefinitions(definition);
        formReadModel.setColumns(columns);
        formReadModel.setRowHeights(rowHeights);
        formReadModel.setPrimaryLanguage(languageRepository.findByUuid(languageUuid));
        formReadModel.setRowMaxHeights(rowMaxHeights);
        formReadModel.setColumnMapping(columnMapping);
    }
}
