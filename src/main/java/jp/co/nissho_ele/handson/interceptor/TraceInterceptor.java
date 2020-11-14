package jp.co.nissho_ele.handson.interceptor;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import jp.co.nissho_ele.jfr.DatabaseRWInvocationEvent;

@Interceptor
@DatabaseRWInvokeInterceptor
@Priority(Interceptor.Priority.APPLICATION + 1)
public class TraceInterceptor {

    @AroundInvoke
    public Object obj(InvocationContext ic) throws Exception {

        DatabaseRWInvocationEvent event = new DatabaseRWInvocationEvent();

        // メソッド実行前
        preProcess(ic, event);

        try {
            // 次のインターセプターチェーンの実行
            Object result = ic.proceed();

            // メソッドの実行後
            postProcess(ic, event);

            return result;
        } catch (Exception ex) {
            // 例外処理
            System.out.println("exceptions happened");
            postProcess(ic, event);
            throw ex;
        }
    }

    private void preProcess(InvocationContext ic, DatabaseRWInvocationEvent event) {
        System.out.println("start in Interceptor");

        if (event.isEnabled()) {
            event.begin();
        }

    }

    private void postProcess(InvocationContext ic, DatabaseRWInvocationEvent event) {
        System.out.println("end in Interceptor");
        String method = ic.getMethod().getName();
        if (event == null || !event.isEnabled()) {
            return;
        }

        event.end();

        if (event.shouldCommit()) {
            event.javaMethod = method;
            event.commit();
        }
    }
}
