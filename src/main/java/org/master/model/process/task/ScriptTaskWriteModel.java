package org.master.model.process.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.master.model.script.ScriptWriteModel;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "script_tasks_write_model")
public class ScriptTaskWriteModel extends TaskWriteModel {
    @ManyToOne
    @JoinColumn(name = "scriptWriteModel", referencedColumnName = "id")
    private ScriptWriteModel scriptWriteModel;
}
