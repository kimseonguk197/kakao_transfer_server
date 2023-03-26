package com.example.remittance.controller;

import com.example.remittance.domain.TradingBankInfo;
import com.example.remittance.domain.Transfer;
import com.example.remittance.service.TrandingBankInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
@RestController
public class TradingBankInfoController {

    private final TrandingBankInfoService trandingBankInfoService;

    public TradingBankInfoController(TrandingBankInfoService trandingBankInfoService) {
        this.trandingBankInfoService = trandingBankInfoService;
    }

//    거래외국환 정보 저장. 파일을 파일서버 특정 경로에 저장 후 database에는 file경로만 저장
    @PostMapping("tradingbank/create")
    public String create(@RequestParam("multiPartfiles")List<MultipartFile> multiPartfiles,
                         @RequestParam("user_email")String user_email,
                         @RequestParam("type")String type ) throws Exception {
        trandingBankInfoService.save(multiPartfiles, user_email, type);
        return "ok";
    }

//    user의 거래외국환 정보 조회
    @GetMapping("tradingbank/mylist")
    @ResponseStatus(value = HttpStatus.OK)
    public List<TradingBankInfo> myTradingBankInfos(@RequestParam(value="email")String email) throws Exception {
        return trandingBankInfoService.myTradingBankInfos(email);
    }
}
