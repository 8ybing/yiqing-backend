package cn.bybing.mapper;

import cn.bybing.entity.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@Mapper
public interface HistoryMapper extends BaseMapper<History> {

    //更新
    void updateHistory(History history);
}
