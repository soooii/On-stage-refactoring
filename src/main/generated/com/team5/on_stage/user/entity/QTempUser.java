package com.team5.on_stage.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTempUser is a Querydsl query type for TempUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTempUser extends EntityPathBase<TempUser> {

    private static final long serialVersionUID = 1540194004L;

    public static final QTempUser tempUser = new QTempUser("tempUser");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath username = createString("username");

    public QTempUser(String variable) {
        super(TempUser.class, forVariable(variable));
    }

    public QTempUser(Path<? extends TempUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTempUser(PathMetadata metadata) {
        super(TempUser.class, metadata);
    }

}

