package com.coder520.attend.service;

import com.coder520.attend.dao.AttendMapper;
import com.coder520.attend.entity.Attend;
import com.coder520.attend.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;
import com.coder520.common.utils.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by   song 醉美柳舞之众星捧月
 * Date & Time  2017/9/26 13:08
 */
@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService {
    /**
     * 中午12点判断上下午
     */
    private static final int NOON_HOUR = 12;
    private static final int NOON_MINUTE = 0;

    //早晚上班时间判定
    private static final int MORNING_HOUR = 9;
    private static final int MORNING_MINUTE = 30;
    private static final int EVENING_HOUR = 18;
    private static final int EVENING_MINUTE = 30;
    //缺勤一整天
    private static final int ABSENCE_DAY = 480;
    //考勤异常状态
    private static final Byte ATTEND_STATUS_ABNORMAL = 2;
    private static final Byte ATTEND_STATUS_NORMAL = 1;

    private Log log = LogFactory.getLog(AttendServiceImpl.class);
    private SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:mm");
    @Autowired
    private AttendMapper attendMapper;

    @Override
    public void signAttend(Attend attend) throws Exception {
        try {
            Date today = new Date();
            attend.setAttendDate(today);
            attend.setAttendWeek((byte) DateUtils.getTodayWeek());
            // 查询当天 此人有没有打卡记录
            Attend todayRecord = attendMapper.selectTodaySignRecord(attend.getUserId());
            Date noon = DateUtils.getDate(NOON_HOUR, NOON_MINUTE);
            Date morningAttend = DateUtils.getDate(MORNING_HOUR, MORNING_MINUTE);
            if (todayRecord == null) {
                //打卡记录还不存在
                if (today.compareTo(noon) <= 0) {
                    //打卡时间 早于12点 属于上午打卡
                    attend.setAttendMoring(today);
                    if (today.compareTo(morningAttend) > 0) {
                        //大于9点半迟到了
                        attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                        attend.setAbsence(DateUtils.getMinute(morningAttend, today));
                    }
                } else {
                    //否则就是下午打卡
                    attend.setAttendEvening(today);
                }
                attendMapper.insertSelective(attend);
            } else {
                if (today.compareTo(noon) <= 0) {
                    //打卡时间 早于12点 属于上午打卡
                    return;
                } else {
                    //否则就是下午打卡
                    todayRecord.setAttendEvening(today);
                    attendMapper.updateByPrimaryKeySelective(todayRecord);
                }
            }
            //中午12点前打卡 都算早上打卡 ;十二点以后都算是下午打卡      如果9：30以后 直接异常,算迟到 ； 18点之前算异常
            // 下午打卡 检查与上午打卡时间 不足8小时都算是打卡异常； 并且缺席多长时间都要存进去
        } catch (Exception e) {
            log.error("用户签到异常", e);
            throw e;
        }
    }

    @Override
    public PageQueryBean listAttend(QueryCondition condition) {
        //根据条件查询count记录数目
        int count = attendMapper.countByCondition(condition);
        PageQueryBean pageResult = new PageQueryBean();
        if (count > 0) {
            pageResult.setTotalRows(count);
            pageResult.setCurrentPage(condition.getCurrentPage());
            pageResult.setPageSize(condition.getPageSize());
            List<Attend> attendsList = attendMapper.selectAttendPage(condition);
            pageResult.setItems(attendsList);
        }
        //如果有记录 才去查询分页数据 没有相关记录数目 没必要去查分页数据
        return pageResult;
    }

    /**
     * Author song  醉美柳舞之众星捧月
     * Date & Time  2017/9/29 0:26
     * Description 检查考勤异常数据
     */
    @Override
    @Transactional
    public void checkAttend() {

        //查询缺勤用户ID 插入打卡记录  并且设置为异常 缺勤480分钟
        List<Long> userIdList = attendMapper.selectTodayAbsence();
        if (!CollectionUtils.isEmpty(userIdList)) {
            List<Attend> attendList = new ArrayList();
            for (Long userId : userIdList) {
                Attend attend = new Attend();
                attend.setUserId(userId);
                attend.setAttendDate(new Date());
                attend.setAttendWeek((byte) DateUtils.getTodayWeek());
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendList.add(attend);
            }
            attendMapper.batchInsert(attendList);
        }
//        检查晚上打卡 将下班未打卡记录设置为异常
        List<Attend> absenceList = attendMapper.selectTodayEveningAbsence();
        if (!CollectionUtils.isEmpty(absenceList)) {
            for (Attend attend : absenceList) {
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendMapper.updateByPrimaryKeySelective(attend);
            }
        }
    }
}
