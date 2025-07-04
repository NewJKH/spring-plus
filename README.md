# SPRING PLUS

## 대용량 비교 

![img.png](image/유저_수.png)

---
### 첫번째 시도
( 기본 JPA 를 이용하여 쿼리 실행 )

![img.png](image/기본_코드.png)

![img.png](image/기본_쿼리.png)

![img.png](image/기본_결과.png)

### 두번째 개선
![img.png](image/중간_코드.png)

![img.png](image/중간_쿼리.png)

![img.png](image/중간_결과.png)

### 세번째 개선
![img.png](image/개선2_코드.png)

![img.png](image/개선2_쿼리.png)

![img.png](image/개선2_결과.png)

---
## 결과
처음 시도 : 기본 JPA 742 ms
개선 1: 닉네임으로 아이디 조회 후 아이디로 유저를 조회 : 378 ms
개선 2: DB 인덱스를 사용하여 닉네임으로 유저 조회 : 136 ms