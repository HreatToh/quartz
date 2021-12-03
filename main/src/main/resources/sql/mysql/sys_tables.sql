#------------------------------------------ 系统配置表 ------------------------------------------
create table sys_config_info
(
    item_id      varchar(200)  not null comment '配置id'
        primary key,
    item_name    varchar(200)  not null comment '配置项名称',
    item_val     varchar(2000)  null comment '配置值',
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
    comm_v2      varchar(200)  null comment '扩展字段2',
    comm_v3      varchar(200)  null comment '扩展字段3',
    comm_v4      varchar(200)  null comment '扩展字段4',
    comm_v5      varchar(200)  null comment '扩展字段5'
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
    comm_v2      varchar(200)  null comment '扩展字段2',
    comm_v3      varchar(200)  null comment '扩展字段3',
    comm_v4      varchar(200)  null comment '扩展字段4',
    comm_v5      varchar(200)  null comment '扩展字段5',
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
    comm_v2      varchar(200)   null comment '扩展字段2',
    comm_v3      varchar(200)   null comment '扩展字段3',
    comm_v4      varchar(200)   null comment '扩展字段4',
    comm_v5      varchar(200)   null comment '扩展字段5'
)
comment '异常信息表';
create unique index sys_exception_info_exp_id_uindex on sys_exception_info (exp_id);
alter table sys_exception_info add constraint sys_exception_info_pk primary key (exp_id);

#------------------------------------------ 异常信息表 ------------------------------------------

#------------------------------------------ 菜单信息表 ------------------------------------------
create table sys_menu_info
(
    sys_id 			varchar(10) not null comment '系统编号',
    menu_id 		varchar(20) not null comment '菜单编号',
    menu_name 		varchar(200) 	null comment '菜单名称',
    menu_type 		varchar(10) 	null comment '菜单类型',
    menu_icon 		varchar(50) 	null comment '菜单图标',
    menu_url 		varchar(200) 	null comment '请求地址',
    menu_parent_id 	varchar(20) 	null comment '上级菜单编号',
    menu_path 		varchar(200) 	null comment '上级路径',
    menu_order 		varchar(10) 	null comment '序号',
    menu_class 		varchar(200) 	null comment '菜单初始化Service名称',
    menu_target 	varchar(10) 	null comment '窗口渲染模式',
    comm_cdate   	varchar(10)   	null comment '创建时间',
    comm_udate   	varchar(10)   	null comment '更新时间',
    comm_cuser   	varchar(50)   	null comment '创建人',
    comm_uuser   	varchar(50)   	null comment '修改人',
    comm_delfalg 	varchar(2)    	null comment '是否删除标记',
    comm_v1      	varchar(200)  	null comment '扩展字段1',
    comm_v2      	varchar(200)  	null comment '扩展字段2',
    comm_v3      	varchar(200)  	null comment '扩展字段3',
    comm_v4      	varchar(200)  	null comment '扩展字段4',
    comm_v5      	varchar(200)  	null comment '扩展字段5',
    constraint sys_menu_info_pk primary key (sys_id , menu_id)
)comment '菜单表信息表';

#------------------------------------------ 菜单信息表 ------------------------------------------

#------------------------------------------ 子系统信息表 ------------------------------------------
create table sys_system_info (
     sys_id          varchar(10)     null comment '系统名称',
     sys_name        varchar(100)    null comment '系统名称',
     sys_url         varchar(100)    null comment '系统接口地址',
     sys_parent_id   varchar(10)     null comment '系统父级菜单id',
     sys_path        varchar(200)    null comment '系统依赖路径',
     sys_icon        varchar(30)     null comment '系统图标',
     sys_target      varchar(10)     null comment '系统开窗口模式',
     sys_order       varchar(10)     null comment '系统排序',
     comm_cdate   	 varchar(10)   	 null comment '创建时间',
     comm_udate   	 varchar(10)   	 null comment '更新时间',
     comm_cuser   	 varchar(50)   	 null comment '创建人',
     comm_uuser   	 varchar(50)   	 null comment '修改人',
     comm_delfalg 	 varchar(2)    	 null comment '是否删除标记',
     comm_v1      	 varchar(200)  	 null comment '扩展字段1',
     comm_v2      	 varchar(200)  	 null comment '扩展字段2',
     comm_v3      	 varchar(200)  	 null comment '扩展字段3',
     comm_v4      	 varchar(200)  	 null comment '扩展字段4',
     comm_v5      	 varchar(200)  	 null comment '扩展字段5',
    constraint sys_system_info_pk primary key (sys_id)
)comment '子系统信息表';
create unique index sys_system_info_sys_id_uindex on sys_system_info (sys_id);
#------------------------------------------ 子系统信息表 ------------------------------------------

#------------------------------------------ 用户信息表 ------------------------------------------
create table sys_user_info (
    user_id         varchar(100) not null comment '用户id',
    user_enname     varchar(100)     null comment '用户英文名',
    user_chname     varchar(100)     null comment '用户中文名',
    user_password   varchar(1000)    null comment '用户密码',
    user_sex        varchar(100)     null comment '性别',
    user_birthdate  varchar(10)      null comment '出生年月',
    user_tel        varchar(11)      null comment '电话号码',
    user_email      varchar(100)     null comment '用户邮箱',
    user_addr       varchar(200)     null comment '用户地址',
    user_is_first   varchar(2)       null comment '是否第一次登录: Y-是 N-否',
    user_login_date varchar(10)      null comment '登录时间',
    user_error_num  varchar(10)		 null comment '密码错误次数',
    user_is_lock    varchar(2)       null comment '是否锁定: Y-是 N-否',
    user_is_onlion  varchar(2)       null comment '是否在线: Y-是 N-否',
    comm_cdate      varchar(10)      null comment '创建时间',
    comm_udate      varchar(10)      null comment '更新时间',
    comm_cuser      varchar(50)      null comment '创建人',
    comm_uuser      varchar(50)      null comment '修改人',
    comm_delfalg    varchar(2)       null comment '是否删除标记',
    comm_v1         varchar(200)     null comment '扩展字段1',
    comm_v2         varchar(200)     null comment '扩展字段2',
    comm_v3         varchar(200)     null comment '扩展字段3',
    comm_v4         varchar(200)     null comment '扩展字段4',
    comm_v5         varchar(200)     null comment '扩展字段5'
) comment '用户信息表';

create unique index sys_user_user_id_uindex on sys_user_info (user_id);

alter table sys_user_info add constraint sys_user_pk primary key (user_id);
alter table sys_user_info change user_login_date user_login_time varchar(30) null comment '登录时间';
#------------------------------------------ 用户信息表 ------------------------------------------

#------------------------------------------ 系统日志表 ------------------------------------------
create table sys_log_info
(
    sys_id    		varchar(20)   	 null     comment '系统编号',
    log_id 	  		varchar(32)   	 not null comment '日志主键',
    log_level 		varchar(10)   	 null     comment '日志级别',
    log_type  		varchar(50)   	 null     comment '日志类型',
    log_msg   		varchar(2000) 	 null     comment '日志信息',
    log_other 		varchar(200)  	 null     comment '日志其他信息',
    log_time        varchar(30)  	 null     comment '日志创建时间',
    comm_cdate      varchar(10)      null     comment '创建时间',
    comm_udate      varchar(10)      null     comment '更新时间',
    comm_cuser      varchar(50)      null     comment '创建人',
    comm_uuser      varchar(50)      null     comment '修改人',
    comm_delfalg    varchar(2)       null     comment '是否删除标记',
    comm_v1         varchar(200)     null     comment '扩展字段1',
    comm_v2         varchar(200)     null     comment '扩展字段2',
    comm_v3         varchar(200)     null     comment '扩展字段3',
    comm_v4         varchar(200)     null     comment '扩展字段4',
    comm_v5         varchar(200)     null     comment '扩展字段5'
)comment '系统日志表';
create unique index sys_log_info_log_id_uindex on sys_log_info (log_id);
alter table sys_log_info add constraint sys_log_info_pk primary key (log_id);
#------------------------------------------ 系统日志表 ------------------------------------------