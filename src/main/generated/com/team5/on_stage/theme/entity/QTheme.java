package com.team5.on_stage.theme.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTheme is a Querydsl query type for Theme
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTheme extends EntityPathBase<Theme> {

    private static final long serialVersionUID = 1949936272L;

    public static final QTheme theme = new QTheme("theme");

    public final StringPath backgroundImage = createString("backgroundImage");

    public final NumberPath<Integer> borderRadius = createNumber("borderRadius", Integer.class);

    public final StringPath buttonColor = createString("buttonColor");

    public final StringPath fontColor = createString("fontColor");

    public final StringPath iconColor = createString("iconColor");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath profileColor = createString("profileColor");

    public final StringPath username = createString("username");

    public QTheme(String variable) {
        super(Theme.class, forVariable(variable));
    }

    public QTheme(Path<? extends Theme> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTheme(PathMetadata metadata) {
        super(Theme.class, metadata);
    }

}

