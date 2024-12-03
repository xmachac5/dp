package org.master.repository.script;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.command.script.commands.CreateScriptCommand;
import org.master.command.script.commands.DeleteScriptCommand;
import org.master.command.script.commands.UpdateScriptCommand;
import org.master.model.script.ScriptWriteModel;
import org.master.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class ScriptWriteRepository {

    @Inject
    EntityManager em;

    @Inject
    UserRepository userRepository;

    public void create(UUID id, CreateScriptCommand createScriptCommand) {
        ScriptWriteModel scriptWriteModel = new ScriptWriteModel();
        scriptWriteModel.setId(id);
        scriptWriteModel.setVariables(createScriptCommand.variables());
        scriptWriteModel.setName(createScriptCommand.name());
        scriptWriteModel.setCode(createScriptCommand.code());

        em.persist(scriptWriteModel);
    }

    public void update(UpdateScriptCommand updateScriptCommand ) {
        ScriptWriteModel scriptWriteModel = em.find(ScriptWriteModel.class, updateScriptCommand.id());
        scriptWriteModel.setVariables(updateScriptCommand.variables());
        scriptWriteModel.setName(updateScriptCommand.name());
        scriptWriteModel.setCode(updateScriptCommand.code());

        em.merge(scriptWriteModel);
    }

    public void delete(DeleteScriptCommand deleteScriptCommand) {
        ScriptWriteModel scriptWriteModel = em.find(ScriptWriteModel.class, deleteScriptCommand.id());
        scriptWriteModel.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());
    }

    public ScriptWriteModel findByUuid(UUID uuid) {
        return em.find(ScriptWriteModel.class, uuid);
    }
}
