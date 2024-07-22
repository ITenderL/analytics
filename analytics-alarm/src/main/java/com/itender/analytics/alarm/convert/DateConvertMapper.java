package com.itender.analytics.alarm.convert;

import com.itender.analytics.alarm.exception.BizException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.itender.analytics.alarm.enums.BizExceptionEnum.MAPSTRUCT_CONVERT_ERROR;

/**
 * @author itender
 * @date 2022/9/22 17:47
 * @desc
 */
@Component
public class DateConvertMapper {

    public String asString(Date date) {
        return date != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(date) : null;
    }

    public Date asDate(String date) {
        try {
            return date != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(date) : null;
        } catch (ParseException e) {
            throw new BizException(MAPSTRUCT_CONVERT_ERROR);
        }
    }
}