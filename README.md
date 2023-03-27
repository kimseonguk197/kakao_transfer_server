# kakao_transfer_server
카카오 뱅크 해외송금 서비스 분석 및 서버프로그램 구현
## Prerequisites
java11 설치
mysql 설치

## (if)mysql docker install
docker download 및 설치
```
docker run --name kakao_test -e MYSQL_ROOT_PASSWORD=1234 -d -p 3306:3306 mysql:latest
```
##  build 및 실행
```
./gradlew build (윈도우PC의 경우 ./gradlew.bat build 실행)
cd build/libs
java -jar remittance-0.0.1-SNAPSHOT.jar
```
##  참고사항
테이블 생성은 JPA를 통해 자동생성
초기 필요 데이터(user객체, 환율정보)는 dataseeding 컴포넌트를 통해 자동생성
##  주요 process
* 예상비용 및 환율조회 -> 송금정보 validation -> 1. 계좌잔고 여부 2. 5천불이상 여부 3. 지정외국환 여부 4. 5만불 이상 등 check -> 수신자정보 입력 -> 송금 실행(계좌잔액 수정 및 통계금액 가산)
# 주요 API
* 예상비용 및 환율조회(수수료, 적용환율, 총예상비용) : /exchange/total
* 송금실행 : /transfer/create
* 최근3개월 송금내역 조회 : /user/mytransfers?userEmail=
* 회원가입 : /user/create
* 계좌추가개설 : /userbankinfo/create
* 거래외국환등록 : /tradingbank/create
