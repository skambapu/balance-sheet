package com.bian.statement.config;

import com.bian.statement.controller.BalanceStatementController;
import com.bian.statement.controller.TransactionStatementController;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		property(ServletProperties.FILTER_FORWARD_ON_404, true);
		register(BalanceStatementController.class);
		register(TransactionStatementController.class);
	}
}
