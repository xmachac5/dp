package org.master.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.master.model.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity extends PanacheEntityBase  {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id ;

    @Getter
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false, referencedColumnName = "id")
    private User createdBy;

    @Setter
    @Getter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private User  updatedBy;

    @Getter
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Getter
    @ManyToOne
    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    private User deletedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters
    public UUID getUuid() {
        return id;
    }


    public void setDeleted(User deletedBy, LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
    }
}
