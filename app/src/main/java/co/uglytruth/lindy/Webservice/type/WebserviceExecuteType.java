package co.uglytruth.lindy.Webservice.type;

/**
 * Created by tjw127 on 6/26/17.
 */

public enum  WebserviceExecuteType {

    SERIAL_EXECUTOR(1), THREAD_POOL_EXECUTOR(2), GET_RESULT(3), EXECUTE_PARAMS(4), EXECUTE_RUNNABLE(5);

    private int type;

    private WebserviceExecuteType(final int type)
    {
        this.type = type;
    }

    public int getValue()
    {
        return this.type;
    }

    @Override
    public String toString()
    {
        return new Integer(this.type).toString();
    }
}
