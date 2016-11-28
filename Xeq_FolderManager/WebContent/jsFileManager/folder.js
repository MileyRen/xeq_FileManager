/**
 * 
 */

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

/** 动态上传文件 */
function addMore() {
	var div = document.getElementById("more");

	var br = document.createElement("br");
	var input = document.createElement("input");
	var button = document.createElement("input");

	input.type = "file";
	input.name = "uploadFiles";
	input.placeholder = "Please select a file..."

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

	/* 上传按钮动态出现 */
	var divMore = document.getElementById("more").innerHTML;
	if (divMore == null || divMore == "") {
		document.getElementById("uploadBtn").style.display = "none";
	} else {
		document.getElementById("uploadBtn").style.display = "block";
	}

}
