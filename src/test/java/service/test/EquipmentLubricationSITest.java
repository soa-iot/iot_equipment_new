package service.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.soa.IotEquipmentApplication;
import cn.soa.dao.lubrication.EquipmentLubricationMapper;
import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.service.impl.lubrication.EquipmentLubricationS;
import cn.soa.service.intel.lubrication.EquipmentLubricationSI;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IotEquipmentApplication.class })
@WebAppConfiguration
public class EquipmentLubricationSITest {
	
	@Autowired
	private EquipmentLubricationSI equipmentLubricationSI;
	
	@Test
	public void addLubTest() {
		
		LubricateEquipmentPlace lubricateEquipmentPlace = new LubricateEquipmentPlace();
		lubricateEquipmentPlace.setIsuser("是");
		lubricateEquipmentPlace.setLname("设备名称2");
		lubricateEquipmentPlace.setLnamekey("设备名称1（唯一）");
		lubricateEquipmentPlace.setLnumber("设备编号");
		lubricateEquipmentPlace.setLposition1("设备所属1");
		lubricateEquipmentPlace.setLposition2("设备所属2");
		lubricateEquipmentPlace.setLremark1("备用1");
		lubricateEquipmentPlace.setLremark2("备用2");
		lubricateEquipmentPlace.setLstate(1);
		lubricateEquipmentPlace.setLtype("设备类型");
		Date date = new Date();
		lubricateEquipmentPlace.setLastchangetime(date);
		lubricateEquipmentPlace.setNextchangetime(date);
		lubricateEquipmentPlace.setPamount("标准加油量");
		lubricateEquipmentPlace.setPfrequency("润滑周期");
		lubricateEquipmentPlace.setPlastamount("最后一次加油量");
		lubricateEquipmentPlace.setPnote("描述");
		lubricateEquipmentPlace.setPplace("润滑部位");
		lubricateEquipmentPlace.setPremark1("备用1");
		lubricateEquipmentPlace.setPremark2("备用2");
		lubricateEquipmentPlace.setPtype("类型");
		lubricateEquipmentPlace.setPunit("周期单位");
		lubricateEquipmentPlace.setRequireoil1("要求油品1");
		lubricateEquipmentPlace.setRequireoil2("要求油品2");
		
		Integer row = equipmentLubricationSI.addLub(lubricateEquipmentPlace);
		System.err.println("========row:"+row);
	}
	


}
