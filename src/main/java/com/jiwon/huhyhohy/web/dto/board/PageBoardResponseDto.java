package com.jiwon.huhyhohy.web.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageBoardResponseDto {
  private List<BoardResponseDto> content;
  private int pageNo; // 현재 페이지 넘버
  private int pageSize; // 페이지 사이즈
  private long totalElements; //  최초 시작점
  private int totalPages; // 페이지들 총 갯수
  private boolean first; // 마지막 페이지인지
  private boolean last; // 첫번째 페이지인지
}
