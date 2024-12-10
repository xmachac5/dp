package org.master.repository.process;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.command.process.task.commands.*;
import org.master.model.process.ProcessVersionWriteModel;
import org.master.model.process.ProcessWriteModel;
import org.master.model.process.task.*;
import org.master.repository.dataObject.DataObjectWriteRepository;
import org.master.repository.screen.ScreenWriteRepository;
import org.master.repository.script.ScriptWriteRepository;
import org.master.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TaskWriteRepository {

    @Inject
    EntityManager em;

    @Inject
    UserRepository userRepository;

    @Inject
    ProcessWriteRepository processWriteRepository;

    @Inject
    DataObjectWriteRepository dataObjectWriteRepository;

    @Inject
    ScreenWriteRepository screenWriteRepository;

    @Inject
    ScriptWriteRepository scriptWriteRepository;

    public void create(UUID id, CreateDOTaskCommand createDOTaskCommand) {

        ProcessVersionWriteModel processVersionWriteModel = processWriteRepository
                .findVersionByUuid(createDOTaskCommand.processVersionWriteModel());

        if (processVersionWriteModel.getPublished()){
            throw new IllegalStateException("Cannot edit published process version");
        }

        DOTaskWriteModel doTaskWriteModel = new DOTaskWriteModel();
        doTaskWriteModel.setId(id);
        doTaskWriteModel.setName(createDOTaskCommand.name());
        doTaskWriteModel.setDataObjectsWriteModel(dataObjectWriteRepository
                .findByUuid(createDOTaskCommand.dataObjectsWriteModel()));
        doTaskWriteModel.setProcessVersionWriteModel(processVersionWriteModel);
        doTaskWriteModel.setColumnsMapping(createDOTaskCommand.columnsMapping());
        doTaskWriteModel.setVariableMapping(createDOTaskCommand.variableMapping());

        processWriteRepository.setUpdatedVersion(processVersionWriteModel);

        em.persist(doTaskWriteModel);
    }

    public void create(UUID id, CreateScreenTaskCommand createScreenTaskCommand) {

        ProcessVersionWriteModel processVersionWriteModel = processWriteRepository
                .findVersionByUuid(createScreenTaskCommand.processVersionWriteModel());

        if (processVersionWriteModel.getPublished()){
            throw new IllegalStateException("Cannot edit published process version");
        }

        ScreenTaskWriteModel screenTaskWriteModel = new ScreenTaskWriteModel();
        screenTaskWriteModel.setId(id);
        screenTaskWriteModel.setName(createScreenTaskCommand.name());
        screenTaskWriteModel.setScreenWriteModel(screenWriteRepository
                .findByUuid(createScreenTaskCommand.screenWriteModel()));
        screenTaskWriteModel.setProcessVersionWriteModel(processVersionWriteModel);
        screenTaskWriteModel.setVariableMapping(createScreenTaskCommand.variableMapping());

        processWriteRepository.setUpdatedVersion(processVersionWriteModel);

        em.persist(screenTaskWriteModel);
    }

    public void create(UUID id, CreateScriptTaskCommand createScriptTaskCommand) {

        ProcessVersionWriteModel processVersionWriteModel = processWriteRepository
                .findVersionByUuid(createScriptTaskCommand.processVersionWriteModel());

        if (processVersionWriteModel.getPublished()){
            throw new IllegalStateException("Cannot edit published process version");
        }

        ScriptTaskWriteModel scriptTaskWriteModel = new ScriptTaskWriteModel();
        scriptTaskWriteModel.setId(id);
        scriptTaskWriteModel.setName(createScriptTaskCommand.name());
        scriptTaskWriteModel.setScriptWriteModel(scriptWriteRepository
                .findByUuid(createScriptTaskCommand.scriptWriteModel()));
        scriptTaskWriteModel.setProcessVersionWriteModel(processVersionWriteModel);
        scriptTaskWriteModel.setVariableMapping(createScriptTaskCommand.variableMapping());

        processWriteRepository.setUpdatedVersion(processVersionWriteModel);

        em.persist(scriptTaskWriteModel);
    }

    public void update(UpdateDOTaskCommand updateDOTaskCommand ) {
        DOTaskWriteModel doTaskWriteModel = em.find(DOTaskWriteModel.class, updateDOTaskCommand.id());

        if (doTaskWriteModel.getProcessVersionWriteModel().getPublished()){
            throw new IllegalStateException("Cannot edit published process version");
        }

        doTaskWriteModel.setUpdatedAt(LocalDateTime.now());
        doTaskWriteModel.setUpdatedBy(userRepository.getCurrentUser());
        doTaskWriteModel.setName(updateDOTaskCommand.name());
        doTaskWriteModel.setDataObjectsWriteModel(dataObjectWriteRepository
                .findByUuid(updateDOTaskCommand.dataObjectsWriteModel()));
        doTaskWriteModel.setColumnsMapping(updateDOTaskCommand.columnsMapping());
        doTaskWriteModel.setVariableMapping(updateDOTaskCommand.variableMapping());

        processWriteRepository.setUpdatedVersion(doTaskWriteModel.getProcessVersionWriteModel());

        em.merge(doTaskWriteModel);
    }

    public void update(UpdateScreenTaskCommand updateScreenTaskCommand ) {
        ScreenTaskWriteModel screenTaskWriteModel = em.find(ScreenTaskWriteModel.class, updateScreenTaskCommand.id());

        if (screenTaskWriteModel.getProcessVersionWriteModel().getPublished()){
            throw new IllegalStateException("Cannot edit published process version");
        }

        screenTaskWriteModel.setUpdatedAt(LocalDateTime.now());
        screenTaskWriteModel.setUpdatedBy(userRepository.getCurrentUser());
        screenTaskWriteModel.setName(updateScreenTaskCommand.name());
        screenTaskWriteModel.setScreenWriteModel(screenWriteRepository
                .findByUuid(updateScreenTaskCommand.screenWriteModel()));
        screenTaskWriteModel.setVariableMapping(updateScreenTaskCommand.variableMapping());

        processWriteRepository.setUpdatedVersion(screenTaskWriteModel.getProcessVersionWriteModel());

        em.merge(screenTaskWriteModel);
    }

    public void update(UpdateScriptTaskCommand updateScriptTaskCommand ) {
        ScriptTaskWriteModel scriptTaskWriteModel = em.find(ScriptTaskWriteModel.class, updateScriptTaskCommand.id());

        if (scriptTaskWriteModel.getProcessVersionWriteModel().getPublished()){
            throw new IllegalStateException("Cannot edit published process version");
        }

        scriptTaskWriteModel.setUpdatedAt(LocalDateTime.now());
        scriptTaskWriteModel.setUpdatedBy(userRepository.getCurrentUser());
        scriptTaskWriteModel.setName(updateScriptTaskCommand.name());
        scriptTaskWriteModel.setScriptWriteModel(scriptWriteRepository
                .findByUuid(updateScriptTaskCommand.scriptWriteModel()));
        scriptTaskWriteModel.setVariableMapping(updateScriptTaskCommand.variableMapping());

        processWriteRepository.setUpdatedVersion(scriptTaskWriteModel.getProcessVersionWriteModel());

        em.merge(scriptTaskWriteModel);
    }

    public void delete(DeleteDOTaskCommand deleteDOTaskCommand) {
        DOTaskWriteModel doTaskWriteModel = em.find(DOTaskWriteModel.class, deleteDOTaskCommand.uuid());

        if (doTaskWriteModel.getProcessVersionWriteModel().getPublished()){
            throw new IllegalStateException("Cannot edit published process version");
        }

        doTaskWriteModel.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());

        processWriteRepository.setUpdatedVersion(doTaskWriteModel.getProcessVersionWriteModel());

    }

    public void delete(DeleteScreenTaskCommand deleteScreenTaskCommand) {
        ScreenTaskWriteModel screenTaskWriteModel = em.find(ScreenTaskWriteModel.class, deleteScreenTaskCommand.uuid());

        if (screenTaskWriteModel.getProcessVersionWriteModel().getPublished()){
            throw new IllegalStateException("Cannot edit published process version");
        }

        screenTaskWriteModel.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());

        processWriteRepository.setUpdatedVersion(screenTaskWriteModel.getProcessVersionWriteModel());

    }

    public void delete(DeleteScriptTaskCommand deleteScriptTaskCommand) {
        ScriptTaskWriteModel scriptTaskWriteModel = em.find(ScriptTaskWriteModel.class, deleteScriptTaskCommand.uuid());

        if (scriptTaskWriteModel.getProcessVersionWriteModel().getPublished()){
            throw new IllegalStateException("Cannot edit published process version");
        }

        scriptTaskWriteModel.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());

        processWriteRepository.setUpdatedVersion(scriptTaskWriteModel.getProcessVersionWriteModel());

    }

    public void conceptProcess(ProcessVersionWriteModel processVersionWriteModel){
        ProcessWriteModel processWriteModel = processWriteRepository
                .findByUuid(processVersionWriteModel.getProcessWriteModel().getId());

        ProcessVersionWriteModel lastVersion = processWriteModel.getProcessVersionWriteModel();

        List<TaskWriteModel> tasks = findByProcessVersion(lastVersion);

        for (TaskWriteModel task : tasks) {
            TaskWriteModel newTask = copyTaskForNewVersion(task, processVersionWriteModel);
            em.persist(newTask); // Persist the copied task
        }


    }

    public TaskWriteModel findByUuid(UUID uuid, TaskType type) {
        return switch (type) {
            case DO -> em.find(DOTaskWriteModel.class, uuid);
            case SCREEN -> em.find(ScreenTaskWriteModel.class, uuid);
            case SCRIPT -> em.find(ScriptTaskWriteModel.class, uuid);
        };
    }

    public List<TaskWriteModel> findByProcessVersion(ProcessVersionWriteModel processVersionWriteModel) {
        return em.createQuery(
                """
                        SELECT tDO FROM DOTaskWriteModel tDO WHERE tDO.processVersionWriteModel = :uuid
                        UNION
                        SELECT tS FROM ScreenTaskWriteModel tS WHERE tS.processVersionWriteModel = :uuid
                        UNION
                        SELECT tSC FROM ScriptTaskWriteModel tSC WHERE tSC.processVersionWriteModel = :uuid
                    """,
                TaskWriteModel.class
        ).setParameter("uuid", processVersionWriteModel.getId()).getResultList();
    }

    // Helper method to copy a task to the new version
    private TaskWriteModel copyTaskForNewVersion(TaskWriteModel task, ProcessVersionWriteModel newVersion) {
        if (task instanceof DOTaskWriteModel doTask) {
            DOTaskWriteModel newTask = new DOTaskWriteModel();
            newTask.setName(doTask.getName());
            newTask.setDataObjectsWriteModel(doTask.getDataObjectsWriteModel());
            newTask.setColumnsMapping(doTask.getColumnsMapping());
            newTask.setVariableMapping(doTask.getVariableMapping());
            newTask.setProcessVersionWriteModel(newVersion);
            return newTask;
        } else if (task instanceof ScreenTaskWriteModel screenTask) {
            ScreenTaskWriteModel newTask = new ScreenTaskWriteModel();
            newTask.setName(screenTask.getName());
            newTask.setScreenWriteModel(screenTask.getScreenWriteModel());
            newTask.setVariableMapping(screenTask.getVariableMapping());
            newTask.setProcessVersionWriteModel(newVersion);
            return newTask;
        } else if (task instanceof ScriptTaskWriteModel scriptTask) {
            ScriptTaskWriteModel newTask = new ScriptTaskWriteModel();
            newTask.setName(scriptTask.getName());
            newTask.setScriptWriteModel(scriptTask.getScriptWriteModel());
            newTask.setVariableMapping(scriptTask.getVariableMapping());
            newTask.setProcessVersionWriteModel(newVersion);
            return newTask;
        } else {
            throw new IllegalArgumentException("Unknown task type: " + task.getClass().getSimpleName());
        }
    }
}
