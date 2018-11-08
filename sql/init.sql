#街乡镇
Insert into company (name,status,company_group) values ('丰台街道办事处',1, 0);
Insert into company (name,status,company_group) values ('西罗园街道办事处',1, 0);
Insert into company (name,status,company_group) values ('方庄地区办事处',1, 0);
Insert into company (name,status,company_group) values ('太平桥街道办事处',1, 0);
Insert into company (name,status,company_group) values ('东铁营街道办事处',1, 0);
Insert into company (name,status,company_group) values ('右安门街道办事处',1, 0);
Insert into company (name,status,company_group) values ('长辛店街道办事处',1, 0);
Insert into company (name,status,company_group) values ('新村街道办事处',1, 0);
Insert into company (name,status,company_group) values ('卢沟桥街道办事处',1, 0);
Insert into company (name,status,company_group) values ('云岗街道办事处',1, 0);
Insert into company (name,status,company_group) values ('东高地街道办事处',1, 0);
Insert into company (name,status,company_group) values ('南苑街道办事处',1, 0);
Insert into company (name,status,company_group) values ('大红门街道办事处',1, 0);
Insert into company (name,status,company_group) values ('马家堡街道办事处',1, 0);
Insert into company (name,status,company_group) values ('和义街道办事处',1, 0);
Insert into company (name,status,company_group) values ('宛平城地区办事处',1, 0);
Insert into company (name,status,company_group) values ('卢沟桥乡',1, 0);
Insert into company (name,status,company_group) values ('花乡',1, 0);
Insert into company (name,status,company_group) values ('南苑乡',1, 0);
Insert into company (name,status,company_group) values ('长辛店镇',1, 0);
Insert into company (name,status,company_group) values ('王佐镇',1, 0);

#委办局
Insert into company (name,status,company_group) values ('区住建委',1, 1);
Insert into company (name,status,company_group) values ('区城市管理委',1, 1);
Insert into company (name,status,company_group) values ('区规划国土分局',1, 1);
Insert into company (name,status,company_group) values ('区旅游委',1, 1);
Insert into company (name,status,company_group) values ('区民政局',1, 1);
Insert into company (name,status,company_group) values ('区委武装部',1, 1);
Insert into company (name,status,company_group) values ('区房管局',1, 1);
Insert into company (name,status,company_group) values ('区财政局',1, 1);
Insert into company (name,status,company_group) values ('区教委',1, 1);
Insert into company (name,status,company_group) values ('区经信委',1, 1);
Insert into company (name,status,company_group) values ('区公安分局',1, 1);
Insert into company (name,status,company_group) values ('区交通支队',1, 1);
Insert into company (name,status,company_group) values ('区规划分局',1, 1);
Insert into company (name,status,company_group) values ('区农委',1, 1);
Insert into company (name,status,company_group) values ('区水务局',1, 1);
Insert into company (name,status,company_group) values ('区商务委',1, 1);
Insert into company (name,status,company_group) values ('区气象局',1, 1);
Insert into company (name,status,company_group) values ('区卫计委',1, 1);
Insert into company (name,status,company_group) values ('区安全监管局',1, 1);
Insert into company (name,status,company_group) values ('区园林绿化局',1, 1);
Insert into company (name,status,company_group) values ('区民防局',1, 1);
Insert into company (name,status,company_group) values ('区消防支队',1, 1);
Insert into company (name,status,company_group) values ('区供电公司',1, 1);
Insert into company (name,status,company_group) values ('区体育局',1, 1);
Insert into company (name,status,company_group) values ('区城管执法监察局',1, 1);
Insert into company (name,status,company_group) values ('区房屋经营管理中心',1, 1);
Insert into company (name,status,company_group) values ('区环卫中心',1, 1);
Insert into company (name,status,company_group) values ('区城市管理监督指挥中心',1, 1);
Insert into company (name,status,company_group) values ('丰台科技园管委会',1, 1);
Insert into company (name,status,company_group) values ('丽泽金融商务区管委会',1, 1);
Insert into company (name,status,company_group) values ('园博园管理中心',1, 1);
Insert into company (name,status,company_group) values ('北京南站地区管委会',1, 1);

#汛情类型
insert into common_type
(name,common_type_enum,status,create_time,update_time,type_desc)
values
('其他',1,1,0,0,'其他情况'),
('坍塌',1,1,0,0,'请输多少处'),
('坍塌面积',1,1,0,0,'请输入坍塌多少平方米'),
('人员伤亡',1,1,0,0,'请输入伤亡多少人'),
('房屋进水',1,1,0,0,'请输入房屋进水多少间'),
('房屋倒塌',1,1,0,0,'请输入房屋倒塌多少间'),
('普通地下室进水',1,1,0,0,'请输入普通地下室进水多少平米'),
('人防工事雨水被淹',1,1,0,0,'请输入人防工事雨水被淹多少处'),
('超27公分积水断路',1,1,0,0,'请输入超27公分积水断路多少处'),
('泡车',1,1,0,0,'请输入泡车多少辆'),
('道路坍塌',1,1,0,0,'请输入道路坍塌多少处'),
('道路坍塌面积',1,1,0,0,'请输入道路坍塌多少平方米'),
('（15-27公分）积水路段',1,1,0,0,'请输入（15-27公分）积水路段多少处'),
('各类市政管线受损',1,1,0,0,'请输入各类市政管线管线受损多少处'),
('泥石流',1,1,0,0,'请输入泥石流多少处'),
('不稳定斜坡',1,1,0,0,'请输入不稳定斜坡多少处'),
('关闭景区（公园）',1,1,0,0,'请输入关闭景区（公园）多少处'),
('疏散旅客',1,1,0,0,'请输入疏散旅客多少人次'),
('疏散旅客',1,1,0,0,'请输入疏散旅客多少人次'),
('树木倒伏',1,1,0,0,'请输入树木倒伏多少处'),
('备勤人数',1,1,0,0,'请输入备勤多少人'),
('出动人数',1,1,0,0,'请输入出动多少人'),
('备勤物资麻袋、编织袋',1,1,0,0,'请输入备勤物资麻袋、编织袋多少条'),
('备勤物抽水泵',1,1,0,0,'请输入备勤物资抽水泵多少台'),
('应急车辆',1,1,0,0,'请输入应急车辆多少辆')
;
#解决措施
insert into common_type
(name,common_type_enum,status,create_time,update_time,type_desc)
values
('出动人数',2,1,0,0,'请输入出动多少人'),
('采取措施',2,1,0,0,'请输入采取措施'),
('人员伤亡',2,1,0,0,'请输入伤亡多少人'),
('应急处置',2,1,0,0,'请输入应急处置多少处'),
('抽排地下室、院落进水',2,1,0,0,'请输入抽排地下室、院落进水多少方'),
('转移',2,1,0,0,'请输入转移多少人次'),
('重点布控地点',2,1,0,0,'请输入重点布控地点多少处'),
('主动断路',2,1,0,0,'请输入主动断路条次'),
('道路恢复',2,1,0,0,'请输入道路恢复多少条'),
('发布预警',2,1,0,0,'请输入发布预警多少次'),
('派出专家工作组',2,1,0,0,'请输入派出专家工作多少组'),
('派出专家工作人数',2,1,0,0,'请输入派出专家工作多少人'),
('避险转移（乡）',2,1,0,0,'请输入避险转移多少个乡（镇）'),
('避险转移（村）',2,1,0,0,'请输入避险转移多少个村'),
('避险转移（人）',2,1,0,0,'请输入避险转移人次'),
('解救被困人员',2,1,0,0,'请输入解救被困人员多少人'),
('包村干部出动',2,1,0,0,'请输入包村干部出动多少人'),
('疏散游客',2,1,0,0,'请输入疏散游客多少人'),
('出动救援人员',2,1,0,0,'请输入出动救援人员多少人次'),
('主动关闭旅游企业',2,1,0,0,'请输入主动关闭旅游企业多少个'),
('解救被困旅客',2,1,0,0,'请输入解救被困旅客多少人'),
('填表人及电话',2,1,0,0,'请输入填表人及电话'),
('现场负责人和电话',2,1,0,0,'请输入现场负责人和电话'),
('在岗领导',2,1,0,0,'请输入姓名及职务')
;

#物资
insert into common_type
(name,common_type_enum,status,create_time,update_time,type_desc)
values
('抢险人员',3,1,0,0,'请输入多少人');

















