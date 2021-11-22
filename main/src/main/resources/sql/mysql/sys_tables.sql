#------------------------------------------ 系统配置表 ------------------------------------------
create table sys_config_info
(
    item_id      varchar(200)  not null comment '配置id'
        primary key,
    item_name    varchar(200)  not null comment '配置项名称',
    item_val     varchar(200)  null comment '配置值',
    item_defval  varchar(200)  null comment '默认值',
    item_type    varchar(20)   null comment '配置类型',
    item_conf    varchar(2000) null comment '配置信息',
    item_desc    varchar(500)  null comment '配置描述',
    item_group   varchar(200)  null comment '配置组',
    item_order   varchar(5)    null comment '序号',
    item_enable  varchar(2)    null comment '是否启用',
    comm_cdate   varchar(10)   null comment '创建时间',
    comm_udate   varchar(10)   null comment '更新时间',
    comm_cuser   varchar(50)   null comment '创建人',
    comm_uuser   varchar(50)   null comment '修改人',
    comm_delfalg varchar(2)    null comment '是否删除标记',
    comm_v1      varchar(200)  null comment '扩展字段1',
    comm_v2      varchar(200)  null comment '扩展字段1',
    comm_v3      varchar(200)  null comment '扩展字段1',
    comm_v4      varchar(200)  null comment '扩展字段1',
    comm_v5      varchar(200)  null comment '扩展字段1'
)
comment '系统配置表';
#------------------------------------------ 系统配置表 ------------------------------------------

#------------------------------------------ 子系统配置表 ------------------------------------------
-- auto-generated definition
create table sys_subconfig_info (
    sys_id       varchar(200)  not null comment '系统id',
    item_id      varchar(200)  not null comment '配置id',
    item_name    varchar(200)  not null comment '配置项名称',
    item_val     varchar(200)  null comment '配置值',
    item_defval  varchar(200)  null comment '默认值',
    item_type    varchar(20)   null comment '配置类型',
    item_conf    varchar(2000) null comment '配置信息',
    item_desc    varchar(500)  null comment '配置描述',
    item_group   varchar(200)  null comment '配置组',
    item_order   varchar(5)    null comment '序号',
    item_enable  varchar(2)    null comment '是否启用',
    comm_cdate   varchar(10)   null comment '创建时间',
    comm_udate   varchar(10)   null comment '更新时间',
    comm_cuser   varchar(50)   null comment '创建人',
    comm_uuser   varchar(50)   null comment '修改人',
    comm_delfalg varchar(2)    null comment '是否删除标记',
    comm_v1      varchar(200)  null comment '扩展字段1',
    comm_v2      varchar(200)  null comment '扩展字段1',
    comm_v3      varchar(200)  null comment '扩展字段1',
    comm_v4      varchar(200)  null comment '扩展字段1',
    comm_v5      varchar(200)  null comment '扩展字段1',
    primary key (sys_id, item_id)
)
comment '子系统配置表';
#------------------------------------------ 子系统配置表 ------------------------------------------

#------------------------------------------ 异常信息表 ------------------------------------------
create table sys_exception_info
(
    exp_id       varchar(40)    null comment '主键id',
    exp_ip       varchar(20)    null comment '服务器',
    exp_port     varchar(10)    null comment '端口',
    exp_class_name varchar(200) null comment '类名',
    exp_name     varchar(20)    null comment '名称',
    exp_msg      text           null comment '错误详情',
    exp_time     varchar(20)    null comment '错误时间',
    comm_cdate   varchar(10)    null comment '创建时间',
    comm_udate   varchar(10)    null comment '更新时间',
    comm_cuser   varchar(50)    null comment '创建人',
    comm_uuser   varchar(50)    null comment '修改人',
    comm_delfalg varchar(2)     null comment '是否删除标记',
    comm_v1      varchar(200)   null comment '扩展字段1',
    comm_v2      varchar(200)   null comment '扩展字段1',
    comm_v3      varchar(200)   null comment '扩展字段1',
    comm_v4      varchar(200)   null comment '扩展字段1',
    comm_v5      varchar(200)   null comment '扩展字段1'
);

create unique index sys_exception_info_exp_id_uindex on sys_exception_info (exp_id);
alter table sys_exception_info add constraint sys_exception_info_pk primary key (exp_id);

#------------------------------------------ 异常信息表 ------------------------------------------


