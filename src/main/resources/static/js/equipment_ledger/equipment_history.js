var global_equId = getParams('equId');
var global_equTypeId = getParams('equTypeId');
var global_equPositionNum = getParams('equPositionNum');

layui.use(['table', 'layer'], function() {
			var table = layui.table;
			var layer = layui.layer;
			$('#equInfo').text(global_equPositionNum);
			var query_data = {
				equTypeId : global_equTypeId,
				equId : global_equId
			}
			load_table(table, query_data);
			// 监听工具条
			table.on('tool(equ_history)', function(obj) {
						var tr = obj.tr; // 获得当前行 tr 的 DOM 对象（如果有的话）
						var layEvent = obj.event;
						if (layEvent === 'detail') { // 查看
							// do somehing
						} else if (layEvent === 'recovery') { // 恢复
							layer.confirm('真的要恢复此行数据么', function(index) {
										layer.close(index);
										recovery_data(obj.data);
									});
						} else if (layEvent === 'edit') { // 编辑
							// do something
						} else if (layEvent === 'LAYTABLE_TIPS') {
							// do something
						}
					});

		});

/**
 * 加载数据表格
 * 
 * @param {}
 *            table
 * @param {}
 *            query_data
 */
function load_table(table, query_data) {

	/**
	 * 请求表头数据
	 */
	$.ajax({
		url : '/iot_equipment/equipmentLedger/getEquipmentDisplayInfo',
		type : 'get',
		dataType : 'json',
		data : {
			equTypeId : query_data.equTypeId
		},
		success : function(res) {
			console.log(res.data);
			var cols = [[{
						type : 'checkbox',
						fixed : 'left',
						rowspan : 2

					}, {
						title : '序号',
						type : 'numbers',
						fixed : 'left',
						rowspan : 2
					}, {
						title : '操作类型',
						field : 'operateType',
						width : '100',
						fixed : 'left',
						rowspan : 2,
						templet : function(d) {
							return d.operateType;

						}
					}, {
						title : '操作时间',
						field : 'operateTime',
						width : '120',
						fixed : 'left',
						rowspan : 2
					}, {
						title : '操作人员',
						field : 'operatePeople',
						width : '100',
						fixed : 'left',
						rowspan : 2
					}]];

			var data_item = {};
			/**
			 * 循环表头数据，生成layui要求格式的表头数据
			 */
			$.each(res.data, function(index, item) {

						// console.log(index);
						var cols_index = parseInt(item.classNum) - 1;
						// console.log(item);
						var cols_item = {};
						cols_item.title = item.title;
						cols_item.field = item.field;
						cols_item.colspan = item.colspan;
						cols_item.rowspan = item.rowspan;
						cols_item.fixed = item.fixed;
						cols_item.sort = item.sort;
						cols_item.type = item.displayType;
						cols_item.width = parseInt(item.width);

						if (item.colspan != 1) {
							cols_item.unresize = true;
						} else {
							cols_item.unresize = false;
						}

						data_item[item.field] = item.field;

						if (!cols[parseInt(item.classNum) - 1]) {
							cols.push([]);
						}

						cols[cols_index][cols[cols_index].length] = cols_item;

					});

			cols[0].push({
						title : '恢复操作',
						width : '100',
						align : 'center',
						fixed : 'right',
						toolbar : '#recovery',
						rowspan : 2
					});
			/*
			 * cols[0].push({ title : '操作', fixed : 'right', rowspan : 2 });
			 */

			table.render({
				elem : '#equ_history',
				url : '/iot_equipment/equipmentHistory/getEquHistoryList',
				height : 'full-150',
				toolbar : '#toolbar',
				title : '设备历史版本',
				method : 'post',
				contentType : 'application/json',
				where : query_data,
				cols : cols,
				page : true,
				loading : true,
				limits : [30, 60, 90, 120, 150],
				limit : 30,
				initSort : {
					field : 'operateTime' // 排序字段，对应 cols 设定的各字段名
					,
					type : 'desc' // 排序方式 asc: 升序、desc: 降序、null: 默认排序
				},
				parseData : function(res) {
					var data = res.data;
					console.log(res.data);
					if (data) {
						$.each(data, function(index, item) {
									$.each(item.equipmentPropertiesBack,
											function(index1, item1) {
												item[item1.proNameEn] = item1.proValue;
											});
								});
					}

					return {
						"code" : res.code, // 解析接口状态
						"msg" : res.msg, // 解析提示文本
						"count" : res.count, // 解析数据长度
						"data" : data
					};

				},
				done : function(res, curr, count) {

				}

			});

		},
		error : function() {

		}

	});

}

/**
 * 恢复设备数据
 * 
 * @param {}
 *            data
 */
function recovery_data(data) {

	$.ajax({
				url : '/iot_equipment/equipmentHistory/recoveryEquInfo',
				type : 'post',
				dataType : 'json',
				contentType : 'application/json',
				data : JSON.stringify({
							equId : data.equId,
							backId : data.backId
						}),
				success : function(res) {
					
				},
				error : function() {

				}

			});

}
