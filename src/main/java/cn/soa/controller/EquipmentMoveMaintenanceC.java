package cn.soa.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.entity.EquipmentMoveInfo;
import cn.soa.entity.EquipmentMoveRunningTime;
import cn.soa.entity.ResultJson;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentBigEventSI;
import cn.soa.service.intel.EquipmentMoveMaintenanceSI;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;
import cn.soa.utils.CommonUtil;
import cn.soa.utils.ExportMaintenanceExcel;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

/**
 * 动设备信息处理控制层
 * @author Jiang, Hang
 * @since 2019-11-21
 */

@RestController
@Slf4j
@RequestMapping("/maintenance")
@PropertySource(value="classpath:config/fileUpload.properties")
public class EquipmentMoveMaintenanceC {
	
	@Autowired
	private EquipmentMoveMaintenanceSI equipMoveS;
	
	/**
	 * 分页查找动设备检维修信息
	 * @return List<EquipmentMoveInfo> - 动设备检维修信息
	 */
	@GetMapping("/equipment/move/showlist")
	public ResultJsonForTable<List<EquipmentMoveInfo>> getEquipMoveInfo(
			String positionNum, String equipName,String startDate, String endDate,Integer page, Integer limit){
		log.info("-----进入方法EquipmentMoveMaintenanceC...getEquipMoveInfo-----");
		log.info("-----positionNum={}", positionNum);
		log.info("-----equipName={}", equipName);
		log.info("-----startDate={}", startDate);
		log.info("-----endDate={}", endDate);
		log.info("-----page={}", page);
		log.info("-----limit={}", limit);
		
		Integer rows = equipMoveS.countEquipMoveInfo(positionNum, equipName, startDate, endDate);
		if(rows == null || rows == 0) {
			return new ResultJsonForTable<>(ResultJson.SUCCESS, "查找动设备检维修信息条数为0", 0, null);
		}
		
		List<EquipmentMoveInfo> result = equipMoveS.getEquipMoveInfo(positionNum, equipName, startDate, endDate, page, limit);
		if(result == null) {
			return new ResultJsonForTable<>(ResultJson.ERROR, "查找动设备检维修信息失败", 0, null);
		}

		log.info("-----结束方法EquipmentMoveMaintenanceC...getEquipMoveInfo-----");
		return new ResultJsonForTable<>(ResultJson.SUCCESS, "查找动设备检维修信息成功", rows, result);
	}
	
	
	/**
	 * 添加一条动设备检维修信息
	 * @param info - 动设备检维修信息
	 */
	@PostMapping("/equipment/move/add")
	public ResultJson<Boolean> insertEquipMoveInfo(@NotNull EquipmentMoveInfo info){
		log.info("-----进入方法EquipmentMoveMaintenanceC...insertEquipMoveInfo-----");
		log.info("-----EquipmentMoveInfo={}", info);
		
		boolean result = equipMoveS.insertEquipMoveInfo(info);
		
		log.info("-----结束方法EquipmentMoveMaintenanceC...insertEquipMoveInfo-----");
		if(result) {
			return new ResultJson<>(ResultJson.SUCCESS, "添加动设备检维修信息成功", true);
		}
		return new ResultJson<>(ResultJson.ERROR, "添加动设备检维修信息失败", false);
	}
	
	/**
	 * 删除一条动设备检维修信息
	 * @param mid - 设备id
	 */
	@PostMapping("/equipment/move/delete")
	public ResultJson<Boolean> deleteEquipMoveInfo(String mid){
		log.info("-----进入方法EquipmentMoveMaintenanceC...deleteEquipMoveInfo-----");
		log.info("-----mid={}", mid);
		
		boolean result = equipMoveS.deleteEquipMoveInfo(mid);
		
		log.info("-----结束方法EquipmentMoveMaintenanceC...deleteEquipMoveInfo-----");
		if(result) {
			return new ResultJson<>(ResultJson.SUCCESS, "删除动设备检维修信息成功", true);
		}
		return new ResultJson<>(ResultJson.ERROR, "删除动设备检维修信息失败", false);
	}
	
	/**
	 * 更新一条动设备检维修信息
	 * @param info - 动设备检维修信息
	 */
	@PostMapping("/equipment/move/update")
	public ResultJson<Boolean> updateEquipMoveInfo(@NotNull EquipmentMoveInfo info){
		log.info("-----进入方法EquipmentMoveMaintenanceC...updateEquipMoveInfo-----");
		log.info("-----EquipmentMoveInfo={}", info);
		
		boolean result = equipMoveS.updateEquipMoveInfo(info);
		
		log.info("-----结束方法EquipmentMoveMaintenanceC...updateEquipMoveInfo-----");
		if(result) {
			return new ResultJson<>(ResultJson.SUCCESS, "更新动设备检维修信息成功", true);
		}
		return new ResultJson<>(ResultJson.ERROR, "更新动设备检维修信息失败", false);
	}
	
	/**
	 * 动设备检维修添加文件
	 * @param info - 动设备检维修信息
	 */
	
	@Value("${maintenance.file.upload.path}")
	private String filePath;        //文件路径
	
	@PostMapping("/equipment/move/fileupload")
	public ResultJson<Boolean> uploadEquipMoveFile(@NotNull MultipartFile file, String positionNum, String maintenanceTime){
		log.info("-----进入方法EquipmentMoveMaintenanceC...uploadEquipMoveFile-----");
		log.info("------上传文件原名：{}", file.getOriginalFilename());
		log.info("------上传文件文件大小：{}", file.getSize());
		log.info("------设备位号：{}", positionNum);
		log.info("------检维修时间：{}", maintenanceTime);
		
		if(filePath == null	|| positionNum == null || maintenanceTime == null) { 
			return new ResultJson<>(ResultJson.ERROR, "文件上传失败", false);
		}
		
		File dirParent = new File(filePath.replace("file:/", "")+positionNum);
		

		log.debug("----------动设备检维修文件路径{}不存在！", filePath);
		try {
			if(!dirParent.exists()) {
				dirParent.mkdirs();
			}
			
			//使用指定日期创建文件夹
			File dirDate = new File(dirParent, maintenanceTime);
			dirDate.mkdir();
			File f = new File(dirDate, file.getOriginalFilename());
			file.transferTo(f);

		}catch (Exception e) {
			log.info("----------动设备检维修文件夹{}创建失败！", filePath);
			log.info("{}", e);
			return new ResultJson<>(ResultJson.ERROR, "文件上传失败", false);
		}

		return new ResultJson<>(ResultJson.SUCCESS, "文件上传成功", true);
	}
	
	/**
	 * 动设备检维修下载文件
	 * @param date - 文件上传日期
	 * @param filename - 文件名
	 */
	@GetMapping("/equipment/move/filedownload")
	public ResponseEntity<byte[]> downEquipMoveFile(String positionNum, String filename, String maintenanceTime){
		log.info("-----进入方法EquipmentMoveMaintenanceC...downEquipMoveFile-----");
		log.info("------设备位号：{}", positionNum);
		log.info("------上传文件名：{}", filename);
		log.info("------检维修日期：{}", maintenanceTime);
		
		if(filename == null	|| maintenanceTime == null) { 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		InputStream is = null;
		try {
			File dirParent = new File(filePath.replace("file:/", "")+positionNum+"/"+maintenanceTime);
			File file = new File(dirParent, filename);
			
			byte[] body = null;
		    is = new FileInputStream(file);
		    body = new byte[is.available()];    //不要用available()去获取文件大小
		    is.read(body);  
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentLength(file.length());
		    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		    headers.setContentDispositionFormData("attachment", URLEncoder.encode(filename, "UTF-8"));
		    HttpStatus statusCode = HttpStatus.OK;
		    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);

			return entity;

		}catch (Exception e) {
			log.info("-----动设备检维修文件下载发生错误-----");
			log.info("{}", e);
			
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch (IOException e) {
				log.info("-----关闭文件输入流发生错误-----");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 动设备检维修导出信息文件
	 * @param mid - 主键id
	 */
	@GetMapping("/equipment/move/export")
	public ResponseEntity<byte[]> exportEquipMoveInfo(String[] mid){
		log.info("-----进入方法EquipmentMoveMaintenanceC...exportEquipMoveInfo-----");
		log.info("------mid列表：{}", Arrays.toString(mid));
		
		if(mid == null || mid.length == 0) {
			log.info("-----mid列表为空------");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<EquipmentMoveInfo> result = equipMoveS.getEquipMoveInfo(mid);
		
		try {
			//调用工具类生成excel表格
			ByteArrayOutputStream bos = ExportMaintenanceExcel.exportReport(result.get(0).getPositionNum(), result);
			if(bos == null) {
				log.info("-----生成excel表格失败------");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentLength(bos.size());
		    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		    headers.setContentDispositionFormData("attachment", URLEncoder.encode("动设备检维修记录"+result.get(0).getPositionNum()+".xlsx", "UTF-8"));
		    HttpStatus statusCode = HttpStatus.OK;
		    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(bos.toByteArray(), headers, statusCode);

			return entity;

		}catch (Exception e) {
			log.info("-----动设备检维修信息导出发生错误-----");
			log.info("{}", e);
			
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}
	
	/**
	 * 获取动设备检维修文件列表
	 * @param positionNum - 设备位号
	 * @return  
	 */
	@GetMapping("/equipment/move/file/showlist")
	public ResultJsonForTable<List<Map<String, Object>>> getEquipMoveFileList(String positionNum){
		log.info("-----进入方法EquipmentMoveMaintenanceC...getEquipMoveFileList-----");
		log.info("------设备位号：{}", positionNum);
		
		if(positionNum == null) { 
			return new ResultJsonForTable<>(ResultJson.SUCCESS, "获取动设备检维修文件列表失败", 0, null);
		}
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		try {
			File dirParent = new File(filePath.replace("file:/", "")+positionNum);
			File[] list = dirParent.listFiles();
			log.info("-----日期文件夹数量： {}", list.length);
			for(File f : list) {
				File[] fileList = f.listFiles();
				log.info("-----文件数量：{}", fileList.length);
				for(File ff : fileList) {
					String name = ff.getName();
					if(name != null && !"".equals(name)) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("filename", name);
						map.put("date", f.getName());
						log.info("-----文件信息：{}", map);
						result.add(map);
					}
				}
			}
			
			return new ResultJsonForTable<>(ResultJson.SUCCESS, "获取动设备检维修文件列表成功", result.size(), result);
		}catch (Exception e) {
			log.info("-----获取动设备检维修文件列表发生错误-----");
			log.info("{}", e);
			return new ResultJsonForTable<>(ResultJson.ERROR, "暂无相关数据", 0, null);
		}
		
	}
}
