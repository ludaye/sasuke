package sasuke.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import sasuke.Icons;

public class ShowDialogAction extends AnAction {

    public ShowDialogAction() {
        super(Icons.SUSAKE);
        setEnabledInModalContext(true);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
//        SasukeSettings sasukeSettings = ServiceManager.getService(SasukeSettings.class);
//        DataContext dataContext = e.getDataContext();
//        Module currentModule = DataKeys.MODULE.getData(dataContext);
//        Properties properties = SasukeUtils.stringToProperties(sasukeSettings.getProperties());
//        try (MysqlLink mysqlLink = new MysqlLink(properties.getProperty("url"), properties.getProperty("user"),
//                properties.getProperty("password"))) {
//            Project project = e.getProject();
//            ProjectModules projectModules = SasukeUtils.findProjectModulePaths(project, currentModule);
//
//            GenerateDialog generateDialog = new GenerateDialog(project, mysqlLink, projectModules, sasukeSettings, properties);
//            generateDialog.show();
//        } catch (SQLException | ClassNotFoundException e1) {
//            throw new RuntimeException(e1.getMessage(), e1);
//        } catch (Exception e1) {
//            throw new RuntimeException(e1.getMessage(), e1);
//        }
//        BackgroundTaskQueue myQueue = new BackgroundTaskQueue(e.getProject(), "test queue111");
//        TestTask task = new TestTask(e.getProject(), "wtf");
//        myQueue.run(task);
//        myQueue.setForceAsyncInTests(true, null);


//        BackgroundTaskUtil.computeInBackgroundAndTryWait(() -> {
//                    List<Integer> list = new ArrayList<>(10000);
//                    for (int i = 0; i < 10000; i++) {
//                        list.add(i);
//                    }
//                    return list;
//                }, i -> {
//                    ApplicationManager.getApplication().invokeLater(() -> {
//                        System.out.println(i.size());
//                    }, e.getProject().getDisposed());
//                },
//                ProgressWindow.DEFAULT_PROGRESS_DIALOG_POSTPONE_TIME_MILLIS);


    }

//    private enum TaskState {
//        CREATED, RUNNING, SUCCEEDED, EXCEPTION, CANCELLED;
//
//        boolean isComplete() {
//            return this == SUCCEEDED || this == EXCEPTION || this == CANCELLED;
//        }
//    }

//    private class TestTask extends Task.Backgroundable {
//
//        public TestTask(@Nullable Project project, @Nls @NotNull String title) {
//            super(project, title);
//        }
//
//        @Override
//        public void run(@NotNull ProgressIndicator progressIndicator) {
//            progressIndicator.setFraction(0.5);
//            try {
//                Thread.sleep(1000000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}


//    private final AtomicReference<TaskState> myState = new AtomicReference<>(TaskState.CREATED);
//    private final Semaphore mySemaphore = new Semaphore(0);
//
//    public TestTask(Project project) {
//        super(project, "Test Task", true);
//    }
//
//    protected void execute(ProgressIndicator indicator) {
//        indicator.start();
//        for (int i = 0; i < 10000; i++) {
//            indicator.setFraction(i / 10000);
//        }
//    }
//
//    @NotNull
//    public TaskState getState() {
//        return myState.get();
//    }
//
//    public boolean isComplete() {
//        return myState.get().isComplete();
//    }
//
//
//    @Override
//    public final void run(@NotNull ProgressIndicator indicator) {
//        myState.compareAndSet(TaskState.CREATED, TaskState.RUNNING);
//        execute(indicator);
//    }
//
//    @Override
//    public final void onCancel() {
//        myState.compareAndSet(TaskState.RUNNING, TaskState.CANCELLED);
//    }
//
//    @Override
//    public final void onSuccess() {
//        myState.compareAndSet(TaskState.RUNNING, TaskState.SUCCEEDED);
//    }
//
//    @Override
//    public final void onThrowable(@NotNull Throwable error) {
//        myState.compareAndSet(TaskState.RUNNING, TaskState.EXCEPTION);
//    }
//
//    @Override
//    public final void onFinished() {
//        mySemaphore.release();
//        assertTrue(myState.get() != TaskState.RUNNING);
//        assertTrue(myState.get() != TaskState.CREATED);
//    }
//
//    public void waitFor(int timeout) throws InterruptedException {
//        assertTrue(mySemaphore.tryAcquire(1, timeout, TimeUnit.MILLISECONDS));
//        mySemaphore.release();
//    }
//
//    @Override
//    public boolean isHeadless() {
//        return false;
//    }