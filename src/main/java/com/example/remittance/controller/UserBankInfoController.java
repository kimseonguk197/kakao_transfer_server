package com.example.remittance.controller;

import com.example.remittance.domain.User;
import com.example.remittance.domain.UserBankInfo;
import com.example.remittance.dto.UserBankInfoRequestDto;
import com.example.remittance.repository.UserBankInfoRepository;
import com.example.remittance.service.UserService;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserBankInfoController {

    private final UserService userService;
    private final UserBankInfoRepository userBankInfoRepository;

    public UserBankInfoController(UserService userService, UserBankInfoRepository userBankInfoRepository) {
        this.userService = userService;
        this.userBankInfoRepository = userBankInfoRepository;
    }
    @PostMapping("userbankinfo/create")
    public String create(@RequestBody UserBankInfoRequestDto userBankInfoRequestDto) throws Exception {
        User user = userService.findByEmail(userBankInfoRequestDto.getUser_email());
        UserBankInfo userBankInfo = UserBankInfo.createUserBankInfo(userBankInfoRequestDto.getAccount_number(), userBankInfoRequestDto.getBalance(), user);
        userBankInfoRepository.save(userBankInfo);
        return "ok";
    }

    //    user 계좌번호를 통해 user계좌 잔액조회
    @GetMapping("userbankinfo/checkbalance")
    public UserBankInfo checkBalance(@RequestParam(value="accnum")String accnum) throws Exception {
        return userBankInfoRepository.findByAccount(accnum).orElseThrow(Exception::new);
    }


}
