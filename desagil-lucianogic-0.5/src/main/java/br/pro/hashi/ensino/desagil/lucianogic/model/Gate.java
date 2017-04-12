package br.pro.hashi.ensino.desagil.lucianogic.model;

public abstract class Gate implements Receiver, Emitter {
	private int size;
	protected String name;

	protected Gate(int size) {
		this.size = size;
	}

	@Override
	public void connect(Emitter emitter, int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		doConnect(emitter, index);
	}

	protected abstract void doConnect(Emitter emitter, int index);

	public String toString() {
		return name;
	}

	public int getSize() {
		return size;
	}
}
