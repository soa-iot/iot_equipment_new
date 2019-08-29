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
	
	//当前选中的设备位号
	var positionNum = null;
	
	/**
	 * 设备信息展示表
	 */
	var equipTable = table.render({
		elem: '#equipLubrication',
		method: 'get',
		url: '#',
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
			{field:'id', title:'序号', minWidth: 95, sort:false, type:'numbers', fixed:'left', align:'center'},
			{field:'positionnum', title:'设备位号', sort:false, align:'center'},
			{field:'tname', title:'设备名称', sort:false, align:'center'},
			{fixed:'right', title:'换油记录', minWidth: 95, sort:false, align:'center', toolbar:'#barBtn'}]]
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
    		  'welName': $("#welName").val(),
    		  'positionnum': $("#positionnum").val(),
    		  'tname': $("#tname").val(),
    		  'equModel': $("#equModel").val(),
    		  'meduimType': $("#meduimType").val(),
    		  'measuretype': $("#measuretype").val()
    	   }
    	})
	}
	
	/**
	 * 监听每一行工具事件
	 */
	table.on('tool(equipMeasureManagement)', function(obj){
		console.log(obj);
	    var data = obj.data;
	    if(obj.event === 'query'){
	      var poisitionNum = data.positionnum;
	      var path = data.filepath;
	      console.log("测点示图路径为："+path);
	      layer.open({
				type: 1,
				id:"showPicture",
				title: poisitionNum+'测点示图',
				content: '<div style="width:100%; text-align:center;"><img alt="测点示图" id="thick-record-img" src="'+path+'" /></div>',
				area: ['800px','450px'],
				offset: '50px',
				btn: '关&nbsp;&nbsp;闭',
				btnAlign: 'c', //按钮居中对齐
				yes: function(index, layero){
					layer.close(index);
				}
	      })
	    }
	});
	
	/**
	 * 设备换油记录信息展示表
	 */
	table.render({
		elem: '#equipLubricationRecord',
		method: 'get',
		url: '#',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:70,
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
			{field:'id', title:'序号', minWidth: 95, sort:false, fixed:'left', align:'center'},
			{field:'positionnum', title:'润滑点位', sort:false, align:'center'},
			{field:'tname', title:'润滑油品', sort:false, align:'center'},
			{field:'tname', title:'更换量（L）', sort:false, align:'center'},
			{field:'tname', title:'更换时间', sort:false, align:'center'},
			{field:'tname', title:'操作人员', sort:false, align:'center'}]]
	});
})