package kr.co.bikego;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertyConfig {

    public PropertyConfig() {
        this.osName = System.getProperty("os.name").toLowerCase();
        setFilePathSeperator();
    }

    @Value("${upload.path}")
    @Getter
    String uploadPath;

    @Getter
    String osName;

    @Getter
    String filePathSeperator;

    private void setFilePathSeperator() {
        if (this.osName.contains("win")) {
            this.filePathSeperator = "\\";
        } else if (this.osName.contains("mac")) {
            this.filePathSeperator = "/";
        } else if (this.osName.contains("nix") || this.osName.contains("nux") || this.osName.contains("aix")) {
            this.filePathSeperator = "/";
        } else if (this.osName.contains("linux")) {
            this.filePathSeperator = "/";
        } else if (this.osName.contains("sunosName")) {
            this.filePathSeperator = "/";
        }
    }

}
