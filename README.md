# currencylayer(환율계산)

![image](https://user-images.githubusercontent.com/70180381/93984140-65196c80-fdbe-11ea-9d8d-79ee9ad10fc5.png)

- 수취국가를 선택하고 송금액을 입력하면 수취국가별 환율이 계산되는 프로그램

## Built With
- Gradle
- Spring Boot 2.3.4
- Java 8
- Thymeleaf
- H2 DB

## 구동방법
- 프로그램을 실행후 http://localhost:8080/ 접속

## 주요 로직
- 프로그램 시작 시 외부 API를 이용하여 국가코드, 환율정보를 내장 H2DB에 저장
- 수취국가 선택시 저장된 DB에서 환율정보 출력
- 송금액 입력후 Summit 클릭 시 해당 국가의 환율과 송금액을 계산하여 출력
