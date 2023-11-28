package com.twojin.wooritheseday.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Slf4j
@Configuration
public class CustomAuthSuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {



}
