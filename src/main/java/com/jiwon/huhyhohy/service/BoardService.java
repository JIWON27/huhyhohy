package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.file.ImgFile;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.BoardRepository;
import com.jiwon.huhyhohy.repository.UserRepository;
import com.jiwon.huhyhohy.web.dto.board.BoardResponseDto;
import com.jiwon.huhyhohy.web.dto.board.BoardSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.board.BoardUpdateRequestDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
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
public class BoardService {
  private final BoardRepository boardRepository;
  private final UserRepository userRepository;

  private final FileService FileService;

  // 게시판 등록
  @Transactional
  public Long save(BoardSaveRequestDto request, String nickname) throws IOException {
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);
    List<ImgFile> imgFiles = FileService.transferImgFile(request.getImgFiles());// List<multipartfile> -> List<ImgFile>

    Board board = request.toEntity(); // title, content
    board.setUser(user);
    if (imgFiles == null){
      board.setImgFile(new ArrayList<>()); // 첨부파일이 없더라도 게시글이 저장이 되도록하기 위해서 추가함. 맞는지는 모르겠음...
    } else {
      board.setImgFile(imgFiles); //
    }
    Board savedBoard = boardRepository.save(board);
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
  // 게시판 조회 - 전체, 페이징 적용 - Spring MVC
  public Page<BoardResponseDto> findAll(Pageable pageable) {
    Page<BoardResponseDto> boards = boardRepository.findAll(pageable).map(BoardResponseDto::new);
    return boards;
  }
  // 게시판 조회 - 전체, 페이징 적용 - Rest API
  public Page<BoardResponseDto> findAll(int pageNo, int pageSize,  String sortBy) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
    Page<BoardResponseDto> boards = boardRepository.findAll(pageable).map(BoardResponseDto::new);
    return boards;
  }
  // 회원 삭제
  public void delete(Long id){
    boardRepository.deleteById(id);
  }

  // 게시글 수정
  @Transactional
  public void update(Long id, BoardUpdateRequestDto request) throws IOException {
    Board board = boardRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));

    List<ImgFile> imgFiles = FileService.transferImgFile(request.getImgFiles()); // 새로 업로드한 파일
    FileService.deleteBeforeFile(board); // 이전에 업로드 한 파일 삭제

    // 수정한 첨부파일 업로드
    if (imgFiles == null){
      board.setImgFile(new ArrayList<>()); // 첨부파일이 없더라도 게시글이 저장이 되도록하기 위해서 추가함. 맞는지는 모르겠음...
    } else {
      board.setImgFile(imgFiles); //
    }
    board.update(request.getTitle(), request.getContent());
  }

  // 내가 쓴 게시물 보기
  public List<BoardResponseDto> findMyBoards(String nickname){
    User user = userRepository.findUserByNickname(nickname).orElseThrow(IllegalArgumentException::new);
    List<Board> myBoards = boardRepository.findByUser(user);
    return myBoards.stream().map(BoardResponseDto::new).collect(Collectors.toList());
  }
}
