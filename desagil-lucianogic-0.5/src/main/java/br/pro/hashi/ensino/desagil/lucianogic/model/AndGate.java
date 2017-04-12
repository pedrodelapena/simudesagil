package br.pro.hashi.ensino.desagil.lucianogic.model;

public class AndGate extends Gate {
	private NandGate nandLeft;
	private NandGate nandRight;

	public AndGate() {
		super(2);

		name = "AND";

		nandLeft = new NandGate();

		nandRight = new NandGate();
		nandRight.connect(nandLeft, 0);
		nandRight.connect(nandLeft, 1);
	}

	@Override
	public boolean read() {
		return nandRight.read();
	}

	@Override
	protected void doConnect(Emitter emitter, int index) {
		nandLeft.connect(emitter, index);
	}
}
