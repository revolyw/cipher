package security;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import util.LoggerUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Willow on 12/25/16.
 * 改写 org.springframework.security.web.csrf.CsrfFilter
 */
public class CsrfFilter extends OncePerRequestFilter {
    private final CsrfTokenRepository tokenRepository;
    private RequestMatcher requireCsrfProtectionMatcher = new CsrfFilter.DefaultRequiresCsrfMatcher();
    private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

    public CsrfFilter(CsrfTokenRepository csrfTokenRepository) {
        Assert.notNull(csrfTokenRepository, "csrfTokenRepository cannot be null");
        this.tokenRepository = csrfTokenRepository;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CsrfToken csrfToken = tokenRepository.loadToken(request);
        final boolean missingToken = csrfToken == null;
        if (missingToken) {
            CsrfToken generatedToken = tokenRepository.generateToken(request);
            csrfToken = new CsrfFilter.SaveOnAccessCsrfToken(tokenRepository, request, response, generatedToken);
        }
        request.setAttribute(CsrfToken.class.getName(), csrfToken);
        request.setAttribute(csrfToken.getParameterName(), csrfToken);

        //是否放行
        if (!requireCsrfProtectionMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String actualToken = request.getHeader(csrfToken.getHeaderName());
        if (actualToken == null) {
            actualToken = request.getParameter(csrfToken.getParameterName());
        }
        if (!csrfToken.getToken().equals(actualToken)) {
            LoggerUtil.error("Invalid CSRF token found for " + UrlUtils.buildFullRequestUrl(request)+"."+request.getParameter("action"));
            response.sendError(403);
//            if (missingToken) {
//                accessDeniedHandler.handle(request, response, new MissingCsrfTokenException(actualToken));
//            } else {
//                accessDeniedHandler.handle(request, response, new InvalidCsrfTokenException(csrfToken, actualToken));
//            }
            return;
        }

        filterChain.doFilter(request, response);
    }

    //csrf放行控制类
    private static final class DefaultRequiresCsrfMatcher implements RequestMatcher {
        //白名单
        private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
        //URI黑名单
        private List<String> deniedURIs = new ArrayList<String>() {
            {
                add("/testCSRF");
            }
        };
        private List<String> deniedActions = new ArrayList<String>() {
            {
                add("");
            }
        };
        /* (non-Javadoc)
         * @see org.springframework.security.web.util.matcher.RequestMatcher#matches(javax.servlet.http.HttpServletRequest)
         */

        //根据外层调用的
        public boolean matches(HttpServletRequest request) {
//            return !allowedMethods.matcher(request.getMethod()).matches();
            //默认不需要检测
            boolean isNeedCsrfProtection = false;

            //csrf白名单检测：在白名单中则不需要csrf检测
            if (allowedMethods.matcher(request.getMethod()).matches())
                isNeedCsrfProtection = false;
            //csrf黑名单检测：在黑名单中则须通过csrf检测
            if (deniedURIs.contains(request.getRequestURI()))
                isNeedCsrfProtection = true;

            return isNeedCsrfProtection;
        }
    }

    /**
     * Specifies a {@link RequestMatcher} that is used to determine if CSRF
     * protection should be applied. If the {@link RequestMatcher} returns true
     * for a given request, then CSRF protection is applied.
     * <p>
     * <p>
     * The default is to apply CSRF protection for any HTTP method other than
     * GET, HEAD, TRACE, OPTIONS.
     * </p>
     *
     * @param requireCsrfProtectionMatcher the {@link RequestMatcher} used to determine if CSRF
     *                                     protection should be applied.
     */
    public void setRequireCsrfProtectionMatcher(RequestMatcher requireCsrfProtectionMatcher) {
        Assert.notNull(requireCsrfProtectionMatcher, "requireCsrfProtectionMatcher cannot be null");
        this.requireCsrfProtectionMatcher = requireCsrfProtectionMatcher;
    }


    /**
     * Specifies a {@link AccessDeniedHandler} that should be used when CSRF protection fails.
     * <p>
     * <p>
     * The default is to use AccessDeniedHandlerImpl with no arguments.
     * </p>
     *
     * @param accessDeniedHandler the {@link AccessDeniedHandler} to use
     */
    public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        Assert.notNull(accessDeniedHandler, "accessDeniedHandler cannot be null");
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @SuppressWarnings("serial")
    private static final class SaveOnAccessCsrfToken implements CsrfToken {
        private transient CsrfTokenRepository tokenRepository;
        private transient HttpServletRequest request;
        private transient HttpServletResponse response;

        private final CsrfToken delegate;

        public SaveOnAccessCsrfToken(CsrfTokenRepository tokenRepository,
                                     HttpServletRequest request, HttpServletResponse response,
                                     CsrfToken delegate) {
            super();
            this.tokenRepository = tokenRepository;
            this.request = request;
            this.response = response;
            this.delegate = delegate;
        }

        public String getHeaderName() {
            return delegate.getHeaderName();
        }

        public String getParameterName() {
            return delegate.getParameterName();
        }

        public String getToken() {
            saveTokenIfNecessary();
            return delegate.getToken();
        }

        @Override
        public String toString() {
            return "SaveOnAccessCsrfToken [delegate=" + delegate + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((delegate == null) ? 0 : delegate.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CsrfFilter.SaveOnAccessCsrfToken other = (CsrfFilter.SaveOnAccessCsrfToken) obj;
            if (delegate == null) {
                if (other.delegate != null)
                    return false;
            } else if (!delegate.equals(other.delegate))
                return false;
            return true;
        }

        private void saveTokenIfNecessary() {
            if (this.tokenRepository == null) {
                return;
            }

            synchronized (this) {
                if (tokenRepository != null) {
                    this.tokenRepository.saveToken(delegate, request, response);
                    this.tokenRepository = null;
                    this.request = null;
                    this.response = null;
                }
            }
        }

    }
}
