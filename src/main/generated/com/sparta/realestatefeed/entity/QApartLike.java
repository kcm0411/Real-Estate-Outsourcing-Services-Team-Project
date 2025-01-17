package com.sparta.realestatefeed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApartLike is a Querydsl query type for ApartLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApartLike extends EntityPathBase<ApartLike> {

    private static final long serialVersionUID = -1334595424L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApartLike apartLike = new QApartLike("apartLike");

    public final QTimestamped _super = new QTimestamped(this);

    public final QApart apart;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QUser user;

    public QApartLike(String variable) {
        this(ApartLike.class, forVariable(variable), INITS);
    }

    public QApartLike(Path<? extends ApartLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApartLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApartLike(PathMetadata metadata, PathInits inits) {
        this(ApartLike.class, metadata, inits);
    }

    public QApartLike(Class<? extends ApartLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.apart = inits.isInitialized("apart") ? new QApart(forProperty("apart"), inits.get("apart")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

