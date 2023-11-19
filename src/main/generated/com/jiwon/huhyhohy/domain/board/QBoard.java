package com.jiwon.huhyhohy.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1934799016L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final com.jiwon.huhyhohy.domain.QBaseTimeEntity _super = new com.jiwon.huhyhohy.domain.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.jiwon.huhyhohy.domain.file.ImgFile, com.jiwon.huhyhohy.domain.file.QImgFile> imgFiles = this.<com.jiwon.huhyhohy.domain.file.ImgFile, com.jiwon.huhyhohy.domain.file.QImgFile>createList("imgFiles", com.jiwon.huhyhohy.domain.file.ImgFile.class, com.jiwon.huhyhohy.domain.file.QImgFile.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<com.jiwon.huhyhohy.domain.comment.Comment, com.jiwon.huhyhohy.domain.comment.QComment> replies = this.<com.jiwon.huhyhohy.domain.comment.Comment, com.jiwon.huhyhohy.domain.comment.QComment>createList("replies", com.jiwon.huhyhohy.domain.comment.Comment.class, com.jiwon.huhyhohy.domain.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final com.jiwon.huhyhohy.domain.user.QUser user;

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.jiwon.huhyhohy.domain.user.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

