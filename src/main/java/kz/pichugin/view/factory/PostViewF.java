package kz.pichugin.view.factory;

import kz.pichugin.view.PostView;
import kz.pichugin.view.View;

public class PostViewF implements ViewFactory {

    @Override
    public View getView() {
        return new PostView();
    }
}
