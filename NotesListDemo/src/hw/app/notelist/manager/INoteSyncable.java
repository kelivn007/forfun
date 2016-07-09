package hw.app.notelist.manager;

import com.baidu.xcloud.plugincloudnote.note.XCLNote;

public interface INoteSyncable {
    public XCLNote genXCLNote();
    public long getNoteId();
}
