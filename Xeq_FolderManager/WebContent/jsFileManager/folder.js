/**
 * 
 */


$(document).ready(function(){

	/*弹出创建文件夹窗口*/
	$("#btncreate").click(function(){
		$("#win_add").window("open");
	});
	/*弹出上传文件窗口*/
	$("#btnupload").click(function(){
		$("#win_upload").window("open");
	});
});