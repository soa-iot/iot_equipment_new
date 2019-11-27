package service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.soa.IotEquipmentApplication;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecordVO;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;
import cn.soa.service.intel.lubrication.EquipmentLubricationOilSI;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IotEquipmentApplication.class })
@WebAppConfiguration
public class EquipmentLubricationOilServiceTest {
	
	@Autowired
	private EquipmentLubricationOilSI equipmentLubricationOilSI;
	
	@Test
	public void addOilTest() {
		for (int i = 1; i < 25; i++) {
			
			EquipmentLubricationOil equipmentLubricationOil = new EquipmentLubricationOil();
			equipmentLubricationOil.setOname("新增测试油"+i);
			equipmentLubricationOil.setOstock(15.0);
			equipmentLubricationOil.setOstate(1);
			equipmentLubricationOil.setOunit("油品集团");
			equipmentLubricationOil.setOdescribe("没有描述");
			equipmentLubricationOil.setOtype("没有类型");
			equipmentLubricationOil.setManufacture("非法厂家");
			equipmentLubricationOil.setOsign("没有牌号");
			equipmentLubricationOil.setOremark1("备用1");
			equipmentLubricationOil.setOremark2("备用2");
			Integer row = equipmentLubricationOilSI.addOil(equipmentLubricationOil, "czr", "bz");
			System.err.println("插入油品数量"+i+"："+row);
		}
		
	}

	
	@Test 
	public void findOilAllTest() { 
		List<EquipmentOilRecordVO> row = equipmentLubricationOilSI.queryOilAll(1,10,null,"2019-06-06 08:06:22","2020-06-06 08:06:22");
		System.err.println("插入油品数量："+row); 
	}
	
	@Test 
	public void entityTest() { 
		LubricateEquipmentRecord lubricateEquipmentRecord = new LubricateEquipmentRecord();
		lubricateEquipmentRecord.setLid("lid");
		lubricateEquipmentRecord.setPid("pid");
		lubricateEquipmentRecord.setRid("rid");
		lubricateEquipmentRecord.setOid("oid");
		System.err.println("插入油品数量："+lubricateEquipmentRecord); 
	}
	

}
