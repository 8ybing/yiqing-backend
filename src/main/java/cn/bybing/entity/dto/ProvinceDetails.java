package cn.bybing.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Jhonny
 * @Date: 2022/01/28/19:57
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProvinceDetails implements Serializable {

    private static final long serialVersionUID = -5957433707110390852L;

    /**
     * 主键
     */
    private String id;

    /**
     *省份名字
     */
    private String provinceName;

    /**
     * 现有确诊
     */
    private Long nowConfirm;

    /**
     * 现有确诊数据较上日比较
     */
    private Long nowConfirmCompare;

    /**
     * 总共累计确诊
     */
    private Long allConfirm;



    /**
     * 每日新增
     */
    private Long confirmAdd;

    /**
     * 无症状
     */
    private Long noInfect;

    /**
     * 累计死亡
     */
    private Long dead;

    /**
     * 死亡数较上日
     */
    private Long deadCompare;

    /**
     * 累计治愈
     */
    private Long heal;

    /**
     * 治愈较上日
     */
    private Long healCompare;

    /**
     * 0增加天数
     */
    private int zeroDay;


}
