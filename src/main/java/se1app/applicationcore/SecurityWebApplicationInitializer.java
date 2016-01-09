package se1app.applicationcore;

/**
 * Created by nilo on 07.12.15.
 */

import org.springframework.security.web.context.*;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

        public SecurityWebApplicationInitializer() {
            super(WebSecurityConfig.class);
        }
}
