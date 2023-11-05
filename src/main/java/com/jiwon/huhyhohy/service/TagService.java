package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.tag.Tag;
import com.jiwon.huhyhohy.repository.BoardRepository;
import com.jiwon.huhyhohy.repository.TagRepository;
import com.jiwon.huhyhohy.web.dto.tag.TagSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {
  private final TagRepository tagRepository;
  private final BoardRepository boardRepository;

  public void addTag(TagSaveRequestDto tagSaveRequestDto){
    // 음 tag는 반환 dto필드가 tag 엔티티랑 똑같은데 꼭 reponsedto를 만들어서 반환해야하는가?
    Tag tag = tagRepository.save(tagSaveRequestDto.toEntity());
    //Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
    //tag.setBoard(board);
  }
}
