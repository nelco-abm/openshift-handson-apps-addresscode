package jp.co.nissho_ele.jfr;

import java.io.Closeable;
import java.io.IOException;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;

@Name(DatabaseRWInvocationEvent.NAME)
@Label("Database Invocation")
@Category("Database")
@Description("Invocation of a Database Dao resource method")
@StackTrace(true)
public class DatabaseRWInvocationEvent extends Event {

    static final String NAME = "dev.jfr.DatabaseRWInvocation";

    @Label("Java Method")
    public String javaMethod;

}