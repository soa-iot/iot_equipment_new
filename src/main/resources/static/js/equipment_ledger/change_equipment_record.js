layui.use(['tree', 'util', 'table', 'layer', 'form', 'laydate'], function() {
	var tree = layui.tree, layer = layui.layer, util = layui.util;
	var layer = layui.layer;
	var table = layui.table;
	var $ = layui.jquery;
	var laydate = layui.laydate;
	var form = layui.form;
	var form_initial_data;

	// 出厂时间
	laydate.render({
				elem : '#equProducDate'
			});

	// 投用时间
	laydate.render({
				elem : '#equCommissionDate'
			});

	/**
	 * 请求获取表单的初始化数据
	 */
	var equId = getParams('equId');
	console.log(equId);
	$.ajax({
				url : '/iot_equipment/equipmentLedger/getEquipmentList',
				type : 'post',
				contentType : 'application/json',
				data : JSON.stringify({
							equId : equId,
							page : 1,
							limit : 1
						}),
				dataType : 'json',
				success : function(res) {
					console.log(res.data);
					var data = res.data;
					$.each(data, function(index, item) {
								$.each(item.equipmentProperties, function(
												index1, item1) {
											item[item1.proNameEn] = item1.proValue;
										});
							});
					form_initial_data = data[0];
					/**
					 * 转换状态
					 */
					switch (form_initial_data.equStatus) {
						case '在用' :
							form_initial_data.equStatus = '1';
							break;
						case '备用' :
							form_initial_data.equStatus = '2';
							break;
						case '停用' :
							form_initial_data.equStatus = '3';
							break;
						case '闲置' :
							form_initial_data.equStatus = '4';
							break;
						case '报废' :
							form_initial_data.equStatus = '5';
							break;

					}

					// console.log(form_initial_data);
					/**
					 * 设备类型加载
					 */
					$('#equTypeId').html('<option selected="selected" value="'
							+ form_initial_data.equTypeId + '">'
							+ form_initial_data.equType + '</option>');

					form.render('select');

					/**
					 * 获取扩属性表单信息并加载
					 */
					$.ajax({
								url : '/iot_equipment/equipmentLedger/getFormInfo',
								data : {
									equTypeId : form_initial_data.equTypeId
								},
								dataType : 'json',
								type : 'post',
								success : function(res) {
									// console.log(res.data);
									load_form(res.data);
								},
								error : function() {

								}
							});
				},
				error : function() {
					layer.msg('数据请求失败，请联系管理员！！！', {
								icon : 2
							});

				}
			});

	/**
	 * 监听提交
	 */
	form.on('submit(search_button_save)', function(data) {

		var save_msg = layer.open({
					type : 3
				});

		var form_data = data.field;
		var common_properties = ['equName', 'equStatus', 'equPositionNum',
				'processUnits', 'equModel', 'equTypeId', 'assetValue',
				'equManufacturer', 'equProducDate', 'equCommissionDate',
				'equInstallPosition'];

		var save_data = {};
		var equId = form_initial_data.equId;
		save_data.equId = equId;
		var equipmentProperties = [];
		for (key in form_data) {
			if (common_properties.indexOf(key) >= 0) {
				// console.log(data[key]);
				save_data[key] = form_data[key];
			} else {
				var obj = {};
				obj.equId = equId;
				obj.proNameCn = '';
				obj.proNameEn = key;
				obj.proValue = form_data[key];
				equipmentProperties.push(obj);
			}
		}
		save_data.equipmentProperties = equipmentProperties;

		save_data.equType = $('#equTypeId').find("option:selected").text();

		var query_data = {};
		query_data.equipmentCommonInfo = save_data;
		query_data.operatePeople = decodeURI(getCookie('name')).replace(/\"/g,
				'');
		// console.log(query_data.operatePeople);
		query_data.operateType = "3";// 3-更换设备

		console.log(save_data);
		$.ajax({
					url : '/iot_equipment/equipmentLedger/updateEquipmentRecord',
					type : 'post',
					data : JSON.stringify(query_data),
					dataType : 'json',
					contentType : 'application/json',
					success : function(res) {
						if (res.code == 0) {
							layer.close(save_msg);
							layer.msg('数据保存成功！！！');
							window.location.href = './equipment_ledger.html?equType='
									+ escape(getParams('equType').trim())
									+ '&equTypeId='
									+ escape(getParams('equTypeId').trim());

						} else {
							layer.close(save_msg);
							layer.msg('数据保存失败，请联系管理员！！！', {
										icon : 2
									});
						}

					},
					error : function() {
						layer.close(save_msg);
						layer.msg('数据保存失败，请联系管理员！！！', {
									icon : 2
								});
					}
				});

		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	/**
	 * 监听取消按钮
	 */

	$('#search_button_cancel').on('click', function() {

		window.location.href = './equipment_ledger.html?equType='
				+ escape(getParams('equType').trim()) + '&equTypeId='
				+ escape(getParams('equTypeId').trim());
		return false;
	});

	/** *****************************************************function******************************************************************************** */
	/**
	 * 加载表单数据
	 */
	function load_form(data) {

		var data_picker_ids = [];

		var html = '';
		$.each(data, function(index, item) {
					if (index % 2 == 0) {
						html += '<div class="layui-form-item">'
					}

					switch (item.columnType) {
						case 1 :
							// 字符串格式
							html += '<div class="layui-inline">'
									+ '<label class="layui-form-label">'
									+ item.formName + '</label>'
									+ '<div class="layui-input-inline">'
									+ '<input type="text" name="' + item.field
									+ '" placeholder=""'
									+ '	class="layui-input">' + '</div>'
									+ '</div>';
							break;

						case 2 :
							// 日期格式(yyyy-MM-dd)
							html += '<div class="layui-inline">'
									+ '<label class="layui-form-label">'
									+ item.formName
									+ '</label>'
									+ '<div class="layui-input-inline">'
									+ '<input type="text" class="layui-input" id="'
									+ item.field + '"' + 'name="' + item.field
									+ '"placeholder="yyyy-MM-dd">' + '</div>'
									+ '</div>';
							data_picker_ids.push(item.field);
							break;
					}
					if (index % 2 == 1) {
						html += '</div>';
					}
				});
		$('#equipment_properties').after(html);
		$.each(data_picker_ids, function(index, item) {
					console.log(item);
					laydate.render({
								elem : '#' + item
							});

				});
		$('#equipment_edit_form').css('display', 'block');
		// console.log(form_initial_data);
		// form.val("equipment_edit_form", form_initial_data);
		var equPositionNum_input = $('input[name="equPositionNum"]');
		equPositionNum_input.val(form_initial_data.equPositionNum);
		equPositionNum_input.attr('disabled', true);
		equPositionNum_input.addClass('layui-disabled');
	}

});