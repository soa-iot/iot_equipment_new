/**
 * 所有油品
 * @returns
 */

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
	    ,where:{
	    	"nextchangetime":new Date()
	    }
	    ,page: true
	    ,cols: [[
	    	{field:'rn', title: '序号', width:'6%', align:'center'}
	      ,{field:'lnamekey', title: '设备位号', width:'11%', align:'center'}
	      ,{field:'lname', title: '设备名称', width:'11%', align:'center'}
	      ,{field:'pplace', title: '润滑部位', width:'11%', align:'center'}
	      ,{field:'requireoil1', title:'油品', width:'11%', align:'center'}
	      ,{field: 'pamount', title:'加油量(升)', width:'10%', align:'center'}
	      ,{field: 'pfrequency', title:'润滑周期', width:'10%', align:'center'}
	      ,{field: 'nextchangetime', title:'下一次换油时间', align:'center', width:'15%', templet:function(d){
	    	  var date = '';
	    	  if (d.nextchangetime != null && d.nextchangetime != '') {
	    		  date = d.nextchangetime.substring(0,10);
			}
				return date;
			}}
	      ,{field: 'lastchangetime', title:'最后一次换油时间', align:'center', templet:function(d){
	    	  var date = '';
	    	  if (d.lastchangetime != null && d.lastchangetime != '') {
	    		  date = d.lastchangetime.substring(0,10);
			}
				return date;
			}}
	    ]]
	  });
  
  	form.on("submit(go-oil-place)",function(){
  		location.href = "/iot_equipment/html/lubrication/lubricate-equipment-oil-change.html";
  	});
	  
})
