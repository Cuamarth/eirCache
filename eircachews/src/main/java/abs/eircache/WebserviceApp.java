
package abs.eircache;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.client.RestTemplate;

/**
 * @author beniteza
 * SpringBoot starter class
 */
@SpringBootApplication
@EnableCaching
@EnableJSONDoc
public class WebserviceApp {


    @Value("${ehcache.configuration.location}")
    private String cacheLocation;

    /**
     * SpringBoot starter method
     * @param args
     */
    public static void main(String[] args) {

        SpringApplication.run(WebserviceApp.class, args);
    }



    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();

        factory.setConfigLocation(new FileSystemResource(cacheLocation));
        factory.setShared(true);
        return factory;
    }

    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }
}
