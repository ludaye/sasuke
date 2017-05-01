package sasuke;

import com.intellij.ide.SaveAndSyncHandler;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sasuke.util.SasukeUtils;

import java.util.List;

public class SasukeTask extends Task.Backgroundable {
    private static NotificationGroup group = NotificationGroup.logOnlyGroup("sasuke");
    private List<WillDoTemplate> willDoTemplate;
    private List<Table> willDoTable;
    private ProjectModules projectModules;

    public SasukeTask(@Nullable Project project, @Nls(capitalization = Nls.Capitalization.Title) @NotNull String title,
                      boolean canBeCancelled, List<WillDoTemplate> willDoTemplate, List<Table> willDoTable,
                      ProjectModules projectModules) {
        super(project, title, canBeCancelled);
        this.willDoTemplate = willDoTemplate;
        this.willDoTable = willDoTable;
        this.projectModules = projectModules;
    }

    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {
        TaskResult taskResult = SasukeUtils.generateFile(willDoTemplate, willDoTable, projectModules);
        group.createNotification(SasukeUtils.getResultStr(taskResult), NotificationType.INFORMATION).notify(myProject);
        ApplicationManager.getApplication().invokeLater(this::synchronize);
    }

    private void synchronize() {
        FileDocumentManager.getInstance().saveAllDocuments();
        SaveAndSyncHandler.getInstance().refreshOpenFiles();
        VirtualFileManager.getInstance().refreshWithoutFileWatcher(true);
    }
}
