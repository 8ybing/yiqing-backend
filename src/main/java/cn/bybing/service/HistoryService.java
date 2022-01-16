package cn.bybing.service;

import cn.bybing.entity.Details;
import cn.bybing.entity.History;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
public interface HistoryService extends IService<History> {

    //存储
    void saveHistory(History history);

    //更新
    void updateHistory(History history);

}
