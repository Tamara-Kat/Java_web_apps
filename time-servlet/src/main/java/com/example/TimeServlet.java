package com.example;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @Override
    public void init() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String timezoneFromRequest = request.getParameter("timezone");
        String timezone = timezoneFromRequest;

        if (timezone == null || timezone.isBlank()) {
            timezone = getCookieValue(request, "lastTimezone");
        }

        String normalizedTimezone = TimezoneUtil.normalize(timezone);

        if (timezoneFromRequest != null && !timezoneFromRequest.isBlank()) {
            Cookie cookie = new Cookie("lastTimezone", timezoneFromRequest.replace(" ", "+"));
            cookie.setMaxAge(60 * 60 * 24 * 30);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of(normalizedTimezone));
        String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Context context = new Context();
        context.setVariable("time", formattedTime);
        context.setVariable("timezone", timezoneFromRequest != null
                ? timezoneFromRequest.replace(" ", "+")
                : normalizedTimezone);

        response.setContentType("text/html; charset=UTF-8");
        templateEngine.process("time", context, response.getWriter());
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}