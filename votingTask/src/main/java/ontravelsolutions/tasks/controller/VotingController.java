package ontravelsolutions.tasks.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ontravelsolutions.tasks.model.Voting;
import ontravelsolutions.tasks.model.VotingOption;
import ontravelsolutions.tasks.repo.VotingOptionRepository;
import ontravelsolutions.tasks.repo.VotingRepository;

@RestController
@RequestMapping("/voting")
public class VotingController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private VotingRepository votingRepo;

	private VotingOptionRepository optionRepo;

	@Autowired
	public VotingController(VotingRepository votingRepo, VotingOptionRepository optionRepo) {
		this.votingRepo = votingRepo;
		this.optionRepo = optionRepo;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Voting> getVotings() { // list of all voting themes
		logger.info("in getVotings");
		return (List<Voting>)votingRepo.findAll();
	}
	
	@RequestMapping(value = "/open",method = RequestMethod.GET)
	public List<Voting> getOpenVotings() { // list of open voting themes(for client who wants to choose theme for voting )
		logger.info("in getOpenVotings");
		List<Voting> list = (List<Voting>) votingRepo.findAll();
		return list.stream().filter(voting -> voting.getIsClosed().equals(false)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Voting getVoting(@PathVariable(value = "id") String id) { // client've chosen a voting theme
		logger.info("in getVoting");//and wants to see voting options
		return votingRepo.findOne(id);
	}

	@RequestMapping(value = "/{id}/stat", method = RequestMethod.GET)
	public Map<String, Integer> showStatistics(@PathVariable(value = "id") String id) { //show statistics for chosen voting (option,numberOfVotes)
		logger.info("in show statistics"); 
		Voting voting = votingRepo.findOne(id);
		List<VotingOption> options = voting.getList();
		Map<String, Integer> map = new HashMap<>();
		options.stream().forEach(option -> map.put(option.getTitle(), option.getNumberOfVotes()));
		return map;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createVoting(@RequestBody Voting voting) { // adding a new theme for voting with
															// options
		logger.info("in createVoting");
		votingRepo.save(voting);
	}

	@RequestMapping(value = "/option/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void vote(@PathVariable(value = "id") int id) { // client choose an option to vote
		VotingOption option = optionRepo.findOne(id);
		if(option!=null){
			option.vote();
			optionRepo.save(option);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void closeVoting(@PathVariable(value = "id") String id) { // close voting
		Voting voting = votingRepo.findOne(id);
		voting.setIsClosed(true);
		votingRepo.save(voting);
	}

}
