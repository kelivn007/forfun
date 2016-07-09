package hw.app.notelist.widget;

import com.baidu.xcloud.plugincloudnote.note.XCLNote;

public interface INoteView {
   public void setXCLNote(XCLNote note);
   public void addTextElement(String text);
   public void addImageElement(String path);
   public void addAudioElement(String path);
   public void addVideoElement(String path);
}
