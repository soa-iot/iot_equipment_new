//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});
//从cookie中获取当前登录用户
var resavepeople = getCookie1("name").replace(/"/g,'');

/**
 * 日期插件
 */
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#startdate',
		type: 'year'
	});
});

//加载layui内置模块
layui.use(['jquery','form','layer','table','excel'], function(){
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,$ = layui.$ //使用jQuery
	var excel = layui.excel;
	
	
	/**
	 * 设备运行统计年报信息展示表
	 */
	var yearlyTable = table.render({
		elem: '#yearlyReport',
		method: 'post',
		url: 'iot_equipment/equipmen_influx/query',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:60,
		totalRow: true,
		page: true,   //开启分页
		request: {
		    pageName: 'page' //页码的参数名称，默认：page
		    ,limitName: 'limit' //每页数据量的参数名，默认：limit
		},
		parseData: function(res){ //res 即为原始返回的数据
			var data = res.data     
		    return {
		      "code": res.code, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "count": res.count, //解析数据长度
		      "data": data      //解析数据列表
		    };
		},
		cols: [[{field:'id', title:'序号', width:60, sort:false, type:'numbers', fixed:'left', align:'center'},
			{field:'position', title:'设备位号', width:120,  align:'center'},
			{field:'January', title:'1月', width:60,  align:'center'},
			{field:'February', title:'2月', width:60,  align:'center'},
			{field:'March', title:'3月', width:60,  align:'center'},
			{field:'April', title:'4月', width:60,  align:'center'},
			{field:'May', title:'5月', width:60,  align:'center'},
			{field:'June', title:'6月', width:60,  align:'center'},
			{field:'July', title:'7月', width:60,  align:'center'},
			{field:'August', title:'8月', width:60,  align:'center'},
			{field:'September', title:'9月', width:60,  align:'center'},
			{field:'October', title:'10月', width:70,  align:'center'},
			{field:'November', title:'11月', width:70,  align:'center'},
			{field:'December', title:'12月', width:70,  align:'center'},
			{field:'Total_time', title:'当年累计运行时间', width:150,  align:'center'},
			{field:'Modify_time', title:'大修后运行时间', width:140,  align:'center'},
			{field:'Chang_time', title:'设备更换后运行时间', width:150,  align:'center'},
			{field:'Total', title:'总运行时间', width:120,  align:'center'}
			]]
	});
	/**
	 * 监听查询功能
	 */
})