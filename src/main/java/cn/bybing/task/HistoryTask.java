package cn.bybing.task;


import cn.bybing.entity.History;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Jhonny
 * @Date: 2022/01/11/16:02
 * @Description:
 */
@Component
public class HistoryTask {

    public Map<String,History> getHistory() {
        HashMap<String, History> map = new HashMap<>();
        String url = HttpUtil.get("https://view.inews.qq.com/g2/getOnsInfo?name=disease_other");
        JSONObject jsonObject = JSON.parseObject(url);
        //获取key为data的json对象
        JSONObject data = jsonObject.getJSONObject("data");
        //获取key为chinaDayAddList的json对象
        JSONArray chinaDayAddList = data.getJSONArray("chinaDayAddList");
        //获取key为chinaDayList的json对象
        JSONArray chinaDayList = data.getJSONArray("chinaDayList");
        for (int dateNum = 0; dateNum < chinaDayAddList.size(); dateNum++) {
            History history = new History();
            JSONObject dayAddInfo = chinaDayAddList.getJSONObject(dateNum);
            //两个list中一个是从1月13号开始,另一个是从1月20号开始
            int dateNumforDay = dateNum + 7;
            //防止索引越界,五月一号腾讯服务器那边出了一次bug
            if (dateNumforDay >= chinaDayList.size()) {
                dateNumforDay = chinaDayList.size() - 1;
            }
            JSONObject dayInfo = chinaDayList.getJSONObject(dateNumforDay);

            Object dateTime_MM = dayAddInfo.get("date");
            Object dateTime_yyyy = dayAddInfo.get("y");
            Object confirm_add = dayAddInfo.get("confirm");//当日新增确诊
            Object heal_add = dayAddInfo.get("heal");//当日新增治愈
            Object dead_add = dayAddInfo.get("dead");//当日新增死亡
            Object suspect_add = dayAddInfo.get("suspect");//当日新增疑似

            Object confirm = dayInfo.get("confirm");//当日累计确诊
            Object heal = dayInfo.get("heal");//当日累计治愈
            Object dead = dayInfo.get("dead");//当日累计死亡
            Object suspect = dayInfo.get("suspect");//当日剩余疑似

            Long confirm_add1 = Long.valueOf(String.valueOf(confirm_add));
            Long heal_add1 = Long.valueOf(String.valueOf(heal_add));
            Long dead_add1 = Long.valueOf(String.valueOf(dead_add));
            Long suspect_add1 = Long.valueOf(String.valueOf(suspect_add));

            Long confirm1 = Long.valueOf(String.valueOf(confirm));
            Long heal1 = Long.valueOf(String.valueOf(heal));
            Long dead1 = Long.valueOf(String.valueOf(dead));
            Long suspect1 = Long.valueOf(String.valueOf(suspect));

            history.setConfirm(confirm1);
            history.setConfirmAdd(confirm_add1);
            history.setDead(dead1);
            history.setDeadAdd(dead_add1);
            history.setHeal(heal1);
            history.setHealAdd(heal_add1);
            history.setSuspect(suspect1);
            history.setSuspectAdd(suspect_add1);
            history.setDs(dateTime_yyyy.toString()+"-"+dateTime_MM.toString());

            UUID uuid = UUID.randomUUID();
            map.put("history-" + uuid.toString().replace("-", ""), history);
        }
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
