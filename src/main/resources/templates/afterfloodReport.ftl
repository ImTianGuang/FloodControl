<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>汛情录入</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="https://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="https://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
</head>
<body>
<!-- page集合的容器，里面放多个平行的.page，其他.page作为内联页面由路由控制展示 -->
<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示-->
    <div class="page">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <!--<a class="icon icon-me pull-left open-panel"></a>-->
            <h1 class="title">应对与处置</h1>
        </header>
        <!-- 这里是页面内容区 -->
        <div class="content">
            <input id="companyId" value="${companyId}" type="hidden">
            <!-- Text inputs -->
            <div class="list-block cards-list">
                <ul>
                    <li class="card">
                        <div class="card-header">汛情与灾情</div>
                        <#list situationTypeList as type>
                            <div class="card-content">
                                <div class="card-content-inner">
                                    <div class="item-content">
                                        <div class="item-media">
                                            <i class="icon icon-form-name"></i>
                                        </div>
                                        <div class="item-inner">
                                            <div class="item-title label" style="width:55%;">${type.name}</div>
                                            <div class="item-input">
                                                <input type="text" id="${type.id}" name="situations" placeholder="${type.typeDesc}">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>

                        <!--<div class="card-footer">卡脚</div>-->
                    </li>
                    <li class="card">
                        <div class="card-header">应对措施</div>
                        <#list solutionTypeList as type>
                            <div class="card-content">
                                <div class="card-content-inner">
                                    <div class="item-content">
                                        <div class="item-media">
                                            <i class="icon icon-form-name"></i>
                                        </div>
                                        <div class="item-inner">
                                            <div class="item-title label" style="width:55%;">${type.name}</div>
                                            <div class="item-input">
                                                <input type="text" id="${type.id}" name="solutions" placeholder="${type.typeDesc}">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                    </#list>
                        <!--<div class="card-footer">卡脚</div>-->
                    </li>
                    <li class="card">
                        <div class="card-header">其他</div>
                        <div class="card-content">
                            <div class="card-content-inner">
                                <div class="item-content">
                                    <div class="item-media"><i class="icon icon-form-calendar"></i></div>
                                    <div class="item-inner">
                                        <div class="item-title label">发生日期</div>
                                        <div class="item-input">
                                            <input id="floodTime" type="date" placeholder="Birth day" value="2018-07-30">
                                        </div>
                                    </div>
                                </div>

                                <div class="item-content align-top">
                                    <div class="item-media"><i class="icon icon-form-comment"></i></div>
                                    <div class="item-inner">
                                        <div class="item-title label">汛情描述</div>
                                        <div class="item-input">
                                            <textarea id="floodDesc" placeholder="时间 地点 人物 事件 结果"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <#--<div class="card-footer">卡脚</div>-->
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="row">
                    <p><a href="#" class="button button-big button-round" onclick="submitForm()"> 提交 </a></p>
                </div>
            </div>
        </div>
    </div>
    <!-- 其他的单个page内联页（如果有） -->
    <div class="page">...</div>
</div>
<!-- popup, panel 等放在这里 -->
<div class="panel-overlay"></div>
<!-- Left Panel with Reveal effect -->
<div class="panel panel-left panel-reveal">
    <div class="content-block">
        <p>这是一个侧栏</p>
        <p></p>
        <!-- Click on link with "close-panel" class will close panel -->
        <p>
            <a href="#" class="close-panel">关闭</a>
        </p>
    </div>
</div>
<!-- 默认必须要执行$.init(),实际业务里一般不会在HTML文档里执行，通常是在业务页面代码的最后执行 -->
<script>
    $.config = {
        autoInit: true
    }
    
    function submitForm() {
        var companyId = document.getElementById("companyId").value;
        var situations = document.getElementsByName("situations");
        var situationList = [];
        for(var i=0;i<situations.length;i++){
            var situation = {};
            situation.companyId=companyId;
            situation.situationTargetCode=0;
            situation.status=1;
            situation.targetId=situations[i].id;
            situation.targetValue = situations[i].value;
            situationList.push(situation);
        }

        var solutions = document.getElementsByName("solutions");
        var solutionList = [];
        for(var i=0;i<solutions.length;i++){
            var solution = {};
            solution.companyId=companyId;
            solution.situationTargetCode=1;
            solution.status=1;
            solution.targetId=situations[i].id;
            solution.targetValue = situations[i].value;
            solutionList.push(solution);
        }

        var company = {};
        company.companyId = companyId;

        var floodSituation = {};
        floodSituation.companyId=companyId;
        floodSituation.status=1;
        floodSituation.floodDesc=document.getElementById("floodDesc").value;
        floodSituation.floodTime=document.getElementById("floodTime").value;

        var formData = {};
        formData.compnay = company;
        formData.floodSituation= floodSituation;
        formData.situationDetailList=situationList;
        formData.solutionDetailList=solutionList;

        $.ajax({
            method: "POST",
            url: "/manage/updateSituation",
            contentType: 'application/json',
            data:JSON.stringify(formData),
            success: function( data ) {
                console.log(data);
                if (data.ret && data.data) {
                    alert('提交成功');
                    setTimeout("go()",3000);
                } else {
                    if(data.errorMsg) {
                        alert('提交失败:' + data.errorMsg);
                    } else {
                        alert('提交失败:未知错误');
                    }
                }

            }
        });

    }

    function toast(msg, time, classes) {
        $.toast(msg, time, classes);
    }
    function go()
    {
        window.history.go(-1);
    }

</script>

<script type='text/javascript' src='https://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='https://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='https://g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script type='text/javascript' src='https://m.sui.taobao.org/docs-demos/js/toast.js' charset='utf-8'></script>
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.js"></script>
</body>
</html>