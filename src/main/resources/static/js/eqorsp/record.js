//导入 用layui upload插件layui.use([ "element", "laypage", "layer", "upload"], function() {
layui.use(['layer',   'table'],
	function() {
		var $ = layui.jquery,
			layer = layui.layer,
			table = layui.table;
			//申请编号
			var request_code=SoaIot.getUrlParam('request_code');
			console.log("request_code==.>"+request_code)
		//林勇申请单列表
		getreceiveList();

		function getreceiveList() {
			var data = {};
			// $.post(api.eqorsp.accountInfoUpdate, data, function(results) {
				setEqTable('../../data/demo1.json');
			// });
		}

		//加载设备列表信息
		function setEqTable(data) {
			table.render({
				elem: '#sp_list_table',
				url: data,
				height: TABLE_H-130,
				 toolbar: '#toolbar' //开启头部工具栏，并为其绑定左侧模板
				,title: '用户数据表',
				cols: [
					[{
						type: 'checkbox',
						fixed: 'left'
					}, {
						field: 'id',
						title: 'ID',
						width: 80,
						fixed: 'left',
						unresize: true,
						sort: true
					}, {
						field: 'username',
						title: '用户名',
						width: 120,
						edit: 'text'
					}, {
						field: 'email',
						title: '邮箱',
						width: 150,
						edit: 'text',
						templet: function(res) {
							return '<em>' + res.email + '</em>'
						}
					}, {
						field: 'sex',
						title: '性别',
						width: 80,
						edit: 'text',
						sort: true
					}, {
						field: 'city',
						title: '城市',
						width: 100
					}, {
						field: 'sign',
						title: '签名'
					}, {
						field: 'experience',
						title: '积分',
						width: 80,
						sort: true
					}, {
						field: 'ip',
						title: 'IP',
						width: 120
					}, {
						field: 'logins',
						title: '登入次数',
						width: 100,
						sort: true
					}, {
						field: 'joinTime',
						title: '加入时间',
						width: 120
					}]
				],
				page: true
			});
		}
	});
