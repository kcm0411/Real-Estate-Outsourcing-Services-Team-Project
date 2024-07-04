package com.sparta.realestatefeed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApart is a Querydsl query type for Apart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApart extends EntityPathBase<Apart> {

    private static final long serialVersionUID = -186357399L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApart apart = new QApart("apart");

    public final QTimestamped _super = new QTimestamped(this);

    public final StringPath address = createString("address");

    public final ListPath<ApartLike, QApartLike> apartLikes = this.<ApartLike, QApartLike>createList("apartLikes", ApartLike.class, QApartLike.class, PathInits.DIRECT2);

    public final StringPath apartName = createString("apartName");

    public final StringPath area = createString("area");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<ApartStatusEnum> isSaled = createEnum("isSaled", ApartStatusEnum.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<QnA, QQnA> qnas = this.<QnA, QQnA>createList("qnas", QnA.class, QQnA.class, PathInits.DIRECT2);

    public final NumberPath<Long> salePrice = createNumber("salePrice", Long.class);

    public final QUser user;

    public QApart(String variable) {
        this(Apart.class, forVariable(variable), INITS);
    }

    public QApart(Path<? extends Apart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApart(PathMetadata metadata, PathInits inits) {
        this(Apart.class, metadata, inits);
    }

    public QApart(Class<? extends Apart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

