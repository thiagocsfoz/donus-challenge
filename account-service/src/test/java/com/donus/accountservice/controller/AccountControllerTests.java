package com.donus.accountservice.controller;

import com.donus.accountservice.domain.dto.ClientDto;
import com.donus.accountservice.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Test
    public void create_account_then_response_ok() throws Exception
    {
        ClientDto clientDto = new ClientDto();
        clientDto.setName("Cliente Teste");
        clientDto.setCpf("76636530090");
        clientDto.setAgencyId(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/account/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(clientDto)))
                    .andExpect(status().isOk());
    }
}
