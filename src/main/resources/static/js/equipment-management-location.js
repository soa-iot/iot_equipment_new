//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});


/**
* 获取url地址的参数
*/
function GetQueryString(name)
{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null) return $.trim(decodeURI(r[2])); return null;
}

//加载layui内置模块
layui.use(['jquery','form','layer','table','excel'], function(){
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,$ = layui.$ //使用jQuery
	var excel = layui.excel;
	
	/**
	 * 设备信息展示表
	 */
	var equipmentTable = table.render({
		elem: '#equipmentInfo',
		method: 'get',
		url: '/iot_equipment/equipmentinfo/show',
		/*toolbar: '#toolbarBtn',
		defaultToolbar: [''],*/
		totalRow: true,
		page: true,   //开启分页
		cellMinWidth: 70, √
		request: {
		    pageName: 'page' //页码的参数名称，默认：page
		    ,limitName: 'limit' //每页数据量的参数名，默认：limit
		},
		parseData: function(res){ //res 即为原始返回的数据
		    return {
		      "code": res.code, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "count": res.count, //解析数据长度
		      "data": res.data      //解析数据列表
		    };
		},
		cols: [[
			{type:'radio'}, 
			{field:'equID', title:'工作介质', hide:true, align:'center'},
			{field:'welName', title:'装置列名', align:'center'},    //, templet:"<div>{{layui.util.toDateString(d.applydate,'yyyy-MM-dd HH:mm:ss')}}</div>"
			{field:'welUnit', title:'装置单元', align:'center'}, 
			{field:'equMemoOne', title:'设备类别', align:'center'},
			{field:'equPositionNum', title:'设备位号', align:'center'},
			{field:'equName', title:'设备名称', align:'center'},
			{field:'equModel', title:'规格型号', align:'center'},
			{field:'equProducDate', title:'生产日期', align:'center'},
			{field:'equCommissionDate', title:'投运日期', align:'center'},
			{field:'material', title:'设备材质', align:'center'},
			{field:'meduimType', title:'工作介质', align:'center'}]]
	});
	
	/**
	 * onblur失去焦点事件
	 */
	$(".layui-form-select dd").click(function(){
		tableReload();
	});
	$("#equMemoOne").blur(function(){
		tableReload();
	});
	$("#equPositionNum").blur(function(){
		tableReload();
	});
	$("#equName").blur(function(){
		tableReload();
	});
	
	
	/**
	 * 表重新加载
	 */
	function tableReload(){
		equipmentTable.reload({
    	   page: {
    		   curr: 1 //重新从第 1 页开始
    	   }
    	   ,where: {
    			'welName': $("#welName").val(),
    			'equMemoOne': $("#equMemoOne").val(),
    			'equPositionNum': $("#equPositionNum").val(),
    			'equName': $("#equName").val(),
    	   }
    	})
	}
	
})