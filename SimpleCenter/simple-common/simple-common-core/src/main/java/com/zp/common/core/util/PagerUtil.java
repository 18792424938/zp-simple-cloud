package com.zp.common.core.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagerUtil implements Serializable {
    private long currentPage;
    private long currentSize;
    public <T> Page<T> getPage(Class<T> c){
        return new Page<>(currentPage, currentSize);
    }
}
