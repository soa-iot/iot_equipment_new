package mapper.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ibm.icu.util.Calendar;

import cn.soa.IotEquipmentApplication;
import cn.soa.dao.lubrication.EquipmentLubricationMapper;
import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IotEquipmentApplication.class })
@WebAppConfiguration
public class EquipmentLubricationMapperTest {
	
	@Autowired
	private EquipmentLubricationMapper equipmentLubricationMapper;
	
	@Test
	public void findLubEquiTest() {
		List<LubricateEquipment> lubricateEquipments = equipmentLubricationMapper.findLubEqui(new LubricateEquipment().setLid("kkk"));
		System.err.println(lubricateEquipments);
	}
	
	@Test
	public void insertLubEquiTest() {
		
		LubricateEquipment lubricateEquipment = new LubricateEquipment();
		lubricateEquipment.setIsuser("是");
		lubricateEquipment.setLname("设备名称2");
		lubricateEquipment.setLnamekey("设备名称1（唯一）");
		lubricateEquipment.setLnumber("设备编号");
		lubricateEquipment.setLposition1("设备所属1");
		lubricateEquipment.setLposition2("设备所属2");
		lubricateEquipment.setLremark1("备用1");
		lubricateEquipment.setLremark2("备用2");
		lubricateEquipment.setLstate(1);
		lubricateEquipment.setLtype("设备类型");
		
		Integer row = equipmentLubricationMapper.insertLubEqui(lubricateEquipment);
		System.err.println("========row:"+row);
	}
	
	@Test
	public void insertLubPlaceTest() {
		
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
		lubricateEquipmentPlace.setLid("1F81BBB817CD47C08FBA2D073CCE24EA");
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
		
		Integer row = equipmentLubricationMapper.insertLubPlace(lubricateEquipmentPlace);
		System.err.println("========row:"+row);
	}

	@Test
	public void findPlaceEquiTest() {
		
		List<LubricateEquipmentPlace>  lubricateEquipmentPlaces = equipmentLubricationMapper.findLubPlaceByNamekey("K-1402(Ⅰ)", "风扇叶子");
		 System.err.println(lubricateEquipmentPlaces);
	}
	
	@Test
	public void updateLuEqPlByPidTest() {
		
		LubricateEquipmentPlace lubricateEquipmentPlace = new LubricateEquipmentRecord();
		lubricateEquipmentPlace.setPid("54E8C20BFD9A419CA855A174697001AB");
		lubricateEquipmentPlace.setLastchangetime(new Date());
		lubricateEquipmentPlace.setNextchangetime(new Date());
		Integer  row = equipmentLubricationMapper.updateLuEqPlByPid(lubricateEquipmentPlace );
		 System.err.println(row);
	}
	
	@Test
	public void updateLEqPlByPidTest() {
		
		LubricateEquipmentPlace lubricateEquipmentPlace = new LubricateEquipmentPlace();
		lubricateEquipmentPlace.setPid("54E8C20BFD9A419CA855A174697001AB");
		
		lubricateEquipmentPlace.setPplace("风扇叶子");
		  LubricateEquipmentPlace row = equipmentLubricationMapper.findLuEqPlByAll(lubricateEquipmentPlace );
		 System.err.println(row);
	}
}
