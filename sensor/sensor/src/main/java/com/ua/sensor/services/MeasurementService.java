package com.ua.sensor.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ua.sensor.models.Measurement;
import com.ua.sensor.repositories.MeasurementRepository;

/**
 * @author Anton Muzhytskyi
 */

@Service
@Transactional(readOnly = true)
public class MeasurementService {
	
	private final MeasurementRepository measurementRepository;
	private final SensorService sensorService;
	
	@Autowired
	public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
		this.measurementRepository = measurementRepository;
		this.sensorService = sensorService;
	}
	
	public List<Measurement> findAll(){
		return measurementRepository.findAll();
	}
	
	@Transactional
	public void addMeasurement(Measurement measurement) {
		enrichMeasurement(measurement);
		measurementRepository.save(measurement);
	}
	
	public void enrichMeasurement(Measurement measurement) {
		measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
		measurement.setMeasurementDateTime(LocalDateTime.now());
	}
	

}
