<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的生活</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
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
                                                <input type="text" placeholder="${type.typeDesc}">
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
                                                <input type="text" placeholder="${type.typeDesc}">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                    </#list>
                        <!--<div class="card-footer">卡脚</div>-->
                    </li>
                    <li class="card">
                        <div class="card-header">卡头</div>
                        <div class="card-content">
                            <div class="card-content-inner">卡内容</div>
                        </div>
                        <div class="card-footer">卡脚</div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <div class="row">
                    <div class="col-50">
                        <a href="#" class="button button-big button-fill button-danger">取消</a>
                    </div>
                    <div class="col-50">
                        <a href="#" class="button button-big button-fill button-success">提交</a>
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
</script>
<script type='text/javascript' src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
</body>
</html>