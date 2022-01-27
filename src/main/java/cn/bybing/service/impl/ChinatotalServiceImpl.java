package cn.bybing.service.impl;

import cn.bybing.entity.Chinatotal;
import cn.bybing.mapper.ChinatotalMapper;
import cn.bybing.service.ChinatotalService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@Service
@Slf4j
public class ChinatotalServiceImpl extends ServiceImpl<ChinatotalMapper, Chinatotal> implements ChinatotalService {

    @Override
    public void saveChinatotal(Chinatotal chinatotal) {
        Chinatotal one = this.baseMapper.selectOne(new LambdaQueryWrapper<Chinatotal>().eq(Chinatotal::getUpdatetime, chinatotal.getUpdatetime()));
        if(one == null){
            int rs = this.baseMapper.insert(chinatotal);
            if(rs == 1){
                log.info("数据更新时间"+chinatotal.getUpdatetime() + "最新总览信息数据已存入数据库");
            }else{
                log.warn("总览信息数据存库失败！");
                return;
            }
        }else{
            log.info("当前已是最新数据，无需更新");
            return;
        }

    }
}
