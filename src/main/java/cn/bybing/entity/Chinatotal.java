package cn.bybing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 总览信息
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chinatotal")
public class Chinatotal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 更新日期
     */
    @TableField("updateTime")
    private String updatetime;

    /**
     * 累计确诊
     */
    private String confirm;

    /**
     * 累计治愈
     */
    private String heal;

    /**
     * 累计死亡
     */
    private String dead;

    /**
     * 现有确诊
     */
    @TableField("nowConfirm")
    private String nowconfirm;

    /**
     * 疑似
     */
    private String suspect;

    /**
     * 境外输入
     */
    @TableField("importedCase")
    private String importedcase;

    /**
     * 无症状感染者
     */
    @TableField("noInfect")
    private String noinfect;

    /**
     * 本土现有确诊
     */
    @TableField("localConfirm")
    private String localconfirm;

    /**
     * 较上日新增累计确诊
     */
    private String newConfirm;

    /**
     * 较上日新增无症状
     */
    @TableField("new_noInfect")
    private String newNoinfect;

    /**
     * 较上日新增本土确诊
     */
    @TableField("new_localConfirm")
    private String newLocalconfirm;

    /**
     * 较上日新增确诊
     */
    @TableField("new_nowConfirm")
    private String newNowconfirm;

    /**
     * 较上日新增境外输入
     */
    @TableField("new_importedCase")
    private String newImportedcase;

    /**
     * 较上日新增死亡
     */
    private String newDead;

    /**
     * 较上日新增治愈
     */
    private String newHeal;

}
