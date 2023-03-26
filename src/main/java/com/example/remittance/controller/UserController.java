package com.example.remittance.controller;

import com.example.remittance.domain.UserStatics;
import com.example.remittance.domain.User;
import com.example.remittance.dto.MyRecipientsResponseDto;
import com.example.remittance.dto.MyTransfersResponseDto;
import com.example.remittance.dto.UserRequestDto;
import com.example.remittance.service.TransferService;
import com.example.remittance.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final TransferService transferService;

    public UserController(UserService userService, TransferService transferService) {
        this.userService = userService;
        this.transferService = transferService;
    }
    @PostMapping("user/create")
    public String create(@RequestBody UserRequestDto userRequestDto){
        userRequestDto.setTradingBankInfoYn(userRequestDto.getTradingBankInfoYn().equalsIgnoreCase("Y") ? "Y" : "N");
        User user = User.createUser(userRequestDto.getName(), userRequestDto.getEmail(),userRequestDto.getTradingBankInfoYn(),
                userRequestDto.getAddress(), UserStatics.createUserStatics(BigDecimal.valueOf(0), BigDecimal.valueOf(0)));
        userService.save(user);
        return "ok";
    }

    //    송금인 email을 통해 최근3개월 해외송금내역조회
    @GetMapping("user/mytransfers")
    public List<MyTransfersResponseDto> mytransfers(@RequestParam(value="userEmail")String userEmail) throws Exception {
        return transferService.mytransfers(userEmail);
    }

    //    송금인 email을 통해 최근 해외송금 수신인정보 조회
    @GetMapping("user/myRecipients")
    public List<MyRecipientsResponseDto> myRecipients(@RequestParam(value="userEmail")String userEmail) throws Exception {
        return transferService.myRecipients(userEmail);
    }

}
