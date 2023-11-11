package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.file.ImgFile;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.BoardRepository;
import com.jiwon.huhyhohy.repository.UserRepository;
import com.jiwon.huhyhohy.web.dto.board.BoardResponseDto;
import com.jiwon.huhyhohy.web.dto.board.BoardSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.board.BoardUpdateRequestDto;
import com.jiwon.huhyhohy.web.dto.board.PageBoardResponseDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
  private final BoardRepository boardRepository;
  private final UserRepository userRepository;

  private final FileService FileService;

  // 게시판 등록
  @Transactional
  public Long save(BoardSaveRequestDto request, String nickname) throws IOException {
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);
    Board board = request.toEntity(); // title, content
    board.setUser(user);
    ImgFileProcess(request, board);
    Board savedBoard = boardRepository.save(board);
    return savedBoard.getId();
  }

  @Transactional
  public Long save_api(BoardSaveRequestDto request, Long userId) throws IOException {
    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    log.info("user={}", user.getName());
    Board board = request.toEntity(); // title, content
    board.setUser(user);
    ImgFileProcess(request, board);
    Board savedBoard = boardRepository.save(board);
    log.info("board id={}", board.getId());
    return savedBoard.getId();
  }

  // 게시판 조회 - 단건
  public BoardResponseDto findById(Long id){
    Board board = boardRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));
    return new BoardResponseDto(board);
  }
  // 게시판 조회 - 전체
  public List<BoardResponseDto> findAll() {
    List<BoardResponseDto> boards = boardRepository.findAll()
        .stream()
        .map(BoardResponseDto::new)
        .collect(Collectors.toList());
    return boards;
  }
  // 게시판 조회 - 전체, 페이징 적용 -
  public Page<PageBoardResponseDto> findAll(Pageable pageable) {
    Page<PageBoardResponseDto> boards = boardRepository.findAll(pageable).map(PageBoardResponseDto::new); // 이렇게 바로 map()해도 되나?
    return boards;
  }
  // 회원 삭제
  public void delete(Long id){
    boardRepository.deleteById(id);
  }

  // 게시글 수정
  @Transactional
  public void update(Long id, BoardUpdateRequestDto request) throws IOException {
    // 여기도 User관련된 코드가 필요하지 않나?
    Board board = boardRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));
    FileService.deleteBeforeFile(board); // 이전에 업로드 한 파일 삭제

    List<ImgFile> imgFiles = FileService.transferImgFile(request.getImgFiles()); // 새로 업로드한 파일
    // 수정한 첨부파일 업로드
    if (imgFiles == null){
      board.setImgFile(new ArrayList<>()); // 첨부파일이 없더라도 게시글이 저장이 되도록하기 위해서 추가함. 맞는지는 모르겠음...
    } else {
      board.setImgFile(imgFiles); //
    }
    board.update(request.getTitle(), request.getContent());
  }

  // 사용자  쓴 게시물 보기
  public List<BoardResponseDto> findMyBoards(String nickname){
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);
    List<Board> myBoards = boardRepository.findByUser(user);
    return myBoards.stream().map(BoardResponseDto::new).collect(Collectors.toList());
  }

  // 이미지 처리.. 아 근데 Update는 Dto가 UpdateRequestDto라서 음..
  private void ImgFileProcess(BoardSaveRequestDto request, Board board) throws IOException {
    List<ImgFile> imgFiles = FileService.transferImgFile(request.getImgFiles());// List<multipartfile> -> List<ImgFile>
    if (imgFiles == null){
      board.setImgFile(new ArrayList<>()); // 첨부파일이 없더라도 게시글이 저장이 되도록하기 위해서 추가함. 맞는지는 모르겠음...
    } else {
      board.setImgFile(imgFiles); //
    }
  }
}
