<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.js"></script>
</head>

<style type="text/css">
    * { font-size:12px; }

    .file:hover {
        background: #AADFFD;
        border-color: #78C3F3;
        color: #004974;
        text-decoration: none;
    }
    .head_wrapper {
        position: relative;
        height: 38.2%;
        min-height: 293px;
        width: 1000px;
        margin: 0 auto;
        _height: 293px;
    }
    .s-p-top {
        height: 61.8%;
        min-height: 181px;
        position: relative;
        z-index: 0;
        text-align: center;
    }

    .btn{
        width:70px; color:#fff;background-color:#3598dc; border:0 none;height:28px; line-height:16px!important;cursor:pointer;
        padding: 2px 6px;
        margin-right: 4px;
    }
    .btn:hover{background-color:#63bfff;color:#fff;}

</style>

<body>

<div class="head_wrapper">
    <div class="s-p-top">

    </div>
    <h1 style="text-align: center;font-size: 16px;">${title}</h1>
    <h2 style="text-align: center">最大上传大小:5MB,支持上传格式:jpeg,jpg,png,pdf,doc. 链接有效期10分钟</h2>
    <form id="form1" action="${uploadPath}" target="frame1" method="post" enctype="multipart/form-data" style="text-align: center;">

        <input type="text" style="width: 350px;margin-left: 6px;" name="fileName" id="fileName"/>
        <input type="text" name="extraData" value="${ext}" hidden="true">
        <label  class="btn" style="overflow: hidden">选择文件
            <input style="opacity: 0; display: none; font-size: 28px;" type="file" name="file" id="file" onchange="document.getElementById('fileName').value=this.files[0].name">
        </label>
        <label onclick="upload()" class="btn">上传</label>
    </form>
</div>

<iframe name="frame1" frameborder="0" height="40" hidden="true"></iframe>
<!-- 其实我们可以把iframe标签隐藏掉 -->
<script type="text/javascript">
    function upload() {
        $("#form1").submit();
        var t = setInterval(function() {
            //获取iframe标签里body元素里的文字。即服务器响应过来的"上传成功"或"上传失败"
            var word = $("iframe[name='frame1']").contents().find("body").text();
            if(word != "") {
						alert(word); //弹窗提示是否上传成功
						clearInterval(t); //清除定时器
            }
        }, 1000);
    }
    function bindFileName () {
        var filePath=$("#file").val();
        console.log(filePath)
        var arr=filePath.split('\\');
        var fileName=arr[arr.length-1];
        $(".fileName").html(fileName);
    }
</script>
</body>

</html>
