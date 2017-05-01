package sasuke;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;
import sasuke.common.Constants;

import java.util.List;

@State(name = "sasukeSettings", storages = {@Storage(id = "sasuke", file = "$APP_CONFIG$/sasuke-settings.xml")})
public class SasukeSettings implements PersistentStateComponent<SasukeSettings> {

    private String properties = Constants.DEFAULT_JDBC;
    private List<Template> templates = Constants.TEMPLATE_LIST;

    @Nullable
    @Override
    public SasukeSettings getState() {
        return this;
    }

    @Override
    public void loadState(SasukeSettings sasukeSettings) {
        XmlSerializerUtil.copyBean(sasukeSettings, this);
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
