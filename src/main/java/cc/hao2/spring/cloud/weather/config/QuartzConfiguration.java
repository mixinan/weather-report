package cc.hao2.spring.cloud.weather.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cc.hao2.spring.cloud.weather.job.WeatherDataSyncJob;

@Configuration
public class QuartzConfiguration {
	
	private static final int TIME = 1800;

	@Bean
	public JobDetail weatherDataSyncJobJobDetail() {
		return JobBuilder.newJob(WeatherDataSyncJob.class)
				.withIdentity("weatherDataSyncJob")
				.storeDurably()
				.build();
	}
	
	@Bean
	public Trigger weatherDataSyncTrigger(){
		SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(TIME).repeatForever();
		
		return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobJobDetail())
				.withIdentity("weatherDataSyncTrigger")
				.withSchedule(schedBuilder).build();
	}
	
}
