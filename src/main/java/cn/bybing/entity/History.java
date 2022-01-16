package cn.bybing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 每日数据
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("history")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 日期
     */
    private String ds;

    /**
     * 累计确诊
     */
    private Long confirm;

    /**
     * 当日新增确诊
     */
    private Long confirmAdd;

    /**
     * 剩余疑似
     */
    private Long suspect;

    /**
     * 当日新增疑似
     */
    private Long suspectAdd;

    /**
     * 累计治愈
     */
    private Long heal;

    /**
     * 今日新增治愈
     */
    private Long healAdd;

    /**
     * 累计死亡
     */
    private Long dead;

    /**
     * 当日新增死亡
     */
    private Long deadAdd;


}
