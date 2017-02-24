package net.kleopi.Engine.StatusManagement;

public class Status {
	public enum EngineStatus {
		BOOTING, LOGGING_IN, UNDEFINED
	}

	public enum NetworkStatus {
		CONNECTED, DISCONNECTED, CONNECTING, UNDEFINED,
	}

	private NetworkStatus netStat = NetworkStatus.UNDEFINED;
	private EngineStatus engStat = EngineStatus.UNDEFINED;

	public EngineStatus getEngStat() {
		return engStat;
	}

	public NetworkStatus getNetStat() {
		return netStat;
	}

	public void setEngStat(EngineStatus engStat) {
		this.engStat = engStat;
	}

	public void setNetStat(NetworkStatus netStat) {
		this.netStat = netStat;
	}

}
