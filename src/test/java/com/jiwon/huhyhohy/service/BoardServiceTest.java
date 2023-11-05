package com.jiwon.huhyhohy.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class BoardServiceTest {
  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("포스트 입력값 확인 - 정상")
  void checkSignUp() throws Exception {
    mockMvc.perform(post("/board")
            .param("title", "hello")
            .param("content", "tello"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("board/form"));
  }

}