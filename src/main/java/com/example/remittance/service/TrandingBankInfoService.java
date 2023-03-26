package com.example.remittance.service;

import com.example.remittance.domain.TradingBankInfo;
import com.example.remittance.domain.Transfer;
import com.example.remittance.domain.User;
import com.example.remittance.dto.*;
import com.example.remittance.repository.TradingBankInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TrandingBankInfoService {
    private final TradingBankInfoRepository tradingBankInfoRepository;
    private final UserService userService;

    public TrandingBankInfoService(TradingBankInfoRepository tradingBankInfoRepository, UserService userService) {
        this.tradingBankInfoRepository = tradingBankInfoRepository;
        this.userService = userService;
    }

    public void save(List<MultipartFile> multiPartfiles, String user_email, String type ) throws Exception {
        User user = userService.findByEmail(user_email);
        // 서버에 업로드 파일을 저장할 경로
        File folder = new File("/tmp/test/"+user_email);
        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        if (!folder.exists()) {
            folder.mkdirs(); //폴더 생성합니다.
        }
        String uploadFolder = "/tmp/test/"+user_email;
        List<TradingBankInfo> lst = new ArrayList<>();
        for (MultipartFile multipartFile : multiPartfiles) {
            String uploadFileName = multipartFile.getOriginalFilename();
            // 저장할 파일, 생성자로 경로와 이름을 지정해줌.
            File saveFile = new File(uploadFolder, uploadFileName);
            multipartFile.transferTo(saveFile);
            String filePath = uploadFolder +"/"+ uploadFileName;
            TradingBankInfo tradingBankInfo = TradingBankInfo.builder()
                    .type(type)
                    .file_path(filePath)
                    .user(user)
                    .build();
            lst.add(tradingBankInfo);
        }try{
            tradingBankInfoRepository.saveAll(lst);
            user = user.updateTrading_bank_info_yn(user, "Y");
            userService.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //    송금인 email을 통해 최근3개월 해외송금내역조회
    public List<TradingBankInfo> myTradingBankInfos(String userEmail) throws Exception {
        User user = userService.findByEmail(userEmail);
        return tradingBankInfoRepository.findAllByUser(user);
    }

}
