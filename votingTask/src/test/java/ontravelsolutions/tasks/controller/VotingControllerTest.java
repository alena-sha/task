package ontravelsolutions.tasks.controller;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import ontravelsolutions.tasks.model.Voting;
import ontravelsolutions.tasks.model.VotingOption;
import ontravelsolutions.tasks.repo.VotingOptionRepository;
import ontravelsolutions.tasks.repo.VotingRepository;


@WebAppConfiguration() 
@RunWith(SpringJUnit4ClassRunner.class)
public class VotingControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Mock
	private VotingRepository votingRepoMock;
	@Mock
	private VotingOptionRepository optionRepoMock;
	@InjectMocks
	private VotingController votingController;
	  
	private MockMvc mockMvc;	
	
	@Before
	public void setUp() throws Exception {
	      mockMvc = standaloneSetup(new VotingController(votingRepoMock,optionRepoMock))
	    		         .build();
	 
	}
	  
	  @Test
	public void testGetOpenVotings() throws Exception{
		
		 List<Voting> votingList=initVotingsList();   
		 when(votingRepoMock.findAll()).thenReturn(votingList);
		
	        mockMvc.perform(get("/voting/open"))
			        .andExpect(status().isOk())
		            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
		           	.andDo(print())
    		   		.andExpect(jsonPath("$", hasSize(2)))
	                .andExpect(jsonPath("$[0].title", is("do you like Java?")))
	                .andExpect(jsonPath("$[0].isClosed", is(false)))
	                .andExpect(jsonPath("$[0].list", hasSize(2)))
	                .andExpect(jsonPath("$[0].list[0].title", is("yes")))
	                .andExpect(jsonPath("$[0].list[1].title", is("no")))
	                .andExpect(jsonPath("$[1].title", is("how many hours do you prefer to slip?")))
	                .andExpect(jsonPath("$[1].isClosed", is(false)))
	                .andExpect(jsonPath("$[1].list", hasSize(3)))
	                .andExpect(jsonPath("$[1].list[0].title", is("5 hours")))
	                .andExpect(jsonPath("$[1].list[1].title", is("8 hours")))
	                .andExpect(jsonPath("$[1].list[2].title", is("more than 8 hours")));
	        verify(votingRepoMock, times(1)).findAll();
	        verifyNoMoreInteractions(votingRepoMock);
	}
	  public void testGetVotings() throws Exception{
			
			 List<Voting> votingList=initVotingsList();   
			 when(votingRepoMock.findAll()).thenReturn(votingList);
			
		        mockMvc.perform(get("/voting"))
				        .andExpect(status().isOk())
			            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
			           	.andDo(print())
	    		   		.andExpect(jsonPath("$", hasSize(3)))
		                .andExpect(jsonPath("$[0].title", is("do you like Java?")))
		                .andExpect(jsonPath("$[0].isClosed", is(false)))
		                .andExpect(jsonPath("$[0].list", hasSize(2)))
		                .andExpect(jsonPath("$[0].list[0].title", is("yes")))
		                .andExpect(jsonPath("$[0].list[1].title", is("no")))
		                .andExpect(jsonPath("$[1].title", is("how many hours do you prefer to slip?")))
		                .andExpect(jsonPath("$[1].isClosed", is(false)))
		                .andExpect(jsonPath("$[1].list", hasSize(3)))
		                .andExpect(jsonPath("$[1].list[0].title", is("5 hours")))
		                .andExpect(jsonPath("$[1].list[1].title", is("8 hours")))
		                .andExpect(jsonPath("$[1].list[2].title", is("more than 8 hours")))
				        .andExpect(jsonPath("$[2].title", is("closed voting")))
		                .andExpect(jsonPath("$[2].isClosed", is(true)))
		                .andExpect(jsonPath("$[2].list", hasSize(2)))
		                .andExpect(jsonPath("$[2].list[0].title", is("-")))
		                .andExpect(jsonPath("$[2].list[1].title", is("-")));
		              
		        verify(votingRepoMock, times(1)).findAll();
		        verifyNoMoreInteractions(votingRepoMock);
		}

	  @Test
		public void testGetVoting() throws Exception{
			Voting voting=initVoting();
		    when(votingRepoMock.findOne(voting.getId())).thenReturn(voting);
			
		        mockMvc.perform(get("/voting/{id}",voting.getId()))
		          		.andDo(print())
		          		.andExpect(status().isOk())
		                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
		                .andExpect(jsonPath("$.title", is("do you like Java?")))
		                .andExpect(jsonPath("$.isClosed", is(false)))
		                .andExpect(jsonPath("$.list", hasSize(2)))
		                .andExpect(jsonPath("$.list[0].title", is("yes")))
		                .andExpect(jsonPath("$.list[1].title", is("no")));
		        verify(votingRepoMock, times(1)).findOne(voting.getId());
		        verifyNoMoreInteractions(votingRepoMock);
		}
	  
	  @Test
	    public void testCreateVoting() throws Exception {

			Voting voting=initVoting();
			when(votingRepoMock.save(voting)).thenReturn(voting);
			mockMvc.perform(post("/voting").contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(json(voting))
			).andDo(print())
			.andExpect(status().isCreated());
			ArgumentCaptor<Voting> votingCaptor = ArgumentCaptor.forClass(Voting.class);
			verify(votingRepoMock, times(1)).save(votingCaptor.capture());
			verifyNoMoreInteractions(votingRepoMock);
	
			Voting votingArgument = votingCaptor.getValue();
			assertThat(votingArgument.getId(), is(voting.getId()));
			assertThat(votingArgument.getTitle(), is(voting.getTitle()));
			assertThat(votingArgument.getIsClosed(), is(voting.getIsClosed()));
		  }
	  
	  @Test
	    public void testVote() throws Exception {
			Voting voting = new Voting("do you like Java?", false);
			VotingOption option=new VotingOption(1,"yes", 0, voting);
			when(optionRepoMock.findOne(option.getId())).thenReturn(option);
			when(optionRepoMock.save(option)).thenReturn(option);
			mockMvc.perform(put("/voting/option/"+option.getId()))
					.andDo(print())
	          		.andExpect(status().isOk());              
			verify(optionRepoMock, times(1)).findOne(option.getId());
			verify(optionRepoMock, times(1)).save(option);
			assertEquals(option.getNumberOfVotes(),1);
	  }
	  
	  
	  @Test
		public void testShowStatistics() throws Exception{
			
		  	Voting voting=initVoting();
		    when(votingRepoMock.findOne(voting.getId())).thenReturn(voting);
			
		        mockMvc.perform(get("/voting/{id}/stat",voting.getId()))
		          		.andDo(print())
		          		.andExpect(status().isOk())
		                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
		                .andExpect(jsonPath("$.no", is(8)))
		                .andExpect(jsonPath("$.yes", is(5)));
		               	
		        verify(votingRepoMock, times(1)).findOne(voting.getId());
		        verifyNoMoreInteractions(votingRepoMock);
		}
	  
	  @Test
		public void testCloseVoting() throws Exception{
			Voting voting=initVoting();			  
			when(votingRepoMock.findOne(voting.getId())).thenReturn(voting);
		    when(votingRepoMock.save(voting)).thenReturn(voting);
			
		        mockMvc.perform(put("/voting/{id}",voting.getId()))
		          		.andDo(print())
		          		.andExpect(status().isOk());
		               	
		        verify(votingRepoMock, times(1)).findOne(voting.getId());
		        verify(votingRepoMock, times(1)).save(voting);
			    verifyNoMoreInteractions(votingRepoMock);
			    assertEquals(voting.getIsClosed(),true);
		}
	  	  
	  public List<Voting> initVotingsList(){
		  	Voting voting = new Voting("do you like Java?",false);
		    List<VotingOption> list=Arrays.asList(new VotingOption(1,"yes", 0, voting),new VotingOption(2,"no",0,voting));
		    voting.setList(list);
		    Voting voting2 = new Voting("how many hours do you prefer to slip?",false);
		    List<VotingOption> list2=Arrays.asList(new VotingOption(3,"5 hours", 0, voting2),new VotingOption(4,"8 hours",0,voting2), new VotingOption(5,"more than 8 hours",0,voting2));
		    voting2.setList(list2);
		    Voting voting3 = new Voting("closed voting",true);
		    List<VotingOption> list3=Arrays.asList(new VotingOption(5,"-", 0, voting3),new VotingOption(6,"-",0,voting3));
		    voting3.setList(list3);
		    List<Voting> votingList=Arrays.asList(voting,voting2,voting3);
		    return votingList;
	  }
	  
	  public Voting initVoting(){
		  	Voting voting = new Voting("do you like Java?",false);
		    List<VotingOption> list=Arrays.asList(new VotingOption(1,"yes", 5, voting),new VotingOption(2,"no",8,voting));
		    voting.setList(list);
		    return voting;
	  }
	  
	  public String json(Object o) throws IOException {
	        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
	        MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
	        converter.write(
	                o, APPLICATION_JSON_UTF8, mockHttpOutputMessage);	 
	        return mockHttpOutputMessage.getBodyAsString();
	    }
}

