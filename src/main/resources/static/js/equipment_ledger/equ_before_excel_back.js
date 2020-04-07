var equ_excle_back_record;
var equ_type_info = {
	equTypeId : getParams('equTypeId') != null
			? getParams('equTypeId').trim()
			: null,
	equType : getParams('equType') != null ? getParams('equType').trim() : null
};
var golbal_common_property = [];
var global_trdata;
layui.use(['tree', 'util', 'table', 'layer', 'upload', 'form', 'laydate'],
		function() {
			var tree = layui.tree, layer = layui.layer, util = layui.util;
			var layer = layui.layer;
			var table = layui.table;
			var $ = layui.jquery;
			var upload = layui.upload;
			var form = layui.form;
			var laydate = layui.laydate;

			// 执行一个laydate实例
			laydate.render({
						value : new Date(),
						elem : '#beginDate' // 指定元素
					});
			laydate.render({
						value : new Date(),
						elem : '#endDate' // 指定元素
					});

			console.log(equ_type_info);
			var equ_types_query = $.ajax({
						url : '/iot_equipment/equipmentLedger/getAllEquipmentType',
						type : 'get',
						async : false,
						dataType : 'json',
						success : function(res) {
							if (!equ_type_info.equTypeId
									|| !equ_type_info.equType) {
								equ_type_info.equTypeId = res.data[0].id;
								equ_type_info.equType = res.data[0].title;
							}
							console.log(equ_type_info.equTypeId);
							load_tree(tree, res.data, table);
						},
						error : function() {

						}
					});
			load_table(table, {
						"equTypeId" : equ_type_info.equTypeId
					});

			// 监听表单提交事件
			form.on('submit(search_button_search)', function(data) {
						if (!equ_type_info.equTypeId) {
							layer.msg('设备分类为空，请选择设备分类！！！！', {
										icon : 2
									});
							return false;
						}
						var form_data = data.field;
						var query_data = form_data;
						console.log(query_data);
						query_data.equTypeId = equ_type_info.equTypeId;
						load_table(table, query_data);

						return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。

					});

			// 监听事件
			table.on('toolbar(equ_excle_back_record)', function(obj) {
				var select_data = table.checkStatus(obj.config.id).data;
				switch (obj.event) {
					case 'recovery' :
						if (select_data.length != 1) {
							layer.msg('请至少或至多选择一条数据！！！', {
										icon : '2'
									});
							return;
						}
						var index = layer.load();
						$.ajax({
							url : '/iot_equipment/equipmentHistory/recoveryExcelBackRecord',
							type : 'post',
							dataType : 'json',
							contentType : 'application/json',
							data : JSON.stringify({
										equTypeId : equ_type_info.equTypeId,
										backId : select_data[0].id
									}),
							success : function(res) {
								layer.close(index);

								if (res.code == 0) {
									layer.msg('备份记录恢复成功', {
												icon : 1
											});
								} else {
									layer.msg('备份记录恢复失败！！！', {
												icon : 1
											});
								}

							},
							error : function() {
								layer.msg('备份记录恢复失败！！！', {
											icon : 1
										});
							}

						});
						3

						break;
					default :
						break;

				};
			});

		});

/**
 * 加载右侧设备分类树
 * 
 * @param {}
 *            tree
 * @param {}
 *            data
 */
function load_tree(tree, data, table) {
	// 基本演示
	tree.render({
				elem : '#equipment_type',
				data : data,
				showCheckbox : false // 是否显示复选框
				,
				id : 'id',
				isJump : true // 是否允许点击节点时弹出新窗口跳转
				,
				click : function(obj) {
					var data = obj.data; // 获取当前点击的节点数据
					// console.log(data);
					equ_type_info.equType = data.title;
					equ_type_info.equTypeId = data.id;
					var query_data = {
						"equTypeId" : data.id
					};
					$('#advanced_search_form').html('');
					$('#key_word_search').css('display', '');
					$('#advanced_search_btn').attr('lay-filter',
							'open_advanced_search_btn');
					load_table(table, query_data);
				}
			});
}

/**
 * 加载设备台账表格
 * 
 * @param {}
 *            table
 * @param {}
 *            equ_type
 */
function load_table(table, query_data) {

	table.render({
		elem : '#equ_excle_back_record',
		url : '/iot_equipment/equipmentHistory/getExcelBackRecord',
		height : '790',
		toolbar : '#toolbar',
		title : 'Excel导入备份记录',
		method : 'post',
		contentType : 'application/json',
		initSort : {
			field : 'operateTime' // 排序字段，对应 cols 设定的各字段名
			,
			type : 'desc' // 排序方式 asc: 升序、desc: 降序、null: 默认排序
		},
		where : query_data,
		cols : [[{
					type : 'checkbox'
				}, {
					type : 'numbers',
					title : '序号'
				}, {
					title : '操作人',
					field : 'operator'
				}, {
					title : '操作时间',
					field : 'operateTime'
				}, {
					title : '文件',
					field : 'backFilePathVirtual',
					templet : function(obj) {
						console.log(obj.backFilePathVirtual);
						return '<a href = "/iot_equipment'
								+ obj.backFilePathVirtual
								+ '" style = "text-decoration:underline;color:blue;">下载</a>'
					}
				}, {
					title : '文件名称',
					field : 'backFileName'
				}]],
		page : true,
		loading : true,
		limits : [30, 60, 90, 120, 150],
		limit : 30,
		parseData : function(res) {
		},
		done : function(res, curr, count) {
		}

	});

}
