package com.jiwon.huhyhohy.domain.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -1706275942L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final com.jiwon.huhyhohy.domain.QBaseTimeEntity _super = new com.jiwon.huhyhohy.domain.QBaseTimeEntity(this);

    public final com.jiwon.huhyhohy.domain.board.QBoard board;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final EnumPath<com.jiwon.huhyhohy.domain.DeleteStatus> deleteStatus = createEnum("deleteStatus", com.jiwon.huhyhohy.domain.DeleteStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QComment parent;

    public final ListPath<Comment, QComment> replies = this.<Comment, QComment>createList("replies", Comment.class, QComment.class, PathInits.DIRECT2);

    public final com.jiwon.huhyhohy.domain.user.QUser user;

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.jiwon.huhyhohy.domain.board.QBoard(forProperty("board"), inits.get("board")) : null;
        this.parent = inits.isInitialized("parent") ? new QComment(forProperty("parent"), inits.get("parent")) : null;
        this.user = inits.isInitialized("user") ? new com.jiwon.huhyhohy.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

