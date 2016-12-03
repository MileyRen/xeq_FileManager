<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js">
	
</script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js">
	
</script>

<body>
	error!
	<div class="container">
	<!-- 	<script type="text/javascript">
			document
					.write('<input id="lefile" type="file" style="display: none"><div class="input-append"><a class="btn"onclick="$(\'input[id=lefile]\').click();"><span class="glyphicon glyphicon-folder-open"></span> Browse</a><input id="photoCover" class="input-large" type="text"  style="height: 30px;" readOnly="true"></div>');
		</script> -->

		<input id="lefile" type="file" style="display: none">
		<div class="input-append">
			<a class="btn" onclick="$('input[id=lefile]').click();"><span
				class="glyphicon glyphicon-folder-open"></span> Browse</a><input
				id="photoCover" class="input-large" type="text"
				style="height: 30px;" readOnly="true">
		</div>
		<script type="text/javascript">
			$('input[id=lefile]').change(function() {
				$('#photoCover').val($(this).val());
			});
		</script>
	</div>
</body>

</html>