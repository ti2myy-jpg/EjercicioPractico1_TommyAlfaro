package com.tommy.gimnasiofitness.config;

import com.tommy.gimnasiofitness.domain.AreaEntrenamiento;
import com.tommy.gimnasiofitness.domain.ClaseEntrenamiento;
import com.tommy.gimnasiofitness.repository.AreaEntrenamientoRepository;
import com.tommy.gimnasiofitness.repository.ClaseEntrenamientoRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {

    private final AreaEntrenamientoRepository areas;
    private final ClaseEntrenamientoRepository clases;

    public ProjectConfig(AreaEntrenamientoRepository areas, ClaseEntrenamientoRepository clases) {
        this.areas = areas;
        this.clases = clases;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new AreaDesdeCodigo(areas));
        registry.addConverter(new ClaseDesdeCodigo(clases));
    }

    private static class AreaDesdeCodigo implements Converter<String, AreaEntrenamiento> {

        private final AreaEntrenamientoRepository areas;

        AreaDesdeCodigo(AreaEntrenamientoRepository areas) {
            this.areas = areas;
        }

        @Override
        public AreaEntrenamiento convert(String source) {
            if (source == null || source.isBlank()) {
                return null;
            }
            return areas.findById(Integer.valueOf(source)).orElse(null);
        }
    }

    private static class ClaseDesdeCodigo implements Converter<String, ClaseEntrenamiento> {

        private final ClaseEntrenamientoRepository clases;

        ClaseDesdeCodigo(ClaseEntrenamientoRepository clases) {
            this.clases = clases;
        }

        @Override
        public ClaseEntrenamiento convert(String source) {
            if (source == null || source.isBlank()) {
                return null;
            }
            return clases.findById(Integer.valueOf(source)).orElse(null);
        }
    }
}
