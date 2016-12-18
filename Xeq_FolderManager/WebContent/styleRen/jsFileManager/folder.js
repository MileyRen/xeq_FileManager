/**
 * 
 */
function delbulk() {
	var str = "delbulk.action?";
	var i=0;
	$("input[class='delbulk']:checked").each(function() {
		var temp = $(this).val();
		var strArray = new Array(); 
		strArray  = temp.split("[arr]");
		str+="id["+i+"]="+strArray[1]+"&type["+i+"]="+strArray[2]+"&name["+i+"]="+strArray[3]+"&";
		i++;
	});
	str+="delsize="+i;
	alert(str);
	var temp = document.createElement("form");
	temp.action =str;
	temp.method = "post";
	temp.style.display = "none";
	document.body.appendChild(temp);
	temp.submit();
}

function movebulk() {
	$("input[name='delbulk']:checked").each(function() {
	});
}

// 全选和全不选（第一个参数为复选框名称，第二个参数为是全选还是全不选）
function allCheck(name, boolValue) {
	var allvalue = document.getElementsByClassName(name);
	for (var i = 0; i < allvalue.length; i++) {
		if (allvalue[i].type == "checkbox")
			allvalue[i].checked = boolValue;
	}
}

// 反选 参数为复选框名称
function reserveCheck(name) {
	var revalue = document.getElementsByClassName(name);
	for (i = 0; i < revalue.length; i++) {
		if (revalue[i].checked == true)
			revalue[i].checked = false;
		else
			revalue[i].checked = true;
	}
}

function prom(value) {
	document.getElementById("fromId").value = value;
}
function open() {
	$('#win_move').window('open');
}

function getValue(value) {
	document.getElementById("toPathId").value = value;
}

function getValueTest(value) {
	alert(value);
	document.getElementById("test").value = value;

}

$(document).ready(function() {

	/* 弹出创建文件夹窗口 */
	$("#btncreate").click(function() {
		$("#win_add").window("open");
	});
	/* 弹出上传文件窗口 */
	$("#btnupload").click(function() {
		$("#win_upload").window("open");
	});
});

function del() {
	if (confirm(' ARE YOU SURE DELETE THE FOLDER AND FILES IN THE FOLDER?')) {
		return true;
	} else {
		return false;
	}
}

function post(id) {
	var temp = document.createElement("form");
	temp.action = "folderlist.action?parentFolderId=" + id;
	temp.method = "post";
	temp.style.display = "none";
	document.body.appendChild(temp);
	temp.submit();
	return temp;
}

function into(id) {
	var temp = document.createElement("form");
	temp.action = "pageList.action?pageTag=1&parentFolderId=" + id;
	temp.method = "post";
	temp.style.display = "none";
	document.body.appendChild(temp);
	temp.submit();
	return temp;
}
$('input[id=lefile]').change(function() {
	$('#photoCover').val($(this).val());
});
/** 动态上传文件 */
function addMore() {
	var div = document.getElementById("more");

	var br = document.createElement("br");
	var input = document.createElement("input");
	var button = document.createElement("input");

	input.type = "file";
	input.name = "uploadFiles";
	input.className = "file";

	button.type = "button";
	button.value = "Delete";
	button.onclick = function() {
		div.removeChild(br);
		div.removeChild(input);
		div.removeChild(button);
		div.removeChild(br);
	}

	div.appendChild(br);
	div.appendChild(input);
	div.appendChild(button);
	div.appendChild(br);

}

function addMore_old() {
	var div = document.getElementById("more");
	var div1 = document.createElement("div");
	div.setAttribute("class", "input-append");
	var br = document.createElement("br");
	var text = "Browse"
	var input = document.createElement("input");
	input.id = "lefile";
	input.type = "file";
	input.name = "uploadFiles";

	var span = document.createElement("span");
	span.className = "glyphicon glyphicon-folder-open";
	var a = document.createElement("a");
	a.setAttribute("class", "btn");
	a.onclick = "$(\'input[id=lefile]\').click();";
	a.appendChild(span);
	a.innerHTML = text;

	var input2 = document.createElement("input");
	input2.id = "photoCover";
	input2.setAttribute("class", "input-large");
	input2.type = "text";
	input2.style = "height: 30px;";

	div.appendChild(br);
	div.appendChild(input);
	div.appendChild(a);
	div.appendChild(input2);
	div.appendChild(br);
	document.getElementById("a").onclick = "$(\'input[id=lefile]\').click();";
	// <input id="lefile" type="file" style="display: none">
	// <div class="input-append">
	// <a class="btn" onclick="$(\'input[id=lefile]\').click();">
	// <span class="glyphicon glyphicon-folder-open"></span> Browse
	// </a>
	// <input id="photoCover" class="input-large" type="text" style="height:
	// 30px;" readOnly="true">
	// </div>
	// }
	$('input[id=lefile]').change(function() {
		$('#photoCover').val($(this).val());
	});
}
