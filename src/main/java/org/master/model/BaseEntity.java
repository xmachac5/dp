package org.master.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.master.model.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity extends PanacheEntityBase  {

    @Setter
    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id ;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", updatable = false, referencedColumnName = "id")
    private User createdBy;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private User  updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by", referencedColumnName = "id")
    private User deletedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }



    public void setDeleted(User deletedBy, LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
    }
}
