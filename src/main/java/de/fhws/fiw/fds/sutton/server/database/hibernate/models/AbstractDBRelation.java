package de.fhws.fiw.fds.sutton.server.database.hibernate.models;

import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractDBRelation<A extends AbstractDBModel, B extends AbstractDBModel> {

    @EmbeddedId
    @Column(name = SuttonColumnConstants.DB_RELATION_ID)
    private DbRelationId dbRelationId = new DbRelationId();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId(SuttonColumnConstants.PRIMARY_ID)
    private A primaryModel;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId(SuttonColumnConstants.SECONDARY_ID)
    private B secondaryModel;

    public AbstractDBRelation() {
        // make JPA happy
        // also used for AbstractDBRelationOperations. Must be public.
    }

    public DbRelationId getDbRelationId() {
        return dbRelationId;
    }

    public void setDbRelationId(DbRelationId dbRelationId) {
        this.dbRelationId = dbRelationId;
    }

    public A getPrimaryModel() {
        return primaryModel;
    }

    public void setPrimaryModel(A primaryModel) {
        this.primaryModel = primaryModel;
    }

    public B getSecondaryModel() {
        return secondaryModel;
    }

    public void setSecondaryModel(B secondaryModel) {
        this.secondaryModel = secondaryModel;
    }

    @Embeddable
    public static class DbRelationId implements Serializable {

        @Column(name = SuttonColumnConstants.PRIMARY_ID)
        private long primaryId;

        @Column(name = SuttonColumnConstants.SECONDARY_ID)
        private long secondaryId;

        public long getPrimaryId() {
            return primaryId;
        }

        public void setPrimaryId(long primaryId) {
            this.primaryId = primaryId;
        }

        public long getSecondaryId() {
            return secondaryId;
        }

        public void setSecondaryId(long secondaryId) {
            this.secondaryId = secondaryId;
        }
    }

}
