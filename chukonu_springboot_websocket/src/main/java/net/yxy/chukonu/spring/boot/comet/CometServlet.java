package net.yxy.chukonu.spring.boot.comet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.IOUtils;

@WebServlet(urlPatterns = "/api/v1/comet.do", asyncSupported = true)
public class CometServlet extends HttpServlet {
    private static final long serialVersionUID = -260157400324419618L;
    private final Logger logger = LoggerFactory.getLogger(CometServlet.class);

    /**
     * 将客户端注册到监听 Logger 的消息队列中
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "private");
        response.setHeader("Pragma", "no-cache");
        request.setCharacterEncoding("UTF-8");

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String body = IOUtils.read(reader);

        PrintWriter writer = response.getWriter();
        while (true) {
            writer.println(
                    "<script>window.parent.connection.callback('hello-" + System.currentTimeMillis() + "')</script>");
            writer.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
