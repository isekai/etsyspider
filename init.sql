create table etsy_spider_category
(
    id varchar(36) not null comment '分类id'
 primary key,
    name varchar(100) null comment '分类名称',
    url varchar(300) not null unique comment 'url',
    parent varchar(300) null comment '父分类',
    leaf tinyint not null default 0 comment '是否是叶子类',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    modification_time datetime null on update CURRENT_TIMESTAMP comment '更新时间'
)
    DEFAULT CHARSET=utf8mb4
    comment '分类';

create table etsy_spider_product
(
    id varchar(36) not null comment '产品id' primary key,
    data_id varchar(30) not null comment '原id',
    url varchar(300) null comment 'url',
    name varchar(500) null comment '产品名',
    category varchar(300) not null comment '分类',
    img varchar(300) null comment '图片',
    logo varchar(300) null comment 'logog',
    description text null comment '描述',
    brand varchar(100) null comment '产品品牌',
    price_currency varchar(30) null comment '货币',
    low_price decimal(10, 2) null comment '折扣',
    high_price decimal(10, 2) null comment '原价',
    rating_value decimal(10, 5) null comment '点赞数量',
    rating_count int null comment '点赞数量',
    review_count int null comment '评论数量',
    property text null comment '属性',
    craft varchar(100) null comment '工艺',
    raw_material varchar(500) null comment '原材料',
    keyword text null comment '关键词',
    img_list text null comment '图片队列',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    modify_time datetime null on update CURRENT_TIMESTAMP comment '更新时间'
)
    DEFAULT CHARSET=utf8mb4
    comment '商品';

create table etsy_spider_raw_material
(
    id varchar(36) not null comment '原材料id' primary key,
    name varchar(100) null comment '材料名称',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    modification_time datetime null on update CURRENT_TIMESTAMP comment '更新时间'
)
    DEFAULT CHARSET=utf8mb4
    comment '原材料';

create table etsy_spider_log
(
    id varchar(36) not null comment '日志id' primary key,
    url varchar(300) null comment 'url',
    type tinyint not null default 0 comment '错误类型',
    message text null comment '日志信息',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    modify_time datetime null on update CURRENT_TIMESTAMP comment '更新时间'
)
    DEFAULT CHARSET=utf8mb4
    comment '日志';
