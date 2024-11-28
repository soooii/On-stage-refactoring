package com.team5.on_stage.link.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLink is a Querydsl query type for Link
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLink extends EntityPathBase<Link> {

    private static final long serialVersionUID = -1202496194L;

    public static final QLink link = new QLink("link");

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final NumberPath<Long> prevLinkId = createNumber("prevLinkId", Long.class);

    public final StringPath title = createString("title");

    public final StringPath username = createString("username");

    public QLink(String variable) {
        super(Link.class, forVariable(variable));
    }

    public QLink(Path<? extends Link> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLink(PathMetadata metadata) {
        super(Link.class, metadata);
    }

}

