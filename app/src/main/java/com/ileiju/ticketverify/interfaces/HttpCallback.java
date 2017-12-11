package com.ileiju.ticketverify.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Created by hunter on 17/12/9.
 */

public interface HttpCallback {

    void onSucceed(Map<String, List<String>> headers, String result);

    void onFailure(Exception exception);
}
