package io.xrates.backend.datamodel.dao;

import io.xrates.backend.datamodel.beans.Conversion;
import io.xrates.backend.datamodel.beans.Service;

import java.util.List;

public interface ConversionDao extends Dao<Conversion>{
	public List<Conversion> findAllUnProcessed();
	public Conversion findLastClosingRate(Service service);
	public Conversion findLastClosingRate(Conversion currentRate);
	public List<Conversion> findAllNotifyAlert();
	public Conversion findNotifyAlert(Service service);
	public List<Conversion> todayNotified(Service service);
}
