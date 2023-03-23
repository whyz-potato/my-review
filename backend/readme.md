# MyReview
<b>MyReview</b>는 책/영화 리뷰를 작성할 수 있는 애플리케이션입니다. <br/>
컨텐츠 검색과 리뷰 작성 뿐 아니라 올 해의 목표를 설정하고 달성률을 보여주는 기능도 제공하여 이용자에게 동기를 부여하고자 했습니다. 

## 목차
1. [팀 소개](#🥔-팀)
2. [주요 기능](#⚙︎-주요-기능)
3. [아키텍처](#🔧-아키텍처)
4. [API 명세서](#📜-API-명세서)
5. [개발](#👨🏻‍💻-개발)

<br/>

## 🥔 팀
<details open>
<summary>팀 접기/펼치기</summary>
<div markdown="1">
<table>
<thead>
  <tr>
    <th colspan="3">왜지?감자(Whyz-Potato)🥔</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td>김다슬(BE)</br> <a href="https://github.com/Daseull">깃허브</a><br/> </td>
    <td>문지원(FE)</br> <a href="https://github.com/jeewonMoon">깃허브</a><br/> </td>
    <td>허서영(BE)</br> <a href="https://github.com/hs03130">깃허브</a><br/> </td>
  </tr>
</tbody>
</table>
팀 정보 및 협업 노션 : https://www.notion.so/104b4a6271c743cf883846540cb42a76?p=c20376985a194faab0e2a32b2e535239&pm=s
</div>
</details>
<br/>

## ⚙︎ 주요 기능
<details open>
<summary>주요 기능 접기/펼치기</summary>
<div markdown="1">
<img src="https://user-images.githubusercontent.com/59015764/227125837-a82f2a6e-442b-46a6-bf36-9e385d78f2d4.png">
<ul>
    <li><b>회원</b></li>
        <ul>
            <li> 회원가입 : 사용자는 고유한 이메일을 등록하여 서비스에 가입할 수 있습니다.</li>
            <li> 로그인 : 가입 시 등록한 이메일과 비밀번호를 통해 로그인 할 수 있습니다. 비밀번호는 암호화하여 저장합니다.</li>  
            <li> 회원정보변경 : 가입 시 등록한 이름과 비밀번호를 변경할 수 있습니다.</li>
            <li> 회원탈퇴</li>
        </ul>
    <li><b>목표</b></li>
        사용자의 동기부여를 위하여 목표 설정과 달성률 기능을 제공합니다. 
        <ul>
            <li> 올해 목표 조회 : 올해 목표와 달성률을 조회합니다. 가입 후 변경하지 않았다면 기본값 10으로 설정되어있습니다.</li>
            <li> 올해 목표 수정 : 올해 목표량을 수정합니다. </li>
            <li> 과거 목표 조회 : 회원가입 시점 이후의 모든 연도의 목표와 달성률을 조회할 수 있습니다. </li>
        </ul>
    <li><b>컨텐츠</b></li>
        책과 영화에 대한 정보를 가져오기 위해 네이버 검색 API를 사용했습니다.
        <ul>
            <li> 컨텐츠 검색 : 사용자의 검색 쿼리에 대한 네이버 API 응답을 수정하여 검색 결과 리스트를 반환합니다.</li>
            <li> 컨텐츠 저장 : 사용자가 리뷰를 작성할 때, 서비스 DB에 저장되어 있지 않은 컨텐츠라면 컨텐츠 저장이 먼저 이뤄집니다. 서비스 DB에 저장되어 있는 지 여부는 책은 isbn을 통해, 영화는 제목과 감독을 통해 확인합니다. </li>   
            <li> 둘러보기 : 일종의 추천 리스트로 사용자가 관심 설정한 컨텐츠 리스트, 신작 컨텐츠 리스트, 리뷰가 많이 달린 상위 10개의 컨텐츠 리스트를 확인할 수 있습니다. </li>
        </ul>
    <li><b>리뷰</b></li>
        <ul>
            <li> 리뷰 작성 : 컨텐츠에 대한 리뷰를 작성합니다. 담아놓은 경우에는 상태만, 그 외의 경우에는 감상일, 별점, 감상 내용을 저장합니다. 
            </li> 
            <li> 리뷰 조회 : 작성한 리뷰를 조회합니다. 컨텐츠 제목을 이용해 작성 리뷰 내에서 검색 기능도 제공합니다.</li>
            <li> 리뷰 수정 : 작성한 리뷰를 수정합니다. 담아놓기로 변경 시 기존에 저장한 감상 내용이 삭제됩니다. </li>
            <li> 리뷰 삭제 : 작성한 리뷰를 삭제합니다. </li>
        </ul>
</ul> 
</div>
</details>
<br/>

## 🔧 아키텍처
<details open>
<summary>아키텍처 접기/펼치기</summary>
<div markdown="1">
<h3>도메인 설계</h3>
<img src = "https://user-images.githubusercontent.com/59015764/227146380-3bb79ae2-e4f3-40eb-bccb-7dcb9f3c4f30.png">
<h3>테이블 설계</h3>
<img src = "https://user-images.githubusercontent.com/59015764/227146719-a758245a-a77f-4820-8f40-5e24e013ed73.png">
</div>
</details>
<br/>

## 📜 API 명세서
[최종 버전](https://www.notion.so/Rest-API-document-267c1ae481174b6e82cfe8baf7f5d1b8) <br/>
[ver2 - AWS Gateway](https://documenter.getpostman.com/view/19596204/2s93CPqBt3)<br/>
<br/>

## 👨🏻‍💻 개발
<details open>
<summary>개발 접기/펼치기</summary>
<div markdown="1">

<h3>개발 기간 </h3>
23.02. ~ 23.03.
<h3>개발 환경</h3>
<ul>
    <li> Java 16 </li>
    <li> Spring Boot 2.7.9 </li>
</ul>
<h3>기술 스택 </h3> 
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
<img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white"> 
<img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"> <img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">  <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"> <img src="https://img.shields.io/badge/jpa-59666c?style=for-the-badge&logo=jpa&logoColor=white"> <img src="https://img.shields.io/badge/amazon api gateway-FF4F8B?style=for-the-badge&logo=Amazon API Gateway&logoColor=white"> 
<h3>협업 툴</h3>
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=Notion&logoColor=white">

</div>
</details>
<br/>


