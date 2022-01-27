package cn.bybing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据详情
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("details")
public class Details implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 数据最后更新时间
     */
    private String updateTime;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 累计确诊
     */
    private Long confirm;

    /**
     * 新增确诊
     */
    private Long confirmAdd;

    /**
     * 现有确诊
     */
    private Long nowConfirm;

    /**
     * 累计治愈
     */
    private Long heal;

    /**
     * 累计死亡
     */
    private Long dead;

    /**
     * 治愈率
     */
    private String healRate;

    /**
     * 风险级别
     */
    private String riskGrade;


}
