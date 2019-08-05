package cn.soa.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备台账导入备份信息记录实体类
 * @author Jiang, Hang
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentTypeBackup implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bid;    //主键
	private String bname;   //设备分类
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date bcreatetime;   //操作时间
	private String bperson;    //操作人
	private String bnote;      //操作备注
	private String bpath;      //文件存储路径名
	private String btype;      //操作类型
	private String bremark1;    //备用字段1
	private String bremark2;    //备用字段2

}
