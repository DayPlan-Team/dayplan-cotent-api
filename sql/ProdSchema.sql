create table course_groups
(
    id            bigint auto_increment
        primary key,
    created_at    datetime(6)  not null,
    modified_at   datetime(6)  not null,
    city_code     varchar(32)  not null,
    district_code varchar(32)  not null,
    group_name    varchar(255) not null,
    user_id       bigint       not null
);

create table courses
(
    id              bigint auto_increment
        primary key,
    created_at      datetime(6) not null,
    modified_at     datetime(6) not null,
    course_stage    varchar(32) not null,
    course_group_id bigint      not null,
    place_category  varchar(32) not null,
    place_id        bigint      not null,
    step            int         not null,
    is_visited      bit         not null
);

create table hashtags
(
    id          bigint auto_increment
        primary key,
    hashtag     varchar(50) not null,
    created_at  datetime(6) not null,
    modified_at datetime(6) not null
);

create table review_groups
(
    id                bigint auto_increment
        primary key,
    created_at        datetime(6)  not null,
    modified_at       datetime(6)  not null,
    course_group_id   bigint       not null,
    review_group_name varchar(255) not null,
    user_id           bigint       not null
);

create table review_hashtags
(
    hashtag_id  bigint      not null,
    created_at  datetime(6) not null,
    id          bigint auto_increment
        primary key,
    modified_at datetime(6) not null,
    review_id   bigint      not null
);

create table review_image_metas
(
    review_image_hash_code int          not null,
    sequence               int          not null,
    status                 varchar(32)  not null,
    created_at             datetime(6)  not null,
    id                     bigint auto_increment
        primary key,
    modified_at            datetime(6)  not null,
    review_id              bigint       not null,
    image_name             varchar(255) not null,
    image_url              text         not null,
    original_name          varchar(255) not null
);

create table reviews
(
    id              bigint auto_increment
        primary key,
    created_at      datetime(6) not null,
    modified_at     datetime(6) not null,
    content         text        not null,
    course_id       bigint      not null,
    review_group_id bigint      not null
);


create table district_metrics
(
    id              bigint auto_increment
        primary key,
    created_at      datetime(6) not null,
    modified_at     datetime(6) not null,
    district_code   varchar(32) not null,
    visited_count   bigint      not null,
    review_count    bigint      not null,
    recommend_count bigint      not null
);

create index idx__course_groups_city_district
    on course_groups (city_code, district_code);

create index idx__course_groups_user_id
    on course_groups (user_id);

create index idx__courses_group_id
    on courses (course_group_id);

create index idx__review_groups_course_group_id
    on review_groups (course_group_id);

create index idx__review_groups_user_id
    on review_groups (user_id);

create index idx__review_image_metas_review_id
    on review_image_metas (review_id);

create index idx__reviews_course_id
    on reviews (course_id);

create index idx__reviews_review_group_id
    on reviews (review_group_id);

create index idx__district_metrics_district_code
    on district_metrics (district_code);

