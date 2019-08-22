package cn.soa.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import cn.soa.dao.EquipmentTypeBackupMapper;
import cn.soa.entity.EquipmentTypeBackup;
import cn.soa.entity.EquipmentTypeForExcel;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.service.intel.EquipmentTypeBackupSI;
import cn.soa.utils.CommonUtil;
import cn.soa.utils.RollbackEquipmentBackupExcel;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备台账导入备份 业务层
 * @author Jiang, Hang
 * @since 2019-08-01
 */
@Service
@Slf4j
@PropertySource(value= {"classpath:/config/equipmentBackup.properties"}, encoding="UTF-8")
public class EquipmentTypeBackupS implements EquipmentTypeBackupSI {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private EquipmentTypeBackupMapper equipTypeBackupMapper;

	
	@Value("${export.excel.url}")
	private String url;  //从配置文件中读取excel导出路径
	
	/**
	 * 添加一条设备台账导入备份信息
	 * @param equipBackup 设备台账导入备份信息
	 * @return 是否添加整改
	 */
	@Override
	public String addEquipmentBackupInfo(EquipmentTypeBackup equipBackup) {

		//1. 调用CZ_PIOTMS的导出excel功能ExportExcelOfEquServlet备份设备信息
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
		map.add("EQU_MEMO_ONE", equipBackup.getBname());
		map.add("moduleType", "1");
		map.add("requestType", "backup");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, header);
		//模拟http post请求
		log.info("---------调设备导出功能接口/CZ_PIOTMS/ExportExcelOfEquServlet.action进行excel备份操作----------");
		String result = restTemplate.postForObject(url, request, String.class);
		
		if(result != null && !"failure".equals(result)) {
			log.info("---------设备excel备份成功：{}", result);
			//2. 将备份信息插入数据库
			equipBackup.setBpath(result);
			try {
				Integer rows = equipTypeBackupMapper.insertBackupInfo(equipBackup);
				log.info("---------设备台账导入备份信息插入数据库成功---------");
				log.info("--------插入数据的bid："+equipBackup.getBid());
				return equipBackup.getBid();
			}catch (Exception e) {
				log.error("-------设备台账导入备份信息插入数据库失败-------");
				log.error(e.getMessage());
			}
		}else {
			log.info("---------设备excel备份失败-----------");
		}
		
		return null;
	}
	
	/**
	 * 条件查询设备台账导入备份信息
	 * @param bname 设备类型
	 * @param startDate 开始时间
	 * @param endDate  结束时间
	 * @param bperson  操作人 
	 * @return 设备台账导入备份信息列表
	 */
	@Override
	public ResultJsonForTable<List<EquipmentTypeBackup>> findByCondition(String bname, String startDate, String endDate, String bperson, Integer page, Integer limit) {
		
		log.info("--------开始查询设备台账导入备份信息-----------");
		Integer count = equipTypeBackupMapper.countByCondition(bname, startDate, endDate, bperson);
		if(count == null || count == 0) {
			log.info("------查询备份信息数量为：{}", count);
			return new ResultJsonForTable(0, "查询成功", 0, null);
		}
		List<EquipmentTypeBackup> result = equipTypeBackupMapper.findByCondition(bname, startDate, endDate, bperson, page, limit);
		log.info("------查询备份信息数量为：{}", count);
		log.info("--------查询设备台账导入备份信息结束-----------");		
		return new ResultJsonForTable<List<EquipmentTypeBackup>>(0, "查询成功", (result == null)?0:count, result);
	}
	
	/**
	 * 根据bid查询设备台账导入备份信息
	 * @param bid 主键id
	 * @return 设备台账导入备份信息
	 */
	@Override
	public ResponseEntity<byte[]> findByBid(String bid) {
		
		log.info("------查找备份文件存放路径--------");
		EquipmentTypeBackup backup = equipTypeBackupMapper.findByBid(bid);
		if(backup == null) {
			log.info("------没有在数据库查询到记录--------");
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		String path = backup.getBpath();
		log.info("------设备备份信息存放路径为：{}", path);
		
		//读取备份文件，转换为字节数据byte[]
		try (RandomAccessFile raf = new RandomAccessFile(path, "r")){
			byte[] data = new byte[(int)raf.length()];
			raf.read(data);
			
			String bname = backup.getBname();
			String fileName = bname+"备份表"+CommonUtil.dateFormat(backup.getBcreatetime())+".xls";
			
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			//处理文件名中文乱码
			fileName = URLEncoder.encode(fileName, "UTF-8");
			header.add("Content-Disposition", "attachment; filename="+fileName);
			
			return new ResponseEntity<byte[]>(data, header, HttpStatus.OK);
		} catch (IOException e) {
			log.error("------设备备份文件未找到---------");
			log.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 根据bid删除设备台账导入备份信息
	 * @param bid 主键id
	 * @return 受影响行数
	 */
	@Override
	public Integer deleteByBid(String bid) {
		log.info("---------开始删除备份记录-------");
		try {
			Integer row = equipTypeBackupMapper.deleteByBid(bid);
			log.info("---------删除bid:{}备份记录成功-------", bid);
			return row;
		}catch (Exception e) {
			log.info("---------删除bid:{}备份记录失败-------", bid);
			log.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 还原设备台账导入备份信息
	 * @param bid 主键id
	 * @return 是否还原成功
	 */
	@Override
	@Transactional
	public boolean RollbackData(String bid) {
		EquipmentTypeBackup result = equipTypeBackupMapper.findByBid(bid);
		if(result == null) {
			log.info("-------还原失败，bid={}的备份记录不存在-------", bid);
			return false;
		}
		//取出备份excel文件的路径
		String path = result.getBpath();
		String equipType = result.getBname();
		log.info("--------备份文件的路径为：{}", path);
		log.info("--------备份文件的设备类型为：{}", equipType);
		//读取备份文件，进行数据库还原操作
		BufferedInputStream bis = null;
		
		try{
			bis = new BufferedInputStream(new FileInputStream(path));
			List<EquipmentTypeForExcel> list = RollbackEquipmentBackupExcel.readExcel(bis, equipType);
			if(list == null || list.size() == 0) {
				log.info("------- 备份数据为空，请检查备份文件：{} ------", path);
				return false;
			}
			for(EquipmentTypeForExcel equ : list) {
				if(equ.getEQU_POSITION_NUM() != null || equ.getEQU_POSITION_NUM() != "") {
					try {
						
						Integer row = equipTypeBackupMapper.updateBackup(equ);
						if(row == null || row == 0) {
							String uuid = UUID.randomUUID().toString();
							equ.setEQU_ID(uuid);
							Integer row1 = equipTypeBackupMapper.insertBackup(equ);
							if(row1 == null || row1 == 0) {
								log.info("------数据还原失败，失败数据为：{}", equ);
								return false;
							}
							
						}
					}catch (Exception e) {
						log.info("------数据还原失败，失败数据为：{}", equ);
						log.info(e.getMessage());
						throw new RuntimeException("数据还原失败");
					}	
				}
			}
			log.info("------备份还原成功-------");
			return true;
			
		} catch (FileNotFoundException e) {
			log.info("-------备份文件未找到，路径为：{}", path);
			log.info(e.getMessage());
			e.printStackTrace();
			
		} catch (IOException e) {
			log.info("-------POIFSFileSystem无法解析输入流-------");
			log.info(e.getMessage());
			
		}finally {
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}

}
