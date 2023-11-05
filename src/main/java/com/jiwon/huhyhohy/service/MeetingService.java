package com.jiwon.huhyhohy.service;

import com.jiwon.huhyhohy.domain.crew.Crew;
import com.jiwon.huhyhohy.domain.crew.Meeting;
import com.jiwon.huhyhohy.repository.CrewRepository;
import com.jiwon.huhyhohy.repository.MeetingRepository;
import com.jiwon.huhyhohy.web.dto.meeting.MeetingResponseDto;
import com.jiwon.huhyhohy.web.dto.meeting.MeetingSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {
  private final MeetingRepository meetingRepository;
  private final CrewRepository crewRepository;

  // 모임 저장
  public Long save(Long crewId, MeetingSaveRequestDto meetingSaveRequestDto){
    Crew crew = crewRepository.findById(crewId).orElseThrow(IllegalArgumentException::new);
    Meeting meeting = meetingSaveRequestDto.toEntity();
    meeting.setCrew(crew);
    Meeting save = meetingRepository.save(meeting);
    return save.getId();
  }
  // 모임 상세 조회 -> 아오 귀찮아
  public MeetingResponseDto findById(Long meetingId){
    Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(IllegalArgumentException::new);
    return new MeetingResponseDto(meeting);
  }

  // 모임 전체 조회 -> 이건 지도에 표현.. -> 근데 여기서 기간 지난것들은 거르고 보여줘야할듯
  // 근데 매주 7시30분 이런경우는 어쩌지 -> 몰라~ 이런건 나중에
  public List<MeetingResponseDto> findAll(){
    List<Meeting> meetingList = meetingRepository.findAll();
    List<MeetingResponseDto> meetings = new ArrayList<>();

    for (Meeting meeting : meetingList) {
      if (meeting.getDate().isAfter(LocalDateTime.now())) {
        meetings.add(new MeetingResponseDto(meeting));
      } else {
        // DB에서 Meeting 삭제
        meetingRepository.delete(meeting);
      }
    }

    return meetings;
  }
  // 모임 삭제
  public void delete(Long meetingId){
    meetingRepository.deleteById(meetingId);
  }

  // 모임 수정

  // 모임 참가를 할까 말까.. 그냥 솔직히 크루에 들어왓으면 하겠지..
}
