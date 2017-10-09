package mx.escom.tt.diabetes.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class SimpleCORSFilter implements Filter {

	public static String VALID_METHODS = "DELETE, HEAD, GET, OPTIONS, POST, PUT";
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		log.debug("Inicio");
		HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) res;

        String origin = httpReq.getHeader("Origin");
      
         if (origin == null) {
        	 
           if ("OPTIONS".equalsIgnoreCase(httpReq.getMethod())) {
                httpResp.setHeader("Allow", VALID_METHODS);
                httpResp.setStatus(200);
                return;
            }
           
        } else {

            httpResp.setHeader("Access-Control-Allow-Origin", origin);
            httpResp.setHeader("Access-Control-Allow-Methods", VALID_METHODS);

            String headers = httpReq.getHeader("Access-Control-Request-Headers");
           
            if (headers != null){
            	httpResp.setHeader("Access-Control-Allow-Headers", headers);
            	//httpResp.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, If-Modified-Since");
            }
                
            httpResp.setHeader("Access-Control-Max-Age", "3600");
        }
         
        if (!"OPTIONS".equalsIgnoreCase(httpReq.getMethod())) {
            chain.doFilter(req, res);
        }
        
        log.debug("Fin");
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}

