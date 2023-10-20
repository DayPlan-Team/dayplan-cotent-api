create table course
(
    id             bigint auto_increment
        primary key,
    created_at     datetime(6)                                                                                                                                                                                            null,
    modified_at    datetime(6)                                                                                                                                                                                            null,
    course_stage   enum ('CATEGORY_FINISH', 'PLACE_FINISH', 'START')                                                                                                                                                      null,
    group_id       bigint                                                                                                                                                                                                 null,
    place_category enum ('ACTIVITY', 'AQUARIUM', 'BOTANIC_GARDEN', 'CAFE', 'CONCERT_HALL', 'ETC', 'EXHIBITION', 'FITNESS', 'LIBRARY', 'MOVIE_THEATER', 'MUSEUM', 'PARK', 'RESTAURANT', 'SHOPPING_MALL', 'THEATER', 'ZOO') null,
    place_id       bigint                                                                                                                                                                                                 null,
    step           int                                                                                                                                                                                                    null,
    visited_status bit                                                                                                                                                                                                    null
);

create index idx_course_groupId
    on course (group_id);


create table course_group
(
    id            bigint auto_increment
        primary key,
    created_at    datetime(6) null,
    modified_at   datetime(6) null,
    city_code     enum ('BUSAN', 'CHEONGBUK', 'CHEONGNAM', 'DAEGU', 'DAEJEON', 'DEFAULT', 'GANGWON', 'GWANGJU', 'GYEONGBUK', 'GYEONGGI', 'GYEONGNAM', 'INCHEON', 'JEJU', 'JEONBUK', 'JEONNAM', 'SEJONG', 'SEOUL', 'ULSAN') null,
    district_code enum ('BUSAN_BUK', 'BUSAN_BUSANJIN', 'BUSAN_DONG', 'BUSAN_DONGNAE', 'BUSAN_GANGSEO', 'BUSAN_GEUMJEONG', 'BUSAN_GIJANG', 'BUSAN_HAEUNDAE', 'BUSAN_JUNG', 'BUSAN_NAM', 'BUSAN_SAHA', 'BUSAN_SASANG', 'BUSAN_SEO', 'BUSAN_SUYOUNG', 'BUSAN_YEONJE', 'BUSAN_YOUNGDO', 'CHEONAN_ASAN', 'CHEONAN_BOREUNG', 'CHEONAN_BUYEO', 'CHEONAN_CHEONGYANG', 'CHEONAN_DANGJIN', 'CHEONAN_DONGNAM', 'CHEONAN_GEUMSAN', 'CHEONAN_GONGJU', 'CHEONAN_GYERYONG', 'CHEONAN_HONGSEONG', 'CHEONAN_NONSAN', 'CHEONAN_SEOBUK', 'CHEONAN_SEOCHUN', 'CHEONAN_SEOSAN', 'CHEONAN_TAEAN', 'CHEONAN_YESAN', 'CHEONGBUK_BOEUN', 'CHEONGBUK_CHEONGJU_CHEONGWON', 'CHEONGBUK_CHEONGJU_HEUNGDEOK', 'CHEONGBUK_CHEONGJU_SANGDANG', 'CHEONGBUK_CHEONGJU_SEOWON', 'CHEONGBUK_CHUNGJU', 'CHEONGBUK_DANYANG', 'CHEONGBUK_EUMSEONG', 'CHEONGBUK_GOESAN', 'CHEONGBUK_JECHUN', 'CHEONGBUK_JEUNGPO', 'CHEONGBUK_JINCHEON', 'CHEONGBUK_OKCHEON', 'CHEONGBUK_YEONGDONG', 'DAEGU_BUK', 'DAEGU_DALSEO', 'DAEGU_DALSEONG', 'DAEGU_DONG', 'DAEGU_JUNG', 'DAEGU_NAM', 'DAEGU_SEO', 'DAEGU_SUSEONG', 'DAEJEON_DAEDEOK', 'DAEJEON_DONG', 'DAEJEON_JUNG', 'DAEJEON_SEO', 'DAEJEON_YUSEONG', 'DEFAULT', 'GANGWON_CHEORWON', 'GANGWON_CHUNCHEON', 'GANGWON_DONGHAE', 'GANGWON_GANGNEUNG', 'GANGWON_GOSEONG_GW', 'GANGWON_HOENGSEONG', 'GANGWON_HONGCHEON', 'GANGWON_HWACHEON', 'GANGWON_INJE', 'GANGWON_JEONGSEON', 'GANGWON_PYEONGCHANG', 'GANGWON_SAMCHEOK', 'GANGWON_SOKCHO', 'GANGWON_TAEBAEK', 'GANGWON_WONJU', 'GANGWON_YANGGU', 'GANGWON_YANGYANG', 'GANGWON_YEONGWOL', 'GWANGJU_BUK', 'GWANGJU_DONG', 'GWANGJU_GWANGSAN', 'GWANGJU_NAM', 'GWANGJU_SEO', 'GYEONGBUK_ANDONG', 'GYEONGBUK_BONGHWA', 'GYEONGBUK_CHEONGDO', 'GYEONGBUK_CHEONGSONG', 'GYEONGBUK_CHILGOK', 'GYEONGBUK_GIMCHEON', 'GYEONGBUK_GORYEONG', 'GYEONGBUK_GUMI', 'GYEONGBUK_GUNWI', 'GYEONGBUK_GYEONGJU', 'GYEONGBUK_GYEONGSAN', 'GYEONGBUK_MUNGYEONG', 'GYEONGBUK_POHANG_BUKGU', 'GYEONGBUK_POHANG_NAMGU', 'GYEONGBUK_SANGJU', 'GYEONGBUK_SEONGJU', 'GYEONGBUK_UISEONG', 'GYEONGBUK_ULJIN', 'GYEONGBUK_ULLEUNG', 'GYEONGBUK_YECHON', 'GYEONGBUK_YEONGCHEON', 'GYEONGBUK_YEONGDEOK', 'GYEONGBUK_YEONGJU', 'GYEONGBUK_YEONGYANG', 'GYEONGGI_ANSAN_DANWON', 'GYEONGGI_ANSAN_SANGROK', 'GYEONGGI_ANSEONG', 'GYEONGGI_ANYANG_DONGAN', 'GYEONGGI_ANYANG_MANAN', 'GYEONGGI_BUCHEON', 'GYEONGGI_DONGDUCHEON', 'GYEONGGI_GAPYEONG', 'GYEONGGI_GIMPO', 'GYEONGGI_GOYANG_DEOKYANG', 'GYEONGGI_GOYANG_ILSANDONG', 'GYEONGGI_GOYANG_ILSANSEO', 'GYEONGGI_GURI', 'GYEONGGI_GWACHEON', 'GYEONGGI_GWANGJU_GYEONGGI', 'GYEONGGI_GWANGMYEONG', 'GYEONGGI_HANAM', 'GYEONGGI_HWASEONG', 'GYEONGGI_ICHEON', 'GYEONGGI_NAMYANGJU', 'GYEONGGI_OSAN', 'GYEONGGI_PAJU', 'GYEONGGI_POCHEON', 'GYEONGGI_PYEONGTAEK', 'GYEONGGI_SEONGNAM_BUNDANG', 'GYEONGGI_SEONGNAM_JUNGWON', 'GYEONGGI_SEONGNAM_SUJEONG', 'GYEONGGI_SIHEUNG', 'GYEONGGI_SUWON_GWONSEON', 'GYEONGGI_SUWON_JANGAN', 'GYEONGGI_SUWON_PALDAL', 'GYEONGGI_SUWON_YEONGTONG', 'GYEONGGI_UIJEONGBU', 'GYEONGGI_UIWANG', 'GYEONGGI_YANGJU', 'GYEONGGI_YANGPYEONG', 'GYEONGGI_YEOJU', 'GYEONGGI_YEONCHEON', 'GYEONGGI_YONGIN_CHEOIN', 'GYEONGGI_YONGIN_GIHEUNG', 'GYEONGGI_YONGIN_SUJI', 'GYEONGNAM_CHANGNYEONG', 'GYEONGNAM_CHANGWON_JINHAE', 'GYEONGNAM_CHANGWON_MASANHAPPO', 'GYEONGNAM_CHANGWON_MASANHOEWON', 'GYEONGNAM_CHANGWON_SEONGSAN', 'GYEONGNAM_CHANGWON_UICHANG', 'GYEONGNAM_GEOCHANG', 'GYEONGNAM_GEOJE', 'GYEONGNAM_GIMHAE', 'GYEONGNAM_GOSEONG_GN', 'GYEONGNAM_HADONG', 'GYEONGNAM_HAMAN', 'GYEONGNAM_HAMYANG', 'GYEONGNAM_HAPCHEON', 'GYEONGNAM_JINJU', 'GYEONGNAM_MILYANG', 'GYEONGNAM_NAMHAE', 'GYEONGNAM_SACHEON', 'GYEONGNAM_SANCHEONG', 'GYEONGNAM_TONGYEONG', 'GYEONGNAM_UIRYEONG', 'GYEONGNAM_YANGSAN', 'INCHEON_BUPEONG', 'INCHEON_DONG', 'INCHEON_GANGHWA', 'INCHEON_GYEYANG', 'INCHEON_INCHEON_SEO', 'INCHEON_JUNG', 'INCHEON_MICHUHOL', 'INCHEON_NAMDONG', 'INCHEON_ONGJIN', 'INCHEON_YEONSU', 'JEJU_JEJU', 'JEJU_SEOGWIPO', 'JEONBUK_BUAN', 'JEONBUK_GIMJE', 'JEONBUK_GOCHANG', 'JEONBUK_GUNSAN', 'JEONBUK_IKSAN', 'JEONBUK_IMSIL', 'JEONBUK_JANGSU', 'JEONBUK_JEONGEUP', 'JEONBUK_JEONJU_DEOKJIN', 'JEONBUK_JEONJU_WANSAN', 'JEONBUK_JINAN', 'JEONBUK_MUJU', 'JEONBUK_NAMWON', 'JEONBUK_SUNCHANG', 'JEONBUK_WANJU', 'JEONNAM_BOSEONG', 'JEONNAM_DAMYANG', 'JEONNAM_GANGJIN', 'JEONNAM_GOHEUNG', 'JEONNAM_GOKSEONG', 'JEONNAM_GURYE', 'JEONNAM_GWANGYANG', 'JEONNAM_HAENAM', 'JEONNAM_HAMPYEONG', 'JEONNAM_HWASUN', 'JEONNAM_JANGHEUNG', 'JEONNAM_JANGSEONG', 'JEONNAM_JINDO', 'JEONNAM_MOKPO', 'JEONNAM_MUAN', 'JEONNAM_NAJU', 'JEONNAM_SINAN', 'JEONNAM_SUNCHEON', 'JEONNAM_WANDO', 'JEONNAM_YEONGAM', 'JEONNAM_YEONGGWANG', 'JEONNAM_YEOSU', 'SEJONG_SEJONG', 'SEOUL_DOBONG', 'SEOUL_DONGDAEMUN', 'SEOUL_DONGJAK', 'SEOUL_EUNPYEONG', 'SEOUL_GANGBUK', 'SEOUL_GANGDONG', 'SEOUL_GANGNAM', 'SEOUL_GANGSEO', 'SEOUL_GEUMCHEON', 'SEOUL_GURO', 'SEOUL_GWANAK', 'SEOUL_GWANGJIN', 'SEOUL_JONGNO', 'SEOUL_JUNG', 'SEOUL_JUNGRANG', 'SEOUL_MAPO', 'SEOUL_NOWON', 'SEOUL_SEOCHO', 'SEOUL_SEODAEMUN', 'SEOUL_SEONGBUK', 'SEOUL_SEONGDONG', 'SEOUL_SONGPA', 'SEOUL_YANGCHEON', 'SEOUL_YEONGDEUNGPO', 'SEOUL_YONGSAN', 'ULSAN_BUK', 'ULSAN_DONG', 'ULSAN_JUNG', 'ULSAN_NAM', 'ULSAN_ULJU') null,
    group_name    varchar(255) null,
    user_id       bigint null
);

create table review
(
    id              bigint auto_increment
        primary key,
    created_at      datetime(6)  null,
    modified_at     datetime(6)  null,
    content         varchar(255) null,
    course_id       bigint       null,
    review_group_id bigint       null
);

create index idx_review_courseId
    on review (course_id);

create index idx_review_reviewGroupId
    on review (review_group_id);

create table review_group
(
    id                bigint auto_increment
        primary key,
    created_at        datetime(6)  null,
    modified_at       datetime(6)  null,
    course_group_id   bigint       null,
    review_group_name varchar(255) null,
    user_id           bigint       null
);

create index idx_reviewGroup_courseGroupId
    on review_group (course_group_id);

create index idx_reviewGroup_userId
    on review_group (user_id);

create index idx_course_group_city_district
    on course_group (city_code, district_code);

create index idx_course_group_userId
    on course_group (user_id);

create table review_image_meta
(
    review_image_hash_code int          null,
    sequence               int          null,
    status                 tinyint      null,
    created_at             datetime(6)  null,
    id                     bigint auto_increment
        primary key,
    modified_at            datetime(6)  null,
    review_id              bigint       null,
    image_name             varchar(255) null,
    image_url              varchar(255) null,
    original_name          varchar(255) null,
    check (`status` between 0 and 1)
);

create index idx_reviewImageMeta_reviewId
    on review_image_meta (review_id);