package com.jiwon.huhyhohy.web.controller.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("회원 가입 화면 진입 확인")
  void signUpForm() throws Exception {
    mockMvc.perform(get("/sign-up"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("account/signup"))
        .andExpect(model().attributeExists("userSaveRequestDto"));
  }

  @Test
  @DisplayName("회원가입 입력값 확인 - 정상")
  void checkSignUp() throws Exception {
    mockMvc.perform(post("/sign-up")
            .param("name", "김철수")
            .param("nickname", "tester")
            .param("email", "test@test.com")
            .param("password", "asdasdasd1234!"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/login"));
  }
  @Test
  @DisplayName("회원가입 입력값 확인 - 비정상")
  void checkSignUp_hasError() throws Exception {
    mockMvc.perform(post("/sign-up")
            .param("name", "김철수")
            .param("nickname", "tester")
            .param("email", "test@test.com")
            .param("password", "123!"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("account/signup"));
  }
}