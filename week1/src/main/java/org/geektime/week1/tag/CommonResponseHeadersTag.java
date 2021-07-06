package org.geektime.week1.tag;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * @author FanJiang
 * @since todo (2021/7/6)
 */
public class CommonResponseHeadersTag extends SimpleTagSupport {

    private String cacheControl;
    private String pragma;
    private String expires;

    @Override
    public void doTag() throws JspException, IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setHeader("Cache-Control", getCacheControl());
        response.setHeader("Pragma", getPragma());

        long numExpires = 0;
        try {
            numExpires = Long.parseLong(getExpires());
        } catch (Throwable e) {
            // do nothing
        }
        response.setDateHeader("Expires", numExpires);
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public String getPragma() {
        return pragma;
    }

    public void setPragma(String pragma) {
        this.pragma = pragma;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
