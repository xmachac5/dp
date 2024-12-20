package org.master.repository.form;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.command.dataObject.commands.DeleteDataObjectCommand;
import org.master.command.form.commands.CreateFormCommand;
import org.master.command.form.commands.DeleteFormCommand;
import org.master.command.form.commands.DeleteFormWithDOCommand;
import org.master.command.form.commands.UpdateFormCommand;
import org.master.model.dataObject.DataObjectsWriteModel;
import org.master.model.form.FormVersionWriteModel;
import org.master.model.form.FormWriteModel;
import org.master.repository.dataObject.DataObjectWriteRepository;
import org.master.repository.language.LanguageRepository;
import org.master.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FormWriteRepository implements PanacheRepository<FormWriteModel> {
    @Inject
    EntityManager em;

    @Inject
    LanguageRepository languageRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    DataObjectWriteRepository dataObjectWriteRepository;

    public void create(UUID id, CreateFormCommand createFormCommands) {
        FormWriteModel formWriteModel = new FormWriteModel();
        formWriteModel.setId(id);
        formWriteModel.setName(createFormCommands.name());
        FormVersionWriteModel formVersionWriteModel = createFormVersion(formWriteModel, createFormCommands.definition(), createFormCommands.columns(),
                createFormCommands.rowHeights(), createFormCommands.primaryLanguageId(), createFormCommands.rowMaxHeights(),
                createFormCommands.columnMapping(), 1, createFormCommands.dataObjectsWriteModel());

        em.persist(formWriteModel);
        em.persist(formVersionWriteModel);
        formWriteModel.setLatestVersionUuid(formVersionWriteModel);
        em.merge(formWriteModel);
    }

    public void update(UpdateFormCommand updateFormCommand ) {
        FormWriteModel formWriteModel = em.find(FormWriteModel.class, updateFormCommand.id());
        FormVersionWriteModel latestVersion = em.find(FormVersionWriteModel.class, formWriteModel.getLatestVersionUuid().getId());
        formWriteModel.setUpdatedBy(userRepository.getCurrentUser());
        formWriteModel.setUpdatedAt(LocalDateTime.now());
        FormVersionWriteModel formVersionWriteModel = createFormVersion(formWriteModel, updateFormCommand.definition(),
                updateFormCommand.columns(), updateFormCommand.rowHeights(), updateFormCommand.primaryLanguageId(),
                updateFormCommand.rowMaxHeights(), updateFormCommand.columnMapping(), latestVersion.getVersion() + 1,
                updateFormCommand.dataObjectsWriteModel());
        em.persist(formVersionWriteModel);
        formWriteModel.setLatestVersionUuid(formVersionWriteModel);
        em.merge(formWriteModel);
    }

    public void delete(DeleteFormCommand deleteFormCommand) {
        FormWriteModel formWriteModel = em.find(FormWriteModel.class, deleteFormCommand.uuid());
        if (formWriteModel != null) {
            List<FormVersionWriteModel> versions = em.createQuery(
                    "SELECT fv FROM FormVersionWriteModel fv WHERE fv.formWriteModel.id = :uuid",
                    FormVersionWriteModel.class
            ).setParameter("uuid", formWriteModel.getId()).getResultList();

            for (FormVersionWriteModel version : versions) {
                version.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());
            }

            formWriteModel.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());
        }
    }

    public void delete(DeleteFormWithDOCommand deleteFormCommand) {
        FormWriteModel formWriteModel = em.find(FormWriteModel.class, deleteFormCommand.uuid());
        if (formWriteModel != null) {
            List<FormVersionWriteModel> versions = em.createQuery(
                    "SELECT fv FROM FormVersionWriteModel fv WHERE fv.formWriteModel.id = :uuid",
                    FormVersionWriteModel.class
            ).setParameter("uuid", formWriteModel.getId()).getResultList();

            for (FormVersionWriteModel version : versions) {
                DeleteDataObjectCommand deleteDataObjectCommand = new DeleteDataObjectCommand(version.getDataObjectsWriteModel().getId());

                dataObjectWriteRepository.delete(deleteDataObjectCommand);

                version.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());
            }

            formWriteModel.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());
        }
    }

    private FormVersionWriteModel createFormVersion(FormWriteModel formWriteModel, JsonNode definition, Integer columns,
                                                    List<Integer> rowHeights, UUID languageUuid, List<Integer> rowMaxHeights,
                                                    JsonNode columnMapping, Integer version, DataObjectsWriteModel dataObjectsWriteModel) {
        UUID versionUuid = UUID.randomUUID();
        FormVersionWriteModel formVersionWriteModel = new FormVersionWriteModel();
        formVersionWriteModel.setId(versionUuid);
        formVersionWriteModel.setDefinitions(definition);
        formVersionWriteModel.setColumns(columns);
        formVersionWriteModel.setRowHeights(rowHeights);
        formVersionWriteModel.setPrimaryLanguage(languageRepository.findByUuid(languageUuid));
        formVersionWriteModel.setRowMaxHeights(rowMaxHeights);
        formVersionWriteModel.setColumnMapping(columnMapping);
        formVersionWriteModel.setFormWriteModel(formWriteModel);
        formVersionWriteModel.setVersion(version);
        formVersionWriteModel.setDataObjectsWriteModel(dataObjectsWriteModel);
        return formVersionWriteModel;
    }

    public FormWriteModel findByUuid(UUID uuid) {
        return em.find(FormWriteModel.class, uuid);
    }

    public Integer latestVersion (UUID id) {
        FormWriteModel formWriteModel = em.find(FormWriteModel.class, id);
        FormVersionWriteModel latestVersion = em.find(FormVersionWriteModel.class, formWriteModel.getLatestVersionUuid().getId());
        return latestVersion.getVersion();
    }
}
