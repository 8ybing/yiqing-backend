package cn.bybing.task;

import cn.bybing.entity.Chinatotal;
import cn.bybing.entity.Details;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Jhonny
 * @Date: 2022/01/11/20:29
 * @Description:
 */
@Component
public class TotalTask {
    public Map<String, Chinatotal> getAll(){
        HashMap<String, Chinatotal> map = new HashMap<>();
        String info = HttpUtil.get("https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5");
        JSONObject jsonObject = JSON.parseObject(info);
        JSONObject data = jsonObject.getJSONObject("data");
        //获取key为 chinaTotal
        JSONObject Total = data.getJSONObject("chinaTotal");
        JSONObject chinaAdd = data.getJSONObject("chinaAdd");
        Chinatotal chinatotal = new Chinatotal();
        //更新日期
        Object lastUpdateTime = data.get("lastUpdateTime");
        //累计确诊
        Object confirm = Total.get("confirm");
        //较上日累计确诊
        Object confirmAdd = chinaAdd.get("confirm");
        //疑似
        Object suspect = Total.get("suspect");
        //累计治愈
        Object heal = Total.get("heal");
        //较上日累计治愈
        Object healAdd = chinaAdd.get("heal");
        //累计死亡
        Object dead = Total.get("dead");
        //较上日累计死亡
        Object deadAdd = chinaAdd.get("dead");
        //现有确诊
        Object nowConfirm = Total.get("nowConfirm");
        //较上日现有确诊
        Object nowConfirmAdd = chinaAdd.get("nowConfirm");
        //境外输入
        Object importedCase = Total.get("importedCase");
        //较上日境外输入
        Object importedCaseAdd = chinaAdd.get("importedCase");
        //现有无症状感染者
        Object noInfect = Total.get("noInfect");
        //较上日新增无症状感染者
        Object noInfectAdd = chinaAdd.get("noInfect");
        //本土现有确诊
        Object localConfirm = Total.get("localConfirm");
        //较上日新增本土现有确诊
        Object localConfirmH5Add = chinaAdd.get("localConfirmH5");

        //将数据更新时间转为字符串作为id
        Date parse = null;
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastUpdateTime.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String updateId = new SimpleDateFormat("yyyyMMddHHmmss").format(parse);
        chinatotal.setId(updateId);
        chinatotal.setUpdatetime(String.valueOf(lastUpdateTime));
        chinatotal.setConfirm(String.valueOf(confirm));
        chinatotal.setDead(String.valueOf(dead));
        chinatotal.setHeal(String.valueOf(heal));
        chinatotal.setSuspect(String.valueOf(suspect));
        chinatotal.setNowconfirm(String.valueOf(nowConfirm));
        chinatotal.setImportedcase(String.valueOf(importedCase));
        chinatotal.setNoinfect(String.valueOf(noInfect));
        chinatotal.setLocalconfirm(String.valueOf(localConfirm));
        chinatotal.setNewConfirm(String.valueOf(confirmAdd));
        chinatotal.setNewDead(String.valueOf(deadAdd));
        chinatotal.setNewNowconfirm(String.valueOf(nowConfirmAdd));
        chinatotal.setNewNoinfect(String.valueOf(noInfectAdd));
        chinatotal.setNewImportedcase(String.valueOf(importedCaseAdd));
        chinatotal.setNewLocalconfirm(String.valueOf(localConfirmH5Add));
        chinatotal.setNewHeal(String.valueOf(healAdd));

        map.put("chinaTotal",chinatotal);
        return map;
    }

    /**
     * 获取更新时间
     * @return
     */
    public Object getUpdateTime(){
        String info = HttpUtil.get("https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5");
        JSONObject jsonObject = JSON.parseObject(info);
        //获取key为data的json对象
        JSONObject data = jsonObject.getJSONObject("data");
        //获取最后更新时间
        Object lastUpdateTimeObj = data.get("lastUpdateTime");
        return lastUpdateTimeObj;
    }
}
