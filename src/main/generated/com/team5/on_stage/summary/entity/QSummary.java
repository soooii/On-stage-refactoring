package com.team5.on_stage.summary.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSummary is a Querydsl query type for Summary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSummary extends EntityPathBase<Summary> {

    private static final long serialVersionUID = 373676016L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSummary summary1 = new QSummary("summary1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath summary = createString("summary");

    public final com.team5.on_stage.user.entity.QUser user;

    public QSummary(String variable) {
        this(Summary.class, forVariable(variable), INITS);
    }

    public QSummary(Path<? extends Summary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSummary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSummary(PathMetadata metadata, PathInits inits) {
        this(Summary.class, metadata, inits);
    }

    public QSummary(Class<? extends Summary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.team5.on_stage.user.entity.QUser(forProperty("user")) : null;
    }

}

