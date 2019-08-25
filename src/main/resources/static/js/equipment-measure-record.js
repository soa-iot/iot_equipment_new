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
		elem: '#equipMeasureRecord',
		method: 'get',
		url: '/iot_equipment/equipment/thick/record/query',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:60,
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
			{fixed:'left', title:'序号', width:'10%', sort:false, type:'numbers', align:'center'},
			{field:'equipPositionNum', title:'设备位号', width:'18%', sort:false, align:'center'},
			{field:'equipName', title:'设备名称', width:'18%', sort:false, align:'center'},
			/*{field:'positionnum', title:'测厚点位名称', width:'15%', sort:false, align:'center'},
			{field:'measurevalue', title:'测点厚度(mm)', width:'15%', sort:false, align:'center'},*/
			{field:'measuretime', title:'测厚日期', width:'18%', sort:false, align:'center'},
			{field:'measuror', title:'测厚人', width:'18%', sort:false, align:'center'},
			{fixed:'right', title:'测厚详情', width:'18%', sort:false, align:'center', toolbar:'#barBtn'}]]
	});
	
	/**
	 * 点击查询按钮事件
	 */
	$("#query-equipment").click(function(){
		recordTable.reload({
			page: {
	    		   curr: 1 //重新从第 1 页开始
	    	},
	    	where: {
	    		equipPositionNum: $("#equipPositionNum").val(),
	    		equipName: $("#equipName").val(),
	    		startDate: $("#startDate").val(),
	    		endDate: $("#endDate").val()
	    	}
		})
	})
	
	/**
	 * 监听每一行工具事件
	 */
	table.on('tool(equipMeasureRecord)', function(obj){
		console.log(obj);
	    var data = obj.data;
	    if(obj.event === 'detail'){
	    	//弹窗显示测厚点位详情
	    	layer.open({
				type: 1,
				id:"recordDetail",
				title: data.equipPositionNum+' 测点详情',
				content: $('#recordDetail-window'),
				area: ['600px','85%'],
				offset: '50px',
				btn: ['关&nbsp;&nbsp;闭'],
				btnAlign: 'c', //按钮居中对齐
				yes: function(index, layero){
					layer.close(index);
				},
				success: function(index, layero){
					recordDetailTable.reload({
						
					});
				}
			})
	    }
	});
	
	/**
	 * 测厚点位表格展示
	 */
	var recordDetailTable = table.render({
		elem: '#recordDetail-table',
		method: 'get',
		url: '/iot_equipment/equipment/thick/query',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:60,
		page: false,   //关闭分页
		where: {
			isDetial: true
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
			{field:'positionnum', title:'测厚点位', width:'50%', sort:false, align:'center'},
			{field:'measurevalue', title:'测点厚度(mm)', width:'50%', sort:false, align:'center'}]]
	});

})