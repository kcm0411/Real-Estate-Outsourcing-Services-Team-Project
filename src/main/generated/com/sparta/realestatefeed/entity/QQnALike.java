package com.sparta.realestatefeed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQnALike is a Querydsl query type for QnALike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQnALike extends EntityPathBase<QnALike> {

    private static final long serialVersionUID = -1768594480L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQnALike qnALike = new QQnALike("qnALike");

    public final QTimestamped _super = new QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QQnA qnA;

    public final QUser user;

    public QQnALike(String variable) {
        this(QnALike.class, forVariable(variable), INITS);
    }

    public QQnALike(Path<? extends QnALike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQnALike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQnALike(PathMetadata metadata, PathInits inits) {
        this(QnALike.class, metadata, inits);
    }

    public QQnALike(Class<? extends QnALike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.qnA = inits.isInitialized("qnA") ? new QQnA(forProperty("qnA"), inits.get("qnA")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

