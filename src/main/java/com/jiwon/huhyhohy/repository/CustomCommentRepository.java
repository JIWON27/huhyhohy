package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.comment.Comment;
import com.jiwon.huhyhohy.web.dto.comment.CommentResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jiwon.huhyhohy.domain.comment.QComment.comment;


@Repository
public class CustomCommentRepository {
    private JPAQueryFactory queryFactory;

    public CustomCommentRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<CommentResponseDto> findByBoard(Board board) {
        List<Comment> comments =  queryFactory.selectFrom(comment)
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.board.id.eq(board.getId()))
                .orderBy(comment.parent.id.asc().nullsFirst(), comment.id.asc())
                .fetch();

        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        Map<Long, CommentResponseDto> replyMap = new HashMap<>();

        comments.forEach(r -> {
                    CommentResponseDto commentResponseDto = new CommentResponseDto(r);
                    replyMap.put(r.getId(), commentResponseDto);
                    if(r.getParent() == null) {
                        commentResponseDtos.add(commentResponseDto);
                    } else {
                        replyMap.get(r.getParent().getId()).getReplies().add(commentResponseDto);
                    }
                });

        return commentResponseDtos;
    }
}
