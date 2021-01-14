package com.donus.transactionservice.controller;

import com.donus.transactionservice.domain.dto.DepositDto;
import com.donus.transactionservice.domain.dto.FilterTransactionDto;
import com.donus.transactionservice.domain.dto.TransferDto;
import com.donus.transactionservice.domain.dto.WithdrawDto;
import com.donus.transactionservice.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureDataMongo
@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void when_deposit_then_response_ok() throws Exception {
        DepositDto depositDto = new DepositDto();
        depositDto.setAccountId(123L);
        depositDto.setValue(100D);

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void when_withdraw_then_response_ok() throws Exception {
        WithdrawDto withdrawDto = new WithdrawDto();
        withdrawDto.setAccountId(123L);
        withdrawDto.setValue(100D);

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void when_transfer_then_response_ok() throws Exception {
        TransferDto transferDto = new TransferDto();
        transferDto.setSourceAccountId(123L);
        transferDto.setDestinationAccountId(123L);
        transferDto.setValue(100D);

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void when_history_then_response_ok() throws Exception {
        FilterTransactionDto filterTransactionDto = new FilterTransactionDto();
        filterTransactionDto.setAccountId(123L);

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/history")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filterTransactionDto)))
                .andExpect(status().isOk());
    }

}
