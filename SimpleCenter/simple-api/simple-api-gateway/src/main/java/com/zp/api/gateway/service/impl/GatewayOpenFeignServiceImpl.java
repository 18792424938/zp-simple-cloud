package com.zp.api.gateway.service.impl;


import com.zp.api.gateway.service.GatewayOpenFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayOpenFeignServiceImpl implements GatewayOpenFeignService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private Throwable cause;



    public GatewayOpenFeignServiceImpl(Throwable cause) {
        super();
        this.cause = cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
    }


    @Override
    public void init() {

    }
}
