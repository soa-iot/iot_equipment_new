package cn.soa.controller.lubrication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.soa.entity.LubricationMothlyReport;
import cn.soa.entity.LubricationRecordReport;
import cn.soa.entity.ResultJson;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;
import cn.soa.service.intel.lubrication.EquipmentLubricationSI;
import cn.soa.utils.ExportExcelUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备润滑控制层
 * @author Luo Guimao
 * @since 2019-08-01
 */

@RestController
@Slf4j
@RequestMapping("/lubrication")
public class EquipmentLubricationC {
	
	@Autowired
	private EquipmentLubricationSI equipmentLubricationSI;
	
	/**
	 * 新增换油设备
	 * @return
	 */
	@RequestMapping("/addlub")
	public ResultJson<Integer> addLub(LubricateEquipmentPlace lubricateEquipmentPlace) {
		//Integer row=0;
		log.info("===============================新增润滑设备："+lubricateEquipmentPlace);
		log.info("===============================新增润滑设备："+lubricateEquipmentPlace.getLnamekey()+";"+lubricateEquipmentPlace.getLname());
		Integer row = equipmentLubricationSI.addLub(lubricateEquipmentPlace);
		log.info("===============================新增油品数量："+row);
		if (row > 0 ) {
			return new ResultJson<Integer>(0, "新增油品成功");
		}else {
			return new ResultJson<Integer>(1, "新增油品失败");
		}
	}
	
	/**
	 * 按条件分页查询换油设备
	 * @param equip 查询条件
	 * @return
	 */
	@GetMapping("/management/query")
	public ResultJsonForTable<List<LubricateEquipment>> findEquipLubricateByPage(LubricateEquipment equip, Integer page, Integer limit){
		log.info("------进入findEquipLubricateByPage执行分页查询换油设备");
		log.info("------查询条件为：{}", equip);
		log.info("------page：{} , limit: {}", page, limit);
		
		return equipmentLubricationSI.findEquipLubricationByPage(equip, page, limit);
	}
	
	/**
	 * 根据润滑设备lid查询设备换油记录
	 * @param lid 设备lid
	 * @return
	 */
	@GetMapping("/record/query/lid")
	public ResultJsonForTable<List<LubricateEquipmentRecord>> findEquipLubricateRecordBylid(String lid, Integer page, Integer limit){
		log.info("------进入findEquipLubricateRecordBylid执行分页查询设备换油记录");
		log.info("------润滑设备lid={}", lid);
		log.info("------page：{} , limit: {}", page, limit);
		
		return equipmentLubricationSI.findEquipLubricationRecordByLid(lid, page, limit);
	}
	
	/**
	 * 临时换油记录跟踪
	 * @param lid 设备lid
	 * @return
	 */
	@GetMapping("/place/change/trace")
	public ResultJsonForTable<List<LubricateEquipmentPlace>> findEquipLubricationTrace(String positionnum, String tname, Integer page,
			Integer limit){
		log.info("------进入findEquipLubricationTrace执行临时换油记录跟踪");
		log.info("------换油设备位号={}", positionnum);
		log.info("------换油设备名称={}", tname);
		log.info("------page：{} , limit: {}", page, limit);
		
		return equipmentLubricationSI.findEquipLubricationTrace(positionnum, tname, page, limit);
	}
	
	/**
	 * 按月统计每种润滑油使用量
	 */
	@GetMapping("/year/report")
	public ResultJson<List<LubricationMothlyReport>> findRecordByYear(String year){
		log.info("------进入findRecordByYear统计每种润滑油使用量");
		log.info("------查询年份year：{}", year);
		
		return new ResultJson<>(ResultJson.SUCCESS, "查询成功", equipmentLubricationSI.findRecordByYear(year));
	}
	
	/**
	 * 设备润滑油加油和换油记录
	 * @param lid 设备lid
	 * @return
	 */
	@GetMapping("/add/change/record/report")
	public ResultJsonForTable<List<LubricationRecordReport>> findLubricationRecord(String positionnum, String tname,
			String startDate, String endDate, Integer page, Integer limit){
		log.info("------进入findLubricationRecord执行查询设备润滑油加油和换油记录");
		log.info("------换油设备位号={}", positionnum);
		log.info("------换油设备名称={}", tname);
		log.info("------开始时间startDate={} , 结束时间endDate={}", tname, endDate);
		log.info("------page：{} , limit: {}", page, limit);
		
		return equipmentLubricationSI.findLubricationRecordByPage(positionnum, tname, startDate, endDate, page, limit);
	}
	
	/**
	 * 查询换油部位
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/lubplace")
	public ResultJsonForTable<List<LubricateEquipmentPlace>> findLubPlace(Integer page, Integer limit){
		log.info("------page：{} , limit: {}", page, limit);
		List<LubricateEquipmentPlace> lubricateEquipmentPlaces = equipmentLubricationSI.findLubPlace(page, limit);
		Integer count = equipmentLubricationSI.findLubPlaceCount();
		
		if(lubricateEquipmentPlaces != null) {
			return new ResultJsonForTable<List<LubricateEquipmentPlace>>(0, "查询成功",count, lubricateEquipmentPlaces);
		}else {
			return new ResultJsonForTable<List<LubricateEquipmentPlace>>(1, "查询失败", 0, null);
		}
		
	}
	
	/**
	 * 导出润滑油月度记录表
	 * @throws UnsupportedEncodingException 
	 */
	@GetMapping("/export/report/{year}")
	public ResponseEntity<byte[]> exportReportByYear(@PathVariable("year") String year) throws UnsupportedEncodingException{
		log.info("------进入exportReportByYear导出润滑油月度记录表");
		log.info("------查询年份year：{}", year);
		
		List<LubricationMothlyReport> result = equipmentLubricationSI.findRecordByYear(year);
		byte[] data = null;
		try {
			data = ExportExcelUtil.exportLubricationMonthlyReport(year, result);
			//设置content-type
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			//处理文件名中文乱码
			String fileName = URLEncoder.encode("润滑油月度记录表.xlsx", "UTF-8");
			header.add("Content-Disposition", "attachment; filename="+fileName);
			//header.setContentDispositionFormData("attachment", fileName);
			return new ResponseEntity<byte[]>(data, header, HttpStatus.OK);
			
		} catch (Exception e) {
			log.info("------导出excel表失败------");
			log.info(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 导出设备润滑油记录表
	 * @throws UnsupportedEncodingException 
	 */
	@GetMapping("/export/report/record")
	public ResponseEntity<byte[]> exportReportRecord(String positionnum, String tname, String startDate, String endDate) throws UnsupportedEncodingException{
		log.info("------进入exportReportRecord执行导出设备润滑油记录表");
		log.info("------换油设备位号={}", positionnum);
		log.info("------换油设备名称={}", tname);
		log.info("------开始时间startDate={} , 结束时间endDate={}", tname, endDate);
		
		List<LubricationRecordReport> result = equipmentLubricationSI.findLubricationRecord(positionnum, tname, startDate, endDate);
		byte[] data = null;
		try {
			data = ExportExcelUtil.exportLubricationRecordReport(result);
			//设置content-type
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			//处理文件名中文乱码
			String fileName = URLEncoder.encode("设备润滑油记录表.xlsx", "UTF-8");
			header.add("Content-Disposition", "attachment; filename="+fileName);
			//header.setContentDispositionFormData("attachment", fileName);
			return new ResponseEntity<byte[]>(data, header, HttpStatus.OK);
			
		} catch (Exception e) {
			log.info("------导出excel表失败------");
			log.info(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 根据位号和换油部位查询换油部位
	 * @param lnamekey
	 * @param pplace
	 * @return
	 */
	@PostMapping("/findplaceandnamekey")
	public ResultJson<LubricateEquipmentPlace> findLubPlaceByNamekey(String lnamekey, String pplace) {
		
		log.info("===========根据位号和换油部位查询换油部位=====设备位号====="+lnamekey);
		log.info("===========根据位号和换油部位查询换油部位=====设备部位====="+pplace);
		LubricateEquipmentPlace lubricateEquipmentPlace = equipmentLubricationSI.findLubPlaceByNamekey(lnamekey, pplace);
		
		if (lubricateEquipmentPlace != null) {
			return new ResultJson<LubricateEquipmentPlace>(0, "此设备的该部位已存在！！", lubricateEquipmentPlace);
		}else {
			return new ResultJson<LubricateEquipmentPlace>(1, "该部位可以增加！！", null);
		}
	}
	
	/**
	 * 设备加换油
	 * @param lubricateEquipmentRecord
	 * @return
	 */
	@GetMapping("lueqpladdchangeoil")
	public ResultJson<Integer> LuEqPlAddChangeOil(LubricateEquipmentRecord lubricateEquipmentRecord, String rtype) {
		
		//lubricateEquipmentRecord.setPtime(new Date());
		log.info("-----------C------------加/换油部位id："+lubricateEquipmentRecord.getPid());
		log.info("-----------C------------操作人："+lubricateEquipmentRecord.getExcutor());
		log.info("-----------C------------加/换油时间："+lubricateEquipmentRecord.getPtime());
		log.info("-----------C------------加/换油量："+lubricateEquipmentRecord.getRamount());
		log.info("-----------C------------加/换油类型："+rtype);
		
		Integer row = equipmentLubricationSI.updateLuEqPlByPid(lubricateEquipmentRecord,rtype);
		
		if (row > 0) {
			return new ResultJson<Integer>(0, "加换油成功");
		}else {
			return new ResultJson<Integer>(1, "加换油设备");
		}
		
		
	}
}
