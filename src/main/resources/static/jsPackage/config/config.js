//全局通用配置信息
var host = 'localhost';
var port = '8080';
var project = 'iot_equipment';
var url = "http://" + host + ":" + port;
var BASE_WEB = url + "/" + project + "/"; //测试服务器地址
var TABLE_H = document.documentElement.clientHeight;
/**
 * Api接口
 * @type {{}}
 */
var api = {
	sparepartOutIn: { //备件出入库管理
		doSparepartOutIn: BASE_WEB + 'sparepartOutIn/doSparepartOutIn' ,//备件出入库操作
		getOutInRegisterInfo: BASE_WEB + 'sparepartOutIn/getOutInRegisterInfo' ,//获取出入库登记数据列表
		getSparepartApply: BASE_WEB + 'sparepartOutIn/getSparepartApply' ,//获取采购申请单列表数据
		getSpRecord: BASE_WEB + 'sparepartOutIn/getSpRecord' //通过申请单号获取备件出入库记录
	},
	equipmentLedger: { //设备分类树
		getSparepartsClassInfoAsTree: BASE_WEB + 'equipmentLedger/getAllEquipmentType' //获取设备分类树
	},
	sparepartsLedger: { //备件台账
		addSparePartsInfo: BASE_WEB + 'sparepartsLedger/addSparePartsInfo', //添加备件数据
		delSparePartsInfo: BASE_WEB + 'sparepartsLedger/delSparePartsInfo', //删除备件数据列表
		getSparePartsInfo: BASE_WEB + 'sparepartsLedger/getSparePartsInfo', //获取备件数据列表
		updateSparePartsInfo: BASE_WEB + 'sparepartsLedger/updateSparePartsInfo', //更新备件数据
	},
	sparepartApply: { //备件申请（采购/领用）
	addSparepartApply: BASE_WEB + 'sparepartApply/addSparepartApply' ,//添加备件采购/领用申请
	},
	sparepatsManager: { //设备与备件关系维护
		addEquSpareRe: BASE_WEB + 'sparepatsManager/addEquSpareRe', // 设备与备件关系数据添加
		delEquSpareRe: BASE_WEB + 'sparepatsManager/delEquSpareRe', // 设备与备件关系数据删除
		getEquBaseColumn: BASE_WEB + 'sparepatsManager/getEquBaseColumn', // 获取设备基本信息表字段下拉框数据
		getEquInfo: BASE_WEB + 'sparepatsManager/getEquInfo', // 获取设备基本信息
		getEquSpareRe: BASE_WEB + 'sparepatsManager/getEquSpareRe' // 获取设备与备件关系数据
	},SparepartsExcel: { //设备与备件关系维护
		exportSparepart: BASE_WEB + 'SparepartsExcel/exportSparepart', // 备件导出（xls）
		importEqOrSpRe: BASE_WEB + 'SparepartsExcel/importEqOrSpRe', // 设备与备件关系导入
		importSparepart: BASE_WEB + 'SparepartsExcel/importSparepart' // 备件导入
	},
	sparePartType: { //设备备件分类
		addClassifySpRelation: BASE_WEB + 'sparePartType/addClassifySpRelation', //备件分类与备件关系数据添加
		addSparepartsClassInfo: BASE_WEB + 'sparePartType/addSparepartsClassInfo', //添加设备备件分类数据
		delClassifySpRelation: BASE_WEB + 'sparePartType/delClassifySpRelation', //备件分类与备件关系数据删除
		delSparepartsClassInfo: BASE_WEB + 'sparePartType/delSparepartsClassInfo', //删除设备备件分类数据
		getSparepartsClassInfoAsTree: BASE_WEB + 'sparePartType/getSparepartsClassInfoAsTree', //获取设备备件分类树
		updateSparepartsClassInfo: BASE_WEB + 'sparePartType/updateSparepartsClassInfo' //更新设备备件分类数据
	}
};

var SoaIot = {
	getUrlParam: function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var l = decodeURI(window.location.search); // 解决中文乱码问题
		var r = l.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	},getTimemmm:function(){
		return Date.now();
	}
}
