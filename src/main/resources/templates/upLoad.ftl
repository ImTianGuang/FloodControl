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
    .s_btn_wr {
        width: 102px;
        height: 38px;
        border: 1px solid #38f;
        border-bottom: 1px solid #2e7ae5;
        background-color: #38f;
    }

    .btn {
        cursor: pointer;
        width: 102px;
        height: 38px;
        line-height: 38px;
        padding: 0;
        border: 0;
        background: none;
        background-color: #38f;
        font-size: 16px;
        color: white;
        box-shadow: none;
        font-weight: normal;
    }

    .file {
        position: relative;
        display: inline-block;
        background: #D0EEFF;
        border: 1px solid #99D3F5;
        border-radius: 4px;
        padding: 4px 12px;
        overflow: hidden;
        color: #1E88C7;
        text-decoration: none;
        text-indent: 0;
        line-height: 20px;
    }
    .file input {
        position: absolute;
        font-size: 100px;
        right: 0;
        top: 0;
        opacity: 0;
    }
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

</style>

<body>
<div class="head_wrapper">
    <div class="s-p-top"></div>
    <form id="form1" action="${uploadPath}" target="frame1" method="post" enctype="multipart/form-data" style="text-align: center;">
        <label  class="btn" style="overflow: hidden">选择文件
            <input style="opacity: 0; display: none; font-size: 28px;" type="file" name="file" id="file" onchange="bindFileName">
        </label>
        <input type="text" style="width: 350px" name="fileName" id="fileName"/>
        <input type="text" name="extraData" value="${ext}" hidden="true">
        <label onclick="upload()" class="btn">上传</label>
    </form>
</div>

<iframe name="frame1" frameborder="0" height="40"></iframe>
<!-- 其实我们可以把iframe标签隐藏掉 -->
<script type="text/javascript">
    function upload() {
        $("#form1").submit();
        var t = setInterval(function() {
            //获取iframe标签里body元素里的文字。即服务器响应过来的"上传成功"或"上传失败"
            var word = $("iframe[name='frame1']").contents().find("body").text();
            if(word != "") {
//						alert(word); //弹窗提示是否上传成功
//						clearInterval(t); //清除定时器
            }
        }, 1000);
    }
    function bindFileName (){
        var filePath=$(this).val();
        console.log(filePath)
        var arr=filePath.split('\\');
        var fileName=arr[arr.length-1];
        $(".fileName").html(fileName);
    }
</script>
</body>

</html>
