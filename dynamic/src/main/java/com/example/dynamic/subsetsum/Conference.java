package com.example.dynamic.subsetsum;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Conference {

	/**
	 * 
	 * PROBLEM -
	 * You are planning a big programming conference and have received many proposals 
	 * which have passed the initial screen process but you're having trouble fitting 
	 * them into the time constraints of the day -- there are so many possibilities! 
	 * So you write a program to do it for you.
	 * 
	 * The conference has multiple tracks each of which has a morning and afternoon session.
	 * Each session contains multiple talks. 
	 * Morning sessions begin at 9am and must finish by 12 noon, for lunch. 
	 * Afternoon sessions begin at 1pm and must finish in time for the networking event. 
	 * The networking event can start no earlier than 4:00 and no later than 5:00. 
	 * No talk title has numbers in it. 
	 * All talk lengths are either in minutes (not hours) or lightning (5 minutes). 
	 * Presenters will be very punctual; there needs to be no gap between sessions.
	 * 
	 * 
	 * Assumptions
	 * All seminars are in multiples of 5 minutes
	 * 
	 */
	private final Schedule schedule = new Schedule();


	/**
	 * 
	 * We use s slightly modified version of subset of sum to solve this problem, 
	 * We calculate the subset sum matrix for given max sum S. If none of the 
	 * elements in the set add up to S, we look at S-1 and so on, until we find
	 * the solution
	 * 
	 * 
	 * SS(S,i) -> {
	 * 				false   if s<0 or i >= set.length
	 *              true    if s==0 
	 * 				SS(S, i+1) || SS(S - time(i), i + 1 )
	 *            }
	 * 
	 * @param seminars
	 */

	private boolean[][] calculate(int timeInMins, List<Seminar> seminars) {
		int lightnings = timeInMins/5;
		boolean[][] ssMemo = new boolean [lightnings+1][seminars.size()];
		for(int time = 0; time < lightnings; time++) {
			for(int i = seminars.size() -1; i >= 0; i--) {
				int remTime = time - seminars.get(i).getLightning();
				if(remTime == 0) {
					ssMemo[time][i] = true;
					continue;
				}
				if(i+1 >= seminars.size()) {
					ssMemo[time][i] = false;
					continue;
				}
				if(remTime < 0) {
					ssMemo[time][i] = ssMemo[time][i+1];
					continue;
				}

				ssMemo[time][i] = ssMemo[time][i+1] || ssMemo[remTime][i+1];
			}
		}
		return ssMemo;
	}


	public List<Seminar> planSession(int timeInMins, List<Seminar> seminars) {
		boolean[][] ssmemo = calculate(timeInMins, seminars);
		int lightnings = timeInMins/5;
		while(!ssmemo[lightnings][0]) {
			lightnings--;	
		}
		int i = 0;
		List<Seminar> solution = new ArrayList<>();
		while(lightnings > 0) {
			while(ssmemo[lightnings][i]) {
				i++;
				if(i == seminars.size()) {
					break;
				}
			}
			if(i==0) {
				solution.add(seminars.get(i));
				lightnings = lightnings - seminars.get(i).getLightning();
			} else {
				solution.add(seminars.get(i-1));
				lightnings = lightnings - seminars.get(i-1).getLightning();
			}
		}
		return solution;
	}

	public Schedule scheduleConferrence(List<Seminar> allSeminars) {
		while(allSeminars.size() > 0) {
			List<Seminar> eSession = null;
			List<Seminar> mSession = planSession(180, allSeminars);
			allSeminars.removeAll(mSession);
			if(allSeminars.size() > 0) {
				eSession = planSession(240, allSeminars);
				allSeminars.removeAll(eSession);
			}
			schedule.scheduleTrack(mSession, eSession);
		}
		return schedule;
	}

	public static void main(String[] args) {
		
		List<String> seminars =Arrays.asList("Writing Fast Tests Against Enterprise Rails 60min", 
				"Overdoing it in Python 45min", 
				"Lua for the Masses 30min", 
				"Ruby Errors from Mismatched Gem Versions 45min",
				"Common Ruby Errors 45min",
				"Rails for Python Developers lightning",
				"Communicating Over Distance 60min",
				"Accounting-Driven Development 45min",
				"Woah 30min",
				"Sit Down and Write 30min",
				"Pair Programming vs Noise 45min",
				"Rails Magic 60min",
				"Ruby on Rails: Why We Should Move On 60min",
				"Clojure Ate Scala (on my project) 45min",
				"Programming in the Boondocks of Seattle 30min",
				"Ruby vs. Clojure for Back-End Development 30min",
				"Ruby on Rails Legacy App Maintenance 60min",
				"A World Without HackerNews 30min",
				"User Interface CSS in Rails Apps 30min");
		
		List<Seminar> allSeminars = seminars.stream().map(Seminar:: new).collect(Collectors.toList());
		Conference conf = new Conference();
		System.out.println(conf.scheduleConferrence(allSeminars));
	}

}
