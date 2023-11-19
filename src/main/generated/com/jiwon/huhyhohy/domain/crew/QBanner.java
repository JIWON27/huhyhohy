package com.jiwon.huhyhohy.domain.crew;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBanner is a Querydsl query type for Banner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBanner extends EntityPathBase<Banner> {

    private static final long serialVersionUID = 953176931L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBanner banner = new QBanner("banner");

    public final QCrew crew;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originFilename = createString("originFilename");

    public final StringPath storeFilename = createString("storeFilename");

    public QBanner(String variable) {
        this(Banner.class, forVariable(variable), INITS);
    }

    public QBanner(Path<? extends Banner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBanner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBanner(PathMetadata metadata, PathInits inits) {
        this(Banner.class, metadata, inits);
    }

    public QBanner(Class<? extends Banner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.crew = inits.isInitialized("crew") ? new QCrew(forProperty("crew"), inits.get("crew")) : null;
    }

}

