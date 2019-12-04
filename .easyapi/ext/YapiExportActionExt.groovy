import com.google.inject.Inject
import com.intellij.psi.*
import com.itangcent.idea.plugin.api.export.yapi.YapiApiHelper
import com.itangcent.idea.plugin.script.ActionExt
import com.itangcent.idea.plugin.utils.KtHelper
import com.itangcent.intellij.config.ConfigReader
import com.itangcent.intellij.context.ActionContext

class YapiExportActionExt implements ActionExt {

    void init(ActionContext.ActionContextBuilder builder) {

        builder.bind(YapiApiHelper.class, KtHelper.INSTANCE.ktFunction({
            it.to(OldYapiApiHelper.class).in(com.google.inject.Singleton.class)
            return null
        }))
    }

    static class OldYapiApiHelper extends YapiApiHelper {

        @Inject
        ConfigReader configReader;

        String findCat(String token, String name) {
            return findConfig("catId[" + token + "][" + name + "]")
        }

        String getProjectIdByToken(String token) {
            return findConfig("projectId[" + token + "]")
        }

        String findConfig(String name) {
            String value = configReader.first(name)
            if (value == null || value.isEmpty()) {
                logger.info("please set: " + name)
            }
            return value
        }
    }
}


