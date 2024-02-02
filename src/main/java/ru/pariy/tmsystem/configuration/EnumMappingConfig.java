package ru.pariy.tmsystem.configuration;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.pariy.tmsystem.model.TaskPriority;
import ru.pariy.tmsystem.model.TaskStatus;
import ru.pariy.tmsystem.util.CaseInsensitiveEnumConverter;

import java.util.List;


@Configuration
public class EnumMappingConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        List<Class<? extends Enum>> enums = List.of(TaskStatus.class, TaskPriority.class);
        enums.forEach(enumClass -> registry.addConverter(String.class, enumClass,
                new CaseInsensitiveEnumConverter<>(enumClass)));
        ApplicationConversionService.configure(registry);
    }
}
