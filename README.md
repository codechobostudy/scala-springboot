# scala-springboot
Scala + Springboot + Spring Data Jpa 샘플 입니다.

#### 설정하기
1.frontend 필요 패키지 설치
```
cd frontend
npm install -g bower
npm install
bower install 
#(jquery 선택하라고 나오면 1번)
```
2.실행시 VmOption추가   
-Dspring.profiles.active=dev -Dstatic.path=frontend폴더 경로  
ex) -Dspring.profiles.active=dev -Dstatic.path=/Users/changhwaoh/project/scala-springboot/frontend
