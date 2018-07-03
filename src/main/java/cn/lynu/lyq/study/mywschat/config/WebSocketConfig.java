package cn.lynu.lyq.study.mywschat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
	@Bean
    public CustomSpringConfigurator customSpringConfigurator() {
        return new CustomSpringConfigurator(); // This is just to get context
    }
	
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(5120000);
        container.setMaxBinaryMessageBufferSize(5120000);
        return container;
    }

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	}
}
