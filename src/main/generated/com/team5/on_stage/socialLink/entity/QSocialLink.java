package com.team5.on_stage.socialLink.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSocialLink is a Querydsl query type for SocialLink
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSocialLink extends EntityPathBase<SocialLink> {

    private static final long serialVersionUID = 349279576L;

    public static final QSocialLink socialLink = new QSocialLink("socialLink");

    public final StringPath github = createString("github");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath instagram = createString("instagram");

    public final StringPath spotify = createString("spotify");

    public final StringPath username = createString("username");

    public final StringPath x = createString("x");

    public final StringPath youtube = createString("youtube");

    public QSocialLink(String variable) {
        super(SocialLink.class, forVariable(variable));
    }

    public QSocialLink(Path<? extends SocialLink> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSocialLink(PathMetadata metadata) {
        super(SocialLink.class, metadata);
    }

}

