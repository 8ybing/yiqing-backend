package cn.bybing.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 热搜，最新动态
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hot")
public class Hot implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dt;

    private String content;


}
