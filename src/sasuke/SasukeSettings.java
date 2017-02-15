package sasuke;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;

import org.jetbrains.annotations.Nullable;

import java.util.List;

@State(name = "sasukeSettings", storages = {@Storage(id = "sasuke", file = "$APP_CONFIG$/sasuke-settings.xml")})
public class SasukeSettings implements PersistentStateComponent<SasukeSettings> {

    private String jdbc;
    private List<Template> templates;

    @Nullable
    @Override
    public SasukeSettings getState() {
        return this;
    }

    @Override
    public void loadState(SasukeSettings sasukeSettings) {
        XmlSerializerUtil.copyBean(sasukeSettings, this);
    }

    public String getJdbc() {
        return jdbc;
    }

    public void setJdbc(String jdbc) {
        this.jdbc = jdbc;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }
}
