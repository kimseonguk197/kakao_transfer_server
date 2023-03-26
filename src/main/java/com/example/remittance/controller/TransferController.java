package com.example.remittance.controller;
import com.example.remittance.dto.TransferRequestDto;
import com.example.remittance.service.TransferService;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

//   해외송금 보내기
    @PostMapping("transfer/create")
    public String create(@RequestBody TransferRequestDto transferRequestDto) throws Exception {
        transferService.save(transferRequestDto);
        return "ok";
    }


}
