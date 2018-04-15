package com.isa.instaticketapi.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isa.instaticketapi.domain.Bid;
import com.isa.instaticketapi.domain.Item;
import com.isa.instaticketapi.domain.Offer;
import com.isa.instaticketapi.domain.User;
import com.isa.instaticketapi.repository.BidRepository;
import com.isa.instaticketapi.repository.ItemRepository;
import com.isa.instaticketapi.repository.OfferRepository;
import com.isa.instaticketapi.repository.OfferRequestRepository;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.service.dto.BidDTO;
import com.isa.instaticketapi.service.dto.ChangeItemDTO;
import com.isa.instaticketapi.service.dto.ChangeOfferDTO;
import com.isa.instaticketapi.service.dto.ItemDTO;
import com.isa.instaticketapi.service.dto.OfferDTO;

@Service
@Transactional
public class FanZoneService {
	
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private OfferRequestRepository offerRequestRepository;
	
	@Autowired
	private BidRepository bidRepository;
	
	
	
	public List<Item> getItems() {
				
		return itemRepository.findAll();
	}

	
	
	public Item addNewItem(ItemDTO itemDTO) throws SQLException{
		
		Item item = new Item();
		
		/*
		User logged = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUsername).get();
		System.out.println("ULOGOVAN " + logged.getUsername()); */
		
		
		item.setCreatedBy("User");
		
		item.setName(itemDTO.getName());
		item.setDescription(itemDTO.getDescription());
		item.setImage(itemDTO.getImage());
		item.setPrice(itemDTO.getPrice());
		
		itemRepository.save(item);
		
		return item;	
		
	}
	
	
	
	public Item deleteItem(Long id) {
		
		Item item = itemRepository.findOneById(id);
		
		if(item == null) {
			return null;
		}
		
		itemRepository.delete(item);
		
		return item;
	}
	
	
	public List<Item> editItem(ChangeItemDTO itemDTO, Long id) throws SQLException {
		
		Item item = itemRepository.findOneById(id);
		
		if(item != null) {
			
			item.setName(itemDTO.getName());
			item.setDescription(itemDTO.getDescription());
			item.setImage(itemDTO.getImage());
			item.setPrice(itemDTO.getPrice());
			
			itemRepository.save(item);
		}
		
		
		
		
		return itemRepository.findAll();
		
	}
	
	
	public Item getItemById(Long id) throws SQLException {
		
		Item item = itemRepository.findOneById(id);
		return item;
	}
	
	
	public List<Offer> getOffers() {
		
		return offerRepository.findAll();
	}
	
	
	public Offer addNewOffer(OfferDTO offerDTO) throws SQLException{
		
		Offer offer = new Offer();
		
				
		offer.setCreatedBy("User");
		
		offer.setName(offerDTO.getName());
		offer.setDescription(offerDTO.getDescription());
		offer.setImage(offerDTO.getImage());
		offer.setStartPrice(offerDTO.getStartPrice());
		offer.setBestPrice(offerDTO.getStartPrice());
		offer.setEndDate(offerDTO.getEndDate());
		
		
		offer.setAccepted(false);
		
		offerRepository.save(offer);
				
		return offer;	
		
	}
	
	
	public Offer deleteOffer(Long id) {
		
		Offer offer = offerRepository.findOneById(id);
		
		if(offer == null) {
			return null;
		}
		
		offerRepository.delete(offer);
		
		return offer;
	}
	
	
	
	public Offer editOffer(ChangeOfferDTO offerDTO) throws SQLException {
		
		Offer offer = offerRepository.findOneById(offerDTO.getId());
		
		if(offer == null) {
			return null;
		}
		
		
		offer.setName(offerDTO.getName());
		offer.setDescription(offerDTO.getDescription());
		offer.setImage(offerDTO.getImage());
		
		offerRepository.save(offer);
		
		return offer;
		
	}
	
	
	public Offer acceptOfferRequest(Long id) throws SQLException {
		
		Offer offer = offerRepository.findOneById(id);	
		offer.setAccepted(true);

		offerRepository.save(offer);
		
		return offer;
		
	}
	
	
	public Bid addNewBid(BidDTO bidDTO, Long id) {
		
		Offer offer = offerRepository.findOne(id);
		
		User user = userRepository.findOneByUsername("sansajn").get();

		
			
		Bid bid = new Bid(user, offer, bidDTO.getSum());
		bid.setCreatedBy(user.getUsername());
		bidRepository.save(bid);
		
		System.out.println("UNETA " + bidDTO.getSum() + " NAJBOLJA " + offer.getBestPrice()) ;
		
		
		if(Integer.parseInt(bidDTO.getSum()) > Integer.parseInt(offer.getBestPrice())){
			
			offer.setBestPrice(bidDTO.getSum());
			offerRepository.save(offer);
		}
		
		return bid;
	}
	
	
}
