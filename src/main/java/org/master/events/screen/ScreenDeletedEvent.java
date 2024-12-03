package org.master.events.screen;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.master.events.BaseEvent;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScreenDeletedEvent extends BaseEvent {

    @Builder
    @JsonCreator
    public ScreenDeletedEvent(
            @JsonProperty(value = "id", required = true) UUID id
    )
    {
        super(id);
    }
}
