package com.csx.common.service;

import com.csx.common.base.service.BaseService;
import com.csx.common.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService extends BaseService {

    @Autowired
    private LoginMapper loginMapper;
}
