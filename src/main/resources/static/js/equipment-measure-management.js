//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});
//从cookie中获取当前登录用户
var resavepeople = getCookie1("name").replace(/"/g,'');
//设备id
var equID;

/**
 * 日期插件
 */
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#nextmeasuretime',
		type: 'date',
		format: 'yyyy-MM-dd'
	});
});


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
	 * 设备测厚信息展示表
	 */
	var equipTable = table.render({
		elem: '#equipMeasureManagement',
		method: 'get',
		url: '/iot_equipment/equipment/thick/management/query',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:60,
		page: true,   //开启分页
		request: {
		    pageName: 'page' //页码的参数名称，默认：page
		    ,limitName: 'limit' //每页数据量的参数名，默认：limit
		},
		parseData: function(res){ //res 即为原始返回的数据
			var data = res.data     
			
			for(var i in data){
				data[i].filepath = (data[i].filepath == null || data[i].filepath == '')
									?null:(data[i].filepath.replace(/^[CDEFG]:\\equipment-thick\\/g,'/iot_equipment/picture/'));
			}
			
		    return {
		      "code": res.code, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "count": res.count, //解析数据长度
		      "data": data      //解析数据列表
		    };
		},
		cols: [[
			{field:'id', title:'序号', width:'5%', sort:false, type:'numbers', fixed:'left', align:'center'},
			{field:'welName', title:'装置列名', width:'10%', sort:false, align:'center'},
			{field:'positionnum', title:'设备位号', width:'10%', sort:false, align:'center'},
			{field:'tname', title:'设备名称', width:'10%', sort:false, align:'center'},
			{field:'equMemoOne', title:'设备类别', width:'10%', sort:false, align:'center'},
			{field:'measuretype', title:'测量仪器型号', width:'10%', sort:false, align:'center'},
			{field:'nextmeasuretime', title:'下一次测厚日期', width:'10%', sort:false, align:'center'},
			{field:'equModel', title:'规格型号', width:'10%', sort:false, align:'center'},
			{field:'material', title:'设备材质', width:'10%', sort:false, align:'center'},
			{field:'equProducDate', title:'制造年月', width:'10%', sort:false, align:'center'},
			{field:'equCommissionDate', title:'投用年月', width:'10%', sort:false, align:'center'},
			{field:'meduimType', title:'工作介质', width:'10%', sort:false, align:'center'},
			{field:'filepath', title:'图片路径', hide:true},
			{fixed:'right', title:'测点示图', width:'10%', minWidth: 95, sort:false, align:'center', toolbar:'#barBtn'}]]
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
	 * 条件查询设备测厚数据
	 */
	$("#query-equipment").click(function(){
		reloadEquipTable();
	})
	
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
	 * 添加设备测厚弹窗展示new-equipment-window
	 */
	var index_,count=0;
	$("#add-equipment").click(function(){
		layer.open({
			type: 1,
			id:"equipAdd",
			title: '新增测厚设备',
			content: $('#new-equipment-window'),
			area: '800px',
			offset: '50px',
			btn: ['保&nbsp;&nbsp;存', '取&nbsp;&nbsp;消'],
			btnAlign: 'c', //按钮居中对齐
			yes: function(index, layero){
				index_ = index;
				//表单校验
				if($('#positionnum1').val() == ''){
					layer.msg("设备位号不能为空", {icon: 7, time: 2000, offset: '150px'});
					return;
				}
				if($('#measuretype1').val() == ''){
					layer.msg("仪器型号不能为空", {icon: 7, time: 2000, offset: '150px'});
					return;
				}
				if($('#pointnum').val() == ''){
					layer.msg("测点数量不能为空", {icon: 7, time: 2000, offset: '150px'});
					return;
				}
				if($('#cycle').val() == ''){
					layer.msg("测厚周期不能为空", {icon: 7, time: 2000, offset: '150px'});
					return;
				}
				if($('#nextmeasuretime').val() == ''){
					layer.msg("下一次测厚日期不能为空", {icon: 7, time: 2000, offset: '150px'});
					return;
				}

				//判断是否上传图片
				if($('#imgZmList').children().length == undefined || $('#imgZmList').children().length == 0){
					layer.msg("必须上传测点图片", {icon: 7, time: 2000, offset: '150px'});
					return;
				}
				
				
				//点击保存后的请求
				if(count == 0){
					//ajax请求保存数据
					uploadList.upload();
					count++;
				}	
			},
			success: function(index, layero){
				count = 0;
				console.log(uploadList);
				uploadList.config.elem.next()[0].value = '';  ///清空 input file 值，以免删除后出现同名文件不可选
				$(".layui-layer-btn0").on('click');
				$("#positionnum1").val('');
				$("#tname1").val('');
				$("#measuretype1").val('');
				$("#pointnum").val('');
				$("#cycle").val('');
				$("#nextmeasuretime").val('');
				$('#imgZmList').empty();
				$("#problem-img").css("display", "none");
			}
		})
	})
	
	/**
	 * 图片上传功能
	 */
    var uploadList = upload.render({
         elem: '#addEquipImg'
         , url: '/iot_equipment/equipment/thick/add'
         , data: {	
        	 'eid': function(){ return equID; },
        	 'creator': function(){ return resavepeople;},
        	 'positionnum': function(){  return $("#positionnum1").val();},
        	 'tname': function(){  return $("#tname1").val();},
        	 'measuretype': function(){  return $("#measuretype1").val();},
        	 'pointnum': function(){  return $("#pointnum").val();},
        	 'cycle': function(){  return $("#cycle").val();},
        	 'nextmeasuretime': function(){  return $("#nextmeasuretime").val();},
         }
         , accept: 'images'
         , multiple: false  //不允许多文件上传
         , auto:false
         , bindAction: '#'
         , choose: function (obj) {
       	  //将每次选择的文件追加到文件队列
          $("#problem-img").css("display", "block");
           
         //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
          obj.preview(function (index, file, result) {
        	  $('#imgZmList').empty();
              $('#imgZmList').append('<li style="position:relative" id="'+ index +'"><img name="imgZmList"  src="' + result + '"width="300" height="150"></li>');
              
              form.render();
           });
         }
     	   //上传前执行的函数
         ,before: function(obj){
        	 layer.load(1); //上传loading
         }
          //上传完毕后的，回调函数
         , done: function (res, index, upload) {
        	layer.closeAll('loading'); //关闭loading
        	layer.msg("添加数据成功", {icon: 1, time: 2000, offset: '150px'});
        	count--;
       	 	layer.close(index_);
       	 	equipTable.reload();
         }
          //上传失败时，回调函数
         ,error: function(index, upload){
        	 //layer.msg("添加数据失败, 请检查网络是否正常", {icon: 1, time: 2000, offset: '150px'});
        	 layer.closeAll('loading'); //关闭loading
        	 count--;
         }
     });
	
	/**
	 * 从设备台账中定位设备
	 */
	$("#location-btn").click(function(){
		//显示设备定位弹窗
		layer.open({
			type: 2,
			id:"locationEquipment",
			title: '设备定位',
			content: './equipment-management-location.html',
			offset: ['50px','15%'],
	    	area: ['78%','82%'],
			btn: ['添&nbsp;&nbsp;加', '取消'],
			btnAlign: 'c', //按钮居中对齐
			yes: function(index, layero){
				//获取iframe窗口的body对象
	        	var body = layer.getChildFrame('body', index);
	        	//找到body对象下被选中的设备位号值
	        	var value = body.find(".layui-table-click td[data-field='equPositionNum']").find("div").text();
	        	var equipName = body.find(".layui-table-click td[data-field='equName']").find("div").text();
	        	equID = body.find(".layui-table-click td[data-field='equID']").find("div").text();
	        	
	        	console.log("value:"+value +",equipName:"+equipName)
	        	$("#positionnum1").val(value);
	        	$("#tname1").val(equipName);
	        	layer.close(index); //如果设定了yes回调，需进行手工关闭
			}
		})	
	})
	
})