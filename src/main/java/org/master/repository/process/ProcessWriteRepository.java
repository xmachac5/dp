package org.master.repository.process;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.command.process.commands.*;
import org.master.model.process.ProcessVersionWriteModel;
import org.master.model.process.ProcessWriteModel;
import org.master.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProcessWriteRepository {

    @Inject
    EntityManager em;

    @Inject
    UserRepository userRepository;

    @Inject
    TaskWriteRepository taskWriteRepository;

    public void create(UUID id, CreateProcessCommand createProcessCommand) {
        ProcessWriteModel processWriteModel = new ProcessWriteModel();
        processWriteModel.setId(id);
        processWriteModel.setName(createProcessCommand.name());

        ProcessVersionWriteModel processVersionWriteModel = createProcessVersion(processWriteModel,
                createProcessCommand.variables(), 1);

        em.persist(processWriteModel);
        em.persist(processVersionWriteModel);
        processWriteModel.setProcessVersionWriteModel(processVersionWriteModel);
        em.merge(processWriteModel);

    }

    public void update(UpdateProcessCommand updateProcessCommand ) {
        ProcessWriteModel processWriteModel = em.find(ProcessWriteModel.class, updateProcessCommand.id());

        ProcessVersionWriteModel processVersionWriteModel =  em.createQuery(
                "SELECT pv FROM ProcessVersionWriteModel pv WHERE pv.published = false",
                ProcessVersionWriteModel.class
        ).getSingleResult();

        if (processVersionWriteModel == null) {
            throw new IllegalArgumentException("Cannot edit already published process version");
        } else {

            processWriteModel.setName(updateProcessCommand.name());
            processWriteModel.setUpdatedAt(LocalDateTime.now());
            processWriteModel.setUpdatedBy(userRepository.getCurrentUser());
            em.merge(processWriteModel);

            processVersionWriteModel.setVariables(updateProcessCommand.variables());
            processVersionWriteModel.setUpdatedAt(LocalDateTime.now());
            processVersionWriteModel.setUpdatedBy(userRepository.getCurrentUser());
            em.merge(processVersionWriteModel);
        }
    }

    //Create updatable concept - new version derived form the last process version
    public void concept(ConceptProcessCommand conceptProcessCommand){
        ProcessWriteModel processWriteModel = em.find(ProcessWriteModel.class, conceptProcessCommand.uuid());

        ProcessVersionWriteModel latestVersion = em.find(ProcessVersionWriteModel.class,
                processWriteModel.getProcessVersionWriteModel().getId());

        if (!latestVersion.getPublished()) {
            throw new IllegalArgumentException("Cannot make concept of already process version which is already concept");
        } else {

            ProcessVersionWriteModel processVersionWriteModel = createProcessVersion(processWriteModel,
                    latestVersion.getVariables(), latestVersion.getVersion() + 1);

            processVersionWriteModel.setProcessWriteModel(processWriteModel);

            em.persist(processVersionWriteModel);

            taskWriteRepository.conceptProcess(processVersionWriteModel);
        }

    }

    // Publish process version in concept (makes it non-updatable) and sets it as latest process version
    public void publish(PublishProcessCommand publishProcessCommand){
        ProcessWriteModel processWriteModel = em.find(ProcessWriteModel.class, publishProcessCommand.uuid());

        ProcessVersionWriteModel processVersionWriteModel =  em.createQuery(
                "SELECT pv FROM ProcessVersionWriteModel pv WHERE pv.published = false",
                ProcessVersionWriteModel.class
        ).getSingleResult();

        if (processVersionWriteModel == null) {
            throw new IllegalArgumentException("Cannot publish already published process version");
        } else {

            processVersionWriteModel.setPublished(true);
            processVersionWriteModel.setUpdatedAt(LocalDateTime.now());
            processVersionWriteModel.setUpdatedBy(userRepository.getCurrentUser());
            em.merge(processVersionWriteModel);

            processWriteModel.setProcessVersionWriteModel(processVersionWriteModel);
            processWriteModel.setUpdatedAt(LocalDateTime.now());
            processWriteModel.setUpdatedBy(userRepository.getCurrentUser());
            em.merge(processWriteModel);
        }

    }

    public void delete(DeleteProcessCommand deleteProcessCommand) {
        ProcessWriteModel processWriteModel = em.find(ProcessWriteModel.class, deleteProcessCommand.uuid());
        if (processWriteModel != null) {
            List<ProcessVersionWriteModel> versions = em.createQuery(
                    "SELECT pv FROM ProcessVersionWriteModel pv WHERE pv.processWriteModel.id = :uuid",
                    ProcessVersionWriteModel.class
            ).setParameter("uuid", processWriteModel.getId()).getResultList();

            for (ProcessVersionWriteModel version : versions) {
                version.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());
            }

            processWriteModel.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());
        }
    }

    private ProcessVersionWriteModel createProcessVersion(ProcessWriteModel processWriteModel, JsonNode variables,
                                                          Integer version) {
        UUID versionUuid = UUID.randomUUID();
        ProcessVersionWriteModel processVersionWriteModel = new ProcessVersionWriteModel();
        processVersionWriteModel.setId(versionUuid);
        processVersionWriteModel.setProcessWriteModel(processWriteModel);
        processVersionWriteModel.setVersion(version);
        processVersionWriteModel.setPublished(false);
        processVersionWriteModel.setVariables(variables);

        return processVersionWriteModel;
    }

    public ProcessWriteModel findByUuid(UUID uuid) {
        return em.find(ProcessWriteModel.class, uuid);
    }

    public ProcessVersionWriteModel findVersionByUuid(UUID uuid){
        return em.find(ProcessVersionWriteModel.class, uuid);

    }

    public ProcessWriteModel findByVersionUuid(UUID uuid){
        return em.find(ProcessVersionWriteModel.class, uuid).getProcessWriteModel();
    }

    public void setUpdatedVersion(ProcessVersionWriteModel processVersionWriteModel) {
        processVersionWriteModel.setUpdatedAt(LocalDateTime.now());
        processVersionWriteModel.setUpdatedBy(userRepository.getCurrentUser());
        em.merge(processVersionWriteModel);
    }
}
