package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "schedule_id")
	private Long scheduleId;
	@Column(name = "departure_time")
	private LocalTime departureTime;
	@Column(name = "landing_time")
	private LocalTime landingTime;
	@Column(name = "stop")
	private String stop;
}
