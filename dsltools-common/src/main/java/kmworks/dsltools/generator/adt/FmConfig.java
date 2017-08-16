package kmworks.dsltools.generator.adt;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * Created by cpl on 21.03.2017.
 */
public class FmConfig {

    private static FmConfig INSTANCE = new FmConfig();

    private final Configuration cfg;

    private FmConfig() {
        cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setClassForTemplateLoading(FmConfig.class, "");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
    }

    public static Configuration configuration() {
        return INSTANCE.cfg;
    }
}
