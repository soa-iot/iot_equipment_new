//出入库记录表

var oid = GetQueryString("oid");
 $("#barDemo").hide();
layui.use(['table','laydate','form'], function(){
	
	
  var table = layui.table
  	  ,laydate = layui.laydate
  	  ,form = layui.form;
  
//日期时间选择器
  laydate.render({
	  elem: '#starttime'
	  ,max:0
	  ,format:'yyyy-MM-dd'
  });
  laydate.render({
	  elem: '#endtime'
	  ,format:'yyyy-MM-dd'
  });
  
  initOname();
  function initOname(){
	  $.ajax({
		  type: 'post',
		  async: false,
		  url: '/iot_equipment/equipmentoil/queryoilallstock',
		  dataType : "json",
		  success: function(json){
			  $("#oname1").empty();
			  console.log(json)
			  var data = json.data;
			  console.log($(".layui-anim").html())
			  var option = '<option value=" ">直接选择或搜索选择</option>'
			  $.each(data, function (i, item) {
				  option += '<option  value="'+item.oid+'">'+item.oname+'</option>';
			  });
			  $("#oname1").append(option);
			  form.render('select');
		  }
	  
	  })
  }
  
  var rectable = table.render({
	    elem: '#record-table'
	    ,url:'/iot_equipment/equipmentoil/queryoilall'
	    ,where: {
	    	"oid":oid
	    }
	   // ,limit:10
	    ,page: true
	    ,type:"numbers"
	    ,cols: [[
	    	{field:'zizeng', width:"10%", title: '序号',templet:'#numb', align:'center'}
	      ,{field:'oname', width:"20%", title: '油品名称', align:'center'}
	      ,{field:'rtime', width:"20%", title: '入库时间', align:'center',templet:function(d){
				return d.rtime.replace(/T/, ' ').replace(/\..*/, '');
			}}
	      ,{field:'rtype', width:"10%", title: '类型', align:'center'}
	      ,{field:'ramount', width:"20%", title: '出入库量(升)', align:'center'}
	      ,{field:'rstock', width:"20%", title: '库存量(升)', align:'center'}
	    ]]
	   
	  });
	  
	  form.on('submit(sub-record)', function(obj){
		  var starttime = $("#starttime").val();
		 var endtime = $("#endtime").val();
//		  console.log(starttime);
//		  console.log(endtime);
//		  console.log(oid);

		  if (endtime != '' && starttime > endtime) {
			  layer.msg("开始时间不能大于结束时间！", {icon: 7, time: 2000, offset: '150px'});
		}
		  rectable.reload({
			  where: {
				  "startTime":starttime
				  ,"endTime":endtime
				  ,"oid":$("#oname1").val()
			  }
		  ,page: {
			    curr: 1 
			  }
		  });
	  });
  
  });
