# DayPlan Content Server

## 1. 서버의 역할
- Content Server는 유저가 데이트 코스를 작성 및 조회, 작성한 코스에 대한 리뷰를 작성 및 조회할 수 있는 서버에요.

  ### DateCurse 작성
    - 데이트 코스는 한명의 유저가 다수의 데이트 코스를 짤 수 있어요 (데이트 코스 그룹)
    - 데이트 코스는 지역 기반(노원구, 중랑구, 강남구 ..)으로 짤 수 있도록 구성하였어요.
    - 데이트 코스는 Place(장소), Category(가게의 종류), Step(순서) 등을 포함하는 정보로 구성돼요.

  ### DateCourse 조회

## 2. 도메인 구조

## 3. DB 구조
![courseTable.png](readme%2Fimage%2FcourseTable.png)

## 4. 각 기능별 구현
- > #### [지역 및 카테고리 기반 데이트 코스 짜기를 설명해요](https://github.com/DayPlan-Team/dayplan-cotent-api/blob/main/readme/DateCourseSetting.md)

- > #### [DateCourse 조회 과정을 설명해요(개발 진행 중이에요!)](https://github.com/DayPlan-Team/dayplan-cotent-api/blob/main/readme/DateCourseSearch.md)

- > #### ["접점 및 거점 테스트"로 유저가 방문한 코스 검증하기를 설명해요](https://github.com/DayPlan-Team/dayplan-cotent-api/blob/main/readme/CourseVisited.md)

- > #### [Place 정보 gRPC 및 Retorit을 비교 후 gRPC-Retrofit 함께 처리하는 과정을 설명해요](https://github.com/DayPlan-Team/dayplan-cotent-api/blob/main/readme/PlaceRrpcVsRetrofit.md)
  
## 5. 미비된 점
- 리뷰 작성 기능 개발 중이에요!

## 6. 더 알고 싶은 점