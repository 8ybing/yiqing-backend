package cn.bybing.service.impl;

import cn.bybing.entity.Details;
import cn.bybing.entity.History;
import cn.bybing.mapper.HistoryMapper;
import cn.bybing.service.HistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    @Override
    public void saveHistory(History history) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ds",history.getDs());
        List<History> list = this.baseMapper.selectByMap(map);

        if(list.size() == 0){
            //数据库中没有数据
            //新增
            this.baseMapper.insert(history);
        }else{
            //已有数据，更新数据
            this.baseMapper.updateHistory(history);
        }
    }

    @Override
    public void updateHistory(History history) {
        this.baseMapper.updateHistory(history);
    }
}
