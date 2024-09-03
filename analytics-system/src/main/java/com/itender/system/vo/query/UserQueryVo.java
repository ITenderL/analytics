package com.itender.system.vo.query;


import com.itender.system.entity.User;
import lombok.Data;

@Data
public class UserQueryVo extends User {
    private Long pageNo=1L;//当前页码
    private Long pageSize=10L;//每页显示数量
}
