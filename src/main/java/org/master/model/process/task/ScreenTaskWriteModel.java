package org.master.model.process.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.master.model.screen.ScreenWriteModel;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "screen_tasks_write_model")
public class ScreenTaskWriteModel extends TaskWriteModel {
    @ManyToOne
    @JoinColumn(name = "screenWriteModel", referencedColumnName = "id")
    private ScreenWriteModel screenWriteModel;
}
