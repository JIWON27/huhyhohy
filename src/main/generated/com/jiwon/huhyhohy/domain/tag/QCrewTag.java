package com.jiwon.huhyhohy.domain.tag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCrewTag is a Querydsl query type for CrewTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCrewTag extends EntityPathBase<CrewTag> {

    private static final long serialVersionUID = 360337135L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCrewTag crewTag = new QCrewTag("crewTag");

    public final com.jiwon.huhyhohy.domain.crew.QCrew crew;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTag tag;

    public QCrewTag(String variable) {
        this(CrewTag.class, forVariable(variable), INITS);
    }

    public QCrewTag(Path<? extends CrewTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCrewTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCrewTag(PathMetadata metadata, PathInits inits) {
        this(CrewTag.class, metadata, inits);
    }

    public QCrewTag(Class<? extends CrewTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.crew = inits.isInitialized("crew") ? new com.jiwon.huhyhohy.domain.crew.QCrew(forProperty("crew"), inits.get("crew")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

