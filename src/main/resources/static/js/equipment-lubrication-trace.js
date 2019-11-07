//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});

//设备id
var equID;


//加载layui内置模块
layui.use(['jquery','form','layer','table','excel','upload'], function(){
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,$ = layui.$ //使用jQuery
	,excel = layui.excel
	,upload = layui.upload;
	
	/**
	 * 设备信息展示表
	 */
	var equipTable = table.render({
		elem: '#equipLubrication',
		method: 'get',
		url: '/iot_equipment/lubrication/place/change/trace',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:80,
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
		cols: [[
			{field:'id', title:'序号', sort:false, type:'numbers', align:'center'},
			{field:'positionnum', title:'设备位号', sort:false, align:'center'},
			{field:'tname', title:'设备名称', sort:false, align:'center'},
			{field:'pplace', title:'润滑部位', sort:false, align:'center'},
			{field:'requireoil1', title:'油品', sort:false, align:'center'},
			{field:'pamount', title:'加油量', sort:false, align:'center'},
			{field:'nextchangetime', title:'下一次换油日期', sort:false, align:'center',templet:function(d){
				return d.nextchangetime.replace(/T/, ' ').replace(/\..*/, '');
			}}]]
	});
	
	/**
	 * 重新加载设备测厚管理表
	 */
	function reloadEquipTable(){
		equipTable.reload({
    	   page: {
    		   curr: 1 //重新从第 1 页开始
    	   }
    	   ,where: {
    		  'positionnum': $.trim($("#positionnum").val()),
    		  'tname': $.trim($("#equipname").val())
    	   }
    	})
	}
	
	/**
	 * 条件查询事件
	 */
	$("#query-equipment").click(function(){
		reloadEquipTable();
	})
	
})