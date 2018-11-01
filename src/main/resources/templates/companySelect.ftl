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
            <h1 class="title">单位选择</h1>
        </header>

        <!-- 工具栏 -->
        <!--<nav class="bar bar-tab">-->
        <!--<a class="tab-item external active" href="#">-->
        <!--<span class="icon icon-home"></span>-->
        <!--<span class="tab-label">首页</span>-->
        <!--</a>-->
        <!--<a class="tab-item external" href="#">-->
        <!--<span class="icon icon-star"></span>-->
        <!--<span class="tab-label">收藏</span>-->
        <!--</a>-->
        <!--<a class="tab-item external" href="#">-->
        <!--<span class="icon icon-settings"></span>-->
        <!--<span class="tab-label">设置</span>-->
        <!--</a>-->
        <!--</nav>-->

        <!-- 这里是页面内容区 -->
        <div class="content">
            <div class="buttons-tab">
                <!--<a href="#tab1" class="tab-link active button">全部</a>-->
                <a href="#tab1" class="tab-link active button">街乡镇</a>
                <a href="#tab2" class="tab-link button">委办局</a>
            </div>
            <div class="content-block">
                <div class="tabs">
                    <div id="tab1" class="tab active">
                        <div class="list-block">
                            <ul>
                                <#list companyList as company>
                                    <#if company.companyGroup =0>
                                        <a class="item-title" href="/manage/h5/floodReport?companyId=${company.id}" style="color:#6d6d72;">
                                        <li class="item-content item-link">
                                            <div class="item-media"><i class="icon icon-f7"></i></div>
                                            <div class="item-inner">
                                                <div class="item-title">${company.name}</div>
                                            </div>
                                        </li>
                                        </a>
                                    </#if>
                                </#list>

                            </ul>
                        </div>
                    </div>
                    <div id="tab2" class="tab">
                        <div class="list-block">
                            <ul>
                                <#list companyList as company>
                                    <#if company.companyGroup =1>
                                    <a class="item-title" href="/manage/h5/floodReport?companyId=${company.id}" style="color:#6d6d72;">
                                        <li class="item-content item-link external" >
                                            <div class="item-media"><i class="icon icon-f7"></i></div>
                                            <div class="item-inner">
                                                <div class="item-title">${company.name}</div>
                                            </div>
                                        </li>
                                    </a>
                                    </#if>
                                </#list>
                            </ul>
                        </div>
                    </div>
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
        <p><a href="#" class="close-panel">关闭</a></p>
    </div>
</div>


<!-- 默认必须要执行$.init(),实际业务里一般不会在HTML文档里执行，通常是在业务页面代码的最后执行 -->
<script>
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

</body>
</html>