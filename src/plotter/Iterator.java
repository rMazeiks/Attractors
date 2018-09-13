package plotter;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import transformation.Transformation;

import java.util.List;

public class Iterator extends Task<Image> {
	private LongProperty samples;
	private LongProperty resetInterval;
	private boolean resetPlotter;
	private List<Transformation> transformations;
	private Plotter plotter;

	public Iterator(List<Transformation> t, Plotter p, long samples, long resetInterval, boolean resetPlotter) {
		super();
		plotter = p;
		transformations = t;
		this.samples = new SimpleLongProperty(samples);
		this.resetInterval = new SimpleLongProperty(resetInterval);
		this.resetPlotter = resetPlotter;
	}

	public Iterator(List<Transformation> transformations, Plotter plotter, long samples, long resetInterval) {
		this(transformations, plotter, samples, resetInterval, true);
	}

	public long getSamples() {
		return samples.get();
	}

	public void setSamples(long samples) {
		this.samples.set(samples);
	}

	public LongProperty samplesProperty() {
		return samples;
	}

	public long getResetInterval() {
		return resetInterval.get();
	}

	public void setResetInterval(long resetInterval) {
		this.resetInterval.set(resetInterval);
	}

	public LongProperty resetIntervalProperty() {
		return resetInterval;
	}

	private double[] randPoint(int i) {
		double[] ans = new double[i];
		for (int j = 0; j < ans.length; j++) {
			ans[j] = Math.random() * 2 - 1;
		}
		return ans;
	}


	@Override
	protected Image call() {
		if(resetPlotter) plotter.reset();

		double[] point = null; // it will be initialized in the first iteration, but my IDE was complaining
		for (long i = 0; i < samples.get(); i++) {
			if (isCancelled()) {
				return null;
			}

			this.updateProgress(i, samples.get()-1);


			if (i % resetInterval.get() == 0) {
				point = randPoint(4);
			}

			for (Transformation transformation : transformations) {
				point = transformation.transform(point);
			}
			plotter.plot(point);
		}

		return plotter.getOutput();
	}

	public void threadRun() {
		Thread th = new Thread(this);
		th.setDaemon(true);
		th.start();
	}
}
