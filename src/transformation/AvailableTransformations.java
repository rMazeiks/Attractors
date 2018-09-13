package transformation;

public enum AvailableTransformations {
	SIMPLE(SimpleTrans.transformationName(), SimpleTrans::new), // todo someone please tell me there's a better way to do this.
	FOUR(FourDimWeird.transformationName(), FourDimWeird::new),
	EXP(FourDimWeird2.transformationName(), FourDimWeird2::new),
	FLOWER(Flower.transformationName(), Flower::new),
	ROTATE(Rotate.transformationName(), Rotate::new);


	String name;
	TransformationInitializer initializer;

	AvailableTransformations(String name, TransformationInitializer transformationInitializer) {
		this.name = name;
		initializer = transformationInitializer;
	}

	public String getName() {
		return name;
	}

	public TransformationInitializer getInitializer() {
		return initializer;
	}
}
