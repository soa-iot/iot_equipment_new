/**
 * 所有油品
 * @returns
 */
var userid = getCookie("userID").replace(/"/g,'');//"张三";

$("#barDemo").hide();
$("#select").hide();
 
 $.ajax({
	  type: 'post',
	  async: false,
	  url: '/iot_equipment/equipmentoil/queryoilallstock',
	  dataType : "json",
	  success: function(json){
		  console.log(json)
		  var data = json.data;
		  var select = " <select  lay-submit lay-filter='oname' name='{{d.pid}}' id='{{d.pid}}' lay-search=''><option  value=''>请选择油品</option>"
		  $.each(data, function (i, item) {
			  var option= '<option  value="'+item.oname+'">'+item.oname+'</option>';
			  select+=option;
			});
		  select += "</select>"
			  $("#select").append(select);
	  }

})
 
layui.use(['table','laydate','layer', 'form'], function(){

	
	
  var table = layui.table
  	  ,laydate = layui.laydate
  	  ,layer = layui.layer
  	  ,form = layui.form
  	  ,rn = 1;
  
  var tab = table.render({
	    elem: '#record-table'
	    ,url:'/iot_equipment/lubrication/lubplace'
	    ,limit:10
	    ,page: true
	    //,width:1200
	    ,size: 'lg'
	    ,cols: [[
	    	{field:'rn', title: '序号', width:'4%'}
	      ,{field:'lnamekey', title: '设备位号', width:'8%'}
	      ,{field:'lname', title: '设备名称', width:'11%'}
	      ,{field:'pplace', title: '润滑部位', width:'11%'}
	      ,{field:'oname', title:'油品', width:'11%',  templet: '#select'}
	      ,{field: 'ramount', title:'加油量(升)', width:'10%', edit: 'text'}
	      ,{field: 'pfrequency', title:'润滑周期', width:'10%'}
	      ,{field: 'nextchangetime', title:'下一次换油时间', width:'12%', templet:
	    	  function(d){
		    	 var date = '';
		    	 if (d.nextchangetime != null && d.nextchangetime != '') {
		    		  date = d.nextchangetime.substring(0,10);
		    	 	}
					return date;
	      		}
	      }
	      ,{field: 'lastchangetime', title:'最后一次换油时间', width:'12%', templet:
		    	  function(d){
			    	  var date = '';
				    if (d.lastchangetime != null && d.lastchangetime != '') {
				      date = d.lastchangetime.substring(0,10);
					}
					return date;
				}
	      }
	      ,{fixed: '', title:'操作', toolbar: '#barDemo'}
	    ]]
	  ,done: function (res, curr, count) {
	      $(".layui-table-cell").css("overflow", "visible");
	  	}
	  });
	  
  
  table.on('tool(record-table)', function(obj){
	  console.log(obj);
	  var wheredata = { 
				"pid":obj.data.pid,
				"excutor":userid,
				"ramount": obj.data.ramount,
				"requireoil1":obj.data.oname,
				"ptime":new Date()
			}
	  if( obj.event=="addoil"){
		  if (obj.data.requireoil1.indexOf("脂")==-1) {
			  wheredata["operatetype"]="加油";
		}else{
			wheredata["operatetype"]="加脂"
		}
		  
	  }else{
		  if (obj.data.requireoil1.indexOf("脂")==-1) {
			  wheredata["operatetype"]="换油";
		}else{
			wheredata["operatetype"]="换脂"
		}
	  }
	  
	  console.log(wheredata);
	  if (wheredata.requireoil1 == null ||wheredata.requireoil1 == "" ) {
		  layer.msg("请选择油品！！！", {icon: 7, time: 3000, offset: '150px'});
	}else if (wheredata.ramount == null ||wheredata.ramount == "" || wheredata.ramount <0 ) {
		 layer.msg("请输入大于0的油品数量！！！", {icon: 7, time: 3000, offset: '150px'});
	}else{
	  $.ajax({
			type: 'get',
			async: false,
			url: '/iot_equipment/lubrication/lueqpladdchangeoil',
			data:wheredata,
			dataType: 'JSON',
			success: function(json){
				if(json.state == 0){
					tab.reload('#record-table',null);
					layer.msg("提交成功", {icon: 1, time: 2000, offset: '150px'});
				}else{
					layer.msg("提交失败，发生未知错误请联系管理员", {icon: 2, time: 2000, offset: '150px'});
				}	
			},
			error: function(){
				layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
			}
		});
	}
  })
  
  //select监听
  form.on('select(oname)', function (data) {
	  //console.log(data);
		var selectElem = $(data.elem);
		//console.log(selectElem);
		  var tdElem = selectElem.closest('td');
		//  console.log(tdElem);
		  var trElem = tdElem.closest('tr');
		  var tableView = trElem.closest('.layui-table-view');
		  table.cache[tableView.attr('lay-id')][trElem.data('index')][tdElem.data('field')] = data.value;
		});


})