package com.jiwon.huhyhohy.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1431297640L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.jiwon.huhyhohy.domain.QBaseTimeEntity _super = new com.jiwon.huhyhohy.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<com.jiwon.huhyhohy.domain.crew.Crew, com.jiwon.huhyhohy.domain.crew.QCrew> crews = this.<com.jiwon.huhyhohy.domain.crew.Crew, com.jiwon.huhyhohy.domain.crew.QCrew>createList("crews", com.jiwon.huhyhohy.domain.crew.Crew.class, com.jiwon.huhyhohy.domain.crew.QCrew.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<com.jiwon.huhyhohy.domain.crew.Crew, com.jiwon.huhyhohy.domain.crew.QCrew> leaderCrews = this.<com.jiwon.huhyhohy.domain.crew.Crew, com.jiwon.huhyhohy.domain.crew.QCrew>createList("leaderCrews", com.jiwon.huhyhohy.domain.crew.Crew.class, com.jiwon.huhyhohy.domain.crew.QCrew.class, PathInits.DIRECT2);

    public final ListPath<com.jiwon.huhyhohy.domain.Like, com.jiwon.huhyhohy.domain.QLike> likes = this.<com.jiwon.huhyhohy.domain.Like, com.jiwon.huhyhohy.domain.QLike>createList("likes", com.jiwon.huhyhohy.domain.Like.class, com.jiwon.huhyhohy.domain.QLike.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final QProfile profile;

    public final StringPath userId = createString("userId");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.profile = inits.isInitialized("profile") ? new QProfile(forProperty("profile"), inits.get("profile")) : null;
    }

}

