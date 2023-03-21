package net.thumbtack.buscompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SettingsDtoResponse {
    @Value("${max_name_length}")
    private String maxNameLength;
    @Value("${min_password_length}")
    private String minPasswordLength;
}
