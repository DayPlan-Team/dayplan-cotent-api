# DayPlan Content Server

## 1. 서버의 역할 및 설명
- Content Server는 유저가 데이트 코스를 작성 및 조회, 리뷰를 작성 및 조회할 수 있는 서버에요.

<br/>

## 2. 주요 도메인
- Course
  - 코스는 유저가 만들고자 하는 데이트 코스의 개별적인 장소(행동)을 의미해요
  - 흔히 "오늘 어디로 갈까?"를 의미하는 것으로 장소나 행동(액티비티) 등이 코스에 포함이 돼요.
    - "스타벅스 갔다가 영화보러 가자!"
    - "스타벅스", "영화"는 개별적인 장소나 행동을 의미하므로 각각 코스로 정의할 수 있어요.
  - 코스는 작성자, 몇 번째 코스, 코스 완성 단계, 카테고리, 방문 여부 등으로 구성돼요.
``` kotlin
  data class Course(
      val courseId: Long,
      val userId: Long,
      val step: Int,
      val placeId: Long,
      val courseStage: CourseStage,
      val placeCategory: PlaceCategory,
      val visitedStatus: Boolean = false,
      val groupId: Long = 0L,
  )
```
<br/>

- CourseGroup
  - 코스 그룹은 개별적인 코스를 하나의 데이트 코스로 묶어주는 역할을 것을 의미해요.
  - 데이트 코스를 짤 떄, 여러개의 코스를 순서에 맞춰서 짜게 돼요.
    - "스타벅스 갔다가 영화보러 가자!"
    - "스타벅스", "영화"를 하나의 코스로 묶는 것이 CourseGroup 이에요!
  - 이용자가 데이트 코스를 구성한다고 하면, 하나의 코스 그룹에 코스를 짜게 돼요. 
  - courseGroup은 지역, 이름, 작성자 등으로 구성돼요
``` kotlin
  data class CourseGroup(
      val userId: Long,
      val groupId: Long = 0L,
      val groupName: String = DEFAULT_NAME,
      val cityCode: CityCode,
      val districtCode: DistrictCode,
  ) {
      companion object {
          const val DEFAULT_NAME = "제목 없음"
      }
  }
```
<br/>

## 3. DB 구조
![courseTable.png](readme%2Fimage%2FcourseTable.png)

<br/>

## 4. 각 기능별 구현
- > #### [지역 및 카테고리 기반 데이트 코스 짜기를 설명해요](https://github.com/DayPlan-Team/dayplan-cotent-api/blob/main/readme/DateCourseSetting.md)

- > #### [DateCourse 조회 과정을 설명해요(개발 진행 중이에요!)](https://github.com/DayPlan-Team/dayplan-cotent-api/blob/main/readme/DateCourseSearch.md)

- > #### ["접점 및 거점 테스트"로 유저가 방문한 코스 검증하기를 설명해요](https://github.com/DayPlan-Team/dayplan-cotent-api/blob/main/readme/CourseVisited.md)

- > #### [Place 정보 gRPC 및 Retorit을 비교 후 gRPC-Retrofit 함께 처리하는 과정을 설명해요](https://github.com/DayPlan-Team/dayplan-cotent-api/blob/main/readme/PlaceRrpcVsRetrofit.md)

<br/>

## 5. TODO
- 리뷰 작성 기능 개발 중이에요!
- DateCourse 조회 과정을 재정의 해야해요!