package ontravelsolutions.tasks.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ontravelsolutions.tasks.model.Voting;
@Repository
public interface VotingRepository extends CrudRepository<Voting,String>{
	
}
