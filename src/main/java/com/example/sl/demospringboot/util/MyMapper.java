package com.example.sl.demospringboot.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

//自定义接口集成大牛给我们写好的数据层的接口类
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
