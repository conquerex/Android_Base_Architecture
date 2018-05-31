package app.base.utils.job;

public class JobFlow {

    private long mTimeoutMilli;
    private long mMinTimeMilli;
    private OnJobFlowListener mOnJobFlowListener;


    public JobFlow() {

    }

    public void setListener(OnJobFlowListener listener) {
        mOnJobFlowListener = listener;
    }

    public interface OnJobFlowListener {
        void onJobFlowComplete();

        void onError(IJob job);
    }

    public class Builder {

        public void addJob(IJob job) {

        }

        public void addJobGroup(IJobGroup jobGroup) {

        }

        public void setMinTime(long milli) {

        }

        public void setTimeout(long milli) {

        }

        public JobFlow build() {
            JobFlow jobFlow = new JobFlow();
            return new JobFlow();
        }
    }
}
