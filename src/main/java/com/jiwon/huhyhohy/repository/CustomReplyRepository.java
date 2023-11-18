package com.jiwon.huhyhohy.repository;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.reply.Reply;
import com.jiwon.huhyhohy.web.dto.reply.ReplyResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jiwon.huhyhohy.domain.reply.QReply.reply;

@Repository
public class CustomReplyRepository {
    private JPAQueryFactory queryFactory;

    public CustomReplyRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ReplyResponseDto> findByBoard(Board board) {
        List<Reply> replies =  queryFactory.selectFrom(reply)
                .leftJoin(reply.parent)
                .fetchJoin()
                .where(reply.board.id.eq(board.getId()))
                .orderBy(reply.parent.id.asc().nullsFirst(), reply.id.asc())
                .fetch();

        List<ReplyResponseDto> replyResponseDtos = new ArrayList<>();
        Map<Long, ReplyResponseDto> replyMap = new HashMap<>();

        replies.forEach(r -> {
            ReplyResponseDto replyResponseDto = new ReplyResponseDto(r);
            replyMap.put(r.getId(), replyResponseDto);
            if(r.getParent() == null) {
                replyResponseDtos.add(replyResponseDto);
            } else {
                replyMap.get(r.getParent().getId()).getReply().add(replyResponseDto);
            }
        });
        return replyResponseDtos;
    }
}
