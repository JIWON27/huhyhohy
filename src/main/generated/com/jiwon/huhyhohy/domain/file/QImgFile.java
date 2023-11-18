package com.jiwon.huhyhohy.domain.file;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImgFile is a Querydsl query type for ImgFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImgFile extends EntityPathBase<ImgFile> {

    private static final long serialVersionUID = -2084041821L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImgFile imgFile = new QImgFile("imgFile");

    public final com.jiwon.huhyhohy.domain.QBaseTimeEntity _super = new com.jiwon.huhyhohy.domain.QBaseTimeEntity(this);

    public final com.jiwon.huhyhohy.domain.board.QBoard board;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath originFilename = createString("originFilename");

    public final StringPath storeFilename = createString("storeFilename");

    public QImgFile(String variable) {
        this(ImgFile.class, forVariable(variable), INITS);
    }

    public QImgFile(Path<? extends ImgFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImgFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImgFile(PathMetadata metadata, PathInits inits) {
        this(ImgFile.class, metadata, inits);
    }

    public QImgFile(Class<? extends ImgFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.jiwon.huhyhohy.domain.board.QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

