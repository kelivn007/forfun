package hw.app.notelist.action.impl;

import hw.app.notelist.action.BaseAction;


public class DisplayNoteAction extends BaseAction {

    public static enum DisplayType {
        ALL, IMPORTANT, DELETE
    }

    private DisplayType mType;

    public DisplayNoteAction(DisplayType type) {
        mType = type;
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "DisplayNoteAction";
    }

}
