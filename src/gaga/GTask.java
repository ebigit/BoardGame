package gaga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/********************************************************************
 * GTask
 ********************************************************************/
public abstract class GTask {

	private boolean isTimeout;

	public final boolean isTimeout() {
		return isTimeout;
	}

	public final void setTimeout() {
		isTimeout = true;
	}

	public abstract void countup();

	/********************************************************************
	 * SingleTask
	 ********************************************************************/
	public static abstract class SingleTask extends GTask {

		private int count, countMax;

		public SingleTask(int countMax) {
			this.countMax = (countMax > 0) ? countMax : Integer.MAX_VALUE - 1;
		}

		@Override
		public final void countup() {
			if (count >= countMax) {
				setTimeout();
				return;
			}
			onCountup();
			count++;
		}

		protected abstract void onCountup();
	}

	/********************************************************************
	 * MultiTask
	 ********************************************************************/
	public static final class MultiTask extends GTask {

		private List<GTask> list;

		public MultiTask(GTask ... task) {
			list = new ArrayList<>(Arrays.asList(task));
		}

		public MultiTask add(GTask task) {
			list.add(task);
			return this;
		}

		@Override
		public void countup() {
			if (list.size() > 0 && list.get(0).isTimeout()) {
				list.remove(0);
			}

			if (list.isEmpty()) {
				setTimeout();
				return;
			}

			list.get(0).countup();
		}
	}
}
