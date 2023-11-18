package com.jiwon.huhyhohy;

import com.jiwon.huhyhohy.domain.board.Board;
import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.domain.reply.Reply;
import com.jiwon.huhyhohy.domain.user.User;
import com.jiwon.huhyhohy.repository.BoardRepository;
import com.jiwon.huhyhohy.repository.CrewRepository;
import com.jiwon.huhyhohy.repository.ReplyRepository;
import com.jiwon.huhyhohy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaAuditing
public class BlogApplication {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private BoardRepository boardRepository;
  @Autowired
  private CrewRepository crewRepository;
  @Autowired
  private ReplyRepository replyRepository;

  public static void main(String[] args) {
    SpringApplication.run(BlogApplication.class, args);
  }

  @PostConstruct
  public void initData(){
    // 유저 생성 ==============================
    User user1 = User.builder()
        .name("짱구")
        .nickname("짱구")
        .email("test@test.com")
        .password("asdwasd123!")
        .build();
    User user2 = User.builder()
        .name("흰둥이")
        .nickname("흰둥이")
        .email("white@test.com")
        .password("asdwasd123!")
        .build();
    User user3 = User.builder()
        .name("철수")
        .nickname("철수")
        .email("cheolsu@test.com")
        .password("asdwasd123!")
        .build();
    User user4 = User.builder()
        .name("유리")
        .nickname("유리")
        .email("yuri@test.com")
        .password("asdwasd123!")
        .build();
    userRepository.save(user1);
    userRepository.save(user2);
    userRepository.save(user3);
    userRepository.save(user4);
    // =======================================

    // 게시글 생성 ============================
    for(int i=1; i<9; i++){
      Board board = Board.builder()
          .title("님들아 질문있음.")
          .content("저녁 치킨 vs 피자 추천해줘")
          .build();
      board.setUser(user1);
      boardRepository.save(board);
    }

    for(int i=1; i<7; i++){
      Board board = Board.builder()
          .title("누가 테니스 크루좀 만들어주실분!!!!!!!")
          .content("누가 테니스 크루좀 만들어주세요~~~ 크루장은 민망해요. 리더 해주실 분")
          .build();
      board.setUser(user2);
      boardRepository.save(board);
    }

    Board board = Board.builder()
            .title("댓글 생성 테스트 게시글")
            .content("댓글이 정상적으로 생성되는지 확인하기 위한 게시글")
            .user(user3)
            .build();
    boardRepository.save(board);

    Reply reply = Reply.builder()
            .comment("댓글이 정상적으로 생성되었습니다.")
            .board(board)
            .user(user1)
            .build();
    replyRepository.save(reply);

    Reply reReply = Reply.builder()
            .comment("대댓글이 정상적으로 생성되었습니다.")
            .board(board)
            .user(user3)
            .parent(reply)
            .build();
    replyRepository.save(reReply);

    // =======================================

    // 크루 생성 ==============================
    Crew crew1 = Crew.builder()
        .name("런닝 메이트")
        .type(true)
        .cost(true)
        .isPublished(true)
        .isRecruiting(true)
        .isClosed(false)
        .wisher("특별한 조건은 없습니다. 활발한 크루 활동을 위해 열심히 참여하실 분들이 들어왔으면 좋겠습니다.")
        .plan("1. 주 2회 19:00-21:00\n2. 자율 참석(참여가 활발했으면합니다.)\n3.회비는 딱히 없지만 회식이 있을 경우 n분의 1입니다.")
        .description("저희는 런닝 크루입니다. 혼자 뛰기 싫으신 분, 런닝 관련하여 정보를 교환하고 싶으신 분, 으쌰으쌰 운동할 사람들 모두 환영입니다.")
        .build();
    crew1.setUser(user1);
    crewRepository.save(crew1);

    Crew crew2 = Crew.builder()
        .name("먹부심")
        .type(false)
        .cost(true)
        .isPublished(true)
        .isRecruiting(true)
        .isClosed(false)
        .wisher("특별한 조건은 없습니다. 먹는거 좋아하는 사람들은 다 들어오십쇼.")
        .plan("1. 번개 모임\n2. 자율 참석\n3.항상 n분의 1입니다.")
        .description("먹부심 부리는 사람들이 맛집 탐방하는 크루 입니다. 맛있는 음식 먹으러 갑시다.")
        .build();
    crew2.setUser(user2);
    crewRepository.save(crew2);

    Crew crew3 = Crew.builder()
        .name("오버워치")
        .type(true)
        .cost(false)
        .isPublished(true)
        .isRecruiting(true)
        .isClosed(false)
        .wisher("오버워치 경쟁전 티어 골드 이상만 왔으면 좋게습니다. 저는 플레티넘입니다.")
        .plan("1. 매일 19:00~\n2. 자율 참석")
        .description("오버워치 서로 연습하면서 티어 마스터~그마까지 올리고 싶으신 분들 같이 게임해요.")
        .build();
    crew3.setUser(user3); // 크루장은 철수
    crewRepository.save(crew3);

    Crew crew4 = Crew.builder()
        .name("여행 가자!")
        .type(false)
        .cost(true)
        .isPublished(true)
        .isRecruiting(true)
        .isClosed(false)
        .wisher("여행을 좋아하는 사람들이 왔으면 좋겠습니다. 참고로 저는 주변 구경하는 것을 좋아해서 숙소에서 쉬는 것을 선호하는 사람보단" +
            " 돌아다니는 것을 선호하는 사람을 원합니다.")
        .plan("1. 일정 조율\n2. 자율 참석(하지만 숙소, 비행기 등 예약한 이후 취소할 시 ... )\n3. 여행 경비는 n분의 1입니다.")
        .description("혼자 여행하기 지치신 분들 같이 여행가서 행복한 추억 만들어봐요~")
        .build();
    crew4.setUser(user4); // 크루장은 철수
    crewRepository.save(crew4);

    Crew crew5 = Crew.builder()
        .name("Spring 스터디")
        .type(true)
        .cost(false)
        .isPublished(true)
        .isRecruiting(true)
        .isClosed(false)
        .wisher("Spring 깊게 스터디 하실 분들만 원합니다.")
        .plan("1. 주 3회 19:00-21:00\"2. 필수 참석<\n3.zoom 또는 google meet으로 활동합니다.")
        .description("백엔드 프레임워크인 Spring 스터디이고, 백엔드 개발자 취업에 관해서 정보 공유하는 크루입니다.")
        .build();
    crew5.setUser(user1);
    crewRepository.save(crew5);
    // =======================================
  }
}
