package cn.soa.service.impl.lubrication;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.soa.dao.lubrication.EquipmentLubricationOilMapper;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecord;
import cn.soa.entity.lubrication.EquipmentOilRecordVO;
import cn.soa.service.intel.lubrication.EquipmentLubricationOilSI;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备润滑 业务层
 * @author Luo Guimao
 * 
 */
@Service
@Slf4j
public class EquipmentLubricationOilS implements EquipmentLubricationOilSI{
	
	@Autowired
	private EquipmentLubricationOilMapper equipmentLubricationOilMapper;
	
	/**
	 * 新增油品
	 * @return
	 */
	@Override
	@Transactional
	public Integer addOil(EquipmentLubricationOil equipmentLubricationOil,String userid,String rnote)  {
		
		equipmentLubricationOil.setOunit("升");
			Integer row = insertOil(equipmentLubricationOil);
			
			System.err.println("equipmentLubricationOil:"+ equipmentLubricationOil.toString());
			
//			
//			EquipmentOilRecord equipmentOilRecord = new EquipmentOilRecord();
//			
//			equipmentOilRecord.setOid(equipmentLubricationOil.getOid());
//			equipmentOilRecord.setRtime(new Date());
//			equipmentOilRecord.setRinout("加");
//			equipmentOilRecord.setRamount(equipmentLubricationOil.getOstock());
//			equipmentOilRecord.setUserid(userid);
//			equipmentOilRecord.setRtype("油品入库");
//			equipmentOilRecord.setRnote(rnote);
//			
			String ostock = equipmentLubricationOil.getOstock();
			String oid = equipmentLubricationOil.getOid();
			Integer rstock = Integer.valueOf(ostock);
			log.info("========================rstock:"+rstock);
			
			if (row > 0) {
				Integer row1 = addRecord("加", "油品入库", ostock,oid , userid, rnote,rstock);
				if (row1 > 0) {
					return row1;
				}else {
					return -1;
				}
			}else {
				return -1;
			}
	}
	
	/**
	 * 油品入库
	 */
	@Transactional
	public Integer oilStock(String oname,String ramount,String rnote,String userid) {
		
		EquipmentLubricationOil equipmentLubricationOil = new EquipmentLubricationOil();
		equipmentLubricationOil.setOname(oname);
		List<EquipmentLubricationOil> equipmentLubricationOils = findOilbyConditions(equipmentLubricationOil);
		
		EquipmentLubricationOil equipmentLubricationOi = equipmentLubricationOils.get(0);
		String oid = equipmentLubricationOi.getOid();
		
		Integer ramount1 = Integer.valueOf(ramount);
		log.info("======================================入库数量ramount:"+ramount1);
		String ostock = equipmentLubricationOi.getOstock();
		//生成时时库存
		Integer rstock = ostock == null ? 0 : Integer.valueOf(ostock);
				
		log.info("================================生成时时库存:"+rstock);
				
		if (equipmentLubricationOils != null && equipmentLubricationOils.size() > 0) {
			rstock = rstock+ramount1;
		}
				
		log.info("================================生成时时库存1:"+rstock);
		
		Integer row = equipmentLubricationOilMapper.updateStock(ramount1,oid);
		
		if (row > 0) {
			return addRecord("加", "油品入库", ramount,oid , userid, rnote,rstock);
		}else {
			return row;
		}
		
	}
	
	/**
	 * 根据油品库存条件查询油品 
	 * @param equipmentLubricationOil
	 * @return
	 */
	public List<EquipmentLubricationOil> findOilbyConditions(EquipmentLubricationOil equipmentLubricationOil){
		
		return equipmentLubricationOilMapper.findOilbyConditions(equipmentLubricationOil);
		
	}
	
	/**
	 * 查询出入库记录
	 */
	@Override
	public List<EquipmentOilRecordVO> queryOilAll(Integer page,Integer limit,String oid,String startTime,String endTime) {
		
		Integer minSize = (page - 1 ) * limit;
		Integer maxSize = page * limit;
		
		log.info("=======================minSize:"+minSize);
		log.info("=======================maxSize:"+maxSize);
		log.info("=======================oid:"+oid);
		return findOilAll(minSize, maxSize, oid, startTime, endTime);
	};
	
	/**
	 * 查询油品出入库总数量
	 * @return
	 */
	@Override
	public Integer countRecord(String oid, String startTime,String endTime) {
		return equipmentLubricationOilMapper.countRecord( oid,  startTime, endTime);
	}
	
	/**
	 * 查询所有油品
	 * @param page
	 * @param limit
	 * @return
	 */
	@Override
	public List<EquipmentLubricationOil> queryOilAllStock(Integer page, Integer limit) {
		
		Integer minSize = (page - 1 ) * limit;
		Integer maxSize = page * limit;
		
		return equipmentLubricationOilMapper.queryOilAllStock(minSize, maxSize);
	}
	
	/**
	 * 查询所有油品数量
	 * @return
	 */
	@Override
	public Integer countStock() {
		
		return equipmentLubricationOilMapper.countStock();
	}
	
	/**
	 * 查询油品出入库记录
	 * @author Luo Guimao
	 * @param equipmentLubricationOil
	 * @return
	 */
	private List<EquipmentOilRecordVO> findOilAll(Integer minSize,Integer maxSize,String oid,String startTime,String endTime){
		log.info("------------------------------------------"+oid);
		return equipmentLubricationOilMapper.findOilAll(minSize, maxSize, oid.trim(), startTime, endTime);
		
	}
	/**
	 * 新增油品 持久层实现方法
	 * @return
	 */
	private Integer insertOil(EquipmentLubricationOil equipmentLubricationOil) {
		
		return equipmentLubricationOilMapper.insertOil(equipmentLubricationOil);
	}
	
	/**
	 * 新增油品出入库记录
	 * @author Luo Guimao
	 * @param equipmentLubricationOil
	 * @return
	 */
	private Integer insertRecord(EquipmentOilRecord equipmentOilRecord) {
		return equipmentLubricationOilMapper.insertRecord(equipmentOilRecord);
	}
	
	/**
	 * 新增油品出入库记录
	 * @author Luo Guimao
	 * @param equipmentLubricationOil
	 * @return
	 */
	public Integer addRecord(String rinout,String rtype,String ostock,String oid, String userid,String rnote,Integer rstock)  {
			
		
		EquipmentOilRecord equipmentOilRecord = new EquipmentOilRecord();
		
		equipmentOilRecord.setOid(oid);
		equipmentOilRecord.setRtime(new Date());
		equipmentOilRecord.setRinout("加");
		equipmentOilRecord.setRamount(ostock);
		equipmentOilRecord.setUserid(userid);
		equipmentOilRecord.setRtype("油品入库");
		equipmentOilRecord.setRnote(rnote);
		equipmentOilRecord.setRstock(rstock);
		
		//调用持久层
		Integer row1 = insertRecord(equipmentOilRecord);
		if (row1 > 0) {
			return row1;
		}else {
			return -1;
		}
	}
	
}
