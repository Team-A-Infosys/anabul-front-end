package team.kucing.anabulshopcare;

import com.amoylabs.sfe4j.spring.boot.starter.Sfe4jProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import team.kucing.anabulshopcare.entity.image.Avatar;
import team.kucing.anabulshopcare.entity.image.ImageProduct;

@SpringBootApplication
@EnableConfigurationProperties({
        ImageProduct.class, Avatar.class, Sfe4jProperties.class
})
public class AnabulShopCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnabulShopCareApplication.class, args);
    }

}