package cn.bybing.service;

import cn.bybing.entity.Chinatotal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
public interface ChinatotalService extends IService<Chinatotal> {

    void saveChinatotal(Chinatotal chinatotal);

}
