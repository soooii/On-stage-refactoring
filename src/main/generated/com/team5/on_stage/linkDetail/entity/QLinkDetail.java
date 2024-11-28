package com.team5.on_stage.linkDetail.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLinkDetail is a Querydsl query type for LinkDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLinkDetail extends EntityPathBase<LinkDetail> {

    private static final long serialVersionUID = 521210912L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLinkDetail linkDetail = new QLinkDetail("linkDetail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final com.team5.on_stage.link.entity.QLink link;

    public final EnumPath<com.team5.on_stage.global.constants.Platform> platform = createEnum("platform", com.team5.on_stage.global.constants.Platform.class);

    public final StringPath url = createString("url");

    public QLinkDetail(String variable) {
        this(LinkDetail.class, forVariable(variable), INITS);
    }

    public QLinkDetail(Path<? extends LinkDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLinkDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLinkDetail(PathMetadata metadata, PathInits inits) {
        this(LinkDetail.class, metadata, inits);
    }

    public QLinkDetail(Class<? extends LinkDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.link = inits.isInitialized("link") ? new com.team5.on_stage.link.entity.QLink(forProperty("link")) : null;
    }

}

