package com.jiwon.huhyhohy.web.controller.view;

import com.jiwon.huhyhohy.service.TagService;
import com.jiwon.huhyhohy.web.dto.tag.TagSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class TagController {
  private final TagService tagService;

  @PostMapping("/settings/tags/add")
  @ResponseBody
  public ResponseEntity addTags(@RequestBody TagSaveRequestDto tagSaveRequestDto) {
    tagService.addTag(tagSaveRequestDto);
    return ResponseEntity.ok().build();
  }

}
