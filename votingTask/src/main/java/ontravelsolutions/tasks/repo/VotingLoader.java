package ontravelsolutions.tasks.repo;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ontravelsolutions.tasks.model.Voting;
import ontravelsolutions.tasks.model.VotingOption;

@Component
public class VotingLoader implements ApplicationListener<ContextRefreshedEvent> {

	private VotingRepository repo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public void setVotingRepository(VotingRepository repo) {
		this.repo = repo;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		Voting voting = new Voting("do you like Java?", false);
		List<VotingOption> list = Arrays.asList(new VotingOption("yes", 0, voting), new VotingOption("no", 0, voting));
		voting.setList(list);
		Voting v = repo.save(voting);
		logger.info("Saved  - id: " + v.getId());

		Voting voting2 = new Voting("how many hours do you prefer to slip?", false);
		List<VotingOption> list2 = Arrays.asList(new VotingOption("5 hours", 0, voting2),
				new VotingOption("8 hours", 0, voting2), new VotingOption("more than 8 hours", 0, voting2));
		voting2.setList(list2);
		Voting v2 = repo.save(voting2);
		logger.info("Saved  - id: " + v2.getId());

	}
}
