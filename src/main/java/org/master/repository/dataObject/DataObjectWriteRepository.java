package org.master.repository.dataObject;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.command.dataObject.commands.CreateDataObjectCommand;
import org.master.command.dataObject.commands.DeleteDataObjectCommand;
import org.master.command.dataObject.commands.UpdateDataObjectCommand;
import org.master.model.dataObject.DataObjectsColumnWriteModel;
import org.master.model.dataObject.DataObjectsWriteModel;
import org.master.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DataObjectWriteRepository implements PanacheRepository<DataObjectsWriteModel> {
    @Inject
    EntityManager em;

    @Inject
    UserRepository userRepository;

    public void create(UUID id, CreateDataObjectCommand createDataObjectCommand) {
        DataObjectsWriteModel dataObjectsWriteModel = new DataObjectsWriteModel();
        dataObjectsWriteModel.setId(id);
        dataObjectsWriteModel.setName(createDataObjectCommand.name());
        dataObjectsWriteModel.setDescription(createDataObjectCommand.description());
        dataObjectsWriteModel.setVersion(1);
        dataObjectsWriteModel.setSoftDelete(createDataObjectCommand.softDelete());
        dataObjectsWriteModel.setTrackChanges(createDataObjectCommand.trackChanges());

        em.persist(dataObjectsWriteModel);

        // Parse the columns from the JSON node and create Data entities
        createColumns(dataObjectsWriteModel, createDataObjectCommand.columns());
    }

    // Update is used just for Entities not connected with form (views so can be mutated) Entities connected from form represents real table so to keep historic data are not updated, but with each update is created new Entity
    public void update(UpdateDataObjectCommand updateDataObjectCommand ) {
        DataObjectsWriteModel dataObjectsWriteModel = em.find(DataObjectsWriteModel.class, updateDataObjectCommand.id());
        dataObjectsWriteModel.setDescription(updateDataObjectCommand.description());
        dataObjectsWriteModel.setVersion(dataObjectsWriteModel.getVersion() + 1);
        dataObjectsWriteModel.setSoftDelete(updateDataObjectCommand.softDelete());
        dataObjectsWriteModel.setTrackChanges(updateDataObjectCommand.trackChanges());
        dataObjectsWriteModel.setUpdatedBy(userRepository.getCurrentUser());
        dataObjectsWriteModel.setUpdatedAt(LocalDateTime.now());

        //Delete all existing columns
        List<DataObjectsColumnWriteModel> existingColumns = em.createQuery("SELECT d FROM DataObjectsColumnWriteModel d WHERE d.dataObjectsWriteModelUuid = :dataObject", DataObjectsColumnWriteModel.class)
                .setParameter("dataObject", dataObjectsWriteModel)
                .getResultList();

        for (DataObjectsColumnWriteModel column : existingColumns) {
            em.remove(column);
        }

        // Create new columns from the JsonNode
        createColumns(dataObjectsWriteModel, updateDataObjectCommand.columns());

        em.merge(dataObjectsWriteModel);
    }

    private void createColumns(DataObjectsWriteModel dataObjectsWriteModel, JsonNode columnsNode) {
        if (columnsNode != null && columnsNode.isArray()) {
            for (JsonNode columnNode : columnsNode) {
                DataObjectsColumnWriteModel dataEntity = new DataObjectsColumnWriteModel();
                dataEntity.setName(columnNode.get("name").asText());
                dataEntity.setPrimaryKey(columnNode.get("primaryKey").asBoolean());
                dataEntity.setDataType(columnNode.get("dataType").asText());
                dataEntity.setIsFk(columnNode.get("isFk").asBoolean());
                dataEntity.setDescription(columnNode.get("description").asText());

                // Set the relationship with DataObjectsWriteModel
                dataEntity.setDataObjectsWriteModelUuid(dataObjectsWriteModel);

                // If there's a foreign key reference, set that as well
                if (columnNode.has("dataObjectsWriteModelForeignKeyUuid")) {
                    UUID foreignKeyUuid = UUID.fromString(columnNode.get("dataObjectsWriteModelForeignKeyUuid").asText());
                    DataObjectsWriteModel foreignKeyModel = em.find(DataObjectsWriteModel.class, foreignKeyUuid);
                    if (foreignKeyModel != null) {
                        dataEntity.setDataObjectsWriteModelForeignKeyUuid(foreignKeyModel);
                    }
                }

                // Persist each new Data entity
                em.persist(dataEntity);
            }
        }
    }

    public void delete(DeleteDataObjectCommand deleteDataObjectCommand) {
        DataObjectsWriteModel dataObjectsWriteModel = em.find(DataObjectsWriteModel.class, deleteDataObjectCommand.uuid());
        dataObjectsWriteModel.setDeleted(userRepository.getCurrentUser(), LocalDateTime.now());
    }

    public DataObjectsWriteModel findByUuid(UUID uuid) {
        return em.find(DataObjectsWriteModel.class, uuid);
    }
}
