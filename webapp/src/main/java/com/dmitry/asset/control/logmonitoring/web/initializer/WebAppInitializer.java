package com.dmitry.asset.control.logmonitoring.web.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;

import com.dmitry.asset.control.logmonitoring.web.configure.AppWebConfig;
import org.apache.jasper.servlet.JspServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

public class WebAppInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppWebConfig.class);
        ctx.setServletContext(servletContext);

        addDispatcherServlet(servletContext, ctx);
    }

    private void addDispatcherServlet(ServletContext servletContext, AnnotationConfigWebApplicationContext ctx) {
        Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(2);
        dynamic.setAsyncSupported(true);
    }

} 