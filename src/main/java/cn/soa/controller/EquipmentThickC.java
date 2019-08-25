package cn.soa.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.soa.entity.EquipmentThickManagement;
import cn.soa.entity.EquipmentThickRecord;
import cn.soa.entity.ResultJson;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentThickRecordSI;
import cn.soa.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备测厚处理控制层
 * @author Jiang, Hang
 * @since 2019-08-01
 */

@RestController
@Slf4j
@RequestMapping("/equipment/thick")
@PropertySource(value= {"classpath:config/fileUpload.properties"}, encoding="UTF-8")
public class EquipmentThickC {
	
	@Autowired
	private EquipmentThickRecordSI equipThickRecordS;
	
	
	/**
	 * 根据条件查询设备测厚记录
	 * @param record  查询条件
	 * @param startDate  开始时间
	 * @param endDate   结束时间
	 * @param page    页数
	 * @param limit   每页条数
	 * @return
	 */
	@GetMapping("/record/query")
	public ResultJsonForTable<List<EquipmentThickRecord>> queryEquipmentThickRecord(
			EquipmentThickRecord record, String startDate, String endDate, Integer page, Integer limit){
		
		log.info("---------进入queryEquipmentThickRecord查询条件如下--------");
		log.info("------设备测厚条件：{}", record);
		log.info("------开始时间：{} ， 结束时间：{}", startDate, endDate);
		log.info("------分页信息： page={}, limit={}", page, limit);
		
		//调用业务层执行查询操作
		ResultJsonForTable<List<EquipmentThickRecord>> result = equipThickRecordS.findEquipRecord(record, startDate, endDate, page, limit);
		return result;
	}
	
	/**
	 * 根据条件查询设备测厚数据
	 * @param record  查询条件
	 * @param page    页数
	 * @param limit   每页条数
	 * @return
	 */
	@GetMapping("/management/query")
	public ResultJsonForTable<List<EquipmentThickManagement>> queryEquipmentThickManagement(
			EquipmentThickManagement equipThick, String startDate, String endDate, Integer page, Integer limit){
		
		log.info("---------进入queryEquipmentThickManagement查询条件如下--------");
		log.info("------设备测厚条件：{}", equipThick);
		log.info("------分页信息： page={}, limit={}", page, limit);
		
		//调用业务层执行查询操作
		ResultJsonForTable<List<EquipmentThickManagement>> result = equipThickRecordS.findEquipThick(equipThick, page, limit);
		return result;
	}
	
	
	@Value("${thick.image.upload.path}")
	private String rootDir;   //保存图片的根路径
	
	@PostMapping("/add")
	public ResultJson<Void> saveEquipmentThick(
			@RequestParam(value="file", required=true) MultipartFile file, EquipmentThickManagement data){
		
		log.info("---------进入saveEquipmentThick请求参数如下--------");
		log.info("------上传图片名称：{}", (file == null)?null:file.getOriginalFilename());
		log.info("------添加的设备测厚信息：{}", data);
		log.info("------保存图片的根路径：{}", rootDir);
		
		//保存上传的图片
		String filename = file.getOriginalFilename();
		filename = System.currentTimeMillis()+filename.substring(filename.lastIndexOf("."));
		log.info("------生成的图片名：{}", filename);
		try {
			File result = CommonUtil.saveFile(rootDir, null, filename);
			file.transferTo(result);
			data.setFilepath(result.getAbsolutePath());
		} catch (IOException e) {
			log.info("------图片保存失败-----");
			log.error(e.getMessage());
			e.printStackTrace();
			return new ResultJson<Void>(ResultJson.ERROR, "图片保存失败");
		}
		log.info("------图片保存成功-----");
		//调用业务层执行查询操作
		data.setCreatime(new Date());
		String str = equipThickRecordS.addEquipThick(data);
		if(str != null && (str.indexOf("成功") != -1)) {
			return new ResultJson<Void>(ResultJson.SUCCESS, str);
		}
		return new ResultJson<Void>(ResultJson.ERROR, str);
	}
	
}
