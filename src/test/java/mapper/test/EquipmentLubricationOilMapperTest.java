package mapper.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.soa.IotEquipmentApplication;
import cn.soa.dao.lubrication.EquipmentLubricationOilMapper;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecord;
import cn.soa.entity.lubrication.EquipmentOilRecordVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IotEquipmentApplication.class })
@WebAppConfiguration
public class EquipmentLubricationOilMapperTest {
	
	@Autowired
	private EquipmentLubricationOilMapper equipmentLubricationOilMapper;
	
	@Test
	public void insertOilTest() {
		
		EquipmentLubricationOil equipmentLubricationOil = new EquipmentLubricationOil();
		equipmentLubricationOil.setOname("320#液压齿轮油2");
		equipmentLubricationOil.setOstock("15");
		equipmentLubricationOil.setOstate(1);
		equipmentLubricationOil.setOunit("油品集团");
		equipmentLubricationOil.setOdescribe("没有描述");
		equipmentLubricationOil.setOtype("没有类型");
		equipmentLubricationOil.setManufacture("非法厂家");
		equipmentLubricationOil.setOsign("没有牌号");
		equipmentLubricationOil.setOremark1("备用1");
		equipmentLubricationOil.setOremark2("备用2");
		Integer row = equipmentLubricationOilMapper.insertOil(equipmentLubricationOil);
		System.err.println("插入油品数量："+row+":"+equipmentLubricationOil.getOid());
		
		EquipmentOilRecord equipmentOilRecord = new EquipmentOilRecord();
		equipmentOilRecord.setOid(equipmentLubricationOil.getOid());
		Date date = new Date();
		equipmentOilRecord.setRtime(date);
		equipmentOilRecord.setRinout("加");
		equipmentOilRecord.setRamount(equipmentLubricationOil.getOstock());
		equipmentOilRecord.setUserid("操作人");
		equipmentOilRecord.setRtype("油品入库");
		equipmentOilRecord.setRnote("备注");
		equipmentOilRecord.setRremark1("备用1");
		equipmentOilRecord.setRremark2("备用2");
		
		System.err.println(equipmentOilRecord.getRtime().toString());
		Integer rowr = equipmentLubricationOilMapper.insertRecord(equipmentOilRecord);
		System.err.println("插入油品数量1："+rowr+":"+equipmentOilRecord.getOrid());
	}

	@Test
	public void findOilAllTest() {
			 List<EquipmentOilRecordVO> row = equipmentLubricationOilMapper.findOilAll(1,10,null,"2019-06-06 08:06:22","2020-06-06 08:06:22");
			System.err.println("插入油品数量："+row);
	}
	
	@Test
	public void countTest() {
			 Integer row = equipmentLubricationOilMapper.countRecord(null,null,null);
			System.err.println("查询油品数量："+row);
	}
	

}
