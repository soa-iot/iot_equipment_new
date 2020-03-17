
/**
 * <一句话功能描述>
 * <p>备件采购/领用申请服务层实现类
 * @author 陈宇林
 * @version [版本号, 2020年3月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl.spareparts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.soa.dao.spareparts.SpPutInMapper;
import cn.soa.dao.spareparts.SpRecordMapper;
import cn.soa.entity.spareparts.SpPutIn;
import cn.soa.entity.spareparts.SpRecord;
import cn.soa.entity.spareparts.SparepartApply;
import cn.soa.service.intel.spareparts.SparepartApplyService;
import cn.soa.utils.DateUtils;

@Service
public class SparepartApplyServiceImpl implements SparepartApplyService {

	@Autowired
	private SpPutInMapper spPutInMapper;// 设备备件申请表mapper

	@Autowired
	private SpRecordMapper spRecordMapper;// 设备备件出入库记录表mapper

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartApplyService#addSparepartApply(cn.
	 * soa.entity.spareparts.SparepartApply)
	 */
	@Override
	@Transactional
	public String addSparepartApply(SparepartApply sparepartApply) {

		Integer spPutInResult = 0;// 设备备件申请表数据插入结果

		Integer spRecordResult = 0;// 设备备件出入库记录表数据插入结果

		SpPutIn spPutIn = sparepartApply.getSpPutIn();// 备件申请单
		List<SpRecord> spRecords = sparepartApply.getSpRecords();// 备件出入库记录

		//设置申请日期
		spPutIn.setApplicationDate(DateUtils.getCurrentDate());
		
		spPutInResult = spPutInMapper.insertSelective(spPutIn);

		for (SpRecord spRecord : spRecords) {
			spRecordResult += spRecordMapper.insertSelective(spRecord);
		}

		return "成功添加" + spPutInResult + "条申请记录，" + spRecordResult + "条出入库记录";
	}

}
