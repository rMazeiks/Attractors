package plotter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import transformation.Transformation;

import java.util.List;

public class Iterator extends Task<Image> {
	private LongProperty samples;
	private IntegerProperty resetInterval;
	private List<Transformation> transformations;
	private Plotter plotter;

	public Iterator(List<Transformation> t, Plotter p, long samples, int resetInterval) {
		super();
		plotter = p;
		transformations = t;
		this.samples = new SimpleLongProperty(samples);
		this.resetInterval = new SimpleIntegerProperty(resetInterval);
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

	public int getResetInterval() {
		return resetInterval.get();
	}

	public void setResetInterval(int resetInterval) {
		this.resetInterval.set(resetInterval);
	}

	public IntegerProperty resetIntervalProperty() {
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
		plotter.reset();
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
