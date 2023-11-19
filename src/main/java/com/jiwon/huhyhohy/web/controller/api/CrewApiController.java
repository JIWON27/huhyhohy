package com.jiwon.huhyhohy.web.controller.api;

import com.jiwon.huhyhohy.domain.crew.Category;
import com.jiwon.huhyhohy.domain.crew.Cost;
import com.jiwon.huhyhohy.domain.crew.CrewType;
import com.jiwon.huhyhohy.service.CrewService;
import com.jiwon.huhyhohy.service.FileService;
import com.jiwon.huhyhohy.web.dto.EnrollmentResponseDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewResponseDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewSaveRequestDto;
import com.jiwon.huhyhohy.web.dto.crew.CrewUpdateRequestDto;
import com.jiwon.huhyhohy.web.dto.crew.PageCrewResponseDto;
import com.jiwon.huhyhohy.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/crews")
public class CrewApiController {
  public final CrewService crewService;
  public final FileService fileService; // 배너 이미지 음..


  // 크루 생성, 여기서 userId는 크루장, 크루 생성하는 사람이 크루장이라서
  @PostMapping("/{userId}/form")
  public ResponseEntity createCrew(@RequestBody CrewSaveRequestDto requestDto,
                                   @PathVariable Long userId){
    Long saveCrewId = crewService.save(requestDto, userId);
    return ResponseEntity.ok().build();
  }
  // 크루 카테고리 목록 조회
  @GetMapping("/categories")
  public List<Category.CrewCategoryResponse> category(){
    Class type = Category.class;
    Object[] keys = type.getEnumConstants(); // enum 클래스가 가지고 있는 키값들을 리턴해주는 함수
    return Arrays.stream(keys).map((key)
            -> new Category.CrewCategoryResponse(
            key.toString(), Category.valueOf(key.toString())))
        .collect(Collectors.toList());
  }
  // 크루 온라인/오프라인
  @GetMapping("/type")
  public List<CrewType.CrewTypeResponse> crewType(){
    Class type = CrewType.class;
    Object[] keys = type.getEnumConstants(); // enum 클래스가 가지고 있는 키값들을 리턴해주는 함수
    return Arrays.stream(keys).map((key)
            -> new CrewType.CrewTypeResponse(
                key.toString(), CrewType.valueOf(key.toString())))
        .collect(Collectors.toList());
  }
  // 크루 유료/뮤료
  @GetMapping("/costType")
  public List<Cost.CrewCostResponse> crewCostType(){
    Class cost = CrewType.class;
    Object[] keys = cost.getEnumConstants();
    return Arrays.stream(keys).map( (key)
            -> new Cost.CrewCostResponse(
            key.toString(), Cost.valueOf(key.toString())))
        .collect(Collectors.toList());
  }
  // 크루 전체 조회 (일반 크루) - 페이징 -> 최신순
  @GetMapping
  public PageCrewResponseDto findAll(
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "createdDate", required = false) String sortBy){

    PageCrewResponseDto crews = crewService.findAllApi(pageNo, pageSize, sortBy);
    return crews;
  }
  // 크루 전체 조회 (Hot 10 크루) - 페이징 -> 관심있어요 많은 순
  @GetMapping("/hot")
  public PageCrewResponseDto findHot10Crews(
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "createdDate", required = false) String sortBy){

    PageCrewResponseDto crews = crewService.findHotCrewsAPi(pageNo, pageSize, sortBy);
    return crews;
  }
  // 크루 카테고리 조회 - 페이징 -> 최신순 + crewType이랑 cost 등 복합 검색 API도 만들어야함.
  // /crews?category=게임
  @GetMapping("/category")
  public PageCrewResponseDto findAllByCategory(
      @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "createdDate", required = false) String sortBy,
      @RequestParam(value = "category", required = false) String category) throws UnsupportedEncodingException {

    // 그냥 클라이언트에서 카테고리값을 보낼때 UTF-8로 다시 디코딩해서 보내는가 안전할듯
    // 한글 인코딩 문제 해결
    // 요청 : /crews?category=게임  -> /crews?category=%EA%B2%8C%EC%9E%84 -> %EA%B2%8C%EC%9E%84 디코딩
    String decodedCategory = URLDecoder.decode(category, StandardCharsets.UTF_8.toString());
    PageCrewResponseDto crews = crewService.findAllByCategory(pageNo, pageSize, sortBy, decodedCategory);
    return crews;
  }
  // 크루 단건 조회 - 상세 조회 -> 크루원도
  @GetMapping("/{crewId}")
  public CrewResponseDto findById(@PathVariable Long crewId){
    return crewService.findById(crewId);
  }
  // 크루 정보 수정
  @PutMapping("/{crewId}/edit")
  public ResponseEntity<Void> editCrewById(@PathVariable Long crewId, @RequestBody CrewUpdateRequestDto requestDto){
    crewService.update(crewId, requestDto);
    return ResponseEntity.ok().build(); // ResponseEntity 좀 찾아보기
  }
  // 크루 삭제 - 크루장만 가능, 크루장만 삭제 버튼이 보인다는 가정하에,
  @DeleteMapping("/{crewId}")
  public ResponseEntity<Void> deleteById(@PathVariable Long crewId){
    crewService.delete(crewId);
    return ResponseEntity.ok().build(); // ResponseEntity 좀 찾아보기
  }
  // 크루 관심있어요
  @PostMapping("/{crewId}/like/{userId}")
  public ResponseEntity<Void> likeCrew(@PathVariable Long crewId, @PathVariable Long userId){
    crewService.likeCrew(crewId, userId);
    return ResponseEntity.ok().build();
  }

  // 크루 참가 신청
  @PostMapping("/{crewId}/join/{userId}")
  public ResponseEntity<Void> joinCrew(@PathVariable Long crewId, @PathVariable Long userId) {
    crewService.apply(crewId, userId);
    return ResponseEntity.ok().build();
  }
  // 크루 참가 신청 수락 -> 관심있어요 삭제 -> enrollment 엔티티에서 삭제하기
  @PostMapping("/{crewId}/accept/{acceptId}/{userId}")
  public ResponseEntity<Void> acceptCrew( @PathVariable Long crewId,
                                          @PathVariable Long acceptId,
                                          @PathVariable Long userId) {
    crewService.acceptEnrollment(crewId, acceptId, userId);
    return ResponseEntity.ok().build();
  }
  // 크루장에게만 보이는 가입 신청한 회원목록
  @GetMapping("/{crewId}/apply/users")
  public List<EnrollmentResponseDto> applyUsers(@PathVariable Long crewId){
    List<EnrollmentResponseDto> crewEnrollments = crewService.getApplyUsers(crewId);
    return crewEnrollments;

  }
  // 크루 탈퇴
  @PostMapping("/{crewId}/leave/{userId}")
  public ResponseEntity<Void> leaveCrew(@PathVariable Long crewId, @PathVariable Long userId) {
    crewService.leaveCrew(crewId, userId);
    return ResponseEntity.ok().build();
  }
  // 크루 설정 (ex. 크루 비공개, 크루원 모집 중단 등)
  // 크루 배너 설정 - 업로드
  // 크루 배너 설정 - 다운로드

  // 크루 공개 여부
  @PostMapping("/{crewId}/published")
  public ResponseEntity<Void> publishedCrew(@PathVariable Long crewId) {
    crewService.setPublished(crewId);
    return ResponseEntity.ok().build();
  }
  // 크루 인원 모집 여부
  @PostMapping("/{crewId}/recruit")
  public ResponseEntity<Void> recruitCrew(@PathVariable Long crewId) {
    crewService.setRecruited(crewId);
    return ResponseEntity.ok().build();
  }
  // 크루 활동 종료 여부
  @PostMapping("/{crewId}/close")
  public ResponseEntity<Void> closeCrew(@PathVariable Long crewId) {
    crewService.setClosed(crewId);
    return ResponseEntity.ok().build();
  }

  // 크루원 가져오기
  @GetMapping("/{crewId}/users")
  public ResponseEntity crewUser(@PathVariable Long crewId){
    List<UserResponseDto> users = crewService.getUsers(crewId);
    return ResponseEntity.ok().body(users);
  }

}
