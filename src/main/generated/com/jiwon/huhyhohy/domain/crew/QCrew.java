package com.jiwon.huhyhohy.domain.crew;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCrew is a Querydsl query type for Crew
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCrew extends EntityPathBase<Crew> {

    private static final long serialVersionUID = -562090152L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCrew crew = new QCrew("crew");

    public final com.jiwon.huhyhohy.domain.QBaseTimeEntity _super = new com.jiwon.huhyhohy.domain.QBaseTimeEntity(this);

    public final QBanner banner;

    public final NumberPath<Integer> capacity = createNumber("capacity", Integer.class);

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final EnumPath<Cost> cost = createEnum("cost", Cost.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final EnumPath<CrewType> crewType = createEnum("crewType", CrewType.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isClosed = createBoolean("isClosed");

    public final BooleanPath isPublished = createBoolean("isPublished");

    public final BooleanPath isRecruiting = createBoolean("isRecruiting");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<com.jiwon.huhyhohy.domain.Like, com.jiwon.huhyhohy.domain.QLike> likes = this.<com.jiwon.huhyhohy.domain.Like, com.jiwon.huhyhohy.domain.QLike>createList("likes", com.jiwon.huhyhohy.domain.Like.class, com.jiwon.huhyhohy.domain.QLike.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath plan = createString("plan");

    public final com.jiwon.huhyhohy.domain.user.QUser user;

    public final ListPath<com.jiwon.huhyhohy.domain.user.User, com.jiwon.huhyhohy.domain.user.QUser> users = this.<com.jiwon.huhyhohy.domain.user.User, com.jiwon.huhyhohy.domain.user.QUser>createList("users", com.jiwon.huhyhohy.domain.user.User.class, com.jiwon.huhyhohy.domain.user.QUser.class, PathInits.DIRECT2);

    public final StringPath wisher = createString("wisher");

    public QCrew(String variable) {
        this(Crew.class, forVariable(variable), INITS);
    }

    public QCrew(Path<? extends Crew> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCrew(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCrew(PathMetadata metadata, PathInits inits) {
        this(Crew.class, metadata, inits);
    }

    public QCrew(Class<? extends Crew> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.banner = inits.isInitialized("banner") ? new QBanner(forProperty("banner"), inits.get("banner")) : null;
        this.user = inits.isInitialized("user") ? new com.jiwon.huhyhohy.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

