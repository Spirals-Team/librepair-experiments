package com.isa.instaticketapi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isa.instaticketapi.domain.Place;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.domain.VoteForPlace;
import com.isa.instaticketapi.repository.PlaceRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.repository.VoteForPlaceRepository;
import com.isa.instaticketapi.security.SecurityUtils;
import com.isa.instaticketapi.service.dto.places.VoteForPlaceDTO;

/**
 * services for vote for place
 * 
 * @author Milica
 *
 */
@Service
@Transactional
public class VoteForPlaceService {

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private VoteForPlaceRepository voteForPlaceRepository;

	@Autowired
	private UserRepository userRepository;

	public void createVote(VoteForPlaceDTO voteForPlaceDTO) {
		VoteForPlace vote = new VoteForPlace();
		Place place = placeRepository.findOneByName(voteForPlaceDTO.getPlace());

		vote.setVote(voteForPlaceDTO.getVote());
		vote.setPlace(place);
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		vote.setUser(logged);
		voteForPlaceRepository.save(vote);
		
		int voteSum = 0;
		ArrayList<VoteForPlace> votes = voteForPlaceRepository.findAllByPlace(place);
		for (int i = 0; i < votes.size(); i++) {
			voteSum += votes.get(i).getVote();
		}

		int vote1 = voteSum / votes.size();
		place.setVote(vote1);
		placeRepository.save(place);

	}

	public boolean checkIfHeVoted(Long id) {
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		Place place = placeRepository.findOneById(id);
		ArrayList<VoteForPlace> votes = voteForPlaceRepository.findAllByPlace(place);
		boolean isVote = false;
		for (int i = 0; i < votes.size(); i++) {
			if (votes.get(i).getUser().equals(logged)) {
				isVote = true;
				break;
			}
		}
		return isVote;
	}

}
