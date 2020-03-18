
/**
 * <一句话功能描述>
 * <p>备件出入库服务层实现
 * @author 陈宇林
 * @version [版本号, 2020年3月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl.spareparts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import cn.soa.dao.spareparts.SpPutInMapper;
import cn.soa.dao.spareparts.SpRecordMapper;
import cn.soa.dao.spareparts.SpRegisterMapper;
import cn.soa.dao.spareparts.SparePartMapper;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.SpPutIn;
import cn.soa.entity.spareparts.SpRecord;
import cn.soa.entity.spareparts.SpRegister;
import cn.soa.entity.spareparts.SparePart;
import cn.soa.entity.spareparts.SparepartOutInEntity;
import cn.soa.exception.ParameterNotDiscernmentException;
import cn.soa.service.intel.spareparts.SparepartOutInService;
import cn.soa.utils.DateUtils;

@Service
public class SparepartOutInServiceImpl implements SparepartOutInService {

	@Autowired
	private SpPutInMapper spPutInMapper;// 备件申请表mapper

	@Autowired
	private SpRegisterMapper spRegisterMapper;// 备件出入库登记表mapper

	@Autowired
	private SpRecordMapper spRecordMapper;// 备件出入库记录mapper

	@Autowired
	SparePartMapper sparePartMapper;// 备件表mapper

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartOutInService#getSparepartApply(cn.
	 * soa.entity.QueryCondition)
	 */
	@Override
	public Page<SpPutIn> getSparepartApply(QueryCondition condition) {

		Page<SpPutIn> result = spPutInMapper.findByCondition(condition);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartOutInService#doSparepartOutIn(cn.soa
	 * .entity.spareparts.SparepartOutInEntity)
	 */
	@Override
	@Transactional
	public String doSparepartOutIn(SparepartOutInEntity sparepartOutInEntity) throws ParameterNotDiscernmentException {

		String operateType = sparepartOutInEntity.getOperateType();

		// 备件出入库登记实体
		SpRegister spRegister = sparepartOutInEntity.getSpRegister();

		// 备件出入库记录信息
		List<SpRecord> spRecords = new ArrayList<>();

		/**
		 * 备件申请表数据回写数据（出入库状态、出入库时间）
		 */
		SpPutIn SpPutIn = new SpPutIn();
		SpPutIn.setOutPutTime(DateUtils.getCurrentDate());
		SpPutIn.setRequestCode(spRegister.getRequestCode());

		switch (operateType) {
		case "out":
			// 出库
			spRecords = spRecordMapper.selectByRequestCode(spRegister.getRequestCode());
			for (SpRecord spRecord : spRecords) {
				SparePart sparePart = new SparePart();
				// 设置id
				sparePart.setSpId(spRecord.getSpId());
				// 设置库存=当前库存-出库数量
				sparePart.setSpInventory((short) (spRecord.getSpInventory() - spRecord.getQuantity()));
				// 更新库存
				sparePartMapper.updateSelective(sparePart);
			}
			SpPutIn.setOutPutStatus("出库完成");

			break;
		case "in":
			// 采购入库
			spRecords = spRecordMapper.selectByRequestCode(spRegister.getRequestCode()).getResult();
			for (SpRecord spRecord : spRecords) {
				SparePart sparePart = new SparePart();
				// 设置id
				sparePart.setSpId(spRecord.getSpId());
				// 设置库存=当前库存-出库数量
				sparePart.setSpInventory((short) (spRecord.getSpInventory() + spRecord.getQuantity()));
				// 更新库存
				sparePartMapper.updateSelective(sparePart);
			}

			SpPutIn.setOutPutStatus("入库完成");
			spPutInMapper.updateByRequestCode(SpPutIn);
			break;
		case "normalIn":
			// 普通入库
			spRecords = sparepartOutInEntity.getSpRecords();
			for (SpRecord spRecord : spRecords) {
				SparePart sparePart = new SparePart();
				// 设置id
				sparePart.setSpId(spRecord.getSpId());
				// 设置库存=当前库存-出库数量
				sparePart.setSpInventory((short) (spRecord.getSpInventory() + spRecord.getQuantity()));
				// 更新库存
				sparePartMapper.updateSelective(sparePart);

				// 写入数据到备件出入库记录表
				spRecordMapper.insertSelective(spRecord);
			}
			break;
		default:
			throw new ParameterNotDiscernmentException("传入的参数operateType，参数值无法匹配");
		}

		// 设置登记时间
		spRegister.setRegisterDate(DateUtils.getCurrentDate());

		// 插入数据到备件出入库登记表
		spRegisterMapper.insertSelective(spRegister);

		return "备件出入库操作成功";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartOutInService#getOutInRegisterInfo(cn
	 * .soa.entity.QueryCondition)
	 */
	@Override
	public Page<SpRegister> getOutInRegisterInfo(QueryCondition condition) {
		Page<SpRegister> result = spRegisterMapper.selectByCondition(condition);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartOutInService#getSpRecord(java.lang.
	 * String)
	 */
	@Override
	public Page<SpRecord> getSpRecord(String requestCode) {
		System.out.println(requestCode);
		Page<SpRecord> result = spRecordMapper.selectByRequestCode(requestCode);
		return result;
	}

}
