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
		elem: '#startDate',
		type: 'date',
		format: 'yyyy-MM-dd'
	});
});

layui.use('laydate', function(){
	var laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#endDate',
		type: 'date',
		format: 'yyyy-MM-dd'
	});
});

//加载layui内置模块
layui.use(['jquery','form','layer','table','excel'], function(){
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,$ = layui.$ //使用jQuery
	var excel = layui.excel;
	
	//当前选中的设备位号
	var positionNum = null;
	
	/**
	 * 设备运行信息展示表
	 */
	var recordTable = table.render({
		elem: '#equipMeasureManagement',
		method: 'post',
		url: '/iot_equipment/equipment/list',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:60,
		page: true,   //开启分页
		request: {
		    pageName: 'page' //页码的参数名称，默认：page
		    ,limitName: 'limit' //每页数据量的参数名，默认：limit
		},
		parseData: function(res){ //res 即为原始返回的数据
			var data = res.data     
			
			if(data != null || data != ''){
				positionNum = data[0].positionNum;
			}else{
				positionNum = null;
			}
			console.log("positionNum="+positionNum);
			$("#rfid").text(positionNum+" 大修/更换时间");
			showBigEvent();
			
		    return {
		      "code": res.code, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "count": res.count, //解析数据长度
		      "data": data      //解析数据列表
		    };
		},
		cols: [[
			{field:'id', title:'序号', width:'5%', sort:false, type:'numbers', fixed:'left', align:'center'},
			{field:'wel_name', title:'装置列名', width:'10%', sort:false, align:'center'},
			{field:'positionNum', title:'设备位号', width:'10%', sort:false, align:'center'},
			{field:'name1', title:'设备名称', width:'10%', sort:false, align:'center'},
			{field:'name2', title:'规格型号', width:'10%', sort:false, align:'center'},
			{field:'name3', title:'设备类别', width:'10%', sort:false, align:'center'},
			{field:'name4', title:'设备材质', width:'10%', sort:false, align:'center'},
			{field:'name5', title:'制造年月', width:'10%', sort:false, align:'center'},
			{field:'name6', title:'投用年月', width:'10%', sort:false, align:'center'},
			{field:'date', title:'工作介质', width:'10%', sort:false, align:'center'},
			{field:'actionPerson1', title:'测量仪器型号', width:'10%', sort:false, align:'center'},
			{field:'actionPerson', title:'下一次测厚日期', width:'10%', sort:false, align:'center'}]]
	});
	
	/**
	 * 重新加载动设备信息表
	 */
	function reloadEquipMoveTable(){
		yearlyTable.reload({
    		url: '/iot_equipment/equipment/list'
    	   ,page: {
    		   curr: 1 //重新从第 1 页开始
    	   }
    	   ,where: {
    			'positionNum': $("#positionNum").val(),
    			'equipType': $("#equipType").val(),
    			'name': $("#name").val(),
    	   }
    	})
	}

})