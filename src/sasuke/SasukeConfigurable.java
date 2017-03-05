package sasuke;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import sasuke.ui.SasukeConfiguration;

import javax.swing.*;

/**
 * Created by Administrator on 2017/2/11.
 */
public class SasukeConfigurable implements Configurable {
    private SasukeConfiguration sasukeConfiguration;
    private SasukeSettings sasukeSettings;

    public SasukeConfigurable() {
        sasukeSettings = ServiceManager.getService(SasukeSettings.class);
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "sasuke";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "help.sasuke.configuration";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (sasukeConfiguration == null) {
            sasukeConfiguration = new SasukeConfiguration(sasukeSettings);
        }
        return sasukeConfiguration.getMainPanel();
    }

    @Override
    public boolean isModified() {
        if (sasukeSettings.getJdbc() == null || sasukeSettings.getTemplates() == null) {
            return true;
        }
        if (!sasukeSettings.getJdbc().equals(sasukeConfiguration.getJdbc())) {
            return true;
        }
        if (sasukeSettings.getTemplates().size() != sasukeConfiguration.getTemplates().size()) {
            return true;
        }
        for (int i = 0, len = sasukeSettings.getTemplates().size(); i < len; i++) {
            if (!sasukeSettings.getTemplates().get(i).equals(sasukeConfiguration.getTemplates().get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        sasukeSettings.setJdbc(sasukeConfiguration.getJdbc());
        sasukeSettings.setTemplates(sasukeConfiguration.getTemplates());
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }
}
