package com.uin.gateway.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayFlowRilesController {

  /**
   * 获取当前系统的限流的策略
   */
  @GetMapping("/flowRules")
  public Set<GatewayFlowRule> getFlowRule() {
    return GatewayRuleManager.getRules();
  }

  /**
   * 获取当前的api分组
   */
  @GetMapping("/apiGroups")
  public Set<ApiDefinition> getApiGroup() {
    return GatewayApiDefinitionManager.getApiDefinitions();
  }
}
