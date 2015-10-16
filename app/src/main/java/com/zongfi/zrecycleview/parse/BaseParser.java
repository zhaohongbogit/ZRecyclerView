package com.zongfi.zrecycleview.parse;

import java.util.List;

/**
 * Created by ZHZEPHI on 2015/10/10.
 */
public abstract class BaseParser implements IParser{

    public boolean isRunning = false;

    public abstract List parse(Integer page);
}
