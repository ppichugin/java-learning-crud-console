package kz.pichugin.view.factory;

import kz.pichugin.view.LabelView;
import kz.pichugin.view.View;

public class LabelViewF implements ViewFactory {

    @Override
    public View getView() {
        return new LabelView();
    }
}