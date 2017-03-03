package sasuke;

import com.intellij.openapi.editor.Editor;

/**
 * Created by Administrator on 2017/2/25.
 */
public class WriteEditorAction implements Runnable {
    private Editor templateEditor;
    private String content;

    public WriteEditorAction(Editor templateEditor, String content) {
        this.templateEditor = templateEditor;
        this.content = content;
    }

    @Override
    public void run() {
        templateEditor.getDocument().setText(content == null ? "" : content);
    }
}
