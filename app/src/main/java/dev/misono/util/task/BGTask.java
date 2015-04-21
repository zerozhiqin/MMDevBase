package dev.misono.util.task;

public class BGTask {

    public static class Builder<R> {

        private Start start;
        private Work<R> doing;
        private Done done;

        public Builder when(Start start) {
            this.start = start;
            return this;
        }

        public Builder when(Done<R> done) {
            this.done = done;
            return this;
        }

        public void go() {

            COAsyncTask<Void, Void, R> task = new COAsyncTask<Void, Void, R>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    if (start != null) {
                        start.start();
                    }
                }

                @Override
                protected R doInBackground(Void... params) {
                    return doing.work();
                }

                @Override
                protected void onPostExecute(R result) {
                    super.onPostExecute(result);
                    if (done != null) {
                        done.done(result);
                    }
                }
            };
            task.execute();
        }
    }

    public static <R> Builder doing(Work<R> work) {
        Builder<R> builder = new Builder<>();
        builder.doing = work;
        return builder;
    }


    public interface Work<Result> {
        public Result work();
    }

    public interface Start {
        public void start();
    }

    public interface Done<Result> {
        public void done(Result result);

    }

}
