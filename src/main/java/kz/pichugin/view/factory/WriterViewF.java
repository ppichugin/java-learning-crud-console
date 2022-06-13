package kz.pichugin.view.factory;

import kz.pichugin.view.View;
import kz.pichugin.view.WriterView;

public class WriterViewF implements ViewFactory {
    @Override
    public View getView() {
        return new WriterView();
    }
}
